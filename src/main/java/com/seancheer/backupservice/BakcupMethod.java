package com.seancheer.backupservice;

/**
 * 博客备份的策略
 * @author: seancheer
 * @date: 2018/8/10
 **/
public enum BakcupMethod {
    EMAIL("email");


    private String name;


    private BakcupMethod(String name)
    {
        this.name = name;
    }

}
