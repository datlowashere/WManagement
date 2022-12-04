package com.edu.project1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.project1.Dao.ImportDao;
import com.edu.project1.Models.ExportItems;
import com.edu.project1.Models.ImportItems;
import com.edu.project1.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExportItemAdapter extends BaseAdapter {
    private final List<ExportItems>list;
    private final Context context;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ExportItemAdapter(List<ExportItems> list, Context context) {
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
        TextView tvMaXuatHang,tvSoLuongXuat,tvDonGiaXuat,tvTenHangXuat,tvLoaiHangXuat,tvNgayXuatHang;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewOfItem holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_export_item,parent,false);
            holder=new viewOfItem();
            holder.tvMaXuatHang=view.findViewById(R.id.tvMaXuatHang);
            holder.tvSoLuongXuat=view.findViewById(R.id.tvSoLuongXuat);
            holder.tvDonGiaXuat=view.findViewById(R.id.tvDonGiaXuat);
            holder.tvTenHangXuat=view.findViewById(R.id.tvTenHangXuat);
            holder.tvLoaiHangXuat=view.findViewById(R.id.tvTenLoaiHangXuat);
            holder.tvNgayXuatHang=view.findViewById(R.id.tvNgayXuatHang);
            view.setTag(holder);
        }else {
            holder=(ExportItemAdapter.viewOfItem) view.getTag();
        }

        ExportItems obj=list.get(position);
        holder.tvMaXuatHang.setText("Mã: "+" "+obj.getMaXuatHang());
        holder.tvSoLuongXuat.setText("Số lượng:"+" "+obj.getSoLuongXuat());
        holder.tvDonGiaXuat.setText("Giá: "+""+obj.getDonGiaXuat()+""+"đ");
        holder.tvTenHangXuat.setText("Tên: "+""+obj.getTenHang());
        holder.tvLoaiHangXuat.setText("Loại: "+""+obj.getTenLoaiHang());
        holder.tvNgayXuatHang.setText("Ngày xuất:"+""+sdf.format(obj.getNgayXuatHang()));

//        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_animation);
//        view.startAnimation(animation);

        return view;
    }
}
