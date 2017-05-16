package com.wgy.cigaretteentry.util;

import android.content.Context;
import android.util.Log;

import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.local.PreferenceData;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class DataUtil {
    private static final String TAG = "DataUtil";

    /**
     * 获取int型随机数
     * Math.random()是返回[0,1）之间的数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static final int getIntRandom(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    /**
     * 返回给定值(double)的随机正负
     *
     * @param d
     * @return
     */
    public static final double randomNP(double d) {
        if (getIntRandom(0, 1) == 1) {
            return -d;
        } else {
            return d;
        }
    }

    /**
     * 返回随机正负
     *
     * @return
     */
    public static final boolean getRandomBool() {
        if (getIntRandom(0, 1) == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回当前的时间戳
     *
     * @return
     */
    public static final long getCurrentTimeStamp() {
        long t = System.currentTimeMillis();
        return t;
    }

    /**
     * 获取两个时间之间的时间差
     *
     * @param curTimeStamp
     * @param lastTimeStamp
     * @return 秒
     */
    public static final long getTimeDifference(long curTimeStamp, long lastTimeStamp) {
        long temp = (curTimeStamp - lastTimeStamp) / (1000);
        return temp;
    }

    /**
     * 判断数据有没有过期
     *
     * @param lastTimeStamp 上次记录时间戳
     * @param time          有效时间长度,单位是秒
     * @return true为过期，false为不过期
     */
    public static final boolean isDataOverdue(long lastTimeStamp, long time) {
        if (getTimeDifference(getCurrentTimeStamp(), lastTimeStamp) - time < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取url加密签名
     * @param context
     * @param url     url
     * @param time 时间戳
     * @param isPost  是否是post形式的url，true是post形式的url，false为get形式的url
     * @return
     */
    public static final String getSign(Context context, String url,String time, boolean isPost) {
        String s = "";
        if (isPost) {
            s = url + "?token=" + getUsingToken(context) + "&time=" + time;
        } else {
            s = url + "&token=" + PreferenceData.getToken(context) + "&time=" + time;
        }
        Log.d("ListDataMode", "sign=" + s);
        return MD5.GetMD5Code(s);
    }

    /**
     * 获取最终的请求url
     * @param context
     * @param signUrl 用于签名的url
     * @param url 最初的url请求
     * @param time 时间戳
     * @param isPost 是否是post请求
     * @return
     */
    public static final String getFinalUrl(Context context, String signUrl, String url,String time, boolean isPost) {
        String s = "";
        String sign = getSign(context, signUrl,time, isPost);
        s = url + "&request_uid=" + PreferenceData.getUserID(context)
                + "&time=" + time
                + "&sign=" + sign
                + "&from_platform=" + "app";
        return s;
    }

    /**
     * 获取可以直接使用的token，因为后台给的token有问题，需要处理后才能使用
     * @param context
     * @return
     */
    public static final String getUsingToken(Context context){
        String token = PreferenceData.getToken(context);
        String [] ss = token.split(";");
        Log.d("ListDataMode","usingToken="+ss[0]);
        return ss[0];
    }

    /**
     * 二进制数据编码为BASE二进制
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     * @param bytes
     * @return
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * 两个字符是否匹配
     * @param matchingS
     * @param matchedS
     * @return
     */
    public static final boolean isMatch(String matchingS,String matchedS){
        char[] matchedC = matchedS.toCharArray();
        char[] matchingC = matchingS.toCharArray();
        if(matchingC.length>matchedC.length){
            return false;
        }
        for(int i=0;i<matchedC.length-matchingC.length+1;i++){
            if(matchingC[0]==matchedC[i]){
                boolean match = true;
                int j=1;
                while (match){
                    i++;
                    if(matchingC[j]!=matchedC[i]){
                        match=false;
                    }else {
                        j++;
                        System.out.println("j="+j);
                        if(j>=matchingC.length){
                            return true;
                        }
                    }
                }
                i--;
            }
        }
        return false;
    }

    /**
     * 去除字符串中的括号以及括号中的内容
     * @param s
     * @return
     */
    public static final String removeBracketContent(String s){
        s=format(s);
        s=s.replaceAll("\\(.*?\\(", "");
        return s;
    }
    public static String format(String s){
        String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "(");
        return str;
    }
    public static String format2(String s){
        String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    /**
     * file TO byte[]
     * @param file
     * @return
     */
    public static final byte[] getByteFromFile(File file){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }
}
