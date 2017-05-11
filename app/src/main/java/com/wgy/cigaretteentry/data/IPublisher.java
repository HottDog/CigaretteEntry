package com.wgy.cigaretteentry.data;

import java.util.List;

/**
 * Created by 袁江超 on 2017/5/2.
 */

public interface IPublisher<T> {
    void register(IObserver<T> o);
    void unRegister(IObserver<T> o);
    void clear();
    void publish(T o);
}
