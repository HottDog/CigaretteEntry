package com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wgy.cigaretteentry.BaseActivity;
import com.wgy.cigaretteentry.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_case_info);
        initView();
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

    @Override
    public void setPresenter(DetailCaseInfoContract.Presenter presenter) {

    }
    public final static String TOTAL_NUM_TX="卷烟总数：";
}
