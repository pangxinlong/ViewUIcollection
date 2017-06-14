package com.example.pangxinlong.viewuicollection.flow;

import com.example.pangxinlong.viewuicollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pangxinlong on 2017/6/14.
 */

public class FlowActivity extends AppCompatActivity{
    @BindView(R.id.fl_custom)
    FlowLayout mFlowLayout;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mFlowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(FlowActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FlowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
