package com.wgy.cigaretteentry.data.bean;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public class Case {
    private String date;
    private String number;
    private String departmentID;
    private String userID;
    private int totalNum;
    private long timeStamp;
    //是否上传，true为是
    private boolean upload_or_not;
    private ArrayList<Cigarette> cigarettes;
    public Case(){
        cigarettes=new ArrayList<>();
    }

    public Case(String date, String number, String departmentID, String userID, int totalNum, boolean upload_or_not,long timeStamp) {
        this.date = date;
        this.number = number;
        this.departmentID = departmentID;
        this.userID = userID;
        this.totalNum = totalNum;
        this.upload_or_not = upload_or_not;
        this.timeStamp = timeStamp;
        cigarettes=new ArrayList<>();

    }

    public void addCigarette(Cigarette cigarette){
        if (null!=cigarette) {
            cigarettes.add(cigarette);
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public boolean isUpload_or_not() {
        return upload_or_not;
    }

    public void setUpload_or_not(boolean upload_or_not) {
        this.upload_or_not = upload_or_not;
    }
    public void setUpload_or_not(String  upload_or_not) {
        if (upload_or_not.equals("1")){
            this.upload_or_not=true;
        }else {
            this.upload_or_not = false;
        }
    }
    public String getUpload_or_not(){
        if(upload_or_not){
            return "1";
        }else {
            return "0";
        }
    }
    public void setTimeStamp(long timeStamp){
        this.timeStamp = timeStamp;
    }
    public long getTimeStamp(){
        return timeStamp;
    }
    public ArrayList<Cigarette> getCigarettes() {
        return cigarettes;
    }

    public void setCigarettes(ArrayList<Cigarette> cigarettes) {
        this.cigarettes = cigarettes;
    }
}
