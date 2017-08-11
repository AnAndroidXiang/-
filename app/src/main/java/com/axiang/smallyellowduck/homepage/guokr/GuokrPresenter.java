package com.axiang.smallyellowduck.homepage.guokr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.android.volley.VolleyError;
import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.data.guokr.GuokrNews;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
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
 * Created by a2389 on 2017/7/18.
 */

public class GuokrPresenter implements GuokrContract.Presenter {

    private Context context;

    private GuokrContract.View view;

    private static List<GuokrNewsResult> resultList  = new ArrayList<>();

    private StringModel model;

    private Gson gson;

    private AppDatabase db;

    public GuokrPresenter(Context context, GuokrContract.View view) {
        this.context = context;
        this.view = view;
        this.model = new StringModel(context);
        this.gson = new Gson();
    }

    public static List<GuokrNewsResult> getResultList() {
        return resultList;
    }

    @Override
    public void start() {
        if (NetWorkState.isNetWorkConnected(context)) {
            this.loadPosts();
        } else {
            if (db == null) {
                db = DatabaseCreator.newInstance().getDb();
            }

            if (db != null) {
                new AsyncTask<GuokrContract.View, Void, List<GuokrNewsResult>>() {

                    @Override
                    protected List<GuokrNewsResult> doInBackground(GuokrContract.View... params) {
                        List<GuokrNewsResult> results = null;
                        List<Long> timestamps = db.guokrNewsDao().getAllTimestamp();
                        if (!timestamps.isEmpty()) {
                            Long biggestTimestamp = DateFormatter.getBiggestTimestamp(timestamps);
                            results = db.guokrNewsDao().getAllByDate(biggestTimestamp);
                        }
                        return results;
                    }

                    @Override
                    protected void onPostExecute(List<GuokrNewsResult> results) {
                        super.onPostExecute(results);
                        if (results != null) {
                            view.showResults(results);
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
    public void loadPosts() {
        model.load(Api.GUOKR_ARTICLES, new OnStringListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    GuokrNews news = gson.fromJson(result, GuokrNews.class);
                    resultList.clear();
                    String date = news.getNow();
                    List<GuokrNewsResult> results = news.getResult();
                    for (GuokrNewsResult guokrNewsResult : results) {
                        guokrNewsResult.setTimestamp(DateFormatter.GuokrStringToLong(date));
                        guokrNewsResult.setFavorite(false);
                        resultList.add(guokrNewsResult);
                    }
                    view.showResults(resultList);
                    saveAll(results);
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
        loadPosts();
    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(new Intent(context, DetailActivity.class)
                .putExtra("type", DataType.TYPE_GUOKR)
                .putExtra("id", resultList.get(position).getId())
                .putExtra("title", resultList.get(position).getTitle()));
        if (resultList.get(position).getImageList().isEmpty()) {
            intent.putExtra("bgImageUrl", R.drawable.placeholder);
        } else {
            intent.putExtra("bgImageUrl", resultList.get(position).getImageList().get(0));
        }
        context.startActivity(intent);
    }

    public void saveAll(final List<GuokrNewsResult> results) {

        if (db == null) {
            db = DatabaseCreator.newInstance().getDb();
        }

        if (db != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.beginTransaction();
                    try {
                        db.guokrNewsDao().insertAll(results);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            }).start();
        }
    }
}
