package com.axiang.smallyellowduck.set;

import com.axiang.smallyellowduck.app.BasePresenter;
import com.axiang.smallyellowduck.app.BaseView;

/**
 * Created by a2389 on 2017/8/11.
 */

public interface SetContract {

    interface Presenter extends BasePresenter {

        // 进行夜间模式的来回切换
        void showNightMode(boolean nightMode);
    }

    interface View extends BaseView<Presenter> {
    }
}
