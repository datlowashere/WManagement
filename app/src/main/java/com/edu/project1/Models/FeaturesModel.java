package com.edu.project1.Models;

public class FeaturesModel {
    private String nameF;
    private int img;

    public FeaturesModel() {
    }

    public FeaturesModel(String nameF, int img) {
        this.nameF = nameF;
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
}
