package com.axiang.smallyellowduck.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
import com.axiang.smallyellowduck.interfaze.OnRecyclerViewOnClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by a2389 on 2017/7/17.
 */

public class GuokrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private LayoutInflater inflater;

    private List<GuokrNewsResult> resultList;

    private OnRecyclerViewOnClickListener mListener;

    public GuokrAdapter(Context context, List<GuokrNewsResult> resultList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resultList = resultList;
    }

    public void setResultList(List<GuokrNewsResult> resultList) {
        this.resultList = resultList;
    }

    public void setmListener(OnRecyclerViewOnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return resultList.isEmpty() ? 0 : resultList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.layout_content_i1t2, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GuokrNewsResult result = resultList.get(position);
        Glide.with(context)
                .load(result.getImageList().get(0))
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(((ViewHolder) holder).image);
        ((ViewHolder) holder).title.setText(result.getTitle());
        ((ViewHolder) holder).content.setText(result.getSummary());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        TextView title;

        TextView content;

        OnRecyclerViewOnClickListener listener;

        public ViewHolder(View itemView, OnRecyclerViewOnClickListener listener) {
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
}
