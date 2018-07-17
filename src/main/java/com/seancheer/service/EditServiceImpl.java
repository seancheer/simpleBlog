package com.seancheer.service;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.seancheer.common.BlogConstants;
import com.seancheer.common.ErrorCode;
import com.seancheer.controller.BaseController;
import com.seancheer.dao.entity.Passage;
import com.seancheer.dao.interfaces.Category2Dao;
import com.seancheer.dao.interfaces.BlogDao;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.service.interfaces.IEditService;
import com.seancheer.utils.CookieHelper;
import com.seancheer.utils.IOUtils;

/**
 * 编辑博客的逻辑，包括增加，修改，删除，查询等
 * 
 * @author seancheer
 * @date 2018年4月11日
 */
public class EditServiceImpl extends BaseController implements IEditService {

	private static final Logger logger = LoggerFactory.getLogger(EditServiceImpl.class);

	@Autowired
	private BlogDao passageDao;

	@Autowired
	private Category2Dao category2Dao;

	@Override
	public JSONObject createNewBlog(HttpServletRequest request, HttpServletResponse response) {

		JSONObject blogData = null;

		try {
			String blogStringData = IOUtils.convertStreamToString(request.getInputStream(),
					request.getCharacterEncoding());
			blogData = new JSONObject(blogStringData);
		} catch (IOException | InterruptedException e) {
			return ErrorCode.INTERNAL_ERROR.toJson();
		} catch (JSONException e) {
			logger.error("Parse Json error! Invalid blog Data! ", e);
			return ErrorCode.PARAMETER_ERROR.toJson();
		}

		if (!checkNewBlogData(blogData)) {
			return ErrorCode.PARAMETER_ERROR.toJson();
		}

		return createNewBlogInternal(blogData, request, response);
	}

	/**
	 * 将用户上传的表单数据存储在数据库中
	 * 
	 * @param blogData
	 * @param request
	 * @param response
	 * @return
	 */
	private JSONObject createNewBlogInternal(JSONObject blogData, HttpServletRequest request,
			HttpServletResponse response) {
		String blogTitle = blogData.getString(BlogConstants.NEW_BLOG_KEY_TITLE);
		String blogContent = blogData.getString(BlogConstants.NEW_BLOG_KEY_CONTENT);
		int category1 = Integer.parseInt(blogData.getString(BlogConstants.NEW_BLOG_CATEGORY_0));
		int category2 = Integer.parseInt(blogData.getString(BlogConstants.NEW_BLOG_CATEGORY_1));

		try {
			if (category2Dao.queryRecById(category2).isEmpty()) {
				logger.info("Can not find category2 with id:{}", category2);
				return ErrorCode.PARAMETER_ERROR.toJson();
			}
		} catch (BlogBaseException e) {
			logger.error("Invalid category2 id! category2Id:{}", category2);
			return ErrorCode.PARAMETER_ERROR.toJson();
		}

		Passage passage = new Passage();
		passage.setTitle(blogTitle);
		passage.setContent(blogContent);
		passage.setCategory1Id(String.valueOf(category1));
		passage.setCategory2Id(String.valueOf(category2));
		Cookie cookie = CookieHelper.getCookieByKey(request.getCookies(), "userId");
		if (null == cookie) {
			logger.error("Cookie is null !");
			return ErrorCode.PARAMETER_ERROR.toJson();
		}
		passage.setUserId(Integer.valueOf(cookie.getValue()));

		try {
			passageDao.insertRecord(passage);
		} catch (BlogBaseException e) {
			logger.error("Saving blog failed!", e);
			return ErrorCode.SERVER_BUSY.toJson();
		}

		JSONObject result = ErrorCode.POST_SUCCESS.toJson();
		result.put(BlogConstants.HREF, "/blogList");
		return result;
	}

	@Override
	public JSONObject queryBlog(String blogId, HttpServletResponse response) {
		return null;
	}

	@Override
	public JSONObject deleteBlog(String blogId, HttpServletResponse response) {
		return null;
	}

	/**
	 * 检查参数是否合法
	 * 
	 * @param blogData
	 * @return
	 */
	private boolean checkNewBlogData(JSONObject blogData) {
		String blogTitle, blogContent, select0, select1;

		try {
			blogTitle = blogData.getString(BlogConstants.NEW_BLOG_KEY_TITLE);
			blogContent = blogData.getString(BlogConstants.NEW_BLOG_KEY_CONTENT);
			select0 = blogData.getString(BlogConstants.NEW_BLOG_CATEGORY_0);
			select1 = blogData.getString(BlogConstants.NEW_BLOG_CATEGORY_1);
		} catch (JSONException e) {
			// 当某个key不在jsonObject中的时候，会抛出JSONException，而不是返回null
			logger.error(e.getMessage());
			return false;
		}

		if (StringUtils.isEmpty(blogTitle) || StringUtils.isEmpty(blogContent) || StringUtils.isEmpty(select0)
				|| StringUtils.isEmpty(select1)) {
			logger.error("Invalid blogTitle or blogContent or select0 or select1 !");
			return false;
		}

		if (blogContent.length() <= MIN_BLOG_CONTENT_LENGTH) {
			logger.error("blogContent is too short! at least:{}", MIN_BLOG_CONTENT_LENGTH);
			return false;
		}

		try {
			Integer.parseInt(select0);
			Integer.parseInt(select1);
		} catch (NumberFormatException e) {
			logger.error("category id is invalid!");
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
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("passage",passage);
		return null;
	}

}
