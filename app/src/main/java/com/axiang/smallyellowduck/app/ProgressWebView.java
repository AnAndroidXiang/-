package com.axiang.smallyellowduck.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.axiang.smallyellowduck.util.InfoConstants;

/**
 * Created by a2389 on 2017/8/1.
 */

public class ProgressWebView extends WebView {

    private WebViewProgressBar progressBar;

    private Handler handler;

    private WebView webView;

    private MyWebChromeClient webChromeClient;

    private MyWebClient webClient;

    private Toolbar toolbar;

    private boolean cansetTitle = false;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar = new WebViewProgressBar(context);
        // 设置进度条的size
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        // 刚开始时进度条不可见
        progressBar.setVisibility(GONE);
        // 将进度条添加到WebView中
        addView(progressBar);
        handler = new Handler();
        webView = this;
        initSettings();
    }

    private void initSettings() {

        // 垂直滚动条总是显示白色轨迹底图
        webView.setScrollbarFadingEnabled(true);
        // 支持js
        webView.getSettings().setJavaScriptEnabled(true);
        // 开启DOM
        webView.getSettings().setDomStorageEnabled(true);
        // 设置字符编码
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        // 取消手指控制缩放
        webView.getSettings().setBuiltInZoomControls(false);
        // 开启缓存机制
        webView.getSettings().setAppCacheEnabled(true);
        // 提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        webView.getSettings().setBlockNetworkImage(true);
        // 加载进度条变化
        webChromeClient = new MyWebChromeClient();
        webView.setWebChromeClient(webChromeClient);
        // 网页加载速度优化，先不加载图片，加载完成再判断是否加载图片
        webClient = new MyWebClient();
        webView.setWebViewClient(webClient);
    }

    class MyWebChromeClient extends WebChromeClient {

        /**
         *  加载进度改变的回掉
         * @param view：WebView
         * @param newProgress：新进度
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(100);
                handler.postDelayed(runnable, 200); // 0.2后隐藏ProgressBar
            } else if (progressBar.getVisibility() == GONE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            if (newProgress < 10) {
                newProgress = 10;
            }
            // 不断更新进度条
            progressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }

        /**
         *  获取Web页中的title用来设置自己界面中的title
         *  当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为"找不到该网页"
         * @param view
         * @param title：获取到的title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (cansetTitle) {
                toolbar.setTitle(title);
            }
        }
    }

    class MyWebClient extends WebViewClient {

        /**
         * 加载过程中 拦截加载的地址url
         * @param view
         * @param url：被拦截的url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
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
                            getContext()).getBoolean(
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
            ProgressWebView.this.requestFocus();
            ProgressWebView.this.requestFocusFromTouch();
        }
    }

    /**
     * 刷新界面（此处为加载完成后进度消失）
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(GONE);
        }
    };

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setCansetTitle(boolean cansetTitle) {
        this.cansetTitle = cansetTitle;
    }
}
