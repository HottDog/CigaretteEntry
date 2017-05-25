package com.wgy.cigaretteentry.data.local.db;

import android.content.Context;
import android.util.Log;

/**
 * Created by 袁江超 on 2017/4/24.
 */

public class DBinstance {
    private static final String TAG = "DBinstance";
    public static int DB_VERSION = 1;
    public static int DB__VERSION_TWO = 2;
    public static int DB__VERSION_THREE = 3;
    public static int DB__VERSION_FOUR = 4;
    public static class DBOpenHelperHolder{
        private static DBOpenHelper dbOpenHelper;
        public static void register(Context context){
            dbOpenHelper=new DBOpenHelper(context,DB_VERSION);
            Log.d(TAG,"数据库helper注册成功");
        }
    }

    /**
     * 获取到dbOpenHelper对象
     * @return
     */
    public static DBOpenHelper getInstance(){
        return DBOpenHelperHolder.dbOpenHelper;
    }
}
