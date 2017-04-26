package com.wgy.cigaretteentry.data.local.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.local.db.DBOpenHelper;
import com.wgy.cigaretteentry.data.local.db.DBinstance;
import com.wgy.cigaretteentry.util.DataUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 袁江超 on 2017/4/24.
 */

public class CigaretteDAO {
    private static final String TAG = "CigaretteDAO";
    public CigaretteDAO(){}

    public boolean insertCigaretteValue(List<Cigarette> cs){
        if (null == cs){
            return false;
        }
        boolean result =false;   //插入操作的结果
        MyApplication.lock.writeLock().lock();
        SQLiteDatabase db= DBinstance.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            for(int i=0;i<cs.size();i++) {
                Cigarette cigarette=cs.get(i);
                ContentValues values = new ContentValues();
                values.put(CIGARETTE_PARAMS[1],cigarette.getNum());
                values.put(CIGARETTE_PARAMS[2],cigarette.getName());
                values.put(CIGARETTE_PARAMS[3],Double.valueOf(cigarette.getPrice()).toString());
                values.put(CIGARETTE_PARAMS[4],cigarette.getBarcode());
                values.put(CIGARETTE_PARAMS[5],cigarette.getLasercode());
                values.put(CIGARETTE_PARAMS[6],cigarette.getLasercodeImgUrl());
                values.put(CIGARETTE_PARAMS[7],cigarette.getPic1());
                values.put(CIGARETTE_PARAMS[8],cigarette.getPic2());
                values.put(CIGARETTE_PARAMS[9], Long.valueOf(DataUtil.getCurrentTimeStamp()).toString());
                db.insert(
                        DBOpenHelper.CIGARETTE_TABLE_NAME,CIGARETTE_PARAMS[8],values);
                Log.d(TAG,cigarette.getName()+"插入了数据库");
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
        }finally {
            db.endTransaction();
            MyApplication.lock.writeLock().unlock();
        }
        return result;
    }

    /**
     * 更新卷烟列表数据
     * @param cs
     * @return
     */
    public boolean updateCigaretteValue(List<Cigarette> cs){
        if(null == cs){
            return false;
        }
        boolean result = false;
        MyApplication.lock.writeLock().lock();
        SQLiteDatabase db=DBinstance.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            for(int i=0;i<cs.size();i++) {
                Cigarette cigarette = cs.get(i);
                ContentValues values = new ContentValues();
                Log.d(TAG,cigarette.getName()+"更改了");
                values.put(CIGARETTE_PARAMS[1],cigarette.getNum());
                values.put(CIGARETTE_PARAMS[2],cigarette.getName());
                values.put(CIGARETTE_PARAMS[3],Double.valueOf(cigarette.getPrice()).toString());
                values.put(CIGARETTE_PARAMS[4],cigarette.getBarcode());
                values.put(CIGARETTE_PARAMS[5],cigarette.getLasercode());
                values.put(CIGARETTE_PARAMS[6],cigarette.getLasercodeImgUrl());
                values.put(CIGARETTE_PARAMS[7],cigarette.getPic1());
                values.put(CIGARETTE_PARAMS[8],cigarette.getPic2());
                values.put(CIGARETTE_PARAMS[9], Long.valueOf(DataUtil.getCurrentTimeStamp()).toString());
                String []s={cigarette.getNum()};
                db.update(
                        DBOpenHelper.CIGARETTE_TABLE_NAME,values,"num=?",s);
            }
            db.setTransactionSuccessful();
            result = true;
        }catch (Exception e){
        }finally {
            //结束事务
            db.endTransaction();
            MyApplication.lock.writeLock().unlock();
        }
        return result;
    }

    /**
     * 根据num获取卷烟列表
     * @param num
     * @return
     */
    public List<Cigarette> queryCigaretteByNum(String num){
        MyApplication.lock.readLock().lock();
        SQLiteDatabase db=DBinstance.getInstance().getReadableDatabase();
        db.beginTransaction();
        Cursor cursor=null;
        ArrayList<Cigarette> cs=new ArrayList<>();
        try {
            String s[]={ num };
            cursor=db.query(DBOpenHelper.CIGARETTE_TABLE_NAME,
                    CIGARETTE_PARAMS,"num=?",s,null,null,null);
            int resultCounts=cursor.getCount();
            if(resultCounts==0||!cursor.moveToFirst()){
                cs=null;
            }else {
                Log.d(TAG, "cigarette表查询到的数量"+Integer.valueOf(resultCounts).toString());
                do {
                    Cigarette cigarette=new Cigarette();
                    cigarette.setNum(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[1])));
                    cigarette.setName(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[2])));
                    cigarette.setPrice(Double.valueOf(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[3]))));
                    cigarette.setBarcode(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[4])));
                    cigarette.setLasercode(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[5])));
                    cigarette.setLasercodeImgUrl(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[6])));
                    cigarette.setPic1(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[7])));
                    cigarette.setPic2(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[8])));
                    cigarette.setTimeStamp(Long.valueOf(cursor.getString(cursor.getColumnIndex(CIGARETTE_PARAMS[9]))));
                    cs.add(cigarette);
                } while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
        }finally {
            if(null!=cursor){
                cursor.close();
            }
            db.endTransaction();
            MyApplication.lock.readLock().unlock();
        }
        return cs;
    }

    /**
     * 根据num删除cigarette数据
     * @param num
     * @return
     */
    public boolean deleteByNum(String num){
        if (num ==null){
            return false;
        }
        boolean result=false;
        MyApplication.lock.writeLock().lock();
        SQLiteDatabase db=DBinstance.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            String[]s={num};
            db.delete(
                    DBOpenHelper.CIGARETTE_TABLE_NAME,"num=?",s);
            db.setTransactionSuccessful();
            result = true ;
        }catch (Exception e){
        }finally {
            //结束事务
            db.endTransaction();
            MyApplication.lock.writeLock().unlock();
        }
        return result;
    }
    public static String [] CIGARETTE_PARAMS = {"_id","num","name","price",
            "barcode","laserCode","laserCodeImgUrl","pic1Url","pic2Url","writeTime"};
}
