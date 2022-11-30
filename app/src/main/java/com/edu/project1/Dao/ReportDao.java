package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.project1.DBHelper.DBHelper;
import com.edu.project1.Models.InventoryItems;

import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    private SQLiteDatabase db;
    private List<InventoryItems> list;

    public ReportDao(Context context) {
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }
//    hang ton
    @SuppressLint("Range")
    public List<InventoryItems> getAllHangTon(String name){
        list=new ArrayList<>();
        String sql="select tenHang,tenLoaiHang,soLuongNhap,soLuongXuat,(soLuongNhap-soLuongXuat) as conLai from XuatHang where username=? and conLai>0";
        Cursor c=db.rawQuery(sql,new String[]{name});
        while (c.moveToNext()){
            InventoryItems obj=new InventoryItems();
            obj.setTenHang(c.getString(c.getColumnIndex("tenHang")));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoaiHang")));
            obj.setSoLuongTon(c.getInt(c.getColumnIndex("conLai")));
            list.add(obj);
        };
        return list;
    }
//   tong so luong nhap hang
    public int getTongHangNhap(){
        String sql="select sum(soLuongNhap) as tongSoNhap from NhapHang";
        List<Integer> tong=new ArrayList<>();
        Cursor c=db.rawQuery(sql,null);
        do{
            c.moveToFirst();
            tong.add(c.getInt(0));
        }while (c.moveToNext());
        return tong.get(0);
    }
//    tong so luong xuat hang
    public int getTongHangXuat(){
        String sql="select sum(soLuongXuat) as tongSoXuat from XuatHang";
        List<Integer>tong=new ArrayList<>();
        Cursor c=db.rawQuery(sql,null);
        do{
            c.moveToFirst();
            tong.add(c.getInt(0));
        }while (c.moveToNext());
        return tong.get(0);
    }
//    tong tien nhap hang
    public float getTongTienNhap(){
        String sql="select sum(donGia) from NhapHang";
        List<Float> tong=new ArrayList<>();
        Cursor c=db.rawQuery(sql,null);
        do{
            tong.add(c.getFloat(0));
        }while (c.moveToNext());
        return tong.get(0);
    }
// tong tien xuat
    public float getTongTienXuat(){
        String sql="select sum(donGiaXuat) tongTienXuat from XuatHang";
        List<Float> tong=new ArrayList<>();
        Cursor c=db.rawQuery(sql,null);
        do{
            tong.add(c.getFloat(0));
        }while (c.moveToNext());
        return tong.get(0);
    }
//    tien nhap theo giai doan(2 khoang ngay)
    public float getTienNhapTheoGiaiDoan(String tuNgay,String denNgay){
        String sql="select  sum(donGia) as tonTien from NhapHang where ngayNhapHang between ? and ?";
        List<Float> tong=new ArrayList<>();
        Cursor c=db.rawQuery(sql,new String[]{tuNgay,denNgay});
        do{
            tong.add(c.getFloat(0));
        }while (c.moveToNext());
        return tong.get(0);
    }
    //    tien xuat theo giai doan(2 khoang ngay)
    public float getTienXuatTheoGaiDoan(String tuNgay,String denNgay){
        String sql="select  sum(donGiaXuat) as tonTien from XuatHang where ngayXuatHang between ? and ?";
        List<Float> tong=new ArrayList<>();
        Cursor c=db.rawQuery(sql,new String[]{tuNgay,denNgay});
        do{
            tong.add(c.getFloat(0));
        }while (c.moveToNext());
        return tong.get(0);
    }
}
