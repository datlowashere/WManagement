package com.edu.project1.Models;

public class User {
    private String username,hoTen,email,tenKhoHang,password;
    private byte[] img;

    public User() {
    }

    public User(String username, String hoTen, String tenKhoHang, String password,String email,byte[]img) {
        this.username = username;
        this.hoTen = hoTen;
        this.tenKhoHang = tenKhoHang;
        this.password = password;
        this.email=email;
        this.img=img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenKhoHang() {
        return tenKhoHang;
    }

    public void setTenKhoHang(String tenKhoHang) {
        this.tenKhoHang = tenKhoHang;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
