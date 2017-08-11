package com.axiang.smallyellowduck.homepage.douban;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.android.volley.VolleyError;
import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.data.douban.DoubanNews;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
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
 * Created by a2389 on 2017/7/17.
 */

public class DoubanPresenter implements DoubanContract.Presenter {

    private Context context;

    private DoubanContract.View view;

    private StringModel model;

    private Gson gson;

    private static List<DoubanNewsPosts> list = new ArrayList<>();

    private AppDatabase db;

    public DoubanPresenter(Context context, DoubanContract.View view) {
        this.context = context;
        this.view = view;
        this.model = new StringModel(context);
        this.gson = new Gson();
    }

    public static List<DoubanNewsPosts> getList() {
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
                new AsyncTask<DoubanContract.View, Void, List<DoubanNewsPosts>>() {

                    @Override
                    protected List<DoubanNewsPosts> doInBackground(DoubanContract.View... params) {
                        List<DoubanNewsPosts> postses = null;
                        List<Long> timestamps = db.doubanNewsDao().getAllTimestamp();
                        if (!timestamps.isEmpty()) {
                            Long biggestTimestamp = DateFormatter.getBiggestTimestamp(timestamps);
                            postses = db.doubanNewsDao().getAllByDate(biggestTimestamp);
                        }
                        return postses;
                    }

                    @Override
                    protected void onPostExecute(List<DoubanNewsPosts> postses) {
                        super.onPostExecute(postses);
                        if (postses != null) {
                            view.showResults(postses);
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
        model.load(Api.DOUBAN_MOMENT + DateFormatter.DoubanDateFormat(date),
                new OnStringListener() {

                    @Override
                    public void onSuccess(String result) {
                        try {
                            DoubanNews news = gson.fromJson(result, DoubanNews.class);

                            if(clearing) {
                                list.clear();
                            }
                            List<DoubanNewsPosts> postses = news.getPosts();
                            for (DoubanNewsPosts posts : postses) {
                                posts.setTimestamp(DateFormatter.DoubanStringToLong(posts.getDate()));
                                posts.setFavorite(false);
                                list.add(posts);
                            }
                            view.showResults(list);
                            saveAll(postses);
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
    public void onrefresh() {
        this.loadPosts(System.currentTimeMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(new Intent(context, DetailActivity.class)
            .putExtra("type", DataType.TYPE_DOUBAN)
            .putExtra("id", list.get(position).getId())
            .putExtra("title", list.get(position).getTitle()));
        if (list.get(position).getThumbs().isEmpty()) {
            intent.putExtra("bgImageUrl", R.drawable.placeholder);
        } else {
            intent.putExtra("bgImageUrl", list.get(position).getThumbs().get(0).getMedium().getUrl());
        }
        context.startActivity(intent);
    }

    public void saveAll(final List<DoubanNewsPosts> postses) {

        if (db == null) {
            db = DatabaseCreator.newInstance().getDb();
        }

        if (db != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.beginTransaction();
                    try {
                        db.doubanNewsDao().insertAll(postses);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            }).start();
        }
    }
}
