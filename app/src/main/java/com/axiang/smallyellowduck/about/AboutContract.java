package com.axiang.smallyellowduck.about;

import com.axiang.smallyellowduck.app.BasePresenter;
import com.axiang.smallyellowduck.app.BaseView;

/**
 * Created by a2389 on 2017/7/30.
 */

public interface AboutContract {

    interface Presenter extends BasePresenter {

        // 在应用商店中评分
        void rate();
        // 在GitHub上关注我
        void followOnGithub();
        // 在知乎上关注我
        void followOnZhihu();
        // 通过邮件反馈
        void feedback();
        // 捐赠
        void donate();
    }

    interface View extends BaseView<Presenter> {

        // 如果用户设备没有安装商店应用，提示此错误
        void showRateError();
        // 如果用户设备没有安装邮件应用，提示此错误
        void showFeedbackError();
        // 如果用户没有安装浏览器，提示此错误
        void showBrowserNotFoundError();

    }
}
