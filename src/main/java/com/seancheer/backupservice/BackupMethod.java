package com.seancheer.backupservice;

/**
 * 博客备份的策略
 * @author: seancheer
 * @date: 2018/8/10
 **/
public enum BackupMethod {
    EMAIL("email");


    private String name;


    private BackupMethod(String name)
    {
        this.name = name;
    }

}
