package com.wgy.cigaretteentry.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/8.
 */

public interface BaseDataModel<T> {
    ArrayList<T> getInitData();
    ArrayList<T> getData();
    void httpRequestData();
}
