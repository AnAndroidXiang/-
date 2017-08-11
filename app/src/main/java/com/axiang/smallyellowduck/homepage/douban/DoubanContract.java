package com.axiang.smallyellowduck.homepage.douban;

import com.axiang.smallyellowduck.app.BasePresenter;
import com.axiang.smallyellowduck.app.BaseView;
import com.axiang.smallyellowduck.data.douban.DoubanNewsPosts;

import java.util.List;

/**
 * Created by a2389 on 2017/7/13.
 */

public interface DoubanContract {

    interface Presenter extends BasePresenter {

        // 请求数据
        void loadPosts(long date, boolean clearing);

        // 刷新数据
        void onrefresh();

        // 加载更多
        void loadMore(long date);

        // 显示详情
        void startReading(int position);
    }

    interface View extends BaseView<Presenter> {

        // 停止加载
        void stopLoading();

        // 加载成功
        void showResults(List<DoubanNewsPosts> postsList);

        // 加载失败
        void showError(Exception e);

        // 从指定日期的数据处加载
        void loadDataFromAssignDate(long date);
    }
}
