package com.edu.project1.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.project1.Fragments.TypeItemFragment;
import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.Models.TypeItems;
import com.edu.project1.R;

import java.util.ArrayList;
import java.util.List;

public class TypeItemsAdapter extends BaseAdapter {
    private Context context;
    private List<TypeItems>list;

    public TypeItemsAdapter(Context context, List<TypeItems> list) {
        this.context = context;
        this.list = list;
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

    public static class ViewHolder{
        TextView tvTenLoaiHang;

    }
    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        View view=convertview;
        ViewHolder holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_type_item,null);
            holder=new ViewHolder();
            holder.tvTenLoaiHang=view.findViewById(R.id.tvTenLoaiHang);
            view.setTag(holder);
        }else {
            holder=(ViewHolder) view.getTag();
        }
        TypeItems obj=list.get(position);
        holder.tvTenLoaiHang.setText(obj.getTenLoaiHang());

//        Animation animation=AnimationUtils.loadAnimation(context,R.anim.scale_animation);
//        view.startAnimation(animation);
        return view;
    }


}
