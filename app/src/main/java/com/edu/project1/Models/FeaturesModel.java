package com.edu.project1.Models;

public class FeaturesModel {
    private String nameF,p1,p2;
    private int img;

    public FeaturesModel() {
    }

    public FeaturesModel(String nameF,String p1,String p2, int img) {
        this.nameF = nameF;
        this.p1=p1;
        this.p2=p2;
        this.img = img;
    }

    public String getNameF() {
        return nameF;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }
}
