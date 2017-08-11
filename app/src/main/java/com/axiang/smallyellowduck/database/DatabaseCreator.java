package com.axiang.smallyellowduck.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by a2389 on 2017/7/24.
 */

public class DatabaseCreator {

    @Nullable
    private static DatabaseCreator creator = null;

    private AppDatabase db;

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private final AtomicBoolean isCreated = new AtomicBoolean(false);

    private DatabaseCreator() {

    }

    public synchronized static DatabaseCreator newInstance() {
        if (creator == null) {
            creator = new DatabaseCreator();
        }
        return creator;
    }

    public void createDb(Context context) {
        if(isInitialized.compareAndSet(false, true)) {

            //创建个线程去创建数据库
            new AsyncTask<Context, Void, Void>() {

                @Override
                protected Void doInBackground(Context... params) {
                    Context ctx = params[0].getApplicationContext();
                    db = Room.databaseBuilder(ctx, AppDatabase.class, AppDatabase.DATABASE_NAME).build();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    isCreated.set(true);
                }
            }.execute(context.getApplicationContext());
        }
    }

    public boolean isDatabaseCreated() {
        return isCreated.get();
    }

    @Nullable
    public AppDatabase getDb() {
        return db;
    }
}
