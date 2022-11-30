package com.edu.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.project1.Models.ImportItems;
import com.edu.project1.R;

import java.util.List;

public class SpinnerTypeItemsFromImportAdapter extends BaseAdapter {
    private List<ImportItems>list;
    private Context context;

    public SpinnerTypeItemsFromImportAdapter(List<ImportItems> list, Context context) {
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
    public static class viewOfItem{
        TextView tvSingleItem;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewOfItem viewOfItem;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_single_spinner,parent,false);
            viewOfItem=new viewOfItem();
            viewOfItem.tvSingleItem=view.findViewById(R.id.tvSingleSpinner);
            view.setTag(viewOfItem);
        }else{
            viewOfItem=(SpinnerTypeItemsFromImportAdapter.viewOfItem) view.getTag();
        }
        ImportItems obj=list.get(position);
        viewOfItem.tvSingleItem.setText(obj.getTenLoaiHang());
        return view;
    }
}
