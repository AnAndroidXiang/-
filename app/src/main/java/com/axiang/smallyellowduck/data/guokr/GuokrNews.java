package com.axiang.smallyellowduck.data.guokr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/7/17.
 */

public class GuokrNews {

    @Expose
    @SerializedName("now")
    private String now;

    @Expose
    @SerializedName("result")
    private List<GuokrNewsResult> result;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public List<GuokrNewsResult> getResult() {
        return result;
    }

    public void setResult(List<GuokrNewsResult> result) {
        this.result = result;
    }
}
