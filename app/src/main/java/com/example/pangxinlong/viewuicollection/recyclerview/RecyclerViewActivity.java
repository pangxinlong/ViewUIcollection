package com.example.pangxinlong.viewuicollection.recyclerview;

import com.example.pangxinlong.viewuicollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pangxinlong on 2017/6/27.
 */

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;

    private List<String> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_recyclerview);
        mUnbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataList.add(i + "");
        }

    }

    private void initView() {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                OrientationHelper.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        MyAdapter myAdapter = new MyAdapter(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallBack(myAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setData(mDataList);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        context.startActivity(intent);
    }
}
