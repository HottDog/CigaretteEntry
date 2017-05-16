package com.wgy.cigaretteentry.data.publisher;

import com.wgy.cigaretteentry.data.BasePublisher;
import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment.UploadObserver;

/**
 * Created by 袁江超 on 2017/5/15.
 */

public class UploadPublisher extends BasePublisher{
    public UploadPublisher(){super();}
    @Override
    public void register(IObserver o) {
        observers.add(o);
    }

    @Override
    public void unRegister(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void publish(Object isSuccess) {
        for(int i=0;i<observers.size();i++){
            UploadObserver observer = (UploadObserver)observers.get(i);
            if((boolean)isSuccess){
                observer.onSuccess();
            }else {
                observer.onFail();
            }
        }
    }
}
