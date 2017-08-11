package com.axiang.smallyellowduck.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.app.GlobalContants;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.database.AppDatabase;
import com.axiang.smallyellowduck.database.DatabaseCreator;
import com.axiang.smallyellowduck.interfaze.ItemTouchHelperListener;
import com.axiang.smallyellowduck.interfaze.OnRecyclerViewOnClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Created by a2389 on 2017/8/7.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperListener {

    private Context context;

    private LayoutInflater inflater;

    private List<Object> favoriteData;

    private OnRecyclerViewOnClickListener mListener;

    private AppDatabase db;

    private boolean isMoving = false;

    private boolean isRemoving = false;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    isMoving = false;
                    break;
                case 1:
                    isRemoving = false;
                    break;
            }
        }
    };

    public FavoriteAdapter(Context context, List<Object> favoriteData) {
        this.context = context;
        this.favoriteData = favoriteData;
        this.inflater = LayoutInflater.from(context);
    }

    public void setmListener(OnRecyclerViewOnClickListener mListener) {
        this.mListener = mListener;
    }

    public List<Object> getFavoriteData() {
        return favoriteData;
    }

    @Override
    public int getItemCount() {
        return favoriteData.isEmpty() ? 0 : favoriteData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (favoriteData.get(position) instanceof ZhihuNewsQuestion) {
            ZhihuNewsQuestion question = (ZhihuNewsQuestion) favoriteData.get(position);
            return question.getImages().get(0).isEmpty() ? GlobalContants.T1 : GlobalContants.T1I1;
        } else if (favoriteData.get(position) instanceof DoubanNewsPosts) {
            DoubanNewsPosts posts = (DoubanNewsPosts) favoriteData.get(position);
            return posts.getThumbs().isEmpty() ? GlobalContants.T2 : GlobalContants.T2I1;
        } else if (favoriteData.get(position) instanceof GuokrNewsResult) {
            return GlobalContants.T2I1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case GlobalContants.T2I1:
                return new T2WithIconViewHolder(inflater.inflate(R.layout.layout_content_i1t2, parent, false), mListener);
            case GlobalContants.T1I1:
                return new T1WithIconViewHolder(inflater.inflate(R.layout.layout_content_i1t1, parent, false), mListener);
            case GlobalContants.T2:
                return new T2WithoutIconViewHolder(inflater.inflate(R.layout.layout_content_t2, parent, false), mListener);
            case GlobalContants.T1:
                return new T1WithoutIconViewHolder(inflater.inflate(R.layout.layout_content_t1, parent, false), mListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // 根据不同ViewHolder和不同List<position>进行加载
        Object data = favoriteData.get(position);
        if (holder instanceof T2WithIconViewHolder) {
            if (data instanceof DoubanNewsPosts) {
                DoubanNewsPosts posts = (DoubanNewsPosts) data;
                Glide.with(context)
                        .load(posts.getThumbs().get(0).getMedium().getUrl())
                        .asBitmap()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(((T2WithIconViewHolder) holder).image);
                ((T2WithIconViewHolder) holder).title.setText(posts.getTitle());
                ((T2WithIconViewHolder) holder).content.setText(posts.getAbs());
            } else if (data instanceof GuokrNewsResult) {
                GuokrNewsResult result = (GuokrNewsResult) data;
                Glide.with(context)
                        .load(result.getImageList().get(0))
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((T2WithIconViewHolder) holder).image);
                ((T2WithIconViewHolder) holder).title.setText(result.getTitle());
                ((T2WithIconViewHolder) holder).content.setText(result.getSummary());
            }
        } else if (holder instanceof T1WithIconViewHolder) {
            ZhihuNewsQuestion question = (ZhihuNewsQuestion) data;
            Glide.with(context)
                    .load(question.getImages().get(0))
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(((T1WithIconViewHolder) holder).image);
            ((T1WithIconViewHolder) holder).title.setText(question.getTitle());
        } else if (holder instanceof T2WithoutIconViewHolder) {
            DoubanNewsPosts posts = (DoubanNewsPosts) data;
            ((T2WithoutIconViewHolder) holder).title.setText(posts.getTitle());
            ((T2WithoutIconViewHolder) holder).content.setText(posts.getAbs());
        } else if (holder instanceof  T1WithoutIconViewHolder) {
            ZhihuNewsQuestion question = (ZhihuNewsQuestion) data;
            ((T1WithoutIconViewHolder) holder).title.setText(question.getTitle());
        }
    }

    class T2WithIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        TextView title;

        TextView content;

        OnRecyclerViewOnClickListener listener;

        public T2WithIconViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.content_image);
            this.title = (TextView) itemView.findViewById(R.id.content_title);
            this.content = (TextView) itemView.findViewById(R.id.content_content);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onIitemClick(v, getLayoutPosition());
        }
    }

    class T1WithIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        TextView title;

        OnRecyclerViewOnClickListener listener;

        public T1WithIconViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.content_image);
            this.title = (TextView) itemView.findViewById(R.id.content_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onIitemClick(v, getLayoutPosition());
        }
    }

    class T2WithoutIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;

        TextView content;

        OnRecyclerViewOnClickListener listener;

        public T2WithoutIconViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.content_title);
            this.content = (TextView) itemView.findViewById(R.id.content_content);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onIitemClick(v, getLayoutPosition());
        }
    }

    class T1WithoutIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        OnRecyclerViewOnClickListener listener;

        public T1WithoutIconViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.content_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onIitemClick(v, getLayoutPosition());
        }
    }

    // 交换位置
    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if (!isMoving) {
            isMoving = true;

            Object fromObject = favoriteData.get(fromPosition);
            Object toObject = favoriteData.get(toPosition);
            if (db == null) {
                db = DatabaseCreator.newInstance().getDb();
            }

            if (db != null) {
                if (fromObject instanceof ZhihuNewsQuestion) {
                    if (toObject instanceof ZhihuNewsQuestion) {
                        ZhihuNewsQuestion fromQuestion = (ZhihuNewsQuestion) fromObject;
                        ZhihuNewsQuestion toQuestion = (ZhihuNewsQuestion) toObject;
                        long favoriteTimeTemp = fromQuestion.getFavoriteTime();
                        fromQuestion.setFavoriteTime(toQuestion.getFavoriteTime());
                        toQuestion.setFavoriteTime(favoriteTimeTemp);
                        final ZhihuNewsQuestion newFromQuestion = fromQuestion;
                        final ZhihuNewsQuestion newToQuestion = toQuestion;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.zhihuNewsDao().update(newFromQuestion);
                                db.zhihuNewsDao().update(newToQuestion);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    } else if (toObject instanceof DoubanNewsPosts) {
                        ZhihuNewsQuestion fromQuestion = (ZhihuNewsQuestion) fromObject;
                        DoubanNewsPosts toPosts = (DoubanNewsPosts) toObject;
                        long favoriteTimeTemp = fromQuestion.getFavoriteTime();
                        fromQuestion.setFavoriteTime(toPosts.getFavoriteTime());
                        toPosts.setFavoriteTime(favoriteTimeTemp);
                        final ZhihuNewsQuestion newFromQuestion = fromQuestion;
                        final DoubanNewsPosts newToPosts = toPosts;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.zhihuNewsDao().update(newFromQuestion);
                                db.doubanNewsDao().update(newToPosts);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    } else if (toObject instanceof GuokrNewsResult) {
                        ZhihuNewsQuestion fromQuestion = (ZhihuNewsQuestion) fromObject;
                        GuokrNewsResult toResult = (GuokrNewsResult) toObject;
                        long favoriteTimeTemp = fromQuestion.getFavoriteTime();
                        fromQuestion.setFavoriteTime(toResult.getFavoriteTime());
                        toResult.setFavoriteTime(favoriteTimeTemp);
                        final ZhihuNewsQuestion newFromQuestion = fromQuestion;
                        final GuokrNewsResult newToResult = toResult;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.zhihuNewsDao().update(newFromQuestion);
                                db.guokrNewsDao().update(newToResult);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    }
                } else if (fromObject instanceof DoubanNewsPosts) {
                    if (toObject instanceof ZhihuNewsQuestion) {
                        DoubanNewsPosts fromPosts = (DoubanNewsPosts) fromObject;
                        ZhihuNewsQuestion toQuestion = (ZhihuNewsQuestion) toObject;
                        long favoriteTimeTemp = fromPosts.getFavoriteTime();
                        fromPosts.setFavoriteTime(toQuestion.getFavoriteTime());
                        toQuestion.setFavoriteTime(favoriteTimeTemp);
                        final DoubanNewsPosts newFromPosts = fromPosts;
                        final ZhihuNewsQuestion newToQuestion = toQuestion;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.doubanNewsDao().update(newFromPosts);
                                db.zhihuNewsDao().update(newToQuestion);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    } else if (toObject instanceof DoubanNewsPosts) {
                        DoubanNewsPosts fromPosts = (DoubanNewsPosts) fromObject;
                        DoubanNewsPosts toPosts = (DoubanNewsPosts) toObject;
                        long favoriteTimeTemp = fromPosts.getFavoriteTime();
                        fromPosts.setFavoriteTime(toPosts.getFavoriteTime());
                        toPosts.setFavoriteTime(favoriteTimeTemp);
                        final DoubanNewsPosts newFromPosts = fromPosts;
                        final DoubanNewsPosts newToPosts = toPosts;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.doubanNewsDao().update(newFromPosts);
                                db.doubanNewsDao().update(newToPosts);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    } else if (toObject instanceof GuokrNewsResult) {
                        DoubanNewsPosts fromPosts = (DoubanNewsPosts) fromObject;
                        GuokrNewsResult toResult = (GuokrNewsResult) toObject;
                        long favoriteTimeTemp = fromPosts.getFavoriteTime();
                        fromPosts.setFavoriteTime(toResult.getFavoriteTime());
                        toResult.setFavoriteTime(favoriteTimeTemp);
                        final DoubanNewsPosts newFromPosts = fromPosts;
                        final GuokrNewsResult newToResult = toResult;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.doubanNewsDao().update(newFromPosts);
                                db.guokrNewsDao().update(newToResult);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    }
                } else if (fromObject instanceof GuokrNewsResult) {
                    if (toObject instanceof ZhihuNewsQuestion) {
                        GuokrNewsResult fromResult = (GuokrNewsResult) fromObject;
                        ZhihuNewsQuestion toQuestion = (ZhihuNewsQuestion) toObject;
                        long favoriteTimeTemp = fromResult.getFavoriteTime();
                        fromResult.setFavoriteTime(toQuestion.getFavoriteTime());
                        toQuestion.setFavoriteTime(favoriteTimeTemp);
                        final GuokrNewsResult newFromResult = fromResult;
                        final ZhihuNewsQuestion newToQuestion = toQuestion;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.guokrNewsDao().update(newFromResult);
                                db.zhihuNewsDao().update(newToQuestion);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    } else if (toObject instanceof DoubanNewsPosts) {
                        GuokrNewsResult fromResult = (GuokrNewsResult) fromObject;
                        DoubanNewsPosts toPosts = (DoubanNewsPosts) toObject;
                        long favoriteTimeTemp = fromResult.getFavoriteTime();
                        fromResult.setFavoriteTime(toPosts.getFavoriteTime());
                        toPosts.setFavoriteTime(favoriteTimeTemp);
                        final GuokrNewsResult newFromResult = fromResult;
                        final DoubanNewsPosts newToPosts = toPosts;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.guokrNewsDao().update(newFromResult);
                                db.doubanNewsDao().update(newToPosts);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    } else if (toObject instanceof GuokrNewsResult) {
                        GuokrNewsResult fromResult = (GuokrNewsResult) fromObject;
                        GuokrNewsResult toResult = (GuokrNewsResult) toObject;
                        long favoriteTimeTemp = fromResult.getFavoriteTime();
                        fromResult.setFavoriteTime(toResult.getFavoriteTime());
                        toResult.setFavoriteTime(favoriteTimeTemp);
                        final GuokrNewsResult newFromResult = fromResult;
                        final GuokrNewsResult newToResult = toResult;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.guokrNewsDao().update(newFromResult);
                                db.guokrNewsDao().update(newToResult);
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }).start();
                    }
                }
            }

            Collections.swap(favoriteData, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    // 移除数据
    @Override
    public void onItemRemoved(int position) {
        if (!isRemoving) {
            isRemoving = true;
            if (db == null) {
                db = DatabaseCreator.newInstance().getDb();
            }

            if (db != null) {
                final Object object = favoriteData.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (object instanceof ZhihuNewsQuestion) {
                            ZhihuNewsQuestion question = (ZhihuNewsQuestion) object;
                            question.setFavorite(false);
                            db.zhihuNewsDao().update(question);
                        } else if (object instanceof DoubanNewsPosts) {
                            DoubanNewsPosts posts = (DoubanNewsPosts) object;
                            posts.setFavorite(false);
                            db.doubanNewsDao().update(posts);
                        } else if (object instanceof GuokrNewsResult) {
                            GuokrNewsResult result = (GuokrNewsResult) object;
                            result.setFavorite(false);
                            db.guokrNewsDao().update(result);
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                }).start();
            }

            favoriteData.remove(position);
            notifyItemRemoved(position);
        }
    }
}
