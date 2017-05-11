package com.wgy.cigaretteentry.data.local;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.local.db.DBOpenHelper;
import com.wgy.cigaretteentry.util.DataUtil;

import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by 袁江超 on 2017/5/8.
 */

public class CigaretteLocalData {
    private static final String TAG = "CigaretteLocalData";
    private ArrayList<Cigarette> localCigarettes;
    private Context context;
    private CigaretteLocalData(Context context){
        this.context=context;
        localCigarettes=new ArrayList<>();
    }
    public static class CigaretteLocalDataHolder{
        private static CigaretteLocalData cigaretteLocalData;
        public static void register(Context context){
            cigaretteLocalData=new CigaretteLocalData(context);
        }
    }
    public static CigaretteLocalData getInstance(){
        return CigaretteLocalDataHolder.cigaretteLocalData;
    }
    public void addCigarette(Cigarette cigarette){
        localCigarettes.add(cigarette);
    }

    /**
     * 通过条形码查找该卷烟的基本信息
     * @param barcode
     * @return null表示没有查找到
     */
    public Cigarette search(String barcode){
        for (int i=0;i<localCigarettes.size();i++){
            Cigarette c = localCigarettes.get(i);
            if(c.getBarcode().equals(barcode)){
                return c;
            }
        }
        return null;
    }
    public void readChinaCigaretteData() {
        AssetManager assetManager = context.getAssets();
        try {
            Workbook workbook=Workbook.getWorkbook(assetManager.open(CHINA_CIGARETTE_DATA));
            int sheetNum = workbook.getNumberOfSheets();
            Log.d(TAG,CHINA_CIGARETTE_DATA+"表格有"+sheetNum+"张表格");
            for(int i=0;i<sheetNum;i++){
                Sheet sheet = workbook.getSheet(i);
                Log.d(TAG,CHINA_CIGARETTE_DATA+"的第"+i+"张表格数据");
                int sheetRows = sheet.getRows();
                int sheetColumns = sheet.getColumns();
                Log.d(TAG,"表格名字"+sheet.getName());
                Log.d(TAG,"表格的行数："+sheetRows);
                Log.d(TAG,"表格的列数："+sheetColumns);
                for (int k=3;k<sheetRows;k++){
                    if(sheet.getCell(1,k).getContents().equals("")){
                        break;
                    }
                    Cigarette c=new Cigarette();
                    c.setBarcode(sheet.getCell(1,k).getContents());
                    c.setPrice(Double.parseDouble(sheet.getCell(3,k).getContents()));
                    c.setName(DataUtil.removeBracketContent(sheet.getCell(2,k).getContents()));
                    localCigarettes.add(c);
                    Log.d(TAG,c.toString());
                }
                workbook.close();
            }
        } catch (IOException e) {
            Log.e(TAG,"IOException :"+e.toString());
        } catch (BiffException e) {
            e.printStackTrace();
            Log.e(TAG,"BiffException :"+e.toString());
        }
    }
    public void readCigaretteData() {
        AssetManager assetManager = context.getAssets();
        try {
            Workbook workbook=Workbook.getWorkbook(assetManager.open(CIGARETTE_DATA));
            int sheetNum = workbook.getNumberOfSheets();
            Log.d(TAG,CIGARETTE_DATA+"表格有"+sheetNum+"张表格");
            for(int i=0;i<sheetNum;i++){
                Sheet sheet = workbook.getSheet(i);
                Log.d(TAG,CIGARETTE_DATA+"的第"+i+"张表格数据");
                int sheetRows = sheet.getRows();
                int sheetColumns = sheet.getColumns();
                Log.d(TAG,"表格名字"+sheet.getName());
                Log.d(TAG,"表格的行数："+sheetRows);
                Log.d(TAG,"表格的列数："+sheetColumns);
                for (int k=4;k<sheetRows;k++){
                    if(sheet.getCell(1,k).getContents().equals("")){
                        break;
                    }
                    Cigarette c=new Cigarette();
                    c.setBarcode(DataUtil.format2(sheet.getCell(2,k).getContents()));
                    c.setPrice(Double.parseDouble(sheet.getCell(4,k).getContents()));
                    c.setName(DataUtil.removeBracketContent(sheet.getCell(3,k).getContents()));
                    localCigarettes.add(c);
                    Log.d(TAG,c.toString());
                }
                workbook.close();
            }
        } catch (IOException e) {
            Log.e(TAG,"IOException :"+e.toString());
        } catch (BiffException e) {
            e.printStackTrace();
            Log.e(TAG,"BiffException :"+e.toString());
        }
    }
    public int getSize(){
        return localCigarettes.size();
    }
    private static final String CHINA_CIGARETTE_DATA="china_cigarette_data.xls";
    private static final String CIGARETTE_DATA = "cigarette_data.xls";
}
