package com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo;

import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.bean.CigarettesNum;
import com.wgy.cigaretteentry.model.BasePresenter;
import com.wgy.cigaretteentry.model.BaseView;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/5.
 */

public interface DetailCaseInfoContract {
    interface Presenter extends BasePresenter{
        void register();
        void unRegister();
        void start(int index);
    }
    interface IView extends BaseView<Presenter>{
        void updateNumListview(ArrayList<CigarettesNum> cigarettesNa);
        void updateCigaretteListview(ArrayList<Cigarette> cigarettes);
        void updateDataView(Case c);
    }
    interface IModel{

    }
}
