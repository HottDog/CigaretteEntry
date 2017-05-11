package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.deletefragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.BasePresenter;
import com.wgy.cigaretteentry.model.BaseView;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public interface DeleteFragmentContract {
    interface Presenter extends BasePresenter{
        void unRegister();
        void register();
        void deleteCase(int index);
        void beginDeleteCase(int index);
        void search(String num);
    }
    interface IView extends BaseView<Presenter>{
        void updateListView(ArrayList<Case> cases);
        void deleteCase(int index);
    }
    interface IModel extends BaseDataModel<ArrayList<Case>>{

    }
}
