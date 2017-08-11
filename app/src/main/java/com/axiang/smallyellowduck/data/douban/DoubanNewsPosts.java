package com.axiang.smallyellowduck.data.douban;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.axiang.smallyellowduck.database.converter.douban.DoubanNewsConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/7/12.
 */

@Entity(tableName = "douban_news")
@TypeConverters(DoubanNewsConverter.class)
public class DoubanNewsPosts {

    @ColumnInfo(name = "short_url")
    @Expose
    @SerializedName("short_url")
    private String shortUrl;

    @ColumnInfo(name = "abstract")
    @Expose
    @SerializedName("abstract")
    private String abs;

    @ColumnInfo(name = "date")
    @Expose
    @SerializedName("date")
    private String date;

    @ColumnInfo(name = "thumbs")
    @Expose
    @SerializedName("thumbs")
    private List<DoubanNewsThumbs> thumbs;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "id")
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "favorite")
    @Expose
    private boolean favorite;

    @ColumnInfo(name = "favorite_time")
    @Expose
    private Long favoriteTime;

    @ColumnInfo(name = "timestamp")
    @Expose
    private long timestamp;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DoubanNewsThumbs> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<DoubanNewsThumbs> thumbs) {
        this.thumbs = thumbs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Long getFavoriteTime() {
        return favoriteTime;
    }

    public void setFavoriteTime(Long favoriteTime) {
        this.favoriteTime = favoriteTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
