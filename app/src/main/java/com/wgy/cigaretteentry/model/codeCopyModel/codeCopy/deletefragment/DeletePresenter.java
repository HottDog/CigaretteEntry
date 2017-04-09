package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.deletefragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.ListFragmentContract;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/8.
 */

public class DeletePresenter implements DeleteFragmentContract.Presenter{
    private DeleteFragmentContract.IView iView;
    private BaseDataModel<ArrayList<Case>> iModel;
    public DeletePresenter(DeleteFragmentContract.IView iView) {
        this.iView = iView;
        this.iModel = ListDataMode.getInstance();
    }
    @Override
    public void start() {
        iView.updateListView(iModel.getInitData());
    }
}
