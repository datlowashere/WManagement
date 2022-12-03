package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.edu.project1.DBHelper.DBHelper;
import com.edu.project1.Models.InventoryItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportDao {
    private List<InventoryItems> list;
    SQLiteDatabase db;
    DBHelper dbHelper;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ReportDao(Context context) {
        this.dbHelper=new DBHelper(context);
    }
//    hang ton
    @SuppressLint("Range")
    public List<InventoryItems> getAllHangTon(String name){
        db=dbHelper.getReadableDatabase();
        list=new ArrayList<>();
        String sql="select tenHang,tenLoaiHang,soLuongNhap,soLuongXuat,(soLuongNhap-soLuongXuat) as conLai from XuatHang where username=? and conLai>0";
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{name});
        while (c.moveToNext()){
            InventoryItems obj=new InventoryItems();
            obj.setTenHang(c.getString(c.getColumnIndex("tenHang")));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoaiHang")));
            obj.setSoLuongTon(c.getInt(c.getColumnIndex("conLai")));
            list.add(obj);
        };
        return list;
    }

//    ds tien nhap theo giai doan
    @SuppressLint("Range")
    public Float[] getDSTienNhapTheoGiaiDoan(String username, String tuNgay, String denNgay){
        db=dbHelper.getReadableDatabase();
        ArrayList<Float>tong=new ArrayList<>();
        String sql="SELECT ngayNhapHang, sum( (donGia * soLuongNhap)) as tien from NhapHang WHERE  username=? and ngayNhapHang BETWEEN ? and ? GROUP BY ngayNhapHang ORDER by ngayNhapHang";
        Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                tong.add(c.getFloat(1));
                c.moveToNext();
            };
        }
        return tong.toArray(new Float[tong.size()]);
    }
    @SuppressLint("Range")
    public String[] getDSNgayNhapHangTheoGiaiDoan(String username, String tuNgay, String denNgay) throws ParseException {
        db=dbHelper.getReadableDatabase();
        ArrayList<String> allDate=new ArrayList<>();
        String sql="SELECT ngayNhapHang as tien from NhapHang WHERE  username=? and ngayNhapHang BETWEEN ? and ? GROUP BY ngayNhapHang ORDER by ngayNhapHang";
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                allDate.add(c.getString(0));
                c.moveToNext();
            };
        }
        return allDate.toArray(new String[allDate.size()]);
    }

    //    ds tien nhap theo giai doan
    public Float[] getDSTienXuatTheoGiaiDoan(String username,String tuNgay,String denNgay){
        db=dbHelper.getReadableDatabase();
        ArrayList<Float>tong=new ArrayList<>();
        String sql="SELECT ngayXuatHang,sum( (donGiaXuat * soLuongXuat)) as tien from XuatHang WHERE  username=? and ngayXuatHang BETWEEN ? and ? GROUP BY ngayXuatHang ORDER by ngayXuatHang ";
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
//        if(c!=null){
//            if(c.moveToFirst()){
//                do{
//                    tong.add((float) c.getInt(1));
//                }while (c.moveToNext());
//            }
//            c.close();
//        }
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                tong.add(c.getFloat(1));
                c.moveToNext();
            };
        }
        return tong.toArray(new Float[tong.size()]);
    }
    @SuppressLint("Range")
    public String[] getDSNgayXuatHangTheoGiaiDoan(String username, String tuNgay, String denNgay) throws ParseException {
        db=dbHelper.getReadableDatabase();
        ArrayList<String> allDate=new ArrayList<>();
        String sql="SELECT ngayXuatHang from XuatHang WHERE username=? and ngayXuatHang BETWEEN ? and ? GROUP BY ngayXuatHang  ORDER by ngayXuatHang";
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
//        if(c!=null){
//            if(c.moveToFirst()){
//                do{
//                    allDate.add(c.getString(0));
//                }while (c.moveToNext());
//            }
//            c.close();
//        }
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                allDate.add(c.getString(0));
                c.moveToNext();
            };
        }

        return allDate.toArray(new String[allDate.size()]);
    }

    //    tien nhap theo giai doan(2 khoang ngay)
    public float getTongTienNhapTheoGiaiDoan(String username,String tuNgay,String denNgay){
        db=dbHelper.getReadableDatabase();
        String sql="select  sum(donGia*soLuongNhap) as tonTien from NhapHang where username=? and ngayNhapHang between ? and ?";
        ArrayList<Float> tong=new ArrayList<>();
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
        while (c.moveToNext()){
            tong.add(c.getFloat(0));
        }
        return tong.get(0);
    }
    //    tien xuat theo giai doan(2 khoang ngay)
    public float getTongTienXuatTheoGaiDoan(String username,String tuNgay,String denNgay){
        db=dbHelper.getReadableDatabase();
        String sql="select  sum(donGiaXuat*soLuongXuat) as tonTien from XuatHang where username=? and ngayXuatHang between ? and ?";
        ArrayList<Float> tong=new ArrayList<>();
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
        while (c.moveToNext()){
            tong.add(c.getFloat(0));
        };
        return tong.get(0);
    }

//    tong tien nhap, xuat theo gia doan
    public float[]getTongNhapXuatTheoGiaiDoan(String username,String tuNgay,String denNgay){
        db=dbHelper.getReadableDatabase();
        float nhap=0,xuat=0;
        Cursor cn=db.rawQuery("select  sum(donGia*soLuongNhap) as tonTien from NhapHang where username=? and ngayNhapHang between ? and ?",new String[]{username,tuNgay,denNgay});
        if(cn.getCount()!=0){
            cn.moveToFirst();
            nhap=cn.getFloat(0);
        }
        Cursor cx=db.rawQuery("select  sum(donGiaXuat*soLuongXuat) as tonTien from XuatHang where username=? and ngayXuatHang between ? and ?",new String[]{username,tuNgay,denNgay});
        if(cx.getCount()!=0){
            cx.moveToFirst();
            xuat=cx.getFloat(0);
        }
        float[] tong=new float[]{nhap,xuat};
        return tong;
    }
//    tong nhap+xuat theo giai doan

    public float getTongTienNhapXuatTheoGiaiDoan(String username,String tuNgay,String denNgay){
        db=dbHelper.getReadableDatabase();
        float nhap=0,xuat=0;
        Cursor cn=db.rawQuery("select  sum(donGia*soLuongNhap) as tonTien from NhapHang where username=? and ngayNhapHang between ? and ?",new String[]{username,tuNgay,denNgay});
        if(cn.getCount()!=0){
            cn.moveToFirst();
            nhap=cn.getFloat(0);
        }
        Cursor cx=db.rawQuery("select  sum(donGiaXuat*soLuongXuat) as tonTien from XuatHang where username=? and ngayXuatHang between ? and ?",new String[]{username,tuNgay,denNgay});
        if(cx.getCount()!=0){
            cx.moveToFirst();
            xuat=cx.getFloat(0);
        }
        float tong=nhap+xuat;
        return tong;
    }


}
