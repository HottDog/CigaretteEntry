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
            "CREATE TABLE IF NOT EXISTS cases(" +
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
        Log.d(TAG,"onUpgrade()");
        if (newVersion == DBinstance.DB__VERSION_TWO) {
            Log.d(TAG,"给cigarette添加upload_or_not字段");
            db.execSQL(ADD_PARAM_TO_CIGARETTE);
        }
        if(newVersion ==DBinstance.DB__VERSION_THREE){
            Log.d(TAG,"给cases添加year字段");
            db.execSQL(ADD_PARAM_TO_CASES);
        }
        if(newVersion==DBinstance.DB__VERSION_FOUR){
            Log.d(TAG,"给cases添加is_first字段");
            db.execSQL(ADD_PARAM_IS_FIRST_TO_CASES);
        }
    }

    public static final String CASE_TABLE_NAME="cases";
    public static final String CIGARETTE_TABLE_NAME="cigarette";

    private static final String ADD_PARAM_TO_CIGARETTE = "alter table cigarette add upload_or_not text";
    private static final String ADD_PARAM_TO_CASES = "alter table cases add year text";

    private static final String ADD_PARAM_IS_FIRST_TO_CASES = "alter table cases add is_first text";
}
