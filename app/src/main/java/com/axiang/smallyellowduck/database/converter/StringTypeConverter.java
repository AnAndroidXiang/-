package com.axiang.smallyellowduck.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a2389 on 2017/7/23.
 */

public class StringTypeConverter {

    /**
     * 将数据库中的String类型转化成List<String>
     */
    @TypeConverter
    public static List<String> listStringToString(String json) {
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    /**
     * 将List<String>转化成数据库可存储的String类型
     */
    @TypeConverter
    public static String stringToListString(List<String> stringList) {
        return new Gson().toJson(stringList);
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
