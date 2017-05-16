package com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase;

import com.wgy.cigaretteentry.model.BasePresenter;
import com.wgy.cigaretteentry.model.BaseView;
import com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo.DetailCaseInfoContract;

/**
 * Created by 袁江超 on 2017/4/5.
 */

public interface TakePhotoForCaseContract {
    interface Presenter extends BasePresenter{
        void iniCase(int index);
        void getCigaretteData(String barcode);
        void complete(String lasercode,String pic1,String pic2,String lasercodePic);
        void addMore();
    }
    interface IView extends BaseView<Presenter>{
        void initCigaretteData(boolean hasData,String name,String price);
    }
    interface IModel{

    }
}
