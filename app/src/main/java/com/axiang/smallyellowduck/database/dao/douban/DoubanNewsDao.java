package com.axiang.smallyellowduck.database.dao.douban;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;

import java.util.List;

/**
 * Created by a2389 on 2017/7/24.
 */

@Dao
public interface DoubanNewsDao {

    @Query("SELECT * FROM douban_news")
    List<DoubanNewsPosts> getAll();

    @Query("SELECT * FROM douban_news WHERE favorite = 1 ORDER BY favorite_time DESC")
    List<DoubanNewsPosts> getAllFavorites();

    @Query("SELECT * FROM douban_news WHERE timestamp = :date")
    List<DoubanNewsPosts> getAllByDate(long date);

    @Query("SELECT timestamp FROM douban_news")
    List<Long> getAllTimestamp();

    @Query("SELECT * FROM douban_news WHERE id = :id")
    DoubanNewsPosts getPostsById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<DoubanNewsPosts> postses);

    @Update
    void update(DoubanNewsPosts question);

    @Delete
    void delete(DoubanNewsPosts question);
}
