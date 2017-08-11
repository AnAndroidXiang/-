package com.axiang.smallyellowduck.about;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;
import android.widget.Toast;

import com.axiang.smallyellowduck.R;

/**
 * Created by a2389 on 2017/7/30.
 */

public class AboutPreferenceFragment extends PreferenceFragmentCompat
        implements AboutContract.View {

    private AboutContract.Presenter presenter;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.about_preference_fragment);

        initViews(getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void initViews(View view) {
        findPreference("rate").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.rate();
                return false;
            }

        });

        findPreference("author").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        findPreference("follow_me_on_github").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.followOnGithub();
                return false;
            }
        });

        findPreference("follow_me_on_zhihu").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.followOnZhihu();
                return false;
            }
        });

        findPreference("feedback").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.feedback();
                return false;
            }
        });

        findPreference("coffee").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.donate();
                return false;
            }
        });
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showRateError() {
        showToast(getResources().getString(R.string.rate_fail));
    }

    @Override
    public void showFeedbackError() {
        showToast(getResources().getString(R.string.feedback_fail));
    }

    @Override
    public void showBrowserNotFoundError() {
        showToast(getResources().getString(R.string.no_browser_found));
    }

    private void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
