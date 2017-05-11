package com.wgy.cigaretteentry.data;

import java.util.List;

/**
 * Created by 袁江超 on 2017/5/2.
 */

public interface IObserver<T> {
    void updateData(T datas);
}
