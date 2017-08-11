package com.axiang.smallyellowduck.database.dao.guokr;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;

import java.util.List;

/**
 * Created by a2389 on 2017/7/24.
 */

@Dao
public interface GuokrNewsDao {

    @Query("SELECT * FROM guokr_news")
    List<GuokrNewsResult> getAll();

    @Query("SELECT * FROM guokr_news WHERE favorite = 1 ORDER BY favorite_time DESC")
    List<GuokrNewsResult> getAllFavorites();

    @Query("SELECT * FROM guokr_news WHERE timestamp = :date")
    List<GuokrNewsResult> getAllByDate(long date);

    @Query("SELECT timestamp FROM guokr_news")
    List<Long> getAllTimestamp();

    @Query("SELECT * FROM guokr_news WHERE id = :id")
    GuokrNewsResult getResultById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<GuokrNewsResult> results);

    @Update
    void update(GuokrNewsResult question);

    @Delete
    void delete(GuokrNewsResult question);
}
