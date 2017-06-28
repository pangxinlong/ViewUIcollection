package com.example.pangxinlong.viewuicollection.recyclerview;

import com.example.pangxinlong.viewuicollection.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pangxinlong on 2017/6/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements DataChangeListener{


    private Context mContext;

    private List<String> mStringList;

    public MyAdapter(Context context) {
        mContext = context;
        mStringList = new ArrayList<>();
    }

    public void setData(List<String> list) {
        mStringList.clear();
        mStringList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,
            int position) {
        holder.bind(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    @Override
    public void swipe(RecyclerView.ViewHolder viewHolder, int direction) {
        mStringList.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void drag(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
            RecyclerView.ViewHolder target) {
        int currentPosition=viewHolder.getAdapterPosition();
        int targPosition=target.getAdapterPosition();
        Collections.swap(mStringList,currentPosition,targPosition);
        notifyItemMoved(currentPosition,targPosition);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textview;

        public MyViewHolder(View itemView) {
            super(itemView);
            textview= (TextView) itemView.findViewById(R.id.tv_tag_name);
        }

        public void bind(String name) {
            textview.setText(name);
        }
    }


}
