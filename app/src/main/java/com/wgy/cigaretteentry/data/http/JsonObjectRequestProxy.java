package com.wgy.cigaretteentry.data.http;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by 袁江超 on 2017/4/28.
 */

public class JsonObjectRequestProxy {
    private int method;
    private JsonObjectRequest jsonObjectRequest;
    private Response.Listener<JSONObject> lister;
    private Response.ErrorListener errorListener;
    public JsonObjectRequestProxy(String url){
        method = Request.Method.POST;
    }
    public void onSuccess(){

    }
}
