package com.seancheer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seancheer.backupservice.BackupFactory;
import com.seancheer.backupservice.IBlogBackup;
import com.seancheer.dao.entity.Category2;
import org.apache.log4j.spi.ErrorCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.seancheer.common.BlogConstants;
import com.seancheer.common.BlogCode;
import com.seancheer.common.BaseOperation;
import com.seancheer.dao.entity.Passage;
import com.seancheer.dao.interfaces.Category2Dao;
import com.seancheer.dao.interfaces.BlogDao;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.service.interfaces.IEditService;
import com.seancheer.utils.CookieHelper;

/**
 * 编辑博客的逻辑，包括增加，修改，删除，查询等
 *
 * @author seancheer
 * @date 2018年4月11日
 */
public class EditServiceImpl extends BaseOperation implements IEditService {

    private static final Logger logger = LoggerFactory.getLogger(EditServiceImpl.class);

    @Autowired
    private BlogDao passageDao;

    @Autowired
    private Category2Dao category2Dao;

    //当前系统生效的备份策略
    private IBlogBackup blogBackup = BackupFactory.getCurrentBackup();

    @Override
    public JSONObject createNewBlog(HttpServletRequest request, HttpServletResponse response, Map<String, String> postForm) {
        if (null == postForm) {
            return BlogCode.PARAMETER_ERROR.toJson();
        }

        if (!checkBlogData(postForm)) {
            return BlogCode.PARAMETER_ERROR.toJson();
        }

        try {
            return createNewBlogInternal(postForm, request, response);
        } catch (BlogBaseException e) {
            return BlogCode.SERVER_BUSY.toJson();
        }
    }

    /**
     * 将用户上传的表单数据存储在数据库中
     *
     * @param blogData
     * @param request
     * @param response
     * @return
     */
    @Transactional(rollbackFor = BlogBaseException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    private JSONObject createNewBlogInternal(Map<String, String> blogData, HttpServletRequest request,
                                             HttpServletResponse response) throws BlogBaseException {
        String blogTitle = blogData.get(BlogConstants.KEY_BLOG_TITLE);
        String blogContent = blogData.get(BlogConstants.KEY_BLOG_CONTENT);
        Byte category1 = Byte.parseByte(blogData.get(BlogConstants.KEY_BLOG_CATEGORY_0));
        Byte category2 = Byte.parseByte(blogData.get(BlogConstants.KEY_BLOG_CATEGORY_1));
        Passage passage = new Passage();
        passage.setTitle(blogTitle);
        passage.setContent(blogContent);
        passage.setCategory1Id(category1);
        passage.setCategory2Id(category2);
        Cookie cookie = CookieHelper.getCookieByKey(request.getCookies(), "userId");
        if (null == cookie) {
            logger.error("Cookie is null !");
            return BlogCode.PARAMETER_ERROR.toJson();
        }
        passage.setUserId(Integer.valueOf(cookie.getValue()));

        try {
            passageDao.insertRecord(passage);
        } catch (BlogBaseException e) {
            logger.error("Saving blog failed!", e);
            return BlogCode.SERVER_BUSY.toJson();
        }

        if (!blogBackup.backupImmediatly(passage)) {
            logger.error("Backuping the passage failed! title:" + passage.getTitle());
            throw new BlogBaseException();
        }

        JSONObject result = BlogCode.SUCCESS.toJson();
        result.put(BlogConstants.HREF, "/blogList");
        return result;
    }

    @Override
    /**
     * 通过博客id删除博客
     * @param request
     * @param response
     * @param blogId
     * @param fakeDelete 是否假删
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public JSONObject deleteBlog(HttpServletRequest request, HttpServletResponse response, Integer blogId, boolean fakeDelete) {
        try {
            Passage passage = passageDao.queryRecordById(blogId);
            if (null == passage) {
                return BlogCode.NOT_FOUND.toJson();
            }

            if (fakeDelete) {
                passageDao.deleteRecordFake(passage);
            } else {
                passageDao.deleteRecord(passage);
            }

            JSONObject result = BlogCode.SUCCESS.toJson();
            result.put(BlogConstants.HREF, "/blogList");
            return result;
        } catch (BlogBaseException e) {
            logger.error("Operation to database failed! blogId:{} fakeDelete:{}", blogId, fakeDelete);
            logger.error("Operation to datebase failed!", e);
            return BlogCode.INTERNAL_ERROR.toJson();
        }
    }

    /**
     * 检查参数是否合法
     *
     * @param blogData
     * @return
     */
    private boolean checkBlogData(Map<String, String> blogData) {
        String blogTitle, blogContent, select1, select2;

        try {
            blogTitle = blogData.get(BlogConstants.KEY_BLOG_TITLE);
            blogContent = blogData.get(BlogConstants.KEY_BLOG_CONTENT);
            select1 = blogData.get(BlogConstants.KEY_BLOG_CATEGORY_0);
            select2 = blogData.get(BlogConstants.KEY_BLOG_CATEGORY_1);
        } catch (JSONException e) {
            // 当某个key不在jsonObject中的时候，会抛出JSONException，而不是返回null
            logger.error(e.getMessage());
            return false;
        }

        if (StringUtils.isEmpty(blogTitle) || StringUtils.isEmpty(blogContent) || StringUtils.isEmpty(select1)
                || StringUtils.isEmpty(select2)) {
            logger.error("Invalid blogTitle or blogContent or select0 or select1 !");
            return false;
        }

        if (blogTitle.length() >= BlogConstants.MAX_BLOGTITLE_LENGTH) {
            logger.debug("Blog title is too long!");
            return false;
        }

        if (blogContent.length() <= MIN_BLOG_CONTENT_LENGTH) {
            logger.error("blogContent is too short! at least:{}", MIN_BLOG_CONTENT_LENGTH);
            return false;
        }

        Integer category1 = null;
        Integer category2 = null;
        try {
            category1 = Integer.parseInt(select1);
            category2 = Integer.parseInt(select2);
        } catch (NumberFormatException e) {
            logger.error("category id is invalid!");
            return false;
        }

        try {
            List<Category2> category2List = category2Dao.queryRecById(category2);
            if (category2List.isEmpty()) {
                logger.info("Can not find category2 with id:{}", category2);
                return false;
            }

            String parentId = String.valueOf(category2List.get(0).getParentId().getId());
            if (Integer.parseInt(parentId) != category1) {
                logger.debug("CategoryId is invalid! Not match to parentId:" + parentId);
                return false;
            }
        } catch (BlogBaseException e) {
            logger.info("Invalid category2 id! category2Id:{}", category2);
            return false;
        }

        return true;
    }

    /**
     * 编辑blog的页面
     */
    @Override
    public ModelAndView editBlog(HttpServletResponse response, HttpSession session, Integer blogId) {
        Passage passage = null;

        try {
            passage = passageDao.queryRecordById(blogId);
        } catch (BlogBaseException e) {
            logger.error("Query passage failed! blogId:" + blogId);
            return redirect500View();
        }

        if (null == passage) {
            logger.debug("Can not find the blog! blogId:" + blogId);
            return redirect404View();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("curBlog", passage);
        StringBuilder curBlogCategoryIds = new StringBuilder();
        curBlogCategoryIds.append(passage.getCategory1Id()).append(",").append(passage.getCategory2Id());
        // 放入当前blog的category ids
        modelAndView.addObject("curBlogCategoryIds", curBlogCategoryIds.toString());
        return modelAndView;
    }

    /**
     * 负责更新blog的接口
     *
     * @param request
     * @param response
     * @param blogId
     * @return
     */
    @Override
    public JSONObject updateBlog(HttpServletRequest request, HttpServletResponse response, Integer blogId, Map<String, String> postForm) {
        if (null == postForm) {
            return BlogCode.PARAMETER_ERROR.toJson();
        }

        if (!checkBlogData(postForm)) {
            return BlogCode.PARAMETER_ERROR.toJson();
        }

        try {
            return updateBlogInternal(postForm, request, blogId);
        } catch (BlogBaseException e) {
            return BlogCode.SERVER_BUSY.toJson();
        }
    }

    /**
     * 真正用来更新blog的方法 1 通过blogid查询数据库中的信息 2 检查相关的user信息，cookie中的用户是否有权限进行编辑等。。 3
     *
     * @param blogData
     * @param request
     * @param blogId
     * @return
     */
    @Transactional(rollbackFor = BlogBaseException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    private JSONObject updateBlogInternal(Map<String, String> blogData, HttpServletRequest request, Integer blogId) throws BlogBaseException {
        Passage passage = null;
        try {
            passage = passageDao.queryRecordById(blogId);
            if (null == passage) {
                logger.debug("Can not find this passage! passagedId:" + blogId);
                return BlogCode.NOT_FOUND.toJson();
            }
            Cookie cookie = CookieHelper.getCookieByKey(request.getCookies(), "userId");
            // 以后需要增加对用户acl的控制
            if (null == cookie || !String.valueOf(passage.getUserId()).equals(cookie.getValue())) {
                logger.info("Can not find cookie info or you do not have authorization to this passage!");
                return BlogCode.UNAUTHORIZED.toJson();
            }
        } catch (BlogBaseException e) {
            logger.info("Querying passage failed! blogId:" + blogId);
            return BlogCode.PARAMETER_ERROR.toJson();
        }

        String blogTitle = blogData.get(BlogConstants.KEY_BLOG_TITLE);
        String blogContent = blogData.get(BlogConstants.KEY_BLOG_CONTENT);
        Byte category1 = Byte.parseByte(blogData.get(BlogConstants.KEY_BLOG_CATEGORY_0));
        Byte category2 = Byte.parseByte(blogData.get(BlogConstants.KEY_BLOG_CATEGORY_1));

        passage.setTitle(blogTitle);
        passage.setContent(blogContent);
        passage.setCategory1Id(category1);
        passage.setCategory2Id(category2);

        try {
            passageDao.updateRecord(passage);
        } catch (BlogBaseException e) {
            logger.error("Updating blog failed!", e);
            return BlogCode.SERVER_BUSY.toJson();
        }


        if (!blogBackup.backupImmediatly(passage)) {
            logger.error("Backuping the passage failed! title:" + passage.getTitle());
            throw new BlogBaseException();
        }

        JSONObject result = BlogCode.SUCCESS.toJson();
        // 跳转到博客详情页
        result.put(BlogConstants.HREF, "/blog?blogId=" + passage.getId());
        return result;
    }
}
