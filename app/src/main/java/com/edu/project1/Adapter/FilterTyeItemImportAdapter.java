package com.edu.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.project1.Models.ImportItems;
import com.edu.project1.R;

import org.w3c.dom.Text;

import java.util.List;

public class FilterTyeItemImportAdapter extends BaseAdapter {
    private List<ImportItems>list;
    private Context context;

    public FilterTyeItemImportAdapter(List<ImportItems> list, Context context) {
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
        return position;
    }
    public static class viewOfItem{
        TextView tvSingleItem;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewOfItem viewOfItem;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_single_spinner,null);
            viewOfItem=new viewOfItem();
            viewOfItem.tvSingleItem=view.findViewById(R.id.tvSingleSpinner);
            view.setTag(viewOfItem);
        }else{
            viewOfItem=(FilterTyeItemImportAdapter.viewOfItem) view.getTag();
        }
        ImportItems obj=list.get(position);
        viewOfItem.tvSingleItem.setText(obj.getTenLoaiHang());
        return view;
    }
}
