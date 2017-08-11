package com.axiang.smallyellowduck.homepage.guokr;

import com.axiang.smallyellowduck.app.BasePresenter;
import com.axiang.smallyellowduck.app.BaseView;
import com.axiang.smallyellowduck.data.guokr.GuokrNewsResult;

import java.util.List;

/**
 * Created by a2389 on 2017/7/18.
 */

public interface GuokrContract {

    interface Presenter extends BasePresenter {

        // 加载数据
        void loadPosts();

        // 刷新数据
        void onrefresh();

        // 显示详情
        void startReading(int position);
    }

    interface View extends BaseView<Presenter> {

        // 加载成功
        void showResults(List<GuokrNewsResult> resultList);

        // 停止加载
        void stopLoading();

        // 加载异常
        void showError(Exception e);
    }
}
