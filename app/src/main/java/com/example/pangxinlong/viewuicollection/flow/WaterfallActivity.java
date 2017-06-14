package com.example.pangxinlong.viewuicollection.flow;

import com.example.pangxinlong.viewuicollection.MainActivity;
import com.example.pangxinlong.viewuicollection.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by pangxinlong on 2017/6/14.
 */

public class WaterfallActivity extends AppCompatActivity {


    @BindView(R.id.bt_add)
    Button mBtAdd;

    @BindView(R.id.wf_custom)
    WaterfallLayout mWfCustom;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_waterfall);
        mUnbinder = ButterKnife.bind(this);
        setListener();
    }

    private void setListener() {
        final WaterfallLayout.OnItemClickListener onItemClickListener
                = new WaterfallLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(WaterfallActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        };

        mWfCustom.setOnItemClickListener(onItemClickListener);
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
                mWfCustom.setOnItemClickListener(onItemClickListener);
            }
        });
    }

    private void addView() {
        int height;
        do {
            height = (int) (Math.random() * 200);
        } while (height < 50);

        TextView textView = new TextView(WaterfallActivity.this);
        textView.setText("item");
        textView.setBackgroundColor(Color.BLUE);
        ViewGroup.MarginLayoutParams param = new ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.MATCH_PARENT, height);
        param.setMargins(4, 4, 4, 4);
        textView.setLayoutParams(param);
        mWfCustom.addView(textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WaterfallActivity.class);
        context.startActivity(intent);
    }
}
