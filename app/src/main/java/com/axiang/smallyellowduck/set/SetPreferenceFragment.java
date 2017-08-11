package com.axiang.smallyellowduck.set;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.axiang.smallyellowduck.R;

/**
 * Created by a2389 on 2017/8/11.
 */

public class SetPreferenceFragment extends PreferenceFragmentCompat implements SetContract.View{

    private SetContract.Presenter presenter;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.set_preference_fragment);

        initViews(getView());
    }

    @Override
    public void initViews(View view) {
        findPreference("night_mode").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean value = (boolean) newValue;
                presenter.showNightMode(value);
                return true;
            }
        });
    }

    @Override
    public void setPresenter(SetContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
