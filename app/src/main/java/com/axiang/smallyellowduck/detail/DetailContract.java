package com.axiang.smallyellowduck.detail;

import android.webkit.WebView;

import com.axiang.smallyellowduck.app.BasePresenter;
import com.axiang.smallyellowduck.app.BaseView;

/**
 * Created by a2389 on 2017/7/28.
 */

public interface DetailContract {

    interface Presenter extends BasePresenter {

        // 作为文字分享
        void shareAsText();
        // 添加至收藏夹或者从收藏夹中删除
        void addToOrDeleteFromBookmarks(boolean isBookmarksed);
        // 查询是否已经被收藏了
        boolean queryIfIsBookmarked();
        // 请求数据
        void requestData();
    }

    interface View extends BaseView<Presenter> {

        // 显示加载异常
        void showLoadingError();

        // 显示分享时错误
        void showSharingError();

        // 加载成功
        void showResults(String result);

        // 对于body字段的消息。直接接在url上的内容
        void showResultWithoutBody(String url);

        // 设置顶部大图
        void showCover(String url);

        // 设置标题
        void setTitle(String title);

        // 用户选择在浏览器中打开时，如果没有安装浏览器，显示没有找到浏览器错误
        void showBrowserNotFoundError();

        // 显示已复制文字内容
        void showTextCopied();

        // 显示文字复制失败
        void showCopyTextError();

        // 根据数据库收藏状态的变化来改变收藏图标是否已被收藏
        void setCollectIcon(boolean iscollected);

        // 显示已添加至收藏夹
        void showAddedToBookmarks();

        // 显示已从收藏夹中移除
        void showDeletedFromBookmarks();
    }
}
