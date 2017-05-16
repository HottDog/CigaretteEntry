package com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo;

import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.bean.CigarettesNum;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.ListDataMode;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.deletefragment.DeleteFragmentContract;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/5/7.
 */

public class DetailCaseInfoPresenter implements DetailCaseInfoContract.Presenter,IObserver{
    private static final String TAG = "DetailCaseInfoPresenter";
    private ListDataMode listDataMode;
    private DetailCaseInfoContract.IView iView;
    private Case mCase;
    private ArrayList<CigarettesNum> cigarettesNa;
    public DetailCaseInfoPresenter(DetailCaseInfoContract.IView iView){
        this.iView=iView;
        listDataMode=ListDataMode.getInstance();
        cigarettesNa = new ArrayList<>();
    }
    @Override
    public void start(int index) {
        mCase = listDataMode.getCaseDetailByIndex(index);
        processData();
        iView.updateDataView(mCase);
        iView.updateNumListview(cigarettesNa);
        iView.updateCigaretteListview(mCase.getCigarettes());
    }

    @Override
    public void register() {
        listDataMode.registerCaseDetailPublisher(this);
    }

    @Override
    public void unRegister() {
        listDataMode.unRegisterCaseDetailPublisher(this);

    }

    @Override
    public void updateData(Object datas) {
        mCase = (Case) datas;
        processData();
        iView.updateDataView(mCase);
        iView.updateNumListview(cigarettesNa);
        iView.updateCigaretteListview(mCase.getCigarettes());
    }

    @Override
    public void start() {

    }

    private void processData(){
        ArrayList<Cigarette> cigarettes=mCase.getCigarettes();
        boolean first_or_not = true;
        if(cigarettes.size()>0){
            CigarettesNum cigarettesNum=new CigarettesNum();
            int[]read_or_not = new int[cigarettes.size()];
            int num_count=0;
            for(int i=0;i<cigarettes.size();i++){
                if(read_or_not[i]==0) {
                    if (num_count==0) {
                        if(first_or_not){
                            cigarettesNa.add(cigarettesNum);
                            first_or_not=false;
                        }else {
                            cigarettesNum = new CigarettesNum();
                        }
                        cigarettesNum.setLeftname(cigarettes.get(i).getName());
                        num_count++;
                    }else {
                        cigarettesNum.setRightname(cigarettes.get(i).getName());
                        num_count=0;
                    }
                    if (cigarettes.size() ==1){
                        cigarettesNum.increaseLeftNum();
                    }else {
                        for (int j = i + 1; j < cigarettes.size(); j++) {
                            if (read_or_not[j] == 0) {
                                if (cigarettes.get(i).getName().equals(cigarettes.get(j).getName())) {
                                    if (num_count == 0) {
                                        cigarettesNum.increaseLeftNum();
                                    } else {
                                        cigarettesNum.increaseRightNum();
                                    }
                                    read_or_not[j] = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
