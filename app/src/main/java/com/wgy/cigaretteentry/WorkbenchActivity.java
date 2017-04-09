package com.wgy.cigaretteentry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.CodeCopyActivity;

public class WorkbenchActivity extends BaseActivity {
    private static final String TAG = "WorkbenchActivity";
    private ImageView codeCopy ;
    private ImageView legalinstruments;
    private ImageView notification;
    private ImageView set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench);
        initView();
    }
    private void initView(){
        //条码抄录
        codeCopy = (ImageView)findViewById(R.id.codecopy);
        codeCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkbenchActivity.this, CodeCopyActivity.class);
                startActivity(intent);
            }
        });
        //法律文书
        legalinstruments = (ImageView)findViewById(R.id.legalinstruments);
        legalinstruments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //通知公告
        notification = (ImageView)findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //设置
        set = (ImageView)findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
