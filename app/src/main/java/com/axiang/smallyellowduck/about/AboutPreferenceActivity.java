package com.axiang.smallyellowduck.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.axiang.smallyellowduck.R;

/**
 * Created by a2389 on 2017/7/30.
 */

public class AboutPreferenceActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private AboutPreferenceFragment aboutFragment;

    private AboutPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("night_mode", false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        initViews();

        aboutFragment = new AboutPreferenceFragment();
        presenter = new AboutPresenter(this, aboutFragment, getDelegate());
        aboutFragment.setPresenter(presenter);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_about, aboutFragment).commit();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.about_toolbar);
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
