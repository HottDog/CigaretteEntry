package com.wgy.cigaretteentry.model.codeCopyModel;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wgy.cigaretteentry.BaseActivity;
import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.model.BaseView;

public class CodeCopyActivity extends BaseActivity{
    private static final String TAG = "CodeCopyActivity";
    private ImageView list;
    private ImageView add;
    private ImageView delete;
    private ImageView upload;

    private ImageView back;
    private EditText search_edit;
    private ImageView search;
    private Button batchDelete;

    FragmentManager fm;
    private Fragment mContent;       //目前具体是哪个fragment
    private int index=0;             //目前是第几个fragment
    private static final String INDEX_FRAG="INDEX_FRAG";
    //fragment的标签
    private static final String[] FRAGMENT_TAG =
            {"listfrag","addfrag","deletefrag","uploadfrag"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_copy);
        initView();
    }
    private void initView(){
        back = (ImageView)findViewById(R.id.back);
        search_edit=(EditText)findViewById(R.id.search_edit);
        search=(ImageView)findViewById(R.id.search);
        batchDelete=(Button)findViewById(R.id.batch_delete);
        list = (ImageView)findViewById(R.id.list);
        add = (ImageView)findViewById(R.id.add);
        delete = (ImageView)findViewById(R.id.delete);
        upload = (ImageView)findViewById(R.id.upload);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        batchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void switchContent(Fragment from, Fragment to, int index) {
        if (mContent != to) {
            mContent = to;
            if(fm==null){
                fm=getFragmentManager();
            }
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.codedopy_content, to,
                        FRAGMENT_TAG[index]).commit(); // 隐藏当前的fragment，add下一个到Activity中

            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
