package com.axiang.smallyellowduck.data.zhihu;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.axiang.smallyellowduck.database.converter.StringTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/7/10.
 */

@Entity(tableName = "zhihu_news")
@TypeConverters(StringTypeConverter.class)
public class ZhihuNewsQuestion {

    @ColumnInfo(name = "images")
    @Expose
    @SerializedName("images")
    private List<String> images;

    @ColumnInfo(name = "id")
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "title")
    @Expose
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "favorite")
    @Expose
    private boolean favorite;

    @ColumnInfo(name = "favorite_time")
    @Expose
    private Long favoriteTime;

    @ColumnInfo(name = "timestamp")
    @Expose
    private Long timestamp;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
