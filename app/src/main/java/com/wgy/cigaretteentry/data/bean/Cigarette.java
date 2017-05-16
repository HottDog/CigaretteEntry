package com.wgy.cigaretteentry.data.bean;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public class Cigarette {
    private static final String TAG = "Cigarette";
    private String id;
    private String num ;
    private String name;
    private double price;
    private String barcode;   //条形码
    private String lasercode;    //激光码
    private String  lasercodeImgUrl;
    private String pic1;
    private String pic2;
    //是否上传，true为是
    private boolean upload_or_not;
    private long timeStamp;
    public Cigarette(){

    }

    public Cigarette(String id,String num,String name, double price, String barcode, String lasercode,String lasercodeImgUrl) {
        this.id = id;
        this.num = num;
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.lasercode = lasercode;
        this.lasercodeImgUrl = lasercodeImgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNum(String num){
        this.num = num;
    }

    public String getNum() {
        return num;
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

    public void setLasercodeImgUrl(String lasercodeImgUrl) {
        this.lasercodeImgUrl = lasercodeImgUrl;
    }

    public String getLasercodeImgUrl() {
        return lasercodeImgUrl;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic2() {
        return pic2;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setUpload_or_not(boolean upload_or_not) {
        this.upload_or_not = upload_or_not;
    }
    public void setUpload_or_not(String upload_or_not){
        if(upload_or_not.equals("1")){
            this.upload_or_not = true;
        }else {
            this.upload_or_not=false;
        }
    }
    public boolean isUpload_or_not() {
        return upload_or_not;
    }

    public String getUpload_or_not(){
        if (upload_or_not){
            return "1";
        }else {
            return "0";
        }
    }
    @Override
    public String toString() {
        String s = TAG + ": {{ barcode:" + getBarcode() + "}{name:"+getName()+"}{price:"+getPrice()+"}{upload_or_not:"+getUpload_or_not()+"}}";
        return s;
    }
    public static final Cigarette getCigaretteFromJson(JSONObject json){
        if (json!=null){
            Cigarette cigarette=new Cigarette();
            JSONObject jsonObject = json.optJSONObject("barcode");
            cigarette.setName(jsonObject.optString("name"));
            cigarette.setLasercode(json.optString("laserCodeNum"));
            cigarette.setPrice(jsonObject.optDouble("wholesalesPrice"));
            return cigarette;
        }else {
            return null;
        }
    }
}
