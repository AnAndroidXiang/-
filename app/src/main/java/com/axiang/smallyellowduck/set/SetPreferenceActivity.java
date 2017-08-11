package com.axiang.smallyellowduck.set;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.axiang.smallyellowduck.R;

/**
 * Created by a2389 on 2017/8/11.
 */

public class SetPreferenceActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SetPreferenceFragment setPreferenceFragment;

    private SetPresenter setPresenter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("night_mode", false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set);
        initView();

        setPreferenceFragment = new SetPreferenceFragment();
        setPresenter = new SetPresenter(getDelegate());
        setPreferenceFragment.setPresenter(setPresenter);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_set, setPreferenceFragment).commit();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
