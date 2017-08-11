package com.axiang.smallyellowduck.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.app.ProgressWebView;
import com.axiang.smallyellowduck.custom.CustomTabsHelper;
import com.axiang.smallyellowduck.util.InfoConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by a2389 on 2017/7/28.
 */

public class DetailFragment extends Fragment implements DetailContract.View {

    private Context context;

    private AppCompatActivity appCompatActivity;

    private ImageView detailBgImage;

    private CollapsingToolbarLayout toolbarLayout;

    private Toolbar toolbar;

    private FloatingActionButton share;

    private SwipeRefreshLayout refresh;

    private ProgressWebView webView;

    private DetailContract.Presenter presenter;

    private boolean isStarted = false;

    private boolean refreshing = false;

    private MenuItem menuItem;

    private boolean isCollecting = false;

    private String activityResult;

    private int position;

    private boolean isFavorited = true;

    public DetailFragment() {
        super();
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.appCompatActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStarted) {
            presenter.start();
            isStarted = true;
        }
    }

    public void setActivityResult(String activityResult) {
        this.activityResult = activityResult;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initViews(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.webview_more, menu);
        this.menuItem = menu.findItem(R.id.all_collect);
        if (!isCollecting) {
            isCollecting = true;
            if (presenter.queryIfIsBookmarked()) {
                showAddedToBookmarks();
            }
            isCollecting = false;
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    if (activityResult != null) {
                        if (activityResult.equals("favorite")) {
                            Intent data = new Intent();
                            data.putExtra("isFavorited", isFavorited);
                            data.putExtra("position", position);
                            appCompatActivity.setResult(2, data);
                        }
                    }
                    appCompatActivity.onBackPressed();
                }
                break;
            case R.id.open_in_browser:
                CustomTabsHelper.openUrlByCustom(context, webView.getUrl());
                break;
            case R.id.copy_url:
                if (webView.getUrl() != null) {
                    showTextCopied();
                } else {
                    showCopyTextError();
                }
                break;
            case R.id.all_collect:
                if (!isCollecting) {
                    isCollecting = true;
                    if (!presenter.queryIfIsBookmarked()) {
                        presenter.addToOrDeleteFromBookmarks(true);
                    } else {
                        presenter.addToOrDeleteFromBookmarks(false);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void setCollectIcon(boolean iscollected) {
        if (iscollected) {
            showAddedToBookmarks();
        } else {
            showDeletedFromBookmarks();
        }
        isCollecting = false;
    }

    @Override
    public void initViews(View view) {

        // 初始化CollapsingToolbarLayout
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.Detail_ExpandedToolbar);

        // 初始化Toolbar
        detailBgImage = (ImageView) view.findViewById(R.id.collapsing_bg_image);
        toolbar = (Toolbar) view.findViewById(R.id.collapsing_toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        // 初始化分享操作
        share = (FloatingActionButton) view.findViewById(R.id.floating_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.shareAsText();
            }
        });

        // 初始化主内容
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.detail_refresh);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!refreshing) {
                    refreshing = true;
                    presenter.start();
                    refresh.setRefreshing(false);
                    refreshing = false;
                }
            }
        });

        webView = (ProgressWebView) view.findViewById(R.id.detail_web_view);
        webView.setBackgroundColor(ContextCompat.getColor(context, R.color.viewBackground));
        webView.setWebViewClient(new WebViewClient() {

            /**
             * 加载过程中 拦截加载的地址url
             * @param view
             * @param url：被拦截的url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                CustomTabsHelper.openUrl(context, url);
                return true;
            }

            /**
             * 界面加载结束时调用的方法
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 判断是否开启无图模式，不开启则关闭图片加载阻塞
                webView.getSettings().setBlockNetworkImage(
                        PreferenceManager.getDefaultSharedPreferences(
                                context.getApplicationContext()).getBoolean(
                                InfoConstants.KEY_NO_IMG_MODE, false));
            }

            /**
             *  WebView发生改变时调用
             * @param view
             * @param oldScale
             * @param newScale
             */
            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);
                webView.requestFocus();
                webView.requestFocusFromTouch();
            }
        });
    }

    private void showMessage(String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingError() {
        showMessage(context.getResources().getString(R.string.load_fail));
    }

    @Override
    public void showSharingError() {
        showMessage(context.getResources().getString(R.string.share_fail));
    }

    @Override
    public void showResults(String result) {
        webView.loadDataWithBaseURL("x-data://base", result, "text/html", "utf-8", null);
    }

    @Override
    public void showResultWithoutBody(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void setTitle(String title) {
        toolbarLayout.setTitle(title);
    }

    @Override
    public void showCover(String url) {
        Glide.with(context.getApplicationContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(detailBgImage);
    }

    @Override
    public void showBrowserNotFoundError() {
        showMessage(context.getResources().getString(R.string.no_browser_found));
    }

    @Override
    public void showTextCopied() {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", webView.getUrl());
        manager.setPrimaryClip(clipData);
        showMessage(context.getResources().getString(R.string.copy_url_success));
    }

    @Override
    public void showCopyTextError() {
        showMessage(context.getResources().getString(R.string.copy_url_fail));
    }

    @Override
    public void showAddedToBookmarks() {
        menuItem.setIcon(R.drawable.collected);
        isFavorited = true;
    }

    @Override
    public void showDeletedFromBookmarks() {
        menuItem.setIcon(R.drawable.no_collect);
        isFavorited = false;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack()) {
            //goBack()表示返回WebView的上一页面
            webView.goBack();
        } else {
            if (activityResult != null) {
                if (activityResult.equals("favorite")) {
                    Intent data = new Intent();
                    data.putExtra("isFavorited", isFavorited);
                    data.putExtra("position", position);
                    appCompatActivity.setResult(2, data);
                }
            }
            appCompatActivity.onBackPressed();
        }
    }
}
