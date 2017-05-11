package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.addfragment;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class AddFragmentPresenter implements AddFragmentContract.Presenter{
    private ListDataMode listDataMode;
    private AddFragmentContract.IView iView;

    public AddFragmentPresenter(AddFragmentContract.IView iView){
        this.iView=iView;
        listDataMode=ListDataMode.getInstance();
    }
    @Override
    public void start() {

    }

    @Override
    public int addCase(String year, String num, String department_id, String userID, String date) {
        Case mCase=new Case();
        mCase.setUpload_or_not(true); //待上传
        /*
         mCase.setYear(year);
         mCase.setNumber(num);
         mCase.setDepartmentID(department_id);
         mCase.setUserID(userID);
         mCase.setDate(date);
         mCase.setIs_first(true);
         */
        mCase.setYear("2017");
        mCase.setNumber("sdadafe");
        mCase.setDepartmentID("1");
        mCase.setUserID("1");
        mCase.setDate("2017");
        mCase.setIs_first(true);
        return listDataMode.addCase(mCase);
    }
}
