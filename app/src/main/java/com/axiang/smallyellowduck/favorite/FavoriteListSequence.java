package com.axiang.smallyellowduck.favorite;

import com.axiang.smallyellowduck.enums.DataType;

/**
 * Created by a2389 on 2017/8/8.
 */

public class FavoriteListSequence {

    private DataType type;

    private long favoriteTime;

    public FavoriteListSequence(DataType type, long favoriteTime) {
        this.type = type;
        this.favoriteTime = favoriteTime;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public long getFavoriteTime() {
        return favoriteTime;
    }

    public void setFavoriteTime(long favoriteTime) {
        this.favoriteTime = favoriteTime;
    }
}
