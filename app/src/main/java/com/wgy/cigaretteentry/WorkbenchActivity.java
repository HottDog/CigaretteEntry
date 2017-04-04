package com.wgy.cigaretteentry;

import android.content.Intent;
import android.media.Image;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wgy.cigaretteentry.codeCopyModel.CodeCopyActivity;

public class WorkbenchActivity extends BaseActivity {
    private static final String TAG = "WorkbenchActivity";
    private ImageView codeCopy ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench);
        initView();
    }
    private void initView(){
        codeCopy = (ImageView)findViewById(R.id.codecopy);
        codeCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkbenchActivity.this, CodeCopyActivity.class);
                startActivity(intent);
            }
        });
    }
}
