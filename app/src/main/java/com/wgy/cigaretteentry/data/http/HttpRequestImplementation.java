package com.wgy.cigaretteentry.data.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.local.PreferenceData;
import com.wgy.cigaretteentry.util.DataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 袁江超 on 2017/4/20.
 */
public class HttpRequestImplementation {
    private static final String TAG = "HttpRequestImplementati";
    public HttpRequestImplementation(){}

    /**
     * http上传新的case
     * @param c
     * @param listener
     */
    public static void insertCase(Case c, final HttpGetDataListener listener){
        String url = HttpUrlConstant.INSERT_CASE;
        String signUrl = "/user/upLoadCases";
        String timeStamp = Long.valueOf(DataUtil.getCurrentTimeStamp()).toString();
        String sign = DataUtil.getSign(MyApplication.mContext,signUrl,timeStamp,true);
        JSONObject json = new JSONObject();
        try {
            json.put("date",c.getDate());
            json.put("year",c.getYear());
            json.put("num",c.getNumber());
            json.put("department_id",c.getDepartmentID());
            json.put("user_id",PreferenceData.getUserID(MyApplication.mContext));
            json.put("total_num",c.getTotalNum());
            JSONArray jsonArray = new JSONArray();
            ArrayList<Cigarette> cigarettes=c.getCigarettes();
            for(int i=0;i<cigarettes.size();i++){
                Cigarette cigarette = cigarettes.get(i);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("name",cigarette.getName());
                jsonObject.put("price",cigarette.getPrice());
                jsonObject.put("barcode",cigarette.getBarcode());
                jsonObject.put("image1",cigarette.getPic1());
                jsonObject.put("image2",cigarette.getPic2());
                jsonObject.put("laserCodeNum",cigarette.getLasercode());
                jsonObject.put("laserCodeImg",cigarette.getLasercodeImgUrl());
                jsonArray.put(jsonObject);
            }
            json.put("cigaretteList",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "?request_uid="+ PreferenceData.getUserID(MyApplication.mContext)
                + "&time="+timeStamp
                + "&sign=" + sign
                + "&from_platform=" + "app";
        Log.d(TAG,"url:"+url);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,url ,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"case http request result:"+response.toString());
                        listener.onSuccess(response);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"case http request error");
                Log.e(TAG, error.getMessage(), error);
                listener.onFail();
            }
        }
        );
        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 通过Num获取单个case详细信息
     * @param num
     * @param listener
     */
    public static void getCaseDetailByNum(String num, final HttpGetDataListener listener){
        String url = HttpUrlConstant.GET_CASE_DETAIL_BY_NUM;
        String signUrl = "/user/getTobaccoListbyCaseNum";
        String timeStamp = Long.valueOf(DataUtil.getCurrentTimeStamp()).toString();
        String sign = DataUtil.getSign(MyApplication.mContext,signUrl,timeStamp,true);
        JSONObject json = new JSONObject();
        try {
            json.put("number",num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "?request_uid="+ PreferenceData.getUserID(MyApplication.mContext)
                + "&time="+timeStamp
                + "&sign=" + sign
                + "&from_platform=" + "app";
        Log.d(TAG,"url:"+url);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,url ,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"case http request result:"+response.toString());
                        listener.onSuccess(response);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"case http request error");
                Log.e(TAG, error.getMessage(), error);
                listener.onFail();
            }
        }
        );
        MyApplication.getRequestQueue().add(request);
    }

    /**
     *通用的http请求数据
     * @param url
     * @param signUrl
     * @param json
     * @param listener
     */
    public static void httpGetData(String url, String signUrl, JSONObject json, final HttpGetDataListener listener){
        String timeStamp = Long.valueOf(DataUtil.getCurrentTimeStamp()).toString();
        String sign = DataUtil.getSign(MyApplication.mContext,signUrl,timeStamp,true);
        url = url + "?request_uid="+ PreferenceData.getUserID(MyApplication.mContext)
                + "&time="+timeStamp
                + "&sign=" + sign
                + "&from_platform=" + "app";
        Log.d(TAG,"url:"+url);
        final JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,url ,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"case http request result:"+response.toString());
                        String result = response.optString("success");
                        if (result.equals("1")){
                            JSONArray data = response.optJSONArray("data");
                            listener.onSuccess(data.optJSONObject(0));
                        }else {
                            listener.onFail();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"case http request error");
                Log.e(TAG, error.getMessage(), error);
                listener.onFail();
            }
        });
        MyApplication.getRequestQueue().add(request);
    }
    public interface HttpGetDataListener{
        void onSuccess(JSONObject json);
        void onFail();
    }
}
