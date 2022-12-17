package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.project1.DBHelper.DBHelper;
import com.edu.project1.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private SQLiteDatabase db;
    private List<User> list;
    public UserDao(Context context) {
        DBHelper dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insert(User obj){
        ContentValues values=new ContentValues();
        values.put("username",obj.getUsername());
        values.put("anh",obj.getImg());
        values.put("hoTen",obj.getHoTen());
        values.put("email",obj.getEmail());
        values.put("tenKhoHang",obj.getTenKhoHang());
        values.put("password",obj.getPassword());
        return db.insert("User",null,values);
    }
    public int update(User obj){
        ContentValues values=new ContentValues();
        values.put("anh",obj.getImg());
        values.put("hoTen",obj.getHoTen());
        values.put("email",obj.getEmail());
        values.put("tenKhoHang",obj.getTenKhoHang());
        values.put("password",obj.getPassword());
        return db.update("User",values,"username=?",new String[]{obj.getUsername()});
    }
    public int delete(String id){
        return db.delete("User","username=?",new String[]{id});
    }
    @SuppressLint("Range")
    private List<User> getData(String sql, String...selectionArgs){
        list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            User obj=new User();
            c.moveToFirst();
            obj.setUsername(c.getString(c.getColumnIndex("username")));
            obj.setImg(c.getBlob(c.getColumnIndex("anh")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setEmail(c.getString(c.getColumnIndex("email")));
            obj.setTenKhoHang(c.getString(c.getColumnIndex("tenKhoHang")));
            obj.setPassword(c.getString(c.getColumnIndex("password")));
            list.add(obj);
        }
        return list;
    }
    public User getID(String id){
        String sql="select * from User where username=?";
        list=getData(sql,id);
        return list.get(0);
    }
    public int checkLogin(String username,String password){
        String sql="select * from User where username=? and password=?";
        List<User> list=getData(sql,username,password);
        if(list.size()==0){
            return -1;
        }
        return 1;
    }
    public boolean checkUsername(String username){
        Cursor c=db.rawQuery("select * from User where username=?",new String[]{username});
        if (c.getCount()!=0){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkPass(String pass){
        Cursor c=db.rawQuery("select * from User where password=?",new String[]{pass});
        if(c.getCount()!=0){
            return true;
        }else{
            return false;
        }
    }
}
