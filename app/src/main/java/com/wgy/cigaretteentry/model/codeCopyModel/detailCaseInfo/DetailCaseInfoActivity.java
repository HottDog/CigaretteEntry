package com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wgy.cigaretteentry.BaseActivity;
import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.bean.CigarettesNum;
import com.wgy.cigaretteentry.data.local.PreferenceData;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.addfragment.AddFragment;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.ListFragment;

import java.util.ArrayList;

public class DetailCaseInfoActivity extends BaseActivity implements DetailCaseInfoContract.IView{
    private static final String TAG = "DetailCaseInfoActivity";

    private TextView year_tx;
    private TextView number_tx;
    private TextView userID_tx;
    private TextView departmentID_tx;
    private TextView date_tx;
    private TextView totalnum_tx;
    private ListView numListView;
    private ListView cigaretteListView;
    private Button back;

    private DetailCaseInfoPresenter presenter;
    private DetailCaseInfoNumAdapter numAdapter;
    private CigaretteListAdapter cigaretteListAdapter;

    private int index_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_case_info);
        presenter=new DetailCaseInfoPresenter(this);
        initData();
        initView();
        initListView();
        presenter.start(index_val);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.register();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unRegister();
    }

    private void initData(){
        Bundle myBundle=this.getIntent().getExtras();
        index_val=myBundle.getInt(ListFragment.INDEX);
        Log.d(TAG,"index="+index_val);
    }
    private void initView(){
        year_tx=(TextView)findViewById(R.id.year_tx);
        number_tx=(TextView)findViewById(R.id.number_tx);
        departmentID_tx=(TextView)findViewById(R.id.department_id_tx);
        userID_tx=(TextView)findViewById(R.id.user_id_tx);
        date_tx=(TextView)findViewById(R.id.date_tx);

        totalnum_tx=(TextView)findViewById(R.id.total_num);
        numListView=(ListView)findViewById(R.id.num_listview);
        cigaretteListView=(ListView)findViewById(R.id.cigarette_listview);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initListView(){
        numAdapter=new DetailCaseInfoNumAdapter(this);
        numListView.setAdapter(numAdapter);

        cigaretteListAdapter=new CigaretteListAdapter(this);
        cigaretteListView.setAdapter(cigaretteListAdapter);
    }

    @Override
    public void setPresenter(DetailCaseInfoContract.Presenter presenter) {

    }
    @Override
    public void updateNumListview(ArrayList<CigarettesNum> cigarettesNa) {
        numAdapter.setData(cigarettesNa);
        numAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCigaretteListview(ArrayList<Cigarette> cigarettes) {
        cigaretteListAdapter.setData(cigarettes);
        cigaretteListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateDataView(Case c) {
        year_tx.setText(c.getYear());
        number_tx.setText(c.getNumber());
        userID_tx.setText(PreferenceData.getUserID(this));
        departmentID_tx.setText(c.getDepartmentID());
        date_tx.setText(c.getDate());
        totalnum_tx.setText(TOTAL_NUM_TX+Integer.valueOf(c.getTotalNum()).toString());
    }

    public final static String TOTAL_NUM_TX="卷烟总数：";


}
