package com.wgy.cigaretteentry.data.local.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.local.db.DBOpenHelper;
import com.wgy.cigaretteentry.data.local.db.DBinstance;
import com.wgy.cigaretteentry.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁江超 on 2017/4/24.
 */

public class CaseDAO {
    private static final String TAG = "CaseDAO";
    public CaseDAO(){}

    /**
     * 插入新的数据
     * @param cases
     * @return true为插入成功，false为插入失败
     */
    public boolean insertCaseValue(List<Case> cases){
        if (null == cases){
            return false;
        }
        boolean result =false;   //插入操作的结果
        MyApplication.lock.writeLock().lock();
        SQLiteDatabase db= DBinstance.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            for(int i=0;i<cases.size();i++) {
                Case mCase=cases.get(i);
                ContentValues values = new ContentValues();
                values.put(CASE_PARAMS[1],mCase.getDate());
                values.put(CASE_PARAMS[2],mCase.getNumber());
                values.put(CASE_PARAMS[3],mCase.getDepartmentID());
                values.put(CASE_PARAMS[4],mCase.getUserID());
                values.put(CASE_PARAMS[5],mCase.getTotalNum());
                values.put(CASE_PARAMS[6],mCase.getUpload_or_not());
                values.put(CASE_PARAMS[7], Long.valueOf(DataUtil.getCurrentTimeStamp()).toString());

                db.insert(
                        DBOpenHelper.CASE_TABLE_NAME,CASE_PARAMS[6],values);
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
     * 更新数据
     * @param cases
     * @return true为更新成功，false为更新失败
     */
    public boolean updateCaseValue(List<Case> cases){
        if(null == cases){
            return false;
        }
        boolean result = false;
        MyApplication.lock.writeLock().lock();
        SQLiteDatabase db=DBinstance.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            for(int i=0;i<cases.size();i++) {
                Case mCase = cases.get(i);
                ContentValues values = new ContentValues();
                Log.d(TAG,mCase.getNumber()+"更改了");
                values.put(CASE_PARAMS[1],mCase.getDate());
                values.put(CASE_PARAMS[3],mCase.getDepartmentID());
                values.put(CASE_PARAMS[4],mCase.getUserID());
                values.put(CASE_PARAMS[5],mCase.getTotalNum());
                values.put(CASE_PARAMS[6],mCase.getUpload_or_not());
                values.put(CASE_PARAMS[7], Long.valueOf(DataUtil.getCurrentTimeStamp()).toString());
                String []s={mCase.getNumber()};
                db.update(
                        DBOpenHelper.CASE_TABLE_NAME,values,"num=?",s);
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
     * 获取数据库所有的case数据
     * @return
     */
    public List<Case> queryAllCaseValue(){
        MyApplication.lock.readLock().lock();
        SQLiteDatabase db=DBinstance.getInstance().getReadableDatabase();
        db.beginTransaction();
        Cursor cursor=null;
        ArrayList<Case> cases=new ArrayList<>();
        try {
            cursor=db.query(DBOpenHelper.CASE_TABLE_NAME,
                    CASE_PARAMS,null,null,null,null,null);
            int resultCounts=cursor.getCount();
            if(resultCounts==0||!cursor.moveToFirst()){
                cases=null;
            }else {
                Log.d(TAG, "case表查询到的数量"+Integer.valueOf(resultCounts).toString());
                do {
                    Case mCase=new Case();
                    mCase.setDate(cursor.getString(cursor.getColumnIndex(CASE_PARAMS[1])));
                    mCase.setNumber(cursor.getString(cursor.getColumnIndex(CASE_PARAMS[2])));
                    mCase.setDepartmentID(cursor.getString(cursor.getColumnIndex(CASE_PARAMS[3])));
                    mCase.setUserID(cursor.getString(cursor.getColumnIndex(CASE_PARAMS[4])));
                    mCase.setTotalNum(cursor.getInt(cursor.getColumnIndex(CASE_PARAMS[5])));
                    mCase.setUpload_or_not(cursor.getString(cursor.getColumnIndex(CASE_PARAMS[6])));
                    mCase.setTimeStamp(Long.getLong(cursor.getString(cursor.getColumnIndex(CASE_PARAMS[7]))));
                    cases.add(mCase);
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
        return cases;
    }

    /**
     * 删除case
     * @param cases
     * @return
     */
    public boolean delete(List<Case> cases){
        if (cases ==null){
            return false;
        }
        boolean result=false;
        MyApplication.lock.writeLock().lock();
        SQLiteDatabase db=DBinstance.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            for(int i=0;i<cases.size();i++) {
                Case mCase=cases.get(i);
                String[]s={mCase.getNumber()};
                db.delete(
                        DBOpenHelper.CASE_TABLE_NAME,"num=?",s);
            }
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
    public static String [] CASE_PARAMS = {"_id","date","num","department_id","user_id",
            "total_num","upload_or_not","writeTime"};
}
