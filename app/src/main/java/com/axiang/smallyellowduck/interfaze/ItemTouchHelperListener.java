package com.axiang.smallyellowduck.interfaze;

/**
 * Created by a2389 on 2017/8/7.
 */

public interface ItemTouchHelperListener {

    //数据交换
    void onItemMoved(int fromPosition,int toPosition);

    // 数据删除
    void onItemRemoved(int position);
}
