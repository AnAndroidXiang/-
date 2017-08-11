package com.axiang.smallyellowduck.homepage.zhihu;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.android.volley.VolleyError;
import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNews;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.database.AppDatabase;
import com.axiang.smallyellowduck.database.DatabaseCreator;
import com.axiang.smallyellowduck.detail.DetailActivity;
import com.axiang.smallyellowduck.enums.DataType;
import com.axiang.smallyellowduck.model.Api;
import com.axiang.smallyellowduck.model.OnStringListener;
import com.axiang.smallyellowduck.model.StringModel;
import com.axiang.smallyellowduck.util.DateFormatter;
import com.axiang.smallyellowduck.util.NetWorkState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a2389 on 2017/6/28.
 */

public class ZhihuPresenter implements ZhihuContract.Presenter {

    private Context context;

    private StringModel model;

    private Gson gson;

    private static List<ZhihuNewsQuestion> list = new ArrayList<>();

    private ZhihuContract.View view;

    private AppDatabase db;

    public ZhihuPresenter(Context context, ZhihuContract.View view) {
        this.context = context;
        this.model = new StringModel(context);
        this.gson = new Gson();
        this.view = view;
    }

    public static List<ZhihuNewsQuestion> getList() {
        return list;
    }

    @Override
    public void start() {
        if (NetWorkState.isNetWorkConnected(context)) {
            this.loadPosts(System.currentTimeMillis(), true);
        } else {
            if (db == null) {
                db = DatabaseCreator.newInstance().getDb();
            }

            if (db != null) {
                new AsyncTask<ZhihuContract.View, Void, List<ZhihuNewsQuestion>>() {

                    @Override
                    protected List<ZhihuNewsQuestion> doInBackground(ZhihuContract.View... params) {
                        List<ZhihuNewsQuestion> questions = null;
                        List<Long> timestamps = db.zhihuNewsDao().getAllTimestamp();
                        if (!timestamps.isEmpty()) {
                            Long biggestTimestamp = DateFormatter.getBiggestTimestamp(timestamps);
                            questions = db.zhihuNewsDao().getAllByDate(biggestTimestamp);
                        }
                        return questions;
                    }

                    @Override
                    protected void onPostExecute(List<ZhihuNewsQuestion> questions) {
                        super.onPostExecute(questions);
                        if (questions != null) {
                            view.showResults(questions);
                        } else {
                            view.stopLoading();
                            view.showError(null);
                        }
                    }
                }.execute(view);
            }
        }
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        model.load(Api.ZHIHU_HISTORY + DateFormatter.ZhihuDataFormat(date),
                new OnStringListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    ZhihuNews post = gson.fromJson(result, ZhihuNews.class);
                    if (clearing) {
                        list.clear();
                    }
                    Long timestamp = Long.valueOf(post.getDate());
                    List<ZhihuNewsQuestion> questions = post.getQuestionList();
                    for (ZhihuNewsQuestion question : questions) {
                        question.setTimestamp(timestamp);
                        question.setFavorite(false);
                        list.add(question);
                    }
                    view.showResults(list);
                    saveAll(questions);
                } catch (JsonSyntaxException e) {
                    view.stopLoading();
                    view.showError(e);
                }
            }

            @Override
            public void onError(VolleyError error) {
                view.stopLoading();
                view.showError(error);
            }
        });
    }

    @Override
    public void refresh() {
        this.loadPosts(System.currentTimeMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(context, DetailActivity.class)
            .putExtra("type", DataType.TYPE_ZHIHU)
            .putExtra("id", list.get(position).getId())
            .putExtra("title", list.get(position).getTitle());
        if (list.get(position).getImages().get(0).isEmpty()) {
            intent.putExtra("bgImageUrl", R.drawable.placeholder);
        } else {
            intent.putExtra("bgImageUrl", list.get(position).getImages().get(0));
        }
        context.startActivity(intent);
    }

    public void saveAll(final List<ZhihuNewsQuestion> questions) {

        if (db == null) {
            db = DatabaseCreator.newInstance().getDb();
        }

        if(db != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.beginTransaction();
                    try {
                        db.zhihuNewsDao().insertAll(questions);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            }).start();
        }
    }
}
