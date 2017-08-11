package com.axiang.smallyellowduck.database.converter.douban;

import android.arch.persistence.room.TypeConverter;

import com.axiang.smallyellowduck.data.douban.DoubanNewsThumbs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a2389 on 2017/7/24.
 */

public class DoubanNewsConverter {

    /**
     * 将List<DoubanNewsThumbs>转化成String
     */
    @TypeConverter
    public static String listToString(List<DoubanNewsThumbs> thumbsList) {
        return new Gson().toJson(thumbsList);
    }

    /**
     * 将String转化成List<DoubanNewsThumbs>
     */
    @TypeConverter
    public static List<DoubanNewsThumbs> stringToList(String json) {
        Type type = new TypeToken<ArrayList<DoubanNewsThumbs>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

//    /**
//     * 将boolean转化成0或1
//     */
//    @TypeConverter
//    public static int booleanToInt(boolean favorite) {
//        return favorite ? 1 : 0;
//    }
//
//    /**
//     * 将0或1转化成boolean
//     */
//    @TypeConverter
//    public static boolean intToBoolean(int i) {
//        return i == 1 ? true : false;
//    }
}
