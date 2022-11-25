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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.project1.Helper.CustomToasts;
import com.edu.project1.Models.ImportItems;
import com.edu.project1.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Handler;

public class ImportItemsAdapter extends BaseAdapter {
    private Context context;
    private List<ImportItems>list;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ImportItemsAdapter(Context context, List<ImportItems> list) {
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
        return position;
    }
    public static class viewOfItem{
        TextView tvMaNhapHang,tvSoLuongNhap,tvDonGia,tvTenHang,tvTenLoaiHang,tvNgaySanXuat,tvNgayNhapHang;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewOfItem holder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_import_item,null);
            holder=new viewOfItem();
            holder.tvMaNhapHang=view.findViewById(R.id.tvMaNhapHang);
            holder.tvSoLuongNhap=view.findViewById(R.id.tvSoLuongNhap);
            holder.tvDonGia=view.findViewById(R.id.tvDonGia);
            holder.tvTenHang=view.findViewById(R.id.tvTenHang);
            holder.tvTenLoaiHang=view.findViewById(R.id.tvTenLoaiHangNhapHang);
            holder.tvNgayNhapHang=view.findViewById(R.id.tvNgayNhapHang);
            holder.tvNgaySanXuat=view.findViewById(R.id.tvNgaySanXuat);
            view.setTag(holder);
        }else{
            holder=(viewOfItem) view.getTag();
        }

        ImportItems obj=list.get(position);

        holder.tvMaNhapHang.setText(""+obj.getMaNhapHang());
        holder.tvSoLuongNhap.setText("Số lượng: "+obj.getSoLuongNhap());
        holder.tvDonGia.setText("Đơn giá: "+obj.getDonGia());
        holder.tvTenHang.setText("Tên Hàng:"+obj.getTenHang());
        holder.tvTenLoaiHang.setText("Loại hàng: "+obj.getTenLoaiHang());
        holder.tvNgaySanXuat.setText("Ngày sản xuất: "+sdf.format(obj.getNgaySanXuat()));
        holder.tvNgayNhapHang.setText("Ngày nhập:"+sdf.format(obj.getNgayNhapHang()));

        Animation animation= AnimationUtils.loadAnimation(context,R.anim.scale_animation);
        view.startAnimation(animation);

        return view;
    }
}
