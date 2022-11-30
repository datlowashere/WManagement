package com.edu.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.project1.Models.ImportItems;
import com.edu.project1.R;

import java.util.List;

public class SpinnerItemsAdapter extends BaseAdapter {
    private List<ImportItems>list;
    private Context context;

    public SpinnerItemsAdapter(List<ImportItems> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class viewOfItems{
        TextView tvSingleSpinner;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewOfItems holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_single_spinner,parent,false);
            holder=new viewOfItems();
            holder.tvSingleSpinner=view.findViewById(R.id.tvSingleSpinner);
            view.setTag(holder);
        }else {
            holder=(viewOfItems) view.getTag();
        }
        ImportItems obj=list.get(position);
        holder.tvSingleSpinner.setText(obj.getTenHang());

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_animation);
        view.startAnimation(animation);
        return view;
    }


}
