package com.axiang.smallyellowduck.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by a2389 on 2017/7/5.
 */

public class DateFormatter {

    /**
     * 将long类型date转化成String类型
     */

    public static String ZhihuDataFormat(long date) {
        Date newDate = new Date(date + 1000 * 60 * 60 * 24);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(newDate);
    }

    public static String DoubanDateFormat(long date) {
        Date newDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(newDate);
    }

    /**
     * 将DoubanNewsPosts中的String类型date转化成Long类型
     */
    public static Long DoubanStringToLong(String date) {
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate == null ? 0 : newDate.getTime();
    }

    /**
     * 将GuokrNews中的String类型now转化成Long类型
     */
    public static Long GuokrStringToLong(String date) {
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date.substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate == null ? 0 : newDate.getTime();
    }

    /**
     * 取得最大Long型的日期
     */
    public static Long getBiggestTimestamp(List<Long> timestamps) {
        Long biggestTimestamp = timestamps.get(0);
        for(int i = 1; i < timestamps.size(); i++) {
            if(biggestTimestamp < timestamps.get(i)) {
                biggestTimestamp = timestamps.get(i);
            }
        }
        return biggestTimestamp;
    }
}
