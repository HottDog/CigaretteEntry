package com.wgy.cigaretteentry.application;

import android.app.Application;

import com.wgy.cigaretteentry.data.local.db.DBinstance;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by 袁江超 on 2017/4/24.
 */

public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    public static ReadWriteLock lock;
    @Override
    public void onCreate() {
        super.onCreate();
        DBinstance.DBOpenHelperHolder.register(getApplicationContext());
        lock=new ReentrantReadWriteLock(false);
    }

}
