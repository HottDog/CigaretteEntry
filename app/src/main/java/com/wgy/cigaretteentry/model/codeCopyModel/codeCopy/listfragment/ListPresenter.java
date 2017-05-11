package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment;

import android.content.Context;

import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment.UploadFragmentContract;
import com.wgy.cigaretteentry.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class ListPresenter implements ListFragmentContract.Presenter,IObserver<ArrayList<Case>>{
    private ListFragmentContract.IView iView;
    private ListDataMode iModel;
    private ArrayList<Case> cases;
    private int []searchIndex;
    public ListPresenter(ListFragmentContract.IView view){
        iView=view;
        iModel=ListDataMode.getInstance();
        searchIndex=new int[1000];
    }
    @Override
    public void start() {
        cases=iModel.getData();
        search(null);
        //iView.updateListView(iModel.getData());
    }


    @Override
    public void unRegister() {
        if (iModel!=null) {
            iModel.unRisterCaseListPublisher(this);
        }
    }

    @Override
    public void register() {
        if (iModel!=null){
            iModel.registerCaseListPublisher(this);
        }
    }

    @Override
    public void gotoTakePhoto(int position) {
        iView.gotoTakePhoto(searchIndex[position]);
    }

    @Override
    public void search(String num) {
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
    @Override
    public void updateData(ArrayList<Case> datas) {
        if (datas==null)return;
        cases=datas;
        search(null);
    }

}
