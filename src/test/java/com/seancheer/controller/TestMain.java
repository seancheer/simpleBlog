package com.seancheer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.util.TimezoneDump;
import com.seancheer.dao.entity.Passage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 测试一些方法的时候使用的类
 * @author: seancheer
 * @date: 2018/8/12
 **/
public class TestMain {

    public static void main(String[] args) throws IOException {
        TimeZone newyork = TimeZone.getTimeZone("GMT+0:00");
        TimeZone shanghai = TimeZone.getTimeZone("GMT+8");

        Calendar calendar = Calendar.getInstance(newyork);

        Calendar chinaCalendar = Calendar.getInstance();

        System.out.println(calendar.getTime() + "  zone:" + calendar.getTimeZone());
        long newyorkCurrentTime = calendar.getTime().getTime() - calendar.getTimeZone().getRawOffset() + newyork.getRawOffset();
        //HOUR: 采用12小时制的方式，如果当前时间是下午，那么1就是13，否则为01，HOUR_OF_DAY：24进制。
        calendar.set(Calendar.HOUR_OF_DAY, 3);

        System.out.println(calendar.getTime());
        //获取纽约的凌晨三点时间戳
        long netyorkTargetTime = calendar.getTime().getTime() - calendar.getTimeZone().getRawOffset() + newyork.getRawOffset();

        System.out.println("currenTime:" + newyorkCurrentTime);
        System.out.println("targetTime:" + netyorkTargetTime);

        Calendar c = new Calendar.Builder().set(Calendar.HOUR,1).build();
        System.out.println("calendar:" + c.getTime());

    }
}
