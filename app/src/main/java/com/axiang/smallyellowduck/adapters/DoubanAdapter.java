package com.axiang.smallyellowduck.adapters;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.app.GlobalContants;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
import com.axiang.smallyellowduck.interfaze.OnFooterErrorListener;
import com.axiang.smallyellowduck.interfaze.OnRecyclerViewOnClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by a2389 on 2017/7/13.
 */

public class DoubanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<DoubanNewsPosts> postsList;

    private LayoutInflater inflater;

    private OnRecyclerViewOnClickListener mListener;

    private OnFooterErrorListener mFooterListener;

    public DoubanAdapter(Context context, List<DoubanNewsPosts> postsList) {
        super();
        this.context = context;
        this.postsList = postsList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setPostsList(List<DoubanNewsPosts> postsList) {
        this.postsList = postsList;
    }

    public void setmListener(OnRecyclerViewOnClickListener mListener) {
        this.mListener = mListener;
    }

    public void setmFooterListener(OnFooterErrorListener mFooterListener) {
        this.mFooterListener = mFooterListener;
    }

    public void setFooterError(boolean isError) {
        if (isError) {
            mFooterListener.error(View.GONE, context.getResources().getText(R.string.load_fail).toString());
        } else {
            mFooterListener.error(View.VISIBLE, context.getResources().getText(R.string.loading).toString());
        }
    }

    // 包含页脚，返回值 + 1
    @Override
    public int getItemCount() {
        return postsList.isEmpty() ? 0 : postsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == postsList.size()) {
            return GlobalContants.TYPE_FOOTER;
        } else {
            return postsList.get(position).getThumbs().isEmpty() ? GlobalContants.TYPE_WITHOUT_ICON : GlobalContants.TYPE_WITH_ICON;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case GlobalContants.TYPE_WITH_ICON:
                return new WithIconViewHolder(inflater.inflate(R.layout.layout_content_i1t2, parent, false), mListener);
            case GlobalContants.TYPE_WITHOUT_ICON:
                return new WithoutIconViewHolder(inflater.inflate(R.layout.layout_content_t2, parent, false), mListener);
            case GlobalContants.TYPE_FOOTER:
                return new FooterViewHolder(inflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // 根据不同ViewHolder处理
        if(holder instanceof WithIconViewHolder) {
            DoubanNewsPosts posts = postsList.get(position);
            Glide.with(context)
                    .load(posts.getThumbs().get(0).getMedium().getUrl())
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(((WithIconViewHolder) holder).image);
            ((WithIconViewHolder) holder).title.setText(posts.getTitle());
            ((WithIconViewHolder) holder).content.setText(posts.getAbs());
        } else if (holder instanceof WithoutIconViewHolder) {
            DoubanNewsPosts posts = postsList.get(position);
            ((WithoutIconViewHolder) holder).title.setText(posts.getTitle());
            ((WithoutIconViewHolder) holder).content.setText(posts.getAbs());
        }
    }

    class WithIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;

        TextView title;

        TextView content;

        OnRecyclerViewOnClickListener listener;

        public WithIconViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
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

    class WithoutIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;

        TextView content;

        OnRecyclerViewOnClickListener listener;

        public WithoutIconViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
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

    class FooterViewHolder extends RecyclerView.ViewHolder {

        ContentLoadingProgressBar loadingProgressBar;

        TextView loadingNow;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.loadingProgressBar = (ContentLoadingProgressBar)
                    itemView.findViewById(R.id.loading_progress_bar);
            this.loadingNow = (TextView) itemView.findViewById(R.id.loading_now);
            mFooterListener = new OnFooterErrorListener() {

                @Override
                public void error(int viewVisibility, String content) {
                    loadingProgressBar.setVisibility(viewVisibility);
                    loadingNow.setText(content);
                }
            };
        }
    }
}
