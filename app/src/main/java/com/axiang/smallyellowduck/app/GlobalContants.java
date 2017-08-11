package com.axiang.smallyellowduck.app;

/**
 * Created by a2389 on 2017/7/13.
 */

public class GlobalContants {

    // 格式为有图
    public static final int TYPE_WITH_ICON = 0;

    // 格式为无图
    public static final int TYPE_WITHOUT_ICON = 1;

    // 页尾，加载更多
    public static final int TYPE_FOOTER = 2;

    // 格式为 Title + Content + Icon
    public static final int T2I1 = 3;

    // 格式为 Title + Icon
    public static final int T1I1 = 4;

    // 格式为 Title + Content
    public static final int T2 = 5;

    // 格式为 Title
    public static final int T1 = 6;

    // 从Favorite界面跳转到Detail界面
    public static final int FAVORITE_TO_DETAIL = 7;

    // 从Detail界面返回到Favorite界面
    public static final int DETAIL_TO_FAVORITE = 8;

    // 一天的毫秒数
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;

    // ZhihuFragment
    public static final String ZHIHU_FRAGMENT = "ZhihuFramgent";

    // DoubanFragment
    public static final String DOUBAN_FRAGMENT = "DoubanFragment";

    // GuokrFragment
    public static final String GUOKR_FRAGMENT = "GuokrFramgent";

    // DetailFragment
    public static final String DETAIL_FRAGMENT = "DetailFramgent";

    // FavoriteFragment
    public static final String FAVORITE_FRAGMENT = "FavoriteFragment";
}
