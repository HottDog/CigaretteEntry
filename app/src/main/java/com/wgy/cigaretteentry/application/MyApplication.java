package com.wgy.cigaretteentry.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wgy.cigaretteentry.data.local.CigaretteLocalData;
import com.wgy.cigaretteentry.data.local.db.DBinstance;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by 袁江超 on 2017/4/24.
 */

public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    public static ReadWriteLock lock;
    private static RequestQueue mQueue;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        DBinstance.DBOpenHelperHolder.register(getApplicationContext());
        lock=new ReentrantReadWriteLock(false);
        mQueue = Volley.newRequestQueue(this);
        mContext=this;
        CigaretteLocalData.CigaretteLocalDataHolder.register(mContext);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
    public static RequestQueue getRequestQueue(){
        return mQueue;
    }



}
