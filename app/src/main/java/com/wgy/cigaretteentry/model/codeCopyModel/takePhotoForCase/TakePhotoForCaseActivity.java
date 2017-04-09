package com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgy.cigaretteentry.BaseActivity;
import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.CodeCopyActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class TakePhotoForCaseActivity extends BaseActivity implements TakePhotoForCaseContract.IView{
    private static final String TAG = "TakePhotoForCaseActivit";

    private Button back;
    private Button scan;
    private Button laser_code_bn;            //拍摄激光码
    private Button takephoto;
    private EditText laser_code_edit;
    private TextView price;
    private TextView name;
    private ImageView pic1;
    private ImageView pic2;
    private Button enter;
    private Button cancel;
    private Button complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_for_case);
        initView();
    }
    private void initView(){
        back=(Button)findViewById(R.id.back);
        scan=(Button)findViewById(R.id.scan);
        laser_code_bn=(Button)findViewById(R.id.laser_code_bn);
        takephoto=(Button)findViewById(R.id.takephoto);
        laser_code_edit=(EditText)findViewById(R.id.laser_code_edit);
        price=(TextView)findViewById(R.id.price_tx);
        name=(TextView)findViewById(R.id.name_tx );
        pic1=(ImageView)findViewById(R.id.pic1);
        pic2=(ImageView)findViewById(R.id.pic2);
        enter=(Button)findViewById(R.id.enter);
        cancel=(Button)findViewById(R.id.cancel);
        complete=(Button)findViewById(R.id.complete);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TakePhotoForCaseActivity.this, CodeCopyActivity.class);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void setPresenter(TakePhotoForCaseContract.Presenter presenter) {

    }
    public final static String PRICE_TX="价格：￥";
    public final static String NAME_TX="香烟品牌：";
}
