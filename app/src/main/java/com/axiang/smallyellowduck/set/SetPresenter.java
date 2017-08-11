package com.axiang.smallyellowduck.set;

import android.support.v7.app.AppCompatDelegate;

/**
 * Created by a2389 on 2017/8/11.
 */

public class SetPresenter implements SetContract.Presenter {

    private AppCompatDelegate delegate;

    public SetPresenter(AppCompatDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void start() {

    }

    @Override
    public void showNightMode(boolean nightMode) {
        if (nightMode) {
            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
