package com.example.pangxinlong.viewuicollection;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangxinlong on 2017/6/13.
 */

public class FlowLayout extends ViewGroup {

    /**
     * 实现思路
     *
     * 1、在onMeasure中判断父布局widthMeasureSpec与heightMeasureSpec属于那种mode，一种MeasureSpec.EXACTLY有准确值，另一种未知
     * 2、在mode未知时 需循环便利每一个子view，调用measureChild()分别测量每个子view大小，并记录每行最大宽度和总高度
     * 3、调用setMeasuredDimension（）方法，完成Measure步骤
     * 4、为了方便布局每一个view，定义一个List<List<View>一行一行的存放所有view
     *
     *
     * 注！ 在onMeasure测量到最后一个子view时，不要忘记计算最后子view所在的行宽和高度
     */

    List<List<View>> mLineLists;//存放所有子view

    List<Integer> HeightList;//记录每行行高

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWith = 0;
        int measuredHeight = 0;
        if (widthMeasureSpec == MeasureSpec.EXACTLY
                || widthMeasureSpec == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else {
            mLineLists = new ArrayList<>();
            HeightList = new ArrayList<>();
            List<View> listRow = new ArrayList();
            mLineLists.add(listRow);
            int currentWidth = 0;//当前行行宽
            int currentLineMaxHeight = 0;//当前行最大高度

            int childWidth;
            int childHeight;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);//测量子view尺寸
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child
                        .getLayoutParams();
                childWidth = marginLayoutParams.leftMargin + child.getMeasuredWidth()
                        + marginLayoutParams.rightMargin;

                childHeight = marginLayoutParams.topMargin + child.getMeasuredHeight()
                        + marginLayoutParams.bottomMargin;

                if (childWidth + currentWidth > widthSize) {//需要换行

                    measuredWith = Math.max(measuredWith, currentWidth);//获取换行前最长宽度
                    measuredHeight += currentLineMaxHeight;//获取换行前高度
//                    mLineLists.add(listRow);
                    HeightList.add(currentLineMaxHeight);

                    listRow = new ArrayList();//新加一行
                    listRow.add(child);
                    mLineLists.add(listRow);

                    //在新的一行中重新给currentWidth，currentLineMaxHeight赋值
                    currentWidth = childWidth;
                    currentLineMaxHeight = childHeight;

                } else {
                    listRow.add(child);
                    currentWidth += childWidth;
                    currentLineMaxHeight = Math.max(currentLineMaxHeight, childHeight);
                }

                if (i == childCount - 1) {
                    measuredWith = Math.max(measuredWith, currentWidth);
                    measuredHeight += currentLineMaxHeight;

                    HeightList.add(measuredHeight);
//                    mLineLists.add(listRow);
                }

            }
            setMeasuredDimension(measuredWith, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top, right, bottom;

        int currentLeft = 0;
        int currentTop = 0;

        for (int i = 0; i < mLineLists.size(); i++) {
            List<View> rowList = mLineLists.get(i);
            for (int j = 0; j < rowList.size(); j++) {
                View childView = rowList.get(j);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView
                        .getLayoutParams();
                left = currentLeft + marginLayoutParams.leftMargin;
                top = currentTop + marginLayoutParams.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight() + marginLayoutParams.bottomMargin;
                childView.layout(left, top, right, bottom);
                currentLeft = right + marginLayoutParams.rightMargin;
            }
            currentLeft = 0;
            currentTop += HeightList.get(i);
        }
        mLineLists.clear();
        HeightList.clear();
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener){
        int childCount=getChildCount();
        for (int i=0;i<childCount;i++){
            View view=getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, finalI);
                }
            });
        }
    }

    interface  OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
