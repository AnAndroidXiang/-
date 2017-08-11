package com.axiang.smallyellowduck.data.guokr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by a2389 on 2017/7/29.
 */

public class GuokrContentChannel {

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("date_created")
    private String dateCreated;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("key")
    private String key;

    @Expose
    @SerializedName("articles_count")
    private int articlesCount;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(int articlesCount) {
        this.articlesCount = articlesCount;
    }
}
