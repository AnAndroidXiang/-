package com.axiang.smallyellowduck.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.adapters.FavoriteAdapter;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.interfaze.ItemTouchHelperListener;
import com.axiang.smallyellowduck.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by a2389 on 2017/8/7.
 */

public class FavoriteFragment extends Fragment implements FavoriteContract.View {

    private AppCompatActivity appCompatActivity;

    private Context context;

    private FavoriteContract.Presenter presenter;

    private FavoriteAdapter adapter;

    private RecyclerView recyclerView;

    private Toolbar toolbar;

    private boolean start = true;

    public FavoriteFragment() {
        super();
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appCompatActivity = (AppCompatActivity) getActivity();
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void initViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.other_toolbar);
        toolbar.setTitle(context.getResources().getString(R.string.collection));
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.favorite_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            appCompatActivity.onBackPressed();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (start) {
            presenter.start();
            start = false;
        }
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showResults(List<Object> objects) {
        if (adapter == null) {
            adapter = new FavoriteAdapter(context, objects);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
            adapter.setmListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onIitemClick(View view, int position) {
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void showNoData() {
        Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public FavoriteAdapter getAdapter() {
        return adapter;
    }
}
