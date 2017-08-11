package com.axiang.smallyellowduck.data.douban;

import android.arch.persistence.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a2389 on 2017/7/12.
 */

public class DoubanNewsThumbs {

    @Embedded
    @Expose
    @SerializedName("medium")
    private DoubanNewsMedium medium;

    public DoubanNewsMedium getMedium() {
        return medium;
    }

    public void setMedium(DoubanNewsMedium medium) {
        this.medium = medium;
    }
}
