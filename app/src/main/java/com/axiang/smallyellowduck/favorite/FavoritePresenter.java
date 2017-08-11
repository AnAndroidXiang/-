package com.axiang.smallyellowduck.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.database.AppDatabase;
import com.axiang.smallyellowduck.database.DatabaseCreator;
import com.axiang.smallyellowduck.detail.DetailActivity;
import com.axiang.smallyellowduck.enums.DataType;
import com.axiang.smallyellowduck.interfaze.FavoriteToDetailListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a2389 on 2017/8/7.
 */

public class FavoritePresenter implements FavoriteContract.Presenter {

    private AppDatabase db;

    private Context context;

    private FavoriteContract.View view;

    private FavoriteToDetailListener activityListener;

    public FavoritePresenter(Context context, FavoriteContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void start() {
        loadPosts();
    }

    public void setActivityListener(FavoriteToDetailListener activityListener) {
        this.activityListener = activityListener;
    }

    @Override
    public void loadPosts() {
        if (db == null) {
            db = DatabaseCreator.newInstance().getDb();
        }

        if (db != null) {
            new AsyncTask<FavoriteContract.View, Void, List<Object>>() {

                @Override
                protected List<Object> doInBackground(FavoriteContract.View... params) {
                    List<Object> objects = new ArrayList<>();
                    List<FavoriteListSequence> sequences = new ArrayList<FavoriteListSequence>();

                    // zhihu所有的收藏数据
                    List<ZhihuNewsQuestion> questions = db.zhihuNewsDao().getAllFavorites();
                    if (!questions.isEmpty()) {
                        for (ZhihuNewsQuestion question : questions) {
                            FavoriteListSequence sequence = new FavoriteListSequence(DataType.TYPE_ZHIHU, question.getFavoriteTime());
                            sequences.add(sequence);
                        }
                    }

                    // douban所有的收藏数据
                    List<DoubanNewsPosts> postses = db.doubanNewsDao().getAllFavorites();
                    if (!postses.isEmpty()) {
                        for (DoubanNewsPosts posts : postses) {
                            FavoriteListSequence sequence = new FavoriteListSequence(DataType.TYPE_DOUBAN, posts.getFavoriteTime());
                            sequences.add(sequence);
                        }
                    }

                    // guokr所有的收藏数据
                    List<GuokrNewsResult> results = db.guokrNewsDao().getAllFavorites();
                    if (!results.isEmpty()) {
                        for (GuokrNewsResult result : results) {
                            FavoriteListSequence sequence = new FavoriteListSequence(DataType.TYPE_GUOKR, result.getFavoriteTime());
                            sequences.add(sequence);
                        }
                    }

                    // 对数组进行冒泡排序。按favoriteTime进行从大到小进行排序
                    for (int i = 0;i < sequences.size() - 1;i++) {
                        for (int j = i + 1;j < sequences.size();j++) {
                            if (sequences.get(i).getFavoriteTime() < sequences.get(j).getFavoriteTime()) {
                                FavoriteListSequence big = sequences.get(j);
                                FavoriteListSequence small = sequences.get(i);
                                sequences.set(i, big);
                                sequences.set(j, small);
                            }
                        }
                    }

                    int zhihuNum = 0, doubanNum = 0, guokrNum = 0;
                    // 按照favoriteTime依次添加进List中
                    for (int i = 0; i < sequences.size();i++) {
                        if (sequences.get(i).getType() == DataType.TYPE_ZHIHU) {
                            objects.add(questions.get(zhihuNum++));
                        } else if (sequences.get(i).getType() == DataType.TYPE_DOUBAN) {
                            objects.add(postses.get(doubanNum++));
                        } else if (sequences.get(i).getType() == DataType.TYPE_GUOKR) {
                            objects.add(results.get(guokrNum++));
                        }
                    }
                    return objects;
                }

                @Override
                protected void onPostExecute(List<Object> objects) {
                    super.onPostExecute(objects);
                    if (!objects.isEmpty()) {
                        view.showResults(objects);
                    } else {
                        view.showNoData();
                    }
                }
            }.execute(view);
        }
    }

    @Override
    public void startReading(int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        Object selectItem = view.getAdapter().getFavoriteData().get(position);
        if (selectItem instanceof ZhihuNewsQuestion) {
            ZhihuNewsQuestion question = (ZhihuNewsQuestion) selectItem;
            intent.putExtra("type", DataType.TYPE_ZHIHU)
                    .putExtra("id", question.getId())
                    .putExtra("title", question.getTitle());
            if (question.getImages().get(0).isEmpty()) {
                intent.putExtra("bgImageUrl", R.drawable.placeholder);
            } else {
                intent.putExtra("bgImageUrl", question.getImages().get(0));
            }
        } else if (selectItem instanceof DoubanNewsPosts) {
            DoubanNewsPosts posts = (DoubanNewsPosts) selectItem;
            intent.putExtra("type", DataType.TYPE_DOUBAN)
                    .putExtra("id", posts.getId())
                    .putExtra("title", posts.getTitle());
            if (posts.getThumbs().isEmpty()) {
                intent.putExtra("bgImageUrl", R.drawable.placeholder);
            } else {
                intent.putExtra("bgImageUrl", posts.getThumbs().get(0).getMedium().getUrl());
            }
        } else if (selectItem instanceof GuokrNewsResult) {
            GuokrNewsResult result = (GuokrNewsResult) selectItem;
            intent.putExtra("type", DataType.TYPE_GUOKR)
                    .putExtra("id", result.getId())
                    .putExtra("title", result.getTitle());
            if (result.getImageList().isEmpty()) {
                intent.putExtra("bgImageUrl", R.drawable.placeholder);
            } else {
                intent.putExtra("bgImageUrl", result.getImageList().get(0));
            }
        }
        intent.putExtra("activity_source", "favorite");
        intent.putExtra("position", position);
        activityListener.openDetailFromFavorite(intent);
    }
}
