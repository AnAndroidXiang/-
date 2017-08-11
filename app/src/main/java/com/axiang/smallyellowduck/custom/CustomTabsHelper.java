package com.axiang.smallyellowduck.custom;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.axiang.smallyellowduck.R;
import com.axiang.smallyellowduck.internal.InternalBrowserActivity;
import com.axiang.smallyellowduck.util.InfoConstants;

/**
 * Created by a2389 on 2017/7/31.
 */

public class CustomTabsHelper {

    public static void openUrl(Context context, String url) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (sp.getBoolean(InfoConstants.KEY_CHROME_CUSTOM_TABS, true)) {
            context.startActivity(new Intent(context, InternalBrowserActivity.class).putExtra("url", url));
        } else {
            openUrlByCustom(context, url);
        }
    }

    public static void openUrlByCustom(Context context, String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
            builder.setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.back));
            builder.build().launchUrl(context, Uri.parse(url));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getResources().getString(R.string.no_browser_found), Toast.LENGTH_SHORT).show();
        }
    }
}
