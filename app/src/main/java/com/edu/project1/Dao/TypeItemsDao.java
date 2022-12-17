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
    public  long insert(TypeItems obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        values.put("username",obj.getUsername());
        return db.insert("LoaiHang",null,values);
    }
    public int update(TypeItems obj){
        ContentValues values=new ContentValues();
        values.put("tenLoaiHang",obj.getTenLoaiHang());
        return db.update("LoaiHang",values,"maLoaiHang=?",new String[]{String.valueOf(obj.getMaLoaiHang())});
    }
    public int delete(String id){
        return db.delete("LoaiHang","maLoaiHang=?",new String[]{id});
    }
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
    public boolean checkTenLoai(String name,String username){
        Cursor c=db.rawQuery("select * from LoaiHang where tenLoaiHang=? and username=?",new String[]{name,username});
        if (c.getCount()!=0){
            return true;
        }else {
            return false;
        }
}
    public TypeItems getByID(String id){
        String sql="select * from LoaiHang where maLoaiHang=?";
        list=getData(sql,id);
        return list.get(0);
    }
    public List<TypeItems> getAllByUser(String id){
        String sql="select * from LoaiHang where username=?";
        return getData(sql,id);
    }
    @SuppressLint("Range")
    public List<TypeItems> searchByName(String name){
        String sql="select * from LoaiHang where tenLoaiHang =?";
        return getData(sql,name);
    }
}
