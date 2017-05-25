package com.wgy.cigaretteentry.model.codeCopyModel.takePhotoForCase;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.addfragment.AddFragment;
import com.wgy.cigaretteentry.util.DataUtil;
import com.wgy.cigaretteentry.util.Util;
import com.wgy.cigaretteentry.zxing.activity.CaptureActivity;

import java.io.File;
import java.io.FileNotFoundException;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.makeMainActivity;
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

    private File cropFile1;
    private File cropFile2;
    private File laserCodeCropFile;

    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;

    private Uri laserCodeUri;
    private Uri laserCodeCropUri;

    private int takePhoteCount = 0;
    private boolean takePhotoLasercode = false;

    private TakePhotoForCasePresenter presenter;
    private String barcode_val="";
    private String name_val;
    private double price_val;

    private Intent cameraIntent;
    private Intent cropIntent;

    private Uri currenteUri;
    private int currentResult;

    private int curCropResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate()");
        setContentView(R.layout.activity_take_photo_for_case);
        presenter=new TakePhotoForCasePresenter(this);
        initData();
        initTakePhoto();
        initView();
        clear();
    }
    private void initData(){
        Bundle myBundle=this.getIntent().getExtras();
        presenter.iniCase(myBundle.getInt(AddFragment.INDEX));
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
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    String picTmp1;
                    String picTmp2;
                    String laserPicTmp;
                    if(takePhoteCount==0) {
                        picTmp1=null;
                        picTmp2=null;
                    }else if(takePhoteCount==1){
                        picTmp1 = DataUtil.encode(DataUtil.getByteFromFile(cropFile1));
                        picTmp2=null;
                    }else {
                        picTmp1 = DataUtil.encode(DataUtil.getByteFromFile(cropFile1));
                        picTmp2 = DataUtil.encode(DataUtil.getByteFromFile(cropFile2));
                    }
                    if(takePhotoLasercode){
                        laserPicTmp =DataUtil.encode(DataUtil.getByteFromFile(laserCodeCropFile));
                    }else {
                        laserPicTmp =null;
                    }
                    presenter.complete(laser_code_edit.getText().toString(),picTmp1,picTmp2,laserPicTmp);
                    presenter.addMore();
                    clear();
                }
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
                if(check()){
                    String picTmp1;
                    String picTmp2;
                    String laserPicTmp;
                    if(takePhoteCount==0) {
                        picTmp1=null;
                        picTmp2=null;
                    }else if(takePhoteCount==1){
                        picTmp1 = DataUtil.encode(DataUtil.getByteFromFile(cropFile1));
                        picTmp2=null;
                    }else {
                        picTmp1 = DataUtil.encode(DataUtil.getByteFromFile(cropFile1));
                        picTmp2 = DataUtil.encode(DataUtil.getByteFromFile(cropFile2));
                    }
                    if(takePhotoLasercode){
                        laserPicTmp =DataUtil.encode(DataUtil.getByteFromFile(laserCodeCropFile));
                    }else {
                        laserPicTmp =null;
                    }
                    presenter.complete(laser_code_edit.getText().toString(),picTmp1,picTmp2,laserPicTmp);
                    Intent intent=new Intent(TakePhotoForCaseActivity.this, CodeCopyActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
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
        cropFile1 = new File(path + "/temp_crop1.jpg");
        imageCropUri1 = Uri.fromFile(cropFile1);
        File file2 = new File(path + "/temp2.jpg");
        imageUri2 = Uri.fromFile(file2);
        cropFile2 = new File(path + "/temp_crop2.jpg");
        imageCropUri2 = Uri.fromFile(cropFile2);

        File laserCodeFile = new File(path + "/laserCode.jpg");
        laserCodeUri = Uri.fromFile(laserCodeFile);
        laserCodeCropFile = new File(path + "/laserCodeCrop.jpg");
        laserCodeCropUri = Uri.fromFile(laserCodeCropFile);
    }

    private boolean check(){
        if (barcode_val.equals("")){
            Toast.makeText(this,"请扫描条形码录入卷烟基本信息",Toast.LENGTH_SHORT).show();
            return false;
        }else if (laser_code_edit.getText().toString().equals("")){
            Toast.makeText(this,"请输入激光码",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
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
        Log.d(TAG,"onActivityResult()");
        switch (requestCode) {
            //扫描二维码的返回结果
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Log.d(TAG,bundle.getString("result"));
                    barcode_val=bundle.getString("result");
                    presenter.getCigaretteData(barcode_val);
                    //显示扫描到的内容
                    //mTextView.setText(bundle.getString("result"));
                    //显示
                    //mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
            //拍照获取第一张照片
            case RESULT_CAMERA_ONLY_PIC1:
                Log.d(TAG,"拍摄到第一张照片");
                cropImg(imageUri1,imageCropUri1,RESULT_CAMERA_CROP_PATH_RESULT_PIC1);
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_PIC1:
//                Log.d(TAG,"裁剪好第一张照片");
//                try {
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri1));
//                    pic1.setImageBitmap(bitmap);
//                    takePhoteCount = 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                verifyStoragePermissions(RESULT_CAMERA_CROP_PATH_RESULT_PIC1);
                break;
            //拍照获取第二张照片
            case RESULT_CAMERA_ONLY_PIC2:
                Log.d(TAG,"拍摄到第二张照片");
                cropImg(imageUri2,imageCropUri2,RESULT_CAMERA_CROP_PATH_RESULT_PIC2);
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_PIC2:
//                Log.d(TAG,"裁剪好第二张照片");
//                try {
//                    Bitmap bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri2));
//                    pic2.setImageBitmap(bitmap2);
//                    takePhoteCount = 2 ;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                verifyStoragePermissions(RESULT_CAMERA_CROP_PATH_RESULT_PIC2);
                break;
            //拍照获取激光码照片
            case RESULT_CAMERA_ONLY_LASER_CODE:
                Log.d(TAG,"拍摄到激光码照片");
                cropImg(laserCodeUri,laserCodeCropUri,RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE);
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE:
//                Log.d(TAG,"裁剪好激光码照片");
//                try {
//                    //Bitmap bitmap3 = BitmapFactory.decodeStream(getContentResolver().openInputStream(laserCodeCropUri));
//                    takePhotoLasercode = true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                verifyStoragePermissions(RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE);
                break;
            default:
                break;
        }
    }

    // Storage Permissions
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     */
    private void verifyStoragePermissions(int result) {
// Check if we have write permission
        curCropResult = result;
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ActivityCompat.checkSelfPermission(TakePhotoForCaseActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
// We don't have permission so prompt the user
                ActivityCompat.requestPermissions(TakePhotoForCaseActivity.this, PERMISSIONS_STORAGE,
                        224);
                return;
            }else {
                   showPic(result);
            }
        }else {
            showPic(result);
        }
    }

    private void showPic(int result){
        switch (result){
            case RESULT_CAMERA_CROP_PATH_RESULT_PIC1:
                Log.d(TAG,"裁剪好第一张照片");
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri1));
                    pic1.setImageBitmap(bitmap);
                    takePhoteCount = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_PIC2:
                Log.d(TAG,"裁剪好第二张照片");
                try {
                    Bitmap bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageCropUri2));
                    pic2.setImageBitmap(bitmap2);
                    takePhoteCount = 2 ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE:
                Log.d(TAG,"裁剪好激光码照片");
                try {
                    //Bitmap bitmap3 = BitmapFactory.decodeStream(getContentResolver().openInputStream(laserCodeCropUri));
                    takePhotoLasercode = true;
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
        currenteUri = uri;
        currentResult =result;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(TakePhotoForCaseActivity.this, Manifest.permission.CAMERA);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(TakePhotoForCaseActivity.this,new String[]{Manifest.permission.CAMERA},222);
                return;
            }else{

                takeCamera(uri, result);//调用具体方法
            }
        } else {

            takeCamera(uri, result);//调用具体方法
        }
    }

    private void takeCamera(Uri uri,int result) {
        if(cameraIntent==null)
            cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        cameraIntent.putExtra("return-data", false);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        cameraIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        cameraIntent.putExtra("noFaceDetection", true);
        startActivityForResult(cameraIntent, result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    takeCamera(currenteUri, currentResult);
                } else {
                    // Permission Denied
                    Toast.makeText(TakePhotoForCaseActivity.this, "很遗憾你把相机权限禁用了。请开启相机权限享受我们提供的服务吧。", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case 224:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    showPic(curCropResult);
                } else {
                    // Permission Denied
                    Toast.makeText(TakePhotoForCaseActivity.this, "很遗憾你把读写文件权限禁用了。请开启读写文件权限享受我们提供的服务吧。", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    /**
     * 裁剪拍照后的图片
     * @param uri 被裁决的图片的路径
     * @param cropUri 裁剪后的图片的保存路径
     * @param result 返回结果的获取id
     */
    public void cropImg(Uri uri,Uri cropUri,int result) {
        if (cropIntent==null)
            cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX",150);
        cropIntent.putExtra("outputY", 150);
        cropIntent.putExtra("return-data", false);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        cropIntent.putExtra("noFaceDetection", true);
        startActivityForResult(cropIntent, result);
    }

    private void clear(){
        takePhoteCount = 0;
        price.setText(PRICE_TX);
        name.setText(NAME_TX);
        pic1.setImageBitmap(bitmap1);
        pic2.setImageBitmap(bitmap2);
        laser_code_edit.setText("");
    }
    @Override
    public void setPresenter(TakePhotoForCaseContract.Presenter presenter) {

    }
    @Override
    public void initCigaretteData(boolean hasData, String name, String price) {
        if(hasData){
            this.name.setText(NAME_TX+name+CIGARETTE_TX);
            this.price.setText(PRICE_TX+price);
        }else {
            Toast.makeText(this,"扫描卷烟信息失败",Toast.LENGTH_SHORT).show();
        }
    }
    public final static String PRICE_TX="价格：￥";
    public final static String NAME_TX="香烟品牌：";
    public final static String CIGARETTE_TX = "香烟";

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private static final int RESULT_CAMERA_ONLY_PIC1 = 101;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT_PIC1 = 102;
    private static final int RESULT_CAMERA_ONLY_PIC2 = 103;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT_PIC2 = 104;
    private static final int RESULT_CAMERA_ONLY_LASER_CODE = 105;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT_LASER_CODE = 106;


}
