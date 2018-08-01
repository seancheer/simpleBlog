package com.seancheer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seancheer.common.ErrorCode;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seancheer.dao.entity.BaseCategory;
import com.seancheer.dao.entity.Category1;
import com.seancheer.dao.interfaces.Category1Dao;
import com.seancheer.exception.BlogBaseException;
import com.seancheer.service.interfaces.IEditService;

/**
 * 编辑中心相关的操作
 *
 * @author seancheer
 * @date 2018年3月2日
 */
@Controller
public class EditController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(EditController.class);

    @Autowired
    private IEditService editService;

    @Autowired
    private Category1Dao category1Dao;

    /**
     * 后台管理员界面
     *
     * @param key
     */
    @RequestMapping(value = "/godCenter", method = RequestMethod.GET)
    public void godCenter() {
        return;

    }

    /**
     * 发表新博客界面
     *
     * @param key
     */
    @RequestMapping(value = "/newBlog", method = RequestMethod.GET)
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
     * @return 创建后的json
     */
    @RequestMapping(value = "/createNewBlog", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createNewBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        return editService.createNewBlog(request, response).toString();
    }

    /**
     * 编辑某个特定的博客
     *
     * @param response
     * @param session
     * @param blogId
     * @return
     */
    @RequestMapping(value = "/editBlog", method = RequestMethod.GET)
    public ModelAndView editBlog(HttpServletResponse response, HttpSession session,
                                 @RequestParam(value = "blogId", required = false) Integer blogId) {
        if (null == blogId || blogId < 0) {
            return redirect404View();
        }
        response.setCharacterEncoding("utf-8");
        return editService.editBlog(response, session, blogId);
    }

    /**
     * 更新对应的博客
     *
     * @param request
     * @param response
     * @param session
     * @param blogId
     * @return
     */
    @RequestMapping(value = "/updateBlog", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String createNewBlog(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                @RequestParam(value = "blogId", required = false) Integer blogId) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //return editService.up(request, response).toString();
        if (null == blogId || blogId < 0) {
            return ErrorCode.NOT_FOUND.toString();
        }

        return editService.updateBlog(request, response, blogId).toString();
    }

}
