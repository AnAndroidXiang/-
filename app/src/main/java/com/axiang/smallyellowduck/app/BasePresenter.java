package com.axiang.smallyellowduck.app;

/**
 * Created by a2389 on 2017/6/22.
 */

public interface BasePresenter {

    // 获取数据并再view中展示，todo-mvp项目中在framgent里调用的时机为onResume()方法
    void start();
}
