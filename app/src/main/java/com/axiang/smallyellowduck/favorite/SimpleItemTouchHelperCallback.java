package com.axiang.smallyellowduck.favorite;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.axiang.smallyellowduck.interfaze.ItemTouchHelperListener;

/**
 * Created by a2389 on 2017/8/7.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperListener listener;

    public SimpleItemTouchHelperCallback(ItemTouchHelperListener listener) {
        this.listener = listener;
    }

    /**
     * 该方法用于返回可以滑动的方向，比如说允许从右到左侧滑，允许上下拖动等
     * 我们一般使用makeMovementFlags(int,int)或makeFlag(int, int)来构造我们的返回值
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 允许上下拖动
        int dragFlags  = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // 只允许从右向左侧滑
        int swipeFlags  = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags , swipeFlags);
    }

    // 该方法返回true时，表示支持长按拖动，即长按ItemView后才可以拖动，默认是返回true
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 该方法返回true时，表示如果用户触摸并左右滑动了View，那么可以执行滑动删除操作
     * 即可以调用到onSwiped()方法
     * 默认是返回true
      */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 当用户拖动一个Item进行上下移动从旧的位置到新的位置的时候会调用该方法
     * 在该方法内，我们可以调用Adapter的notifyItemMoved方法来交换两个ViewHolder的位置
     * 最后返回true，表示被拖动的ViewHolder已经移动到了目的位置
     * 所以，如果要实现拖动交换位置，可以重写该方法（前提是支持上下拖动）
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 回调接口的交换方法
        listener.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //回调接口的移除方法
        listener.onItemRemoved(viewHolder.getAdapterPosition());
    }
}
