package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.project1.DBHelper.DBHelper;
import com.edu.project1.Models.ExportItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExportDao {
    private SQLiteDatabase db;
    private List<ExportItems> list;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ExportDao(Context context) {
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }


    public long insert(ExportItems obj){
        ContentValues values=new ContentValues();
        values.put("tenHang",obj.getTenHang());
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("soLuongNhap",obj.getSoLuongNhap());
        values.put("soLuongXuat",obj.getSoLuongXuat());
        values.put("donGia",obj.getDonGia());
        values.put("donGiaXuat",obj.getDonGiaXuat());
        values.put("ngayXuatHang",sdf.format(obj.getNgayXuatHang()));
        values.put("maNhapHang",obj.getMaNhapHang());
        values.put("username",obj.getUsername());
        return db.insert("XuatHang",null,values);
    }
    public int update(ExportItems obj){
        ContentValues values=new ContentValues();
        values.put("tenHang",obj.getTenHang());
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("soLuongNhap",obj.getSoLuongNhap());
        values.put("soLuongXuat",obj.getSoLuongXuat());
        values.put("donGia",obj.getDonGia());
        values.put("donGiaXuat",obj.getDonGiaXuat());
        values.put("ngayXuatHang",sdf.format(obj.getNgayXuatHang()));
        values.put("maNhapHang",obj.getMaNhapHang());
        values.put("username",obj.getUsername());
        return db.update("XuatHang",values,"maXuatHang=?",new String[]{String.valueOf(obj.getMaXuatHang())});
    }
    public int delete(String id){
        return db.delete("XuatHang","maXuatHang=?",new String[]{id});
    }
    @SuppressLint("Range")
    private List<ExportItems>getData(String sql, String...selectionArgs){
        list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ExportItems obj=new ExportItems();
            obj.setMaXuatHang(c.getInt(c.getColumnIndex("maXuatHang")));
            obj.setTenHang(c.getString(c.getColumnIndex("tenHang")));
            obj.setTenLoaiHang(c.getString(c.getColumnIndex("tenLoaiHang")));
            obj.setSoLuongNhap(c.getInt(c.getColumnIndex("soLuongNhap")));
            obj.setSoLuongXuat(c.getInt(c.getColumnIndex("soLuongXuat")));
            obj.setDonGia(c.getFloat(c.getColumnIndex("donGia")));
            obj.setDonGiaXuat(c.getFloat(c.getColumnIndex("donGiaXuat")));
            try {
                obj.setNgayXuatHang(sdf.parse(c.getString(c.getColumnIndex("ngayXuatHang"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            obj.setMaNhapHang(c.getInt(c.getColumnIndex("maNhapHang")));
            obj.setUsername(c.getString(c.getColumnIndex("username")));
            list.add(obj);

        }
        return list;
    }

    public boolean checkHang(String name, String username){
        Cursor c=db.rawQuery("select * from XuatHang where tenHang=?",new String[]{name});
        if (c.getCount()!=0){
            return true;
        }else {
            return false;
        }
    }

    public ExportItems getById(String id){
        String sql="select * from XuatHang where maXuatHang=?";
        list=getData(sql,id);
        return list.get(0);
    }
    public List<ExportItems>getAllByUsername(String id){
        String sql="select * from XuatHang where username=?";
        return getData(sql,id);
    }
    @SuppressLint("Range")
    public List<ExportItems>searchByName(String name){
        String sql="select * from XuatHang where tenHang=?";
        return getData(sql,name);
    }
    public List<ExportItems>getAllByTenLoaiHang(String name){
        String sql="select * from XuatHang where tenLoaiHang=?";
        return getData(sql,name);
    }

}
