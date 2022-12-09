package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.project1.DBHelper.DBHelper;
import com.edu.project1.Models.TypeItems;

import java.util.ArrayList;
import java.util.List;

public class TypeItemsDao {

    private SQLiteDatabase db;
    private List<TypeItems>list;
    public TypeItemsDao(Context context) {
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getReadableDatabase();
    }
//    Thêm loại hàng
    public  long insert(TypeItems obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("username",obj.getUsername());
        return db.insert("LoaiHang",null,values);
    }
//    Sửa loại hàng
    public int update(TypeItems obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        return db.update("LoaiHang",values,"maLoaiHang=?",new String[]{String.valueOf(obj.getMaLoaiHang())});
    }
//    xóa loại hàng
    public int delete(String id){
        return db.delete("LoaiHang","maLoaiHang=?",new String[]{id});
    }
//    Lấy data nhiều tham số
    @SuppressLint("Range")
    private List<TypeItems> getData(String sql, String...selectionArgs){
        list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            TypeItems obj=new TypeItems();
            obj.setMaLoaiHang(c.getInt(c.getColumnIndex("maLoaiHang")));
            obj.setTenLoaiHang(c.getString(c.getColumnIndex("tenLoaiHang")));
            obj.setUsername(c.getString(c.getColumnIndex("username")));
            list.add(obj);
        }
        return list;
    }
//    Check tên loại hàng
    public boolean checkTenLoai(String name){
        Cursor c=db.rawQuery("select * from LoaiHang where tenLoaiHang=?",new String[]{name});
        if (c.getCount()!=0){
            return true;
        }else {
            return false;
        }
}
//    Lấy data theo mã loại
    public TypeItems getByID(String id){
        String sql="select * from LoaiHang where maLoaiHang=?";
        list=getData(sql,id);
        return list.get(0);
    }
//    Lấy toàn bộ ds loại hàng theo user
    public List<TypeItems> getAllByUser(String id){
        String sql="select * from LoaiHang where username=?";
        return getData(sql,id);
    }
//    Search loại hàng theo tên loại hàng
    @SuppressLint("Range")
    public List<TypeItems> searchByName(String name){
        String sql="select * from LoaiHang where tenLoaiHang =?";
        return getData(sql,name);
    }
}
