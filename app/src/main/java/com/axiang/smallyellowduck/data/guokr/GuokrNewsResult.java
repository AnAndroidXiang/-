package com.axiang.smallyellowduck.data.guokr;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.axiang.smallyellowduck.database.converter.StringTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/7/17.
 */

@Entity(tableName = "guokr_news")
@TypeConverters(StringTypeConverter.class)
public class GuokrNewsResult {

    @ColumnInfo(name = "images")
    @Expose
    @SerializedName("images")
    private List<String> imageList;

    @ColumnInfo(name = "id")
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "summary")
    @Expose
    @SerializedName("summary")
    private String summary;

    @ColumnInfo(name = "favorite")
    @Expose
    private boolean favorite;

    @ColumnInfo(name = "favorite_time")
    @Expose
    private Long favoriteTime;

    @ColumnInfo(name = "timestamp")
    @Expose
    private long timestamp;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
