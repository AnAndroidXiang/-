package com.axiang.smallyellowduck.interfaze;

/**
 * Created by a2389 on 2017/8/3.
 */

public interface OnFooterErrorListener {

    // 异常时隐藏progressbar并修改content
    void error(int viewVisibility, String content);
}
