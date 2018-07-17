package com.seancheer.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.seancheer.common.BlogConfigImpl;
import com.seancheer.common.ErrorCode;
import com.seancheer.controller.BaseController;
import com.seancheer.dao.entity.BaseCategory;
import com.seancheer.dao.entity.Category1;
import com.seancheer.dao.entity.Passage;
import com.seancheer.dao.interfaces.BlogDao;
import com.seancheer.dao.interfaces.Category1Dao;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.freemarker.FreeMarkerBlogSummary;
import com.seancheer.freemarker.FreeMarkerRenderContent;
import com.seancheer.service.interfaces.IBlogService;

/**
 * 博客相关的service类，包含创建blog，修改和删除blog等
 * 
 * @author seancheer
 * @date 2018年4月13日
 */
public class BlogServiceImpl extends BaseController implements IBlogService {

	private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

	@Autowired
	private Category1Dao category1Dao;

	@Autowired
	private BlogDao blogDao;

	@Override
	public String getAllCategoies(HttpServletResponse response) {
		List<Category1> allCategories = null;
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		try {
			allCategories = category1Dao.queryAllRecord();
		} catch (BlogBaseException e) {
			logger.error("Query all categories failed!", e);
			return ErrorCode.INTERNAL_ERROR.toString();
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			List<JSONObject> categoryList = BaseCategory.convertToJson(allCategories);

			if (null == categoryList) {
				logger.info("Empty categories");
			} else {
				writer.append(categoryList.toString());
			}
		} catch (IOException e) {
			logger.error("Get writer failed!", e);
			return ErrorCode.INTERNAL_ERROR.toString();
		} finally {
			if (null != writer) {
				writer.flush();
				writer.close();
			}
		}

		return null;
	}

	/**
	 * 将所有的blog封装在modelview中，供html来渲染
	 * 
	 * @throws BlogBaseException
	 */
	@Override
	public ModelAndView getBlogList(HttpServletResponse response, Long page, String categoryIds)
			throws BlogBaseException {
		List<String> ids = BaseCategory.processCategoryIds(categoryIds);
		//如果传入的category非法，那么直接redirect到blogList
		if (null != ids && ids.size() > 0 && !Passage.isCategoryValid(ids.get(0)))
		{
			logger.info("Invalid ids. Redirect to blogList without category! index:" + ids.get(0));
			return new ModelAndView("redirect:/blogList");
		}
		
		List<Long> rowCountWithLimit = new ArrayList<Long>();
		List<Passage> blogList = blogDao.queryLimitRecByCategoryId(page, ids, rowCountWithLimit);

		Long rowCount = rowCountWithLimit.get(0);
		Long totalPage = rowCount / BlogConfigImpl.DEFAULT_SUM_PER_PAGE;
		if (rowCount % BlogConfigImpl.DEFAULT_SUM_PER_PAGE != 0) {
			totalPage += 1;
		}

		page = checkPage(page, totalPage);

		ModelAndView modelAndView = new ModelAndView();
		logger.info("Page:{} totalPage:{} blogList.size:{}", page, totalPage, blogList.size());
		modelAndView.addObject("page", page);
		modelAndView.addObject("totalPage", totalPage);
		modelAndView.addObject("blogList", blogList);
		modelAndView.addObject("blogTitleLimit", BlogConfigImpl.BLOG_TITLE_LIMIT);
		modelAndView.addObject("blogContentLimit", BlogConfigImpl.BLOG_CONTENT_LIMIT);
		modelAndView.addObject("blogSummary",new FreeMarkerBlogSummary());
		modelAndView.addObject("renderBlog",new FreeMarkerRenderContent());
		if (null != ids && ids.size() == 2) {
			modelAndView.addObject("categoryId",ids.get(0) + "," + ids.get(1));
		}

		List<Category1> allCategories = category1Dao.queryAllRecord();
		modelAndView.addObject("categories", BaseCategory.convertToJson(allCategories));
		return modelAndView;
	}

	/**
	 * 检查page的范围，过大或者过小都只取合法值
	 * 
	 * @param page
	 * @param totalPage
	 * @return
	 */
	private Long checkPage(Long page, Long totalPage) {
		if (null == page || page < 1) {
			return 1L;
		} else if (page > totalPage) {
			return totalPage;
		} else {
			return page;
		}
	}
	
	/**
	 * 获取blog详情
	 */
	@Override
	public ModelAndView getBlog(HttpServletResponse response, HttpSession session, Integer blogId)
			throws BlogBaseException {
		Passage passage = blogDao.queryRecordById(blogId);
		
		if (null == passage)
		{
			logger.info("Can not find the blog! blogId:{}",blogId);
			return redirect404View();
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("blog",passage);
		modelAndView.addObject("renderContent",new FreeMarkerRenderContent());
		return modelAndView;
	}

}
