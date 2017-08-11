package com.axiang.smallyellowduck.favorite;

import com.axiang.smallyellowduck.adapters.FavoriteAdapter;
import com.axiang.smallyellowduck.app.BasePresenter;
import com.axiang.smallyellowduck.app.BaseView;

import java.util.List;

/**
 * Created by a2389 on 2017/8/7.
 */

public interface FavoriteContract {

    interface Presenter extends BasePresenter {

        // 加载数据
        void loadPosts();

        // 显示详情
        void startReading(int position);
    }

    interface View extends BaseView<Presenter> {

        // 加载成功
        void showResults(List<Object> postsList);

        // 加载失败
        void showError(Exception e);

        // 没有数据
        void showNoData();

        // 获取FavoriteAdapter
        FavoriteAdapter getAdapter();
    }
}
