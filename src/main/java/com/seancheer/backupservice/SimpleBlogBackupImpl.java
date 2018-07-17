package com.seancheer.backupservice;

import com.seancheer.dao.entity.Passage;

/**
 * 简单的备份实现类，包含以下备份策略
 * 1 立即备份，发表博客的时候进行备份
 * 2 定时备份，定时备份，打包，并发送邮件到指定地址。
 *
 * @author: seancheer
 * @date: 2018/6/12
 **/

public class SimpleBlogBackupImpl implements IBlogBackup {

    /**
     * 立即进行备份
     * @param p
     * @return
     */
    @Override
    public boolean backupImmediatly(Passage p) {
        return false;
    }


    /**
     * 获取备份的目的地
     * @return
     */
    @Override
    public String getBakDestination() {
        return null;
    }
}
