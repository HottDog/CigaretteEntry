package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.BasePresenter;
import com.wgy.cigaretteentry.model.BaseView;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public interface UploadFragmentContract {
    interface Presenter extends BasePresenter {
        void unRegister();
        void register();
        void upload(int index);
        void search(String num);
    }
    interface IView extends BaseView<Presenter> {
        void updateListView(ArrayList<Case> cases);
        void upload(int index);
    }
    interface IModel extends BaseDataModel<ArrayList<Case>>{

    }
}
