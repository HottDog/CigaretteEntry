package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wgy.cigaretteentry.BaseActivity;
import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.local.CigaretteLocalData;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.addfragment.AddFragment;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.deletefragment.DeleteFragment;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.ListFragment;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment.UploadFragment;

import java.util.ArrayList;

public class CodeCopyActivity extends BaseActivity implements AddFragment.OnFragmentInteractionListener,IObserver<ArrayList<Case>> {
    private static final String TAG = "CodeCopyActivity";
    private ImageView list;
    private ImageView add;
    private ImageView delete;
    private ImageView upload;

    private ImageView back;
    private EditText search_edit;
    private ImageView search;
    private Button batchDelete;
    private TextView addTitle;
    private TextView num;
    private LinearLayout search_bg;

    private ReadLocalCigaretteDataThread thread;

    AddFragment addFragment;
    ListFragment listFragment;
    UploadFragment uploadFragment;
    DeleteFragment deleteFragment;
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
        initDefaultView(savedInstanceState);
        readLocalCigaretteData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListDataMode.getInstance().registerCaseListPublisher(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ListDataMode.getInstance().unRisterCaseListPublisher(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //保存tab选中的状态
        outState.putInt(INDEX_FRAG,index);
        super.onSaveInstanceState(outState);
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
        addTitle=(TextView)findViewById(R.id.add_title_tx);
        num=(TextView)findViewById(R.id.num);
        search_bg=(LinearLayout)findViewById(R.id.search_bg);

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
                if(mContent==listFragment){
                    listFragment.search(search_edit.getText().toString());
                }else if(mContent==deleteFragment){
                    deleteFragment.search(search_edit.getText().toString());
                }else if(mContent==uploadFragment){
                    uploadFragment.search(search_edit.getText().toString());
                }
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoiceEffect(0);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoiceEffect(1);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoiceEffect(2);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoiceEffect(3);
            }
        });
    }
    private void initDefaultView(Bundle savedInstanceState){
        if (savedInstanceState==null) {
            if (listFragment == null) {
                listFragment =ListFragment.newInstance();
            }
            switchContent(null,listFragment,0);
        }else {
            if (null == fm){
                fm = getFragmentManager();
            }
            index=savedInstanceState.getInt(INDEX_FRAG);
            listFragment=(ListFragment) fm.findFragmentByTag(FRAGMENT_TAG[0]);
            addFragment=(AddFragment) fm.findFragmentByTag(FRAGMENT_TAG[1]);
            deleteFragment=(DeleteFragment) fm.findFragmentByTag(FRAGMENT_TAG[2]);
            uploadFragment=(UploadFragment)fm.findFragmentByTag(FRAGMENT_TAG[3]);
            setmContent(index);
        }
        setChoiceEffect(0);
    }
    private void readLocalCigaretteData(){
        thread = new ReadLocalCigaretteDataThread();
        thread.start();
    }
    private void setChoiceEffect(int index){
        this.index=index;
        clearChoiceEffect();
        switch (index){
            case 0:
                num.setVisibility(View.VISIBLE);
                num.setText(NUM_TX+ListDataMode.getInstance().getAllCaseNum());
                search_bg.setVisibility(View.VISIBLE);
                list.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_list_up));
                if (null == listFragment){
                    listFragment=ListFragment.newInstance();
                }
                switchContent(mContent,listFragment,index);
                break;
            case 1:
                addTitle.setVisibility(View.VISIBLE);
                add.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_add_up));
                if(null==addFragment){
                    addFragment=AddFragment.newInstance();
                    addFragment.setmListener(this);
                }
                switchContent(mContent,addFragment,index);
                break;
            case 2:
                batchDelete.setVisibility(View.VISIBLE);
                num.setVisibility(View.VISIBLE);
                num.setText(NUM_TX+ListDataMode.getInstance().getAllCaseNum());
                search_bg.setVisibility(View.VISIBLE);
                delete.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_delete_up));
                if(null==deleteFragment){
                    deleteFragment=DeleteFragment.newInstance();
                }
                switchContent(mContent,deleteFragment,index);
                break;
            case 3:
                num.setVisibility(View.VISIBLE);
                num.setText(NUM_TX+ListDataMode.getInstance().getUploadCaseNum());
                search_bg.setVisibility(View.VISIBLE);
                upload.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_upload_up));
                if(uploadFragment==null){
                    uploadFragment=UploadFragment.newInstance();
                }
                switchContent(mContent,uploadFragment,index);
                break;
            default:
                break;
        }
    }
    private void clearChoiceEffect(){
        list.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_list));
        add.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_add));
        delete.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_delete));
        upload.setImageDrawable(getResources().getDrawable(R.mipmap.codecopy_upload));

        batchDelete.setVisibility(View.GONE);
        addTitle.setVisibility(View.GONE);
        search_bg.setVisibility(View.GONE);
        num.setVisibility(View.GONE);

    }
    public void switchContent(Fragment from, Fragment to, int index) {
        if(fm==null){
            fm=getFragmentManager();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (null==from){
            mContent=to;
            fm.beginTransaction().add(R.id.codedopy_content,
                    to,FRAGMENT_TAG[index]).commit();
        }else if(mContent != to){
                mContent = to;
                if (!to.isAdded()) {    // 先判断是否被add过
                    transaction.hide(from).add(R.id.codedopy_content, to,
                            FRAGMENT_TAG[index]).commit(); // 隐藏当前的fragment，add下一个到Activity中

                } else {
                    transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
                }
        }
    }
    private void setmContent(int index){
        switch (index){
            case 0:
                mContent=listFragment;
                break;
            case 1:
                mContent=addFragment;
                break;
            case 2:
                mContent=deleteFragment;
                break;
            case 3:
                mContent=uploadFragment;
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void gotoListFragment() {
        setChoiceEffect(0);
    }

    @Override
    public void updateData(ArrayList<Case> datas) {
        if(mContent!=uploadFragment){
            num.setText(NUM_TX+datas.size());
        }
    }

    public final static String NUM_TX="案件数量：";
    public final static String UPLOAD_NUM_TX="未上传数量：";

    class ReadLocalCigaretteDataThread extends Thread{
        @Override
        public void run() {
            super.run();
            Log.d(TAG,"ReadLocalCigaretteDataThread run"+" "+ CigaretteLocalData.getInstance().getSize());
            if(CigaretteLocalData.getInstance().getSize()==0) {
                CigaretteLocalData.getInstance().readChinaCigaretteData();
                CigaretteLocalData.getInstance().readCigaretteData();
            }
        }
    }


}
