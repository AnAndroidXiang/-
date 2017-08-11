package com.axiang.smallyellowduck.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.app.GlobalContants;
import com.axiang.smallyellowduck.interfaze.FavoriteToDetailListener;

/**
 * Created by a2389 on 2017/8/5.
 */

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteFragment favoriteFragment;

    private FavoritePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getBoolean("night_mode", false)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_favorite);

        if (savedInstanceState != null) {
            favoriteFragment = (FavoriteFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, GlobalContants.FAVORITE_FRAGMENT);
        } else {
            favoriteFragment = FavoriteFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.favorite_container, favoriteFragment).commit();
        }

        presenter = new FavoritePresenter(this, favoriteFragment);
        presenter.setActivityListener(new FavoriteToDetailListener() {

            @Override
            public void openDetailFromFavorite(Intent intent) {
                startDetailFromFavorite(intent);
            }
        });
        favoriteFragment.setPresenter(presenter);
    }

    public void startDetailFromFavorite(Intent intent) {
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 2) {
                if (!data.getBooleanExtra("isFavorited", true)) {
                    favoriteFragment.getAdapter().onItemRemoved(data.getIntExtra("position", 0));
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (favoriteFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    GlobalContants.FAVORITE_FRAGMENT, favoriteFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
