package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.BasePresenter;
import com.wgy.cigaretteentry.model.BaseView;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public interface ListFragmentContract {
    interface Presenter extends BasePresenter<IView> {

    }
    interface IView extends BaseView<Presenter> {
        void updateListView(ArrayList<Case> cases);
    }
    interface IModel extends BaseDataModel<ArrayList<Case>>{

    }
}
