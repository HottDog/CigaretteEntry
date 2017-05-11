package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.addfragment;

import com.wgy.cigaretteentry.model.BasePresenter;
import com.wgy.cigaretteentry.model.BaseView;

/**
 * Created by 袁江超 on 2017/4/4.
 */

public interface AddFragmentContract {
    interface Presenter extends BasePresenter{
        int addCase(String year,String num,String department_id,String userID,String date);
    }
    interface IView extends BaseView<Presenter>{

    }
    interface IModel{

    }
}
