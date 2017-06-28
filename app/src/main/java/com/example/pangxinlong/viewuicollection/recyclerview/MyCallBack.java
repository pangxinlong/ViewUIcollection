package com.example.pangxinlong.viewuicollection.recyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by pangxinlong on 2017/6/27.
 */

public class MyCallBack extends ItemTouchHelper.Callback {

    private DataChangeListener mDataChangeListener;

    public MyCallBack(
            DataChangeListener dataChangeListener) {
        mDataChangeListener = dataChangeListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipe=ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int drag= ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return  makeMovementFlags(drag,swipe);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
            RecyclerView.ViewHolder target) {
        if (mDataChangeListener!=null){
            mDataChangeListener.drag(recyclerView,viewHolder,target);
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mDataChangeListener!=null){
            mDataChangeListener.swipe(viewHolder,direction);
        }
    }

    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx,
            float animateDy) {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }
}
