package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.project1.DBHelper.DBHelper;
import com.edu.project1.Models.ImportItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportDao {
    private SQLiteDatabase db;
    private List<ImportItems>list;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ImportDao(Context context) {
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insert(ImportItems obj){
        ContentValues values=new ContentValues();
        values.put("tenHang",obj.getTenHang());
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("soLuongNhap",obj.getSoLuongNhap());
        values.put("donGia",obj.getDonGia());
        values.put("ngayNhapHang",sdf.format(obj.getNgayNhapHang()));
        values.put("ngaySanXuat",sdf.format(obj.getNgaySanXuat()));
        values.put("maLoaiHang",obj.getMaLoaiHang());
        values.put("username",obj.getUsername());
        return db.insert("NhapHang",null,values);
    }
    public int update(ImportItems obj){
        ContentValues values=new ContentValues();
        values.put("tenHang",obj.getTenHang());
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("soLuongNhap",obj.getSoLuongNhap());
        values.put("donGia",obj.getDonGia());
        values.put("ngayNhapHang",sdf.format(obj.getNgayNhapHang()));
        values.put("ngaySanXuat",sdf.format(obj.getNgaySanXuat()));
        values.put("maLoaiHang",obj.getMaLoaiHang());
        values.put("username",obj.getUsername());
        return db.update("NhapHang",values,"maNhapHang=?",new String[]{String.valueOf(obj.getMaNhapHang())});
    }
    public int delete(String id){
        return db.delete("NhapHang","maNhapHang=?",new String[]{id});
    }
    @SuppressLint("Range")
    private List<ImportItems>getData(String sql, String...selectionArgs){
        list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ImportItems obj=new ImportItems();
            obj.setMaNhapHang(c.getInt(c.getColumnIndex("maNhapHang")));
            obj.setTenHang(c.getString(c.getColumnIndex("tenHang")));
            obj.setTenLoaiHang(c.getString(c.getColumnIndex("tenLoaiHang")));
            obj.setSoLuongNhap(c.getInt(c.getColumnIndex("soLuongNhap")));
            obj.setDonGia(c.getFloat(c.getColumnIndex("donGia")));
            try {
                obj.setNgayNhapHang(sdf.parse(c.getString(c.getColumnIndex("ngayNhapHang"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                obj.setNgaySanXuat(sdf.parse(c.getString(c.getColumnIndex("ngaySanXuat"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            obj.setMaLoaiHang(c.getInt(c.getColumnIndex("maLoaiHang")));
            obj.setUsername(c.getString(c.getColumnIndex("username")));
            list.add(obj);

        }
        return list;
    }

    public boolean checkHang(String name, String username){
        Cursor c=db.rawQuery("select * from NhapHang where tenHang=? and username=?",new String[]{name,username});
        if (c.getCount()!=0){
            return true;
        }else {
            return false;
        }
    }

    public ImportItems getById(String id){
        String sql="select * from NhapHang where maNhapHang=?";
        list=getData(sql,id);
        return list.get(0);
    }
    public List<ImportItems>getAllByUsername(String id){
        String sql="select * from NhapHang where username=?";
        return getData(sql,id);
    }
    public List<ImportItems>getAllByMaLoaiHang(String id){
        String sql="select * from NhapHang where maLoaiHang=?";
        return getData(sql,id);
    }
//    search theo tên
    @SuppressLint("Range")
    public List<ImportItems>searchByName(String name){
        String sql="select * from NhapHang where tenHang=?";
        return getData(sql,name);
}
    public List<ImportItems>getAllByUserGroupByMaLoai(String id){
        String sql="select * from NhapHang where username=? group by tenloaihang" ;
        return getData(sql,id);
    }
}
