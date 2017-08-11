package com.axiang.smallyellowduck.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.axiang.smallyellowduck.app.VolleySingleton;

/**
 * Created by a2389 on 2017/6/28.
 */

public class StringModel {

    private Context context;

    public StringModel(Context context) {
        this.context = context;
    }

    public void load(String url, final OnStringListener listener) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }

}
