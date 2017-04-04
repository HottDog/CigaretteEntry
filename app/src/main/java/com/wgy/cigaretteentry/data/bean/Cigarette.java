package com.wgy.cigaretteentry.data.bean;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public class Cigarette {
    private String name;
    private double price;
    private String barcode;   //条形码
    private String lasercode;    //激光码
    private ArrayList<String> pics;
    public Cigarette(){
        pics = new ArrayList<>();
    }

    public Cigarette(String name, double price, String barcode, String lasercode) {
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.lasercode = lasercode;
        pics =new ArrayList<>();
    }
    public void addPic(String s){
        pics.add(s);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getLasercode() {
        return lasercode;
    }

    public void setLasercode(String lasercode) {
        this.lasercode = lasercode;
    }

    public ArrayList<String> getPics() {
        return pics;
    }

    public void setPics(ArrayList<String> pics) {
        this.pics = pics;
    }
}
