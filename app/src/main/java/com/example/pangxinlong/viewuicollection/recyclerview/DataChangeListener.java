package com.example.pangxinlong.viewuicollection.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by pangxinlong on 2017/6/27.
 */

public interface DataChangeListener {

    //swipe滑动
    void swipe(RecyclerView.ViewHolder viewHolder, int direction);

    //drag拖动
    void drag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
            RecyclerView.ViewHolder target);
}
