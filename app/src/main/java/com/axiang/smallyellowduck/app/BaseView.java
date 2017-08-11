package com.axiang.smallyellowduck.app;

import android.view.View;

/**
 * Created by a2389 on 2017/6/22.
 */

public interface BaseView<T> {

    // 初始化视图
    void initViews(View view);

    // 绑定相对应的Presenter
    void setPresenter(T presenter);
}
