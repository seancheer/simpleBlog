package com.seancheer.acl;

/**
 * 对于每个url的权限控制
 * @author: seancheer
 * @date: 2018/8/26
 **/
public interface IPathAccessController {
    /**
     * 是否该用户可以访问该path
     * @param userId
     * @param path
     * @return
     */
    boolean canAccess(Integer userId, String path);
}
