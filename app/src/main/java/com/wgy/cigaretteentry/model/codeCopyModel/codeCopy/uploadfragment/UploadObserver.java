package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment;

import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.bean.Case;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/5/15.
 */

public interface UploadObserver extends IObserver<ArrayList<Case>>{
    void onSuccess();
    void onFail();

}
