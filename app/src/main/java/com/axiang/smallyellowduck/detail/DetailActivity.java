package com.axiang.smallyellowduck.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.view.KeyEvent;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.app.GlobalContants;
import com.axiang.smallyellowduck.enums.DataType;

/**
 * Created by a2389 on 2017/7/27.
 */

public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;

    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("night_mode", false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);

        // 初始化DetailFragment
        if (savedInstanceState != null) {
            detailFragment = (DetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, GlobalContants.DETAIL_FRAGMENT);
        } else {
            detailFragment = DetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailFragment).commit();
        }
        initViews();
    }

    private void initViews() {

        // 获取传来的数据
        Intent intent = getIntent();

        detailFragment.setActivityResult(intent.getStringExtra("activity_source"));
        detailFragment.setPosition(intent.getIntExtra("position", 0));

        // 初始化DetailPresenter
        presenter = new DetailPresenter(this, detailFragment);
        presenter.setType((DataType) intent.getSerializableExtra("type"));
        presenter.setId(intent.getIntExtra("id", 0));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setBgImageUrl(intent.getStringExtra("bgImageUrl"));

        // 绑定Fragment和Presenter
        detailFragment.setPresenter(presenter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            detailFragment.onKeyDown(keyCode, event);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(detailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, GlobalContants.DETAIL_FRAGMENT, detailFragment);
        }
    }
}
