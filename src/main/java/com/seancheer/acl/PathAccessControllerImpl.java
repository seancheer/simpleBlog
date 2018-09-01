package com.seancheer.acl;

import com.seancheer.common.UrlConstants;
import com.seancheer.service.interfaces.IGodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 权限控制的基本实现类，初始化一个map，存储
 * 当前所有可用的path，该access controller只做管理员和
 * 普通用户的检查，普通用户本身的权限控制在controller代码中
 * 进行
 * @author: seancheer
 * @date: 2018/8/26
 **/
public class PathAccessControllerImpl implements IPathAccessController{

    @Autowired
    private IGodService godService;

    private static List<String> godPath = new ArrayList<>();

    public PathAccessControllerImpl()
    {
        init();
    }


    /**
     * 进行一些初始化动作
     */
    private void init()
    {
        //管理员才可以访问的api
        godPath = Arrays.asList(UrlConstants.BACKUP, UrlConstants.CREATE_NEW_BLOG, UrlConstants.DELETE_BLOG,
                UrlConstants.EDIT_BLOG, UrlConstants.HELLOWORLD,UrlConstants.NEW_BLOG,
                UrlConstants.UPDATE_BLOG);
    }

    /**
     * 此处不判断userid，注意，userid只对于判断是否为god的时候才需要
     * @param userId
     * @param path
     * @return
     */
    @Override
    public boolean canAccess(Integer userId, String path) {
        return !godPath.contains(path) || isGod(userId);
    }

    /**
     * 判断是否为god
     * @param userId
     * @return
     */
    private boolean isGod(Integer userId)
    {
        if (null == userId)
        {
            return false;
        }

        return godService.isGod(userId);
    }
}
