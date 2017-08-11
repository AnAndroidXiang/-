package com.axiang.smallyellowduck.homepage.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.database.DatabaseCreator;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    private DatabaseCreator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("night_mode", false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "main_fragment");
        } else {
            mainFragment = MainFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, mainFragment).commit();

        creator = DatabaseCreator.newInstance();
        if(!creator.isDatabaseCreated()) {
            creator.createDb(getApplicationContext());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("night_mode", false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mainFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "main_fragment", mainFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
