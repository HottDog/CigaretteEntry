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
    private ReadLocalCigaretteDataThread thread;
    @Override
    public void onCreate() {
        super.onCreate();
        DBinstance.DBOpenHelperHolder.register(getApplicationContext());
        lock=new ReentrantReadWriteLock(false);
        mQueue = Volley.newRequestQueue(this);
        mContext=this;
        CigaretteLocalData.CigaretteLocalDataHolder.register(mContext);
        readLocalCigaretteData();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
    public static RequestQueue getRequestQueue(){
        return mQueue;
    }
    private void readLocalCigaretteData(){
        thread = new ReadLocalCigaretteDataThread();
        thread.start();
    }
    class ReadLocalCigaretteDataThread extends Thread{
        @Override
        public void run() {
            super.run();
            Log.d(TAG,"ReadLocalCigaretteDataThread run"+" "+ CigaretteLocalData.getInstance().getSize());
            if(CigaretteLocalData.getInstance().getSize()==0) {
                CigaretteLocalData.getInstance().readChinaCigaretteData();
                CigaretteLocalData.getInstance().readCigaretteData();
            }
        }
    }

}
