package com.axiang.smallyellowduck.detail;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.data.douban.DoubanContent;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.data.zhihu.ZhihuStory;
import com.axiang.smallyellowduck.database.AppDatabase;
import com.axiang.smallyellowduck.database.DatabaseCreator;
import com.axiang.smallyellowduck.enums.DataType;
import com.axiang.smallyellowduck.interfaze.FavoriteToDetailListener;
import com.axiang.smallyellowduck.model.Api;
import com.axiang.smallyellowduck.model.OnStringListener;
import com.axiang.smallyellowduck.model.StringModel;
import com.axiang.smallyellowduck.util.NetWorkState;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by a2389 on 2017/7/28.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private Context context;

    private StringModel model;

    private DetailContract.View view;

    private DataType type;

    private int id;

    private String title;

    private String bgImageUrl;

    private AppDatabase db;

    private AppDatabase database;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            boolean favorite = (Boolean) msg.obj;
            view.setCollectIcon(favorite);
        }
    };

    public DetailPresenter(Context context, DetailContract.View view) {
        this.context = context;
        this.view = view;
        this.model = new StringModel(context);
    }

    @Override
    public void start() {
        requestData();
    }

    @Override
    public void shareAsText() {
        StringBuilder sendUrl = new StringBuilder();
        if (type == DataType.TYPE_ZHIHU) {
            sendUrl.append(Api.ZHIHU_SHARE).append(id);
        } else if (type == DataType.TYPE_DOUBAN) {
            database = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();
            sendUrl.append(database.doubanNewsDao().getPostsById(id).getShortUrl());
            database.close();
        } else if (type == DataType.TYPE_GUOKR) {
            sendUrl.append(Api.GUOKR_ARTICLE_LINK_V1).append(id);
        }
        String sendContent = title + "\r\n"
                + sendUrl.toString() + "\r\n"
                + context.getResources().getString(R.string.share_from) + " "
                + context.getResources().getString(R.string.app_name);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sendContent);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, context.getResources().getString(R.string.share_to)));
    }

    /**
     * true表示已收藏，需要取消收藏，并删除收藏界面的该项数据；false表示未收藏，需要收藏
     * @param favorite：收藏或取消收藏
     */
    @Override
    public void addToOrDeleteFromBookmarks(final boolean favorite) {
        if (db == null) {
            db = DatabaseCreator.newInstance().getDb();
        }

        if (db != null) {
            if (type == DataType.TYPE_ZHIHU) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ZhihuNewsQuestion question = db.zhihuNewsDao().getQuestionById(id);
                        if (question != null) {
                            question.setFavorite(favorite);
                            if (favorite) {
                                question.setFavoriteTime(System.currentTimeMillis());
                            }
                            db.zhihuNewsDao().update(question);
                        }
                        Message msg = new Message();
                        msg.obj = favorite;
                        handler.sendMessage(msg);
                    }
                }).start();
            } else if (type == DataType.TYPE_DOUBAN) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DoubanNewsPosts posts = db.doubanNewsDao().getPostsById(id);
                        if (posts != null) {
                            posts.setFavorite(favorite);
                            if (favorite) {
                                posts.setFavoriteTime(System.currentTimeMillis());
                            }
                            db.doubanNewsDao().update(posts);
                        }
                        Message msg = new Message();
                        msg.obj = favorite;
                        handler.sendMessage(msg);
                    }
                }).start();
            } else if (type == DataType.TYPE_GUOKR) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GuokrNewsResult result = db.guokrNewsDao().getResultById(id);
                        if (result != null) {
                            result.setFavorite(favorite);
                            if (favorite) {
                                result.setFavoriteTime(System.currentTimeMillis());
                            }
                            db.guokrNewsDao().update(result);
                        }
                        Message msg = new Message();
                        msg.obj = favorite;
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        }
    }

    @Override
    public boolean queryIfIsBookmarked() {
        database = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();
        if (type == DataType.TYPE_ZHIHU) {
            return database.zhihuNewsDao().getQuestionById(id).isFavorite();
        } else if (type == DataType.TYPE_DOUBAN) {
            return database.doubanNewsDao().getPostsById(id).isFavorite();
        } else if (type == DataType.TYPE_GUOKR) {
            return database.guokrNewsDao().getResultById(id).isFavorite();
        }
        database.close();
        return false;
    }

    @Override
    public void requestData() {
        if (NetWorkState.isNetWorkConnected(context)) {
            if(type == DataType.TYPE_ZHIHU) {
                model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        try {
                            ZhihuStory story = gson.fromJson(result, ZhihuStory.class);
                            if (story.getBody() == null) {
                                view.showResultWithoutBody(story.getShareUrl());
                            } else {
                                view.showResults(convertZhihuContent(story.getBody()));
                                view.setTitle(story.getTitle());
                                if (story.getImage().isEmpty()) {
                                    view.showCover(bgImageUrl);
                                } else {
                                    view.showCover(story.getImage());
                                }
                            }
                        } catch (JsonSyntaxException e) {
                            view.showLoadingError();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        view.showLoadingError();
                    }

                });
            } else if (type == DataType.TYPE_DOUBAN) {
                model.load(Api.DOUBAN_ARTICLE_DETAIL + id, new OnStringListener() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        try {
                            DoubanContent content = gson.fromJson(result, DoubanContent.class);
                            view.showResults(convertDoubanContent(content.getContent()));
                            view.setTitle(content.getTitle());
                            if (content.getThumbs().isEmpty()) {
                                view.showCover(bgImageUrl);
                            } else {
                                view.showCover(content.getThumbs().get(0).getMedium().getUrl());
                            }
                        } catch (JsonSyntaxException e) {
                            view.showLoadingError();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        view.showLoadingError();
                    }

                });
            } else if (type == DataType.TYPE_GUOKR) {
                model.load(Api.GUOKR_ARTICLE_LINK_V1 + id, new OnStringListener() {
                    @Override
                    public void onSuccess(String result) {
                        view.showResults(result);
                        view.setTitle(title);
                        view.showCover(bgImageUrl);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        view.showLoadingError();
                    }

                });
            }
        } else {
            view.showLoadingError();
        }
    }

    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu.css\" type=\"text/css\">";

        String theme = "<body className=\"\" onload=\"onLoaded()\">";

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }

    private String convertDoubanContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban.css\" type=\"text/css\">";

        String theme = "<body className=\"\" onload=\"onLoaded()\">";

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBgImageUrl() {
        return bgImageUrl;
    }

    public void setBgImageUrl(String bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }
}
