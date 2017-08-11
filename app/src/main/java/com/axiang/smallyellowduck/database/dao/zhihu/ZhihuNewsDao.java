package com.axiang.smallyellowduck.database.dao.zhihu;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;

import java.util.List;

/**
 * Created by a2389 on 2017/7/24.
 */

@Dao
public interface ZhihuNewsDao {

    @Query("SELECT * FROM zhihu_news")
    List<ZhihuNewsQuestion> getAll();

    @Query("SELECT * FROM zhihu_news WHERE favorite = 1 ORDER BY favorite_time DESC")
    List<ZhihuNewsQuestion> getAllFavorites();

    @Query("SELECT * FROM zhihu_news WHERE timestamp = :date")
    List<ZhihuNewsQuestion> getAllByDate(long date);

    @Query("SELECT timestamp FROM zhihu_news")
    List<Long> getAllTimestamp();

    @Query("SELECT * FROM zhihu_news WHERE id = :id")
    ZhihuNewsQuestion getQuestionById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<ZhihuNewsQuestion> questions);

    @Update
    void update(ZhihuNewsQuestion question);

    @Delete
    void delete(ZhihuNewsQuestion question);
}
