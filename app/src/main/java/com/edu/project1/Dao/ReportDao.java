package com.edu.project1.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
        String sql="SELECT ngayNhapHang, sum((donGia * soLuongNhap)) as tien from NhapHang WHERE  username=? and ngayNhapHang BETWEEN ? and ? GROUP BY ngayNhapHang ORDER by ngayNhapHang";
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
        String sql="SELECT ngayNhapHang from NhapHang WHERE  username=? and ngayNhapHang BETWEEN ? and ? GROUP BY ngayNhapHang ORDER by ngayNhapHang";
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
        String sql="SELECT ngayXuatHang,sum((donGiaXuat * soLuongXuat))as tien from XuatHang WHERE  username=? and ngayXuatHang BETWEEN ? and ? GROUP BY ngayXuatHang ORDER by ngayXuatHang ";
        @SuppressLint("Recycle") Cursor c=db.rawQuery(sql,new String[]{username,tuNgay,denNgay});
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

//  ds top so luong nhap theo giai doan
    public Integer[] getDSTopSLNhap(String username, String tuNgay, String denNgay){
        db=dbHelper.getReadableDatabase();
        ArrayList<Integer> sl=new ArrayList<>();
        Cursor c=db.rawQuery("select tenHang, ngayNhapHang, soLuongNhap from NhapHang where username=? and ngayNhapHang between ? and ? order by soLuongNhap desc limit 10",new String[]{username,tuNgay,denNgay});
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                sl.add(c.getInt(2));
                c.moveToNext();
            };
        }
        return sl.toArray(new Integer[sl.size()]);
    }
//    ds ten hang of top sl nhap theo giai doan
    public String[] getDSTenTopSLNhap(String username, String tuNgay, String denNgay){
        db=dbHelper.getReadableDatabase();
        ArrayList<String> ten=new ArrayList<>();
        Cursor c=db.rawQuery("select tenHang, ngayNhapHang, soLuongNhap from NhapHang where username=? and ngayNhapHang between ? and ? order by soLuongNhap desc limit 10",new String[]{username,tuNgay,denNgay});
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                ten.add(c.getString(0));
                c.moveToNext();
            };
        }
        return ten.toArray(new String[ten.size()]);
}
// ds top so luong xuat theo giai doan
    public Integer[] getDSTopSLXuat(String username, String tuNgay, String denNgay){
        db=dbHelper.getReadableDatabase();
        ArrayList<Integer> sl=new ArrayList<>();
        Cursor c=db.rawQuery("select tenHang, ngayXuatHang, soLuongXuat from XuatHang where username=? and ngayXuatHang between ? and ? order by soLuongXuat desc limit 10",new String[]{username,tuNgay,denNgay});
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                sl.add(c.getInt(2));
                c.moveToNext();
            };
        }
        return sl.toArray(new Integer[sl.size()]);
}
    // ds ten oftop so luong xuat theo giai doan
    public String[] getDSTenTopSLXuat(String username, String tuNgay, String denNgay){
        db=dbHelper.getReadableDatabase();
        ArrayList<String> ten=new ArrayList<>();
        Cursor c=db.rawQuery("select tenHang, ngayXuatHang, soLuongXuat from XuatHang where username=? and ngayXuatHang between ? and ? order by soLuongXuat desc limit 10",new String[]{username,tuNgay,denNgay});
        if(c.moveToFirst()){
            while (!c.isAfterLast()){
                ten.add(c.getString(0));
                c.moveToNext();
            };
        }
        return ten.toArray(new String[ten.size()]);
    }

}
