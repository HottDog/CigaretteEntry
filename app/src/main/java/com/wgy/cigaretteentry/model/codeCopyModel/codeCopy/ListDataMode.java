package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy;

import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.ListFragmentContract;
import com.wgy.cigaretteentry.util.DataUtil;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class ListDataMode implements BaseDataModel {
    private ArrayList<Case> cases;
    private static ListDataMode instance;
    private ListDataMode(){
        cases=new ArrayList<>();
        init();
    }
    public static ListDataMode getInstance(){
        if (instance==null){
            instance=new ListDataMode();
        }
        return instance;
    }
    @Override
    public ArrayList<Case> getInitData() {
        return cases;
    }
    private void init(){
        for(int i=0;i<8;i++){
            Case c=new Case();
            c.setDate("2016年10月3日");
            c.setUserID("1546512345");
            c.setDepartmentID("452145661324");
            c.setNumber("1542124575421345");
            c.setUpload_or_not(DataUtil.getRandomBool());
            c.setTotalNum(DataUtil.getIntRandom(100,200));
            cases.add(c);
        }
    }
}
