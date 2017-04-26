package com.wgy.cigaretteentry.util;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class DataUtil {
    /**
     * 获取int型随机数
     * Math.random()是返回[0,1）之间的数
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static final int getIntRandom(int min,int max){
        return (int)(min+Math.random()*(max-min+1));
    }

    /**
     * 返回给定值(double)的随机正负
     * @param d
     * @return
     */
    public static final double randomNP(double d){
        if(getIntRandom(0,1)==1){
            return -d;
        }else {
            return d;
        }
    }
    /**
     * 返回随机正负
     * @return
     */
    public static final boolean getRandomBool(){
        if(getIntRandom(0,1)==1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 返回当前的时间戳
     * @return
     */
    public static final long getCurrentTimeStamp(){
        long t = System.currentTimeMillis();
        return t;
    }
}
