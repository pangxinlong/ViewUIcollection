package com.example.pangxinlong.viewuicollection;

import com.example.pangxinlong.viewuicollection.flow.FlowActivity;
import com.example.pangxinlong.viewuicollection.flow.FlowLayout;
import com.example.pangxinlong.viewuicollection.flow.WaterfallActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_custom)
    FlowLayout mFlowLayout;

    @BindView(R.id.lv_viewui)
    ListView mLvViewUI;

    private List<String> mDataList;

    private ListAdapter mListAdapter;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }
    private void initView() {
        mListAdapter = new ListAdapter(this);
        mLvViewUI.setAdapter(mListAdapter);
    }


    private void initData() {
        mDataList = new ArrayList<>();
        mDataList.add("WaterfallActivity");
        mDataList.add("FlowActivity");
        mListAdapter.setData(mDataList);
    }

    private void setListener() {
        mLvViewUI.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        WaterfallActivity.start(MainActivity.this);
                        break;
                    case 1:
                        FlowActivity.start(MainActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
