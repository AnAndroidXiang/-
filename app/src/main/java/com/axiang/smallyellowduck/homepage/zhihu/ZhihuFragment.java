package com.axiang.smallyellowduck.homepage.zhihu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.adapters.ZhihuAdapter;
import com.axiang.smallyellowduck.app.GlobalContants;
import com.axiang.smallyellowduck.data.zhihu.ZhihuNewsQuestion;
import com.axiang.smallyellowduck.interfaze.OnFooterErrorListener;
import com.axiang.smallyellowduck.interfaze.OnRecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by a2389 on 2017/6/24.
 */

public class ZhihuFragment extends Fragment implements ZhihuContract.View {

    private Context context;

    private static ZhihuContract.Presenter presenter;

    private SwipeRefreshLayout refreshLayout;

    private RecyclerView recyclerView;

    private ZhihuAdapter adapter;

    public ZhihuFragment() {
        super();
    }

    public static ZhihuFragment newInstance() {
        return new ZhihuFragment();
    }

    private boolean refreshing = false;

    private boolean loading = false;

    private static long currentDate = System.currentTimeMillis();

    private OnFooterErrorListener mFooterListener;

    private boolean isError = false;

    public boolean start = true;

    private Snackbar snackbar = null;

    private RelativeLayout errorLayout;

    private Button reload;

    private boolean isInitLoadDataError = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_content, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(start) {
            presenter.start();
            start = false;
        }
    }

    @Override
    public void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (!refreshing) {
                    refreshing = true;
                    presenter.refresh();
                    currentDate = System.currentTimeMillis();
                    refreshLayout.setRefreshing(false);
                    refreshing = false;
                }
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(!refreshing && !loading
                    &&  manager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    loading = true;
                    currentDate = currentDate - GlobalContants.ONE_DAY;
                    presenter.loadMore(currentDate);
                    loading = false;
                }
            }
        });

        errorLayout = (RelativeLayout) view.findViewById(R.id.layout_main_error);
        reload = (Button) view.findViewById(R.id.button_error_to_reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start();
            }
        });
    }

    @Override
    public void setPresenter(ZhihuContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadDataFromAssignDate(long date) {
        currentDate = date;
        presenter.loadPosts(currentDate, true);
    }

    @Override
    public void stopLoading() {
        if (!isInitLoadDataError && start) {
            isInitLoadDataError = true;
            recyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            isError = true;
            adapter.setFooterError(isError);
        }
    }

    @Override
    public void showResults(List<ZhihuNewsQuestion> questionList) {
        if (isInitLoadDataError) {
            recyclerView.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
        }
        if (isError) {
            isError = false;
            adapter.setFooterError(isError);
        }
        if (snackbar == null) {
            initSnackbar();
        }
        if (snackbar.isShown()) {
            snackbar.dismiss();
        }
        if(adapter == null) {
            adapter = new ZhihuAdapter(context, questionList);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                @Override
                public void onIitemClick(View view, int position) {
                    presenter.startReading(position);
                }
            });
            adapter.setmFooterListener(mFooterListener);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setQuestionList(questionList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(Exception e) {
        if (snackbar == null) {
            initSnackbar();
        }
        if (!snackbar.isShown()) {
            snackbar.show();
        }
    }

    private void initSnackbar() {
        snackbar = Snackbar.make(this.getView(), context.getResources().getText(R.string.network_please), Snackbar.LENGTH_SHORT)

                // 点击前往系统网络设置界面
                .setAction(context.getResources().getText(R.string.to_set_network), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        //判断手机系统的版本  即API大于10 就是3.0或以上版本
                        if(android.os.Build.VERSION.SDK_INT>10){
                            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        }else{
                            intent = new Intent();
                            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                            intent.setComponent(component);
                            intent.setAction("android.intent.action.VIEW");
                        }
                        context.startActivity(intent);
                    }
                });
    }
}
