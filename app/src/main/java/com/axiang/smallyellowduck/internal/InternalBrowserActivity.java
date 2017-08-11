package com.axiang.smallyellowduck.internal;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.app.ProgressWebView;
import com.axiang.smallyellowduck.custom.CustomTabsHelper;

/**
 * Created by a2389 on 2017/8/1.
 */

public class InternalBrowserActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ProgressWebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_internal);
        String url = getIntent().getStringExtra("url");
        initViews();
        webView.loadUrl(url);
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.other_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (ProgressWebView) findViewById(R.id.internal_webview);
        webView.setCansetTitle(true);
        webView.setToolbar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 1, getResources().getString(R.string.open_in_browser)).setIcon(android.R.drawable.ic_menu_compass);
        menu.add(Menu.NONE, 2, 2, getResources().getString(R.string.copy_url)).setIcon(android.R.drawable.ic_menu_save);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case 1:
                CustomTabsHelper.openUrlByCustom(this, webView.getUrl());
                break;
            case 2:
                if (webView.getUrl() != null) {
                    ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("text", webView.getUrl());
                    manager.setPrimaryClip(clipData);
                    Toast.makeText(this, getResources().getString(R.string.copy_url_success), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.copy_url_fail), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {
                webView.goBack();
            } else {
                onBackPressed();
            }
        }
        return true;
    }
}
