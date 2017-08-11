package com.axiang.smallyellowduck.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.database.dao.douban.DoubanNewsDao;
import com.axiang.smallyellowduck.database.dao.guokr.GuokrNewsDao;
import com.axiang.smallyellowduck.database.dao.zhihu.ZhihuNewsDao;

/**
 * Created by a2389 on 2017/7/24.
 */

@Database(entities = {
        ZhihuNewsQuestion.class,
        DoubanNewsPosts.class,
        GuokrNewsResult.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "small-yellow-duck-db";

    public abstract ZhihuNewsDao zhihuNewsDao();

    public abstract DoubanNewsDao doubanNewsDao();

    public abstract GuokrNewsDao guokrNewsDao();
}
