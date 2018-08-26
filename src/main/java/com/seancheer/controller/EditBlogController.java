package com.seancheer.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seancheer.common.BaseOperation;
import com.seancheer.common.BlogCode;
import com.seancheer.common.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.seancheer.dao.entity.BaseCategory;
import com.seancheer.dao.entity.Category1;
import com.seancheer.dao.interfaces.Category1Dao;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.service.interfaces.IEditService;

/**
 * 编辑中心相关的操作，负责修改，删除，创建功能，
 * 查询功能，@see BlogController
 *
 * @author seancheer
 * @date 2018年3月2日
 */
@Controller
public class EditBlogController extends BaseOperation {

    private static final Logger logger = LoggerFactory.getLogger(EditBlogController.class);

    @Autowired
    private IEditService editService;

    @Autowired
    private Category1Dao category1Dao;

    /**
     * 后台管理员界面
     *
     * @param key
     */
    @RequestMapping(value = UrlConstants.GOD_CENTER, method = RequestMethod.GET)
    public void godCenter() {
        return;

    }

    /**
     * 发表新博客界面
     *
     * @param key
     */
    @RequestMapping(value = UrlConstants.NEW_BLOG, method = RequestMethod.GET)
    public ModelAndView newBlog(HttpServletResponse response, HttpSession session) {
        List<Category1> allCategories = null;

        try {
            allCategories = category1Dao.queryAllRecord();
        } catch (BlogBaseException e) {
            logger.error("Query all categories failed!", e);
            return redirect500View();
        }

        ModelAndView view = new ModelAndView();
        view.addObject("allCategories", BaseCategory.convertToJson(allCategories));
        return view;
    }

    /**
     * 创建新的博客
     *
     * @param request  request
     * @param response response
     * @param session  session
     * @param postForm post的表单信息
     * @return 创建后的json
     */
    @RequestMapping(value = UrlConstants.CREATE_NEW_BLOG, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createNewBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                @RequestBody final Map<String,String> postForm) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        return editService.createNewBlog(request, response, postForm).toString();
    }

    /**
     * 编辑某个特定的博客
     *
     * @param response
     * @param session
     * @param blogId
     * @return
     */
    @RequestMapping(value = UrlConstants.EDIT_BLOG, method = RequestMethod.GET)
    public ModelAndView editBlog(HttpServletResponse response, HttpSession session,
                                 @RequestParam(value = "blogId", required = false) Integer blogId) {
        if (null == blogId || blogId < 0) {
            return redirect404View();
        }
        response.setCharacterEncoding("utf-8");
        return editService.editBlog(response, session, blogId);
    }


    /**
     * 删除对应的博客
     *
     * @param request
     * @param response
     * @param session
     * @param blogId
     * @return
     */
    @RequestMapping(value = UrlConstants.DELETE_BLOG, method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createNewBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                @RequestParam final String blogId, @RequestParam(required = false)final Integer fakeDelete) {
        if (StringUtils.isEmpty(blogId)) {
            return BlogCode.NOT_FOUND.toString();
        }

        Integer id = 0;
        try {
            id = Integer.parseInt(blogId);
            if (id < 0) {
                return BlogCode.NOT_FOUND.toString();
            }
        } catch (NumberFormatException e) {
            return BlogCode.NOT_FOUND.toString();
        }

        //解析是否需要假删，0为假删，其他值为真正删除
        boolean fake = true;
        if (null != fakeDelete)
        {
            fake = (fakeDelete == 0);
        }
        return editService.deleteBlog(request, response, id, fake).toString();
    }

    /**
     * 更新对应的博客
     *
     * @param request
     * @param response
     * @param session
     * @param blogId
     * @param postForm post的表单信息
     * @return
     */
    @RequestMapping(value = UrlConstants.UPDATE_BLOG, params = {"blogId"}, method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createNewBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                               final String blogId, @RequestBody Map<String,String> postForm) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
      //return editService.up(request, response).toString();
        if (StringUtils.isEmpty(blogId)) {
            return BlogCode.NOT_FOUND.toString();
        }

        Integer id = 0;
        try {
            id = Integer.parseInt(blogId);
            if (id < 0) {
                return BlogCode.NOT_FOUND.toString();
            }
        } catch (NumberFormatException e) {
            return BlogCode.NOT_FOUND.toString();
        }
        return editService.updateBlog(request, response, id, postForm).toString();
    }

}
