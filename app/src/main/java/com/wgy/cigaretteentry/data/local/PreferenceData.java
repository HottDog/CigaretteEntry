package com.wgy.cigaretteentry.data.local;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 袁江超 on 2017/4/20.
 */

public class PreferenceData {
    public static String getToken(Context context){
        SharedPreferences tokenpreferences=context.getSharedPreferences("token", Activity.MODE_PRIVATE);
        return tokenpreferences.getString("token", "");
    }
    public static void setToken(Context context,String s){
        SharedPreferences preferences=context.getSharedPreferences("token",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token",s);
        editor.commit();
    }
}
