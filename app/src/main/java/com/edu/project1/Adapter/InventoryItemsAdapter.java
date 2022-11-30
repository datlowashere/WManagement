package com.edu.project1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.project1.Dao.ExportDao;
import com.edu.project1.Dao.ReportDao;
import com.edu.project1.Models.ExportItems;
import com.edu.project1.Models.InventoryItems;
import com.edu.project1.R;

import java.util.List;

public class InventoryItemsAdapter extends BaseAdapter {
    private List<InventoryItems>list;
    private Context context;

    public InventoryItemsAdapter(List<InventoryItems> list, Context context) {
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
        TextView tvName,tvType,tvAmout;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewOfItem viewOfItem;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_inventory_item,parent,false);
            viewOfItem=new viewOfItem();
            viewOfItem.tvName=view.findViewById(R.id.tvTenHangTon);
            viewOfItem.tvAmout=view.findViewById(R.id.tvSoLuongTon);
            viewOfItem.tvType=view.findViewById(R.id.tvLoaiHangTon);
            view.setTag(viewOfItem);
        }else{
            viewOfItem=(InventoryItemsAdapter.viewOfItem) view.getTag();
        }
        InventoryItems obj=list.get(position);
        viewOfItem.tvName.setText("Tên: "+obj.getTenHang());
        viewOfItem.tvType.setText("Loại: "+obj.getTenLoai());
        viewOfItem.tvAmout.setText("Số lượng: "+obj.getSoLuongTon());
        return view;
    }
}
