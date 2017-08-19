package com.axiang.smallyellowduck.about;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.custom.CustomTabsHelper;

/**
 * Created by a2389 on 2017/7/30.
 */

public class AboutPresenter implements AboutContract.Presenter {

    private AppCompatActivity appCompatActivity;

    private AboutContract.View view;

    private AppCompatDelegate delegate;

    public AboutPresenter(AppCompatActivity appCompatActivity, AboutContract.View view, AppCompatDelegate delegate) {
        this.appCompatActivity = appCompatActivity;
        this.view = view;
        this.delegate = delegate;
    }

    @Override
    public void start() {

    }

    @Override
    public void rate() {
    }

    @Override
    public void followOnGithub() {
        CustomTabsHelper.openUrl(appCompatActivity, appCompatActivity.getString(R.string.follow_me_on_github_desc));
    }

    @Override
    public void followOnZhihu() {
        CustomTabsHelper.openUrl(appCompatActivity, appCompatActivity.getString(R.string.follow_me_on_zhihu_desc));
    }

    @Override
    public void feedback() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(appCompatActivity.getString(R.string.sendto)));
            intent.putExtra(Intent.EXTRA_SUBJECT, appCompatActivity.getString(R.string.mail_topic));
            intent.putExtra(Intent.EXTRA_TEXT,
                    appCompatActivity.getString(R.string.device_model) + Build.MODEL + "\n"
                            + appCompatActivity.getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
                            + appCompatActivity.getString(R.string.version));
            appCompatActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            view.showFeedbackError();
        }
    }

    @Override
    public void donate() {
        ClipboardManager manager = (ClipboardManager) appCompatActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", appCompatActivity.getResources().getString(R.string.donate_account));
        manager.setPrimaryClip(clipData);
        Toast.makeText(appCompatActivity, appCompatActivity.getResources().getString(R.string.donate_content), Toast.LENGTH_SHORT).show();
    }
}
