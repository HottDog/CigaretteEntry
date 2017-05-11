package com.wgy.cigaretteentry.data;

import com.wgy.cigaretteentry.data.bean.Case;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁江超 on 2017/5/7.
 */

public class BasePublisher<T> implements IPublisher{
    protected ArrayList<IObserver> observers;
    public BasePublisher(){
        observers=new ArrayList<>();
    }
    @Override
    public void register(IObserver o) {
        observers.add(o);
    }

    @Override
    public void unRegister(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void clear() {
        observers.clear();
    }

    @Override
    public void publish(Object o) {
        for(int i=0;i<observers.size();i++){
            observers.get(i).updateData(o);
        }
    }

}
