package com.seancheer.backupservice;

import com.seancheer.dao.entity.Passage;

/**
 * 继续博客备份的接口
 *
 * @author seancheer
 * @date 2018年6月9日
 */
public interface IBlogBackup {

    /**
     * 立即进行备份
     * @param p
     * @return
     */
    boolean backupImmediatly(Passage p);

    /**
     * 获取备份的目的地
     * @return
     */
    String getBakDestination();
}
