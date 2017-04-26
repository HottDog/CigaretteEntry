package com.wgy.cigaretteentry.data.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 袁江超 on 2017/4/20.
 */

public class HttpRequestData {
    private Context context;
    private RequestQueue queue;
    public HttpRequestData(Context context){
        this.context =context;
    }

}
