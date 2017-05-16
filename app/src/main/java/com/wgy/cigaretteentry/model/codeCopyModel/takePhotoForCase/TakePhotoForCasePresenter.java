package com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase;

import android.util.Log;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.local.CigaretteLocalData;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;

/**
 * Created by 袁江超 on 2017/5/7.
 */

public class TakePhotoForCasePresenter implements TakePhotoForCaseContract.Presenter{
    private static final String TAG = "TakePhotoForCasePresent";
    private ListDataMode listDataMode;
    private TakePhotoForCaseContract.IView iView;
    private Case mCase;
    private Cigarette mCigarette;

    public TakePhotoForCasePresenter(TakePhotoForCaseContract.IView iView){
        this.iView=iView;
        listDataMode=ListDataMode.getInstance();
        mCigarette=new Cigarette();
    }
    @Override
    public void start() {

    }

    @Override
    public void iniCase(int index) {
        mCase = listDataMode.getCaseByIndex(index);
    }

    @Override
    public void getCigaretteData(String barcode) {
        Cigarette c=CigaretteLocalData.getInstance().search(barcode);
        if (c!=null){
            mCigarette = c;
            Log.d(TAG,"获取到卷烟信息，"+c.getName()+"price:"+Double.valueOf(c.getPrice()).toString());
            iView.initCigaretteData(true,c.getName(),Double.valueOf(c.getPrice()).toString());
        }else {
            Log.d(TAG,"没有获取到卷烟信息");
            iView.initCigaretteData(false,null,null);
        }
    }

    @Override
    public void complete( String lasercode, String pic1, String pic2, String lasercodePic) {
        mCigarette.setLasercode(lasercode);
        if (pic1!=null)
            mCigarette.setPic1(pic1);
        if (pic2!=null)
            mCigarette.setPic2(pic2);
        if(lasercodePic!=null)
            mCigarette.setLasercodeImgUrl(lasercodePic);
        mCigarette.setNum(mCase.getNumber());
        mCase.addCigarette(mCigarette);
        listDataMode.updateCase(mCase,mCigarette);
    }

    @Override
    public void addMore() {
        mCigarette=new Cigarette();
    }


}
