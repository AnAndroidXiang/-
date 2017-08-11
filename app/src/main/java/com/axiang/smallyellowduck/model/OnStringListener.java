package com.axiang.smallyellowduck.model;

import com.android.volley.VolleyError;

/**
 * Created by a2389 on 2017/6/28.
 */

public interface OnStringListener {

    // 请求成功时回调
    void onSuccess(String result);

    // 请求失败时回调
    void onError(VolleyError error);
}
