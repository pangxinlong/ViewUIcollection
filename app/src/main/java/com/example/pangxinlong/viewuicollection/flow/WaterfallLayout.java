package com.example.pangxinlong.viewuicollection.flow;

import com.example.pangxinlong.viewuicollection.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangxinlong on 2017/6/13.
 * 瀑布流
 */

public class WaterfallLayout extends ViewGroup {

    /**
     * 实现思路
     *
     * 1、在onMeasure中判断父布局widthMeasureSpec与heightMeasureSpec属于那种mode，一种MeasureSpec.EXACTLY有准确值，另一种未知
     * 2、在mode未知时 需循环便利每一个子view，调用measureChild()分别测量每个子view大小
     * 3、用一个List<List<View>>存放有所子view，外层list存放列数，内部list存放列内view个数。
     * 4、当第一行的子view加入时，分成N列存放到对应的列的list中，再用一个lineHeightList存放每列中子view的高度，并获取最大值作为setMeasuredDimension的高度。
     * 5、当非第一行子view加入时，判断ineHeightList中高度最低的列并取出对应list的角标，然后将此列的高度加上新加入的子view高度并更新到lineHeightList中，
     *    同时和maxHeight比较取大者，作为setMeasuredDimension的高度
     */



    int lines;//列数

    List<List<View>> mListColumn;//存储所有view 按列排列

    List<Integer> lineHeightList;//记录每列高度

    public WaterfallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mListColumn = new ArrayList<>();
        lineHeightList = new ArrayList<>();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterFall);
        lines=typedArray.getInteger(R.styleable.WaterFall_column,3);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    int widthSize;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMeasureSpec == MeasureSpec.EXACTLY || heightMeasureSpec == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        } else {

            int measureWidth = widthSize;

            int maxHeight = 0;

            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);//测量所有子view
                int childHeight = childView.getMeasuredHeight();

                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) childView
                        .getLayoutParams();

                if (i / lines == 0) {//第一行
                    lineHeightList.add(childHeight);//添加每一列的高度值
                    maxHeight = Math
                            .max(maxHeight, childHeight + marginLayoutParams.topMargin);//比较最大高度值

                    List<View> list = new ArrayList<>();//增加一列
                    list.add(childView);//将view存储到对应列中的list里面
                    mListColumn.add(list);
                } else { //非第一行
//                    MaxAndMinNum maxAndMinNum = col(lineHeightList);//获取最  最值类
                    int minIndex = getMinColumnIndex();
                    Log.e("minIndex====", minIndex + "");
                    Log.e("maxHeight====", maxHeight + "");
                    maxHeight = Math
                            .max(childHeight + childHeight + marginLayoutParams.topMargin
                                            + lineHeightList.get(minIndex),
                                    maxHeight);//将子view高度与最小高度相加在去最大值
                    lineHeightList
                            .set(minIndex,
                                    childHeight + marginLayoutParams.topMargin + lineHeightList
                                            .get(minIndex));//更新对应位置的高度值

                    //保存view
                    mListColumn.get(minIndex).add(childView);
                }
                //判断高度，计算最终布局高度

            }
            Log.e("Width====", measureWidth + "");
            Log.e("Height====", maxHeight + "");
            setMeasuredDimension(measureWidth, maxHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentTop = 0;
        for (int i = 0; i < mListColumn.size(); i++) {
            List<View> list = mListColumn.get(i);
            for (int j = 0; j < list.size(); j++) {
                View view = list.get(j);
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view
                        .getLayoutParams();
                int left = i * widthSize / lines + marginLayoutParams.leftMargin;
                int top = currentTop + marginLayoutParams.topMargin;
                int right = (i + 1) * widthSize / lines + marginLayoutParams.rightMargin;
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                currentTop = bottom + marginLayoutParams.bottomMargin;
            }
            currentTop = 0;
        }
        mListColumn.clear();
        lineHeightList.clear();
    }


    /**
     * 获取高度最小值的列标
     */
    private int getMinColumnIndex() {
        int maxIndex = 0;
        int minIndex = 0;
        int max = 0;//最大值
        int min = lineHeightList.get(0);//最小值
        for (int i = 0; i < lineHeightList.size(); i++) {
            int num = lineHeightList.get(i);
            if (num < min) {
                min = num;
                minIndex = i;
            }
        }
        return minIndex;
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

//    /**
//     * 存放最大值最小值的类
//     */
//    private MaxAndMinNum getMinColumnIndex(List<Integer> list) {
//        int maxIndex = 0;//最大值
//        int minIndex = 0;//最小值
//        int max = 0;
//        int min = list.get(0);
//        for (int i = 0; i < list.size(); i++) {
//            int num = list.get(i);
//            if (num > max) {
//                max = num;
//                maxIndex = i;
//            }
//
//            if (num < min) {
//                min = num;
//                minIndex = i;
//            }
//        }
//        Log.e("minIndex=", "" + minIndex);
//        Log.e("maxIndex=", "" + maxIndex);
//        MaxAndMinNum maxAndMinNum = new MaxAndMinNum();
//        maxAndMinNum.setMinIndex(minIndex);
//        maxAndMinNum.setMaxIndex(maxIndex);
//        return maxAndMinNum;
//    }
//
//    class MaxAndMinNum {
//
//        private int minIndex;
//
//        private int maxIndex;
//
//        public int getMinIndex() {
//            return minIndex;
//        }
//
//        public void setMinIndex(int minIndex) {
//            this.minIndex = minIndex;
//        }
//
//        public int getMaxIndex() {
//            return maxIndex;
//        }
//
//        public void setMaxIndex(int maxIndex) {
//            this.maxIndex = maxIndex;
//        }
//    }
}
