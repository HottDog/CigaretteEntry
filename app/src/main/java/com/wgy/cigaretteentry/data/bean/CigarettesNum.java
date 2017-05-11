package com.wgy.cigaretteentry.data.bean;

/**
 * Created by 袁江超 on 2017/5/7.
 */

public class CigarettesNum {
    private int leftnum;
    private String leftname;
    private int rightnum;
    private String rightname;
    public CigarettesNum(){}

    public void setLeftname(String leftname) {
        this.leftname = leftname;
    }

    public void setLeftnum(int leftnum) {
        this.leftnum = leftnum;
    }

    public void setRightname(String rightname) {
        this.rightname = rightname;
    }

    public void setRightnum(int rightnum) {
        this.rightnum = rightnum;
    }

    public int getLeftnum() {
        return leftnum;
    }

    public String getRightname() {
        return rightname;
    }

    public int getRightnum() {
        return rightnum;
    }

    public String getLeftname() {
        return leftname;
    }

    public void increaseRightNum(){
        this.rightnum++;
    }
    public void increaseLeftNum(){
        this.leftnum++;
    }
}
