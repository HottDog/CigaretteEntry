package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class ListPresenter implements ListFragmentContract.Presenter{
    private ListFragmentContract.IView iView;
    private BaseDataModel<ArrayList<Case>> iModel;
    public ListPresenter(ListFragmentContract.IView view){
        iView=view;
        iModel=ListDataMode.getInstance();
    }
    @Override
    public void start() {
        iView.updateListView(iModel.getInitData());
    }
}
