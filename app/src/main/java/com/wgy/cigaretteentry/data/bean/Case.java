package com.wgy.cigaretteentry.data.bean;

import android.util.EventLogTags;

import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.local.PreferenceData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public class Case {
    private static final String TAG = "Case";
    private String date;
    private String number;
    private String departmentID;
    private String userID;
    private String year;
    private int totalNum;
    private long timeStamp;
    private long queryTimeStamp;   //http查询的时间戳
    //是否上传，true为要上传，false不用上传
    private boolean upload_or_not;
    //是否是第一次上传
    private boolean is_first;
    private ArrayList<Cigarette> cigarettes;
    public Case(){
        cigarettes=new ArrayList<>();
        is_first=true;
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

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setQueryTimeStamp(long queryTimeStamp) {
        this.queryTimeStamp = queryTimeStamp;
    }

    public long getQueryTimeStamp() {
        return queryTimeStamp;
    }

    public ArrayList<Cigarette> getCigarettes() {
        return cigarettes;
    }

    public void setCigarettes(ArrayList<Cigarette> cigarettes) {
        this.cigarettes = cigarettes;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }
    public void setIs_first(String s){
        if(s.equals("1")){
            is_first=true;
        }else {
            is_first = false;
        }
    }

    public boolean is_first() {
        return is_first;
    }
    public String getIs_first(){
        if (is_first)
            return "1";
        else
            return "0";
    }
    @Override
    public String toString() {
        //String s = TAG + ": {{num:"+getNumber()+"},{data:"+getDate()+"}{timeStamp:"+ getTimeStamp()+"}}";
        String s = TAG + ": {{num:"+getNumber()+"}{totalNum:"+getTotalNum()+"}}";
        return s;
    }

    public static Case getCaseFromJson(JSONObject obj){
        if (null!=obj){
            Case c = new Case();
            c.setNumber(obj.optString("caseInfoNum"));
            JSONObject object = obj.optJSONObject("department");
            c.setDepartmentID(object.optString("id"));
            c.setDate(obj.optString("submit_time"));
            c.setUserID(PreferenceData.getUserID(MyApplication.mContext));
            c.setTimeStamp(obj.optInt("TimeStamp"));
            c.setYear(obj.optString("year"));
            c.setTotalNum(obj.optInt("totalCigaretteNum"));
            return c;
        }else {
            return null;
        }
    }
}
