package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment;

import android.util.Log;

import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;
import com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase.TakePhotoForCaseActivity;
import com.wgy.cigaretteentry.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁江超 on 2017/4/8.
 */

public class UploadPresenter implements UploadFragmentContract.Presenter,UploadObserver{
    private static final String TAG = "UploadPresenter";
    private UploadFragmentContract.IView iView;
    private ListDataMode iModel;
    private ArrayList<Case> cases;
    private int[] indexs;
    private  int []searchIndex;
    private String num;
    public UploadPresenter(UploadFragmentContract.IView iView){
        this.iView=iView;
        this.iModel= ListDataMode.getInstance();
        cases=new ArrayList<Case>();
        indexs = new int[1000];
        searchIndex=new int[1000];
    }

    @Override
    public void start() {
        processData(iModel.getInitData());
        search("");
    }

    @Override
    public void unRegister() {
        if (iModel!=null) {
            iModel.unRisterCaseListPublisher(this);
            iModel.unRegisterUploadPublisher(this);
        }
    }

    @Override
    public void register() {
        if (iModel!=null) {
            iModel.registerCaseListPublisher(this);
            iModel.registerUploadPublisher(this);
        }
    }

    @Override
    public void upload(int position) {
        Log.d(TAG,"上传的case的index："+indexs[searchIndex[position]]);
        int index = indexs[searchIndex[position]];
        iView.upload(index);
        iModel.uploadCases(index);
    }

    @Override
    public void search(String num) {
        this.num=num;
        if (num==null || num.equals("")){
            for (int i=0;i<cases.size();i++){
                searchIndex[i]=i;
            }
            iView.updateListView(cases);
            return;
        }
        ArrayList<Case> tmp = new ArrayList<>();
        int j=0;
        for(int i=0;i<cases.size();i++){
            Case c = cases.get(i);
            if(DataUtil.isMatch(num,c.getNumber())){
                tmp.add(c);
                searchIndex[j]=i;
                j++;
            }
        }
        iView.updateListView(tmp);
    }

    private void processData(ArrayList<Case> temp){
        //ArrayList<Case> temp=iModel.getData();
        cases.clear();
        indexs=null;
        indexs=new int[1000];
        for (int i=0,j=0;i<temp.size();i++){
            if (temp.get(i).isUpload_or_not()){
                cases.add(temp.get(i));
                indexs[j]=i;
                j++;
            }
        }

    }

    @Override
    public void updateData(ArrayList<Case> datas) {
        if (datas==null)return;
        processData(datas);
        search("");
    }


    @Override
    public void onSuccess() {
        iView.onSuccess();
    }

    @Override
    public void onFail() {
        iView.onFail();
    }
}
