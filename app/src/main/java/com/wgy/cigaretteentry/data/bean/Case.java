package com.wgy.cigaretteentry.data.bean;

import android.util.EventLogTags;
import android.util.Log;

import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.local.PreferenceData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public class Case implements Cloneable{
    private static final String TAG = "Case";
    private String date;                   //1
    private String number;                 //2
    private String departmentID;            //3
    private String userID;                 //4
    private String year;                    //5
    private int totalNum;                    //6
    private long timeStamp;                    //7
    private long queryTimeStamp;   //http查询的时间戳8
    //是否上传，true为要上传，false不用上传
    private boolean upload_or_not;               //9
    //是否是第一次上传
    private boolean is_first;                   //10
    private ArrayList<Cigarette> cigarettes;      //11
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
            totalNum++;
            Log.d(TAG,"卷烟的数量+1");
        }
    }

    public void addCigaretteWithoutNum(Cigarette cigarette){
        if(null!=cigarette){
            cigarettes.add(cigarette);
            Log.d(TAG,"添加一个从http获取的卷烟信息");
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
    public void setTimeStamp(String timeStamp){
        this.timeStamp= Long.valueOf(timeStamp);
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
            JSONObject userObject = obj.optJSONObject("account");
            c.setUserID(userObject.optString("uid"));
            c.setTimeStamp(obj.optString("timeStamp"));
            c.setYear(obj.optString("year"));
            c.setTotalNum(obj.optInt("totalCigaretteNum"));
            c.setIs_first(false);
            return c;
        }else {
            return null;
        }
    }

    /**
     * 把from的值赋给to
     * @param from
     * @param to
     */
    public static final void clone(Case from,Case to){
        to.setUpload_or_not(from.isUpload_or_not());
        to.setIs_first(from.is_first());
        //to.setCigarettes(from.getCigarettes());
        to.setDate(from.getDate());
        to.setDepartmentID(from.getDepartmentID());
        to.setNumber(from.getNumber());
        to.setQueryTimeStamp(from.getQueryTimeStamp());
        to.setTimeStamp(from.getTimeStamp());
        //to.setTotalNum(from.getTotalNum());
        to.setUserID(from.getUserID());
        to.setYear(from.getYear());
        for(int i=0;i<from.getCigarettes().size();i++){
            to.addCigarette(from.getCigarettes().get(i));
        }
    }
}
