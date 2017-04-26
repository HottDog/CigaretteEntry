package com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wgy.cigaretteentry.BaseActivity;
import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.CodeCopyActivity;
import com.wgy.cigaretteentry.util.Util;
import com.wgy.cigaretteentry.zxing.activity.CaptureActivity;

import java.io.File;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.wgy.cigaretteentry.util.Util.getSDCardPath;

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

    //拍照获取图片
    private Uri imageUri1;
    private Uri imageCropUri1;
    private Uri imageUri2;
    private Uri imageCropUri2;

    private Uri laserCodeUri;
    private Uri laserCodeCropUri;

    private int takePhoteCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_for_case);
        initView();
        initTakePhoto();
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
        //扫描条形码
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TakePhotoForCaseActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (takePhoteCount){
                    case 0:
                        takeCameraOnly(imageUri1,RESULT_CAMERA_ONLY_PIC1);
                        break;
                    case 1:
                        takeCameraOnly(imageUri2,RESULT_CAMERA_ONLY_PIC2);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"当前最多只能拍摄两张照片！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        laser_code_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCameraOnly(laserCodeUri,RESULT_CAMERA_ONLY_LASER_CODE);
            }
        });
    }
    private void initTakePhoto(){
        String path = getSDCardPath();
        File file1 = new File(path + "/temp1.jpg");
        imageUri1 = Uri.fromFile(file1);
        File cropFile1 = new File(path + "/temp_crop1.jpg");
        imageCropUri1 = Uri.fromFile(cropFile1);
        File file2 = new File(path + "/temp2.jpg");
        imageUri2 = Uri.fromFile(file2);
        File cropFile2 = new File(path + "/temp_crop2.jpg");
        imageCropUri2 = Uri.fromFile(cropFile2);

        File laserCodeFile = new File(path + "laserCode.jpg");
        laserCodeUri = Uri.fromFile(laserCodeFile);
        File laserCodeCropFile = new File(path + "laserCodeCrop.jpg");
        laserCodeCropUri = Uri.fromFile(laserCodeCropFile);
    }
    /**
     * 扫描二维码的回调函数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //扫描二维码的返回结果
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Log.d(TAG,bundle.getString("result"));
                    //显示扫描到的内容
                    //mTextView.setText(bundle.getString("result"));
                    //显示
                    //mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
            //拍照获取第一张照片
            case RESULT_CAMERA_ONLY_PIC1:
                cropImg(imageUri1,imageCropUri1,RESULT_CAMERA_CROP_PATH_RESULT_PIC1);
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_PIC1:
                try {
                    Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri1));
                    pic1.setImageBitmap(bitmap1);
                    takePhoteCount = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            //拍照获取第二张照片
            case RESULT_CAMERA_ONLY_PIC2:
                cropImg(imageUri2,imageCropUri2,RESULT_CAMERA_CROP_PATH_RESULT_PIC2);
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_PIC2:
                try {
                    Bitmap bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri2));
                    pic2.setImageBitmap(bitmap2);
                    takePhoteCount = 2 ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //拍照获取激光码照片
            case RESULT_CAMERA_ONLY_LASER_CODE:
                cropImg(laserCodeUri,laserCodeCropUri,RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE);
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE:
                try {
                    Bitmap bitmap3 = BitmapFactory.decodeStream(getContentResolver().openInputStream(laserCodeCropUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 调起拍照
     * @param uri 拍照获取的图片的保存路径
     * @param result 返回结果的获取id
     */
    private void takeCameraOnly(Uri uri,int result) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, result);
    }

    /**
     * 裁剪拍照后的图片
     * @param uri 被裁决的图片的路径
     * @param cropUri 裁剪后的图片的保存路径
     * @param result 返回结果的获取id
     */
    public void cropImg(Uri uri,Uri cropUri,int result) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, result);
    }

    @Override
    public void setPresenter(TakePhotoForCaseContract.Presenter presenter) {

    }

    public final static String PRICE_TX="价格：￥";
    public final static String NAME_TX="香烟品牌：";

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private static final int RESULT_CAMERA_ONLY_PIC1 = 101;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT_PIC1 = 102;
    private static final int RESULT_CAMERA_ONLY_PIC2 = 103;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT_PIC2 = 104;
    private static final int RESULT_CAMERA_ONLY_LASER_CODE = 105;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE = 106;

}
