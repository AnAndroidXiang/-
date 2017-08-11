package com.axiang.smallyellowduck.data.zhihu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a2389 on 2017/6/29.
 */

public class ZhihuNews {

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("stories")
    private List<ZhihuNewsQuestion> questionList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuNewsQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<ZhihuNewsQuestion> questionList) {
        this.questionList = questionList;
    }

}
