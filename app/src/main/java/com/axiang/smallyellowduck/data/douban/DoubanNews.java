package com.axiang.smallyellowduck.data.douban;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/7/12.
 */

public class DoubanNews {

    @Expose
    @SerializedName("posts")
    private List<DoubanNewsPosts> posts;

    public List<DoubanNewsPosts> getPosts() {
        return posts;
    }

    public void setPosts(List<DoubanNewsPosts> posts) {
        this.posts = posts;
    }
}
