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
    String createTBLoaiHang="create table LoaiHang(" +
            "maLoaiHang integer primary key autoincrement," +
            "tenLoaiHang text," +
            "username text references User(username))";

//    them loai hang
    public  long insert(TypeItems obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("username",obj.getUsername());
        return db.insert("LoaiHang",null,values);
    }

//    sua loai hang
    public int update(TypeItems obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        return db.update("LoaiHang",values,"maLoaiHang=?",new String[]{String.valueOf(obj.getMaLoaiHang())});
    }
//    xoa loai hang
    public int delete(String id){
        return db.delete("LoaiHang","maLoaiHang=?",new String[]{id});
    }
//    lay data nhieu tham so
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

//    lay data theo id
    public TypeItems getByID(String id){
        String sql="select * from LoaiHang where maLoaiHang=?";
        list=getData(sql,id);
        return list.get(0);
    }
//    lay toan bo loai hang theo tung nguoi dung(username)
    public List<TypeItems> getAllByUser(String id){
        String sql="select * from LoaiHang where username=?";
        return getData(sql,id);
    }
//    lay toan bo ten hang theo nguoi dung
    public String[]getArrTenLoai(String id){
        String sql="select tenLoaiHang from LoaiHang where username=?";
        ArrayList<String>arr=new ArrayList<>();
        Cursor c=db.rawQuery(sql,new String[]{id});
        while (c.moveToNext()){
            c.moveToFirst();
            arr.add(c.getString(0));
        }
        return (String[]) arr.toArray();

    }

//    search theo ten
    @SuppressLint("Range")
    public List<TypeItems> searchByName(String name){
        list=new ArrayList<>();
        String sql="select * from LoaiHang where tenLoaiHang =?";
        Cursor c=db.rawQuery(sql,new String[]{name});
        while (c.moveToNext()){
            c.moveToFirst();
            TypeItems obj=new TypeItems();
            obj.setMaLoaiHang(c.getInt(c.getColumnIndex("maLoaiHang")));
            obj.setTenLoaiHang(c.getString(c.getColumnIndex("tenLoaiHang")));
            obj.setUsername(c.getString(c.getColumnIndex("username")));
            list.add(obj);
        }
        return list;
    }

}
