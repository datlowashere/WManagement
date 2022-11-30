package com.edu.project1.Models;

import java.util.Date;

public class ExportItems {

    private int maXuatHang,soLuongNhap,soLuongXuat,maNhapHang;
    private float donGia,donGiaXuat;
    private Date ngayXuatHang;
    private String tenHang,tenLoaiHang,username;

    public ExportItems() {
    }

    public ExportItems(int maXuatHang, int soLuongNhap, int soLuongXuat, int maNhapHang, float donGia, float donGiaXuat, Date ngayXuatHang, String tenHang, String tenLoaiHang, String username) {
        this.maXuatHang = maXuatHang;
        this.soLuongNhap = soLuongNhap;
        this.soLuongXuat = soLuongXuat;
        this.maNhapHang = maNhapHang;
        this.donGia = donGia;
        this.donGiaXuat = donGiaXuat;
        this.ngayXuatHang = ngayXuatHang;
        this.tenHang = tenHang;
        this.tenLoaiHang = tenLoaiHang;
        this.username = username;

    }

    public int getMaXuatHang() {
        return maXuatHang;
    }

    public void setMaXuatHang(int maXuatHang) {
        this.maXuatHang = maXuatHang;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

    public int getMaNhapHang() {
        return maNhapHang;
    }

    public void setMaNhapHang(int maNhapHang) {
        this.maNhapHang = maNhapHang;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public float getDonGiaXuat() {
        return donGiaXuat;
    }

    public void setDonGiaXuat(float donGiaXuat) {
        this.donGiaXuat = donGiaXuat;
    }

    public Date getNgayXuatHang() {
        return ngayXuatHang;
    }

    public void setNgayXuatHang(Date ngayXuatHang) {
        this.ngayXuatHang = ngayXuatHang;
    }


    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getTenLoaiHang() {
        return tenLoaiHang;
    }

    public void setTenLoaiHang(String tenLoaiHang) {
        this.tenLoaiHang = tenLoaiHang;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
