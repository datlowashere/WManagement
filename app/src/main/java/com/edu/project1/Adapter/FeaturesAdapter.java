package com.edu.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.project1.Models.FeaturesModel;
import com.edu.project1.R;

import java.util.List;

public class FeaturesAdapter extends BaseAdapter {
    private List<FeaturesModel> list;
    private Context context;

    public FeaturesAdapter(List<FeaturesModel> list, Context context) {
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_feature,parent,false);

            TextView tvName=view.findViewById(R.id.tvFeaturesName);
            TextView tvP1=view.findViewById(R.id.tvFeaturesp1);
            TextView tvP2=view.findViewById(R.id.tvFeaturesp2);
            ImageView img=view.findViewById(R.id.imgFeatures);

            FeaturesModel obj=list.get(position);
            tvName.setText(obj.getNameF());
            img.setImageResource(obj.getImg());
            tvP1.setText(obj.getP1());
            tvP2.setText(obj.getP2());
        }
        return view;
    }
}
