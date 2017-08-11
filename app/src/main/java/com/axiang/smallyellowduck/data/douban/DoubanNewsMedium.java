package com.axiang.smallyellowduck.data.douban;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a2389 on 2017/7/12.
 */

public class DoubanNewsMedium {

    @ColumnInfo(name = "medium_url")
    @Expose
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
