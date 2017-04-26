package com.wgy.cigaretteentry.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by 袁江超 on 2017/4/24.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG="DBOpenHelper";
    private static final String DB_NAME="cigarette.db";

    private static final String CREATE_CASE_TABLE =
            "CREATE TABLE IF NOT EXISTS case(" +
                    "_id integer primary key autoincrement," +
                    "date TEXT ," +
                    "num text ," +
                    "department_id text," +
                    "user_id text," +
                    "total_num integer," +
                    "upload_or_not text," +
                    "writeTime text not null"+
                    ");";
    private static final String CREATE_CIGARETTE_TABLE =
            "CREATE TABLE IF NOT EXISTS cigarette(" +
                    "_id integer primary key autoincrement," +
                    "num text not null," +
                    "name TEXT ," +
                    "price text ," +
                    "barcode text," +
                    "laserCode text," +
                    "laserCodeImgUrl text," +
                    "pic1Url text," +
                    "pic2Url text," +
                    "writeTime text not null"+
                    ");";
    public DBOpenHelper(Context context,int version) {
        super(context, DB_NAME, null, version);
        if(Build.VERSION.SDK_INT>=11){
            getWritableDatabase().enableWriteAheadLogging();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate()");
        db.execSQL(CREATE_CASE_TABLE);
        db.execSQL(CREATE_CIGARETTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String CASE_TABLE_NAME="case";
    public static final String CIGARETTE_TABLE_NAME="cigarette";
}
