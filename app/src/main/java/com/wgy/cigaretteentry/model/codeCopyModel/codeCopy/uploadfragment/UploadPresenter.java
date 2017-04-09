package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/8.
 */

public class UploadPresenter implements UploadFragmentContract.Presenter{
    private UploadFragmentContract.IView iView;
    private BaseDataModel<ArrayList<Case>> iModel;
    public UploadPresenter(UploadFragmentContract.IView iView){
        this.iView=iView;
        this.iModel= ListDataMode.getInstance();
    }
    @Override
    public void start() {
        iView.updateListView(iModel.getInitData());
    }
}
