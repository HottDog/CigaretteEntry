package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wgy.cigaretteentry.application.MyApplication;
import com.wgy.cigaretteentry.data.BasePublisher;
import com.wgy.cigaretteentry.data.IObserver;
import com.wgy.cigaretteentry.data.IPublisher;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.http.HttpRequestImplementation;
import com.wgy.cigaretteentry.data.http.HttpUrlConstant;
import com.wgy.cigaretteentry.data.local.PreferenceData;
import com.wgy.cigaretteentry.data.local.db.DAO.CaseDAO;
import com.wgy.cigaretteentry.data.local.db.DAO.CigaretteDAO;
import com.wgy.cigaretteentry.data.publisher.UploadPublisher;
import com.wgy.cigaretteentry.model.BaseDataModel;
import com.wgy.cigaretteentry.util.DataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁江超 on 2017/4/6.
 */

public class ListDataMode implements BaseDataModel<Case> {
    private static final String TAG = "ListDataMode";
    private ArrayList<Case> cases;
    private int casesTotalNum=0;
    private long timeStamp;
    private static ListDataMode instance;
    private CigaretteDAO cigaretteDAO;
    private CaseDAO caseDAO;
    private BasePublisher<ArrayList<Case>> caseListPublisher;
    private BasePublisher<Case> caseDetailPublisher;
    private UploadPublisher uploadPublisher;

    private int [] searchIndexs;   //搜索的时候存储索引
    private ListDataMode(){
        cases=new ArrayList<>();
        timeStamp=0L;
        cigaretteDAO = new CigaretteDAO();
        caseDAO = new CaseDAO();
        caseListPublisher = new BasePublisher();
        caseDetailPublisher = new BasePublisher();
        uploadPublisher = new UploadPublisher();
        //init();
        //caseDAO.deleteAll();
        //cigaretteDAO.deleteAll();
        //testDB1();
        //testDB2();
    }
    public static ListDataMode getInstance(){
        if (instance==null){
            instance=new ListDataMode();
        }
        return instance;
    }
    @Override
    public ArrayList<Case> getInitData() {
        return cases;
    }

    /**
     * 获取内存中所有case的数量
     * @return
     */
    public int getAllCaseNum(){
        return cases.size();
    }

    /**
     * 获取内存中所有待上传的case的数量
     * @return
     */
    public int getUploadCaseNum(){
        int count = 0;
        for(int i =0;i<cases.size();i++){
            if (cases.get(i).isUpload_or_not()){
                count++;
            }
        }
        return count;
    }
    /**
     * 三级缓存获取案件列表数据
     * @return
     */
    @Override
    public ArrayList<Case> getData() {
        if (cases.size()>0){
            //内存中有数据
            Log.d(TAG,"内存中有数据");
            if (DataUtil.isDataOverdue(timeStamp,OVERDUE_TIME)){
                //内存数据过期,http请求数据
                Log.d(TAG,"内存数据过期,http请求数据");
                httpRequestData();
            }
            return cases;
        }else {
            //内存中没有数据
            Log.d(TAG,"内存中没有数据");
            ArrayList<Case> temp = (ArrayList<Case>)caseDAO.queryAllCaseValue();
            if (null!=temp){
                //本地有数据
                Log.d(TAG,"本地有数据，http请求数据");
                //if (DataUtil.isDataOverdue(temp.get(0).getTimeStamp(),OVERDUE_TIME)){
                    //本地数据过期，http请求数据
                httpRequestData();
                //}
                cases =temp;
                return cases;
            }else {
                //本地没有数据,http请求数据
                Log.d(TAG,"本地没有数据");
                httpRequestData();
            }
        }
        return cases;
    }

    @Override
    public void httpRequestData() {
        String url = HttpUrlConstant.GET_CASES;
        String signUrl = "/user/getUsersCaseList";
        String timeStamp = Long.valueOf(DataUtil.getCurrentTimeStamp()).toString();
        String sign = DataUtil.getSign(MyApplication.mContext,signUrl,timeStamp,true);
        JSONObject json = new JSONObject();
        try {
            json.put("sign",sign);
            json.put("request_uid",PreferenceData.getUserID(MyApplication.mContext) );
            json.put("time",Long.valueOf(DataUtil.getCurrentTimeStamp()).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "&request_uid="+PreferenceData.getUserID(MyApplication.mContext)
                + "&time="+timeStamp
                + "&sign=" + sign
                + "&from_platform=" + "app";
        Log.d(TAG,"url:"+url);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,url , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"case http request result:"+response.toString());
                        processData(response);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"case http request error");
                Log.e(TAG, error.getMessage(), error);
            }
        }
        )/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("sign",DataUtil.getSign(context,url,false));
                map.put("request_uid",PreferenceData.getUserID(context));
                map.put("time",Long.valueOf(DataUtil.getCurrentTimeStamp()).toString());
                return super.getParams();
            }
        }*/;
        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 处理请求成功获取到的案件数据
     * @param json
     */
    private void processData(JSONObject json){
        String result = json.optString("success");
        if (result.equals("1")){
            timeStamp = DataUtil.getCurrentTimeStamp();
            JSONArray jsonArray = json.optJSONArray("data");
            JSONObject jsonObject = jsonArray.optJSONObject(0);
            casesTotalNum = jsonObject.optInt("totalNum");
            cases.clear();
            if (casesTotalNum>0){
                JSONArray jsonArray1 = jsonObject.optJSONArray("caseList");
                for(int i=0;i<jsonArray1.length();i++){
                    Case temp  =Case.getCaseFromJson(jsonArray1.optJSONObject(i));
                    if (temp!=null) {
                        Log.d(TAG,"获取到case："+temp.toString());
                        cases.add(temp);
                    }
                }
                updateIsUpload();
            }
            ArrayList<Case> newCases = (ArrayList<Case>) caseDAO.queryAllNewCase();
            if (newCases == null){
                Log.d(TAG, "获取到的本地有服务器没有的case数量：" + 0);
            }else {
                Log.d(TAG, "获取到的本地有服务器没有的case数量：" + newCases.size());
                for(int i=0;i<newCases.size();i++){
                    cases.add(newCases.get(i));
                }
            }
            casesTotalNum= cases.size();
            Log.d(TAG,"当前case的数据个数："+casesTotalNum);
            publisherCaseList();
        }
    }

    /**
     * 更新当前数据的上传状态
     */
    private void updateIsUpload(){
        for (int i=0;i<cases.size();i++){
            Case curCase = cases.get(i);
            curCase.setIs_first(false);
            Case temp = caseDAO.queryCaseByNumber(curCase.getNumber());
            if (temp==null){
                //本地没有该数据
                Log.d(TAG,"本地没有该case数据");
                curCase.setUpload_or_not(false);
                curCase.setIs_first(false);
                caseDAO.insertCase(curCase);
            }else {
                long serverTimeStamp = curCase.getTimeStamp();
                long dbTimeStamp = temp.getTimeStamp();
                Log.d(TAG,"服务器case数据更新的时间戳："+serverTimeStamp);
                Log.d(TAG,"本地case数据更新的时间戳："+dbTimeStamp);
                if (serverTimeStamp>dbTimeStamp){
                    //服务器的数据比本地数据更新
                    Log.d(TAG,"服务器的该case("+curCase.getNumber()+")数据比本地数据更新");
                    curCase.setUpload_or_not(false);
                    caseDAO.updateCase(curCase,serverTimeStamp);
                }else if(serverTimeStamp==dbTimeStamp) {
                    Log.d(TAG,"服务器的该case("+curCase.getNumber()+")数据和本地数据已同步");
                    curCase.setUpload_or_not(false);
                }else {
                    //本地数据比服务器数据更新,本地数据有待上传
                    Log.d(TAG,"本地的该case("+temp.getNumber()+")数据比服务器数据更新");
                    //把temp的值赋给curCase
                    Case.clone(temp,curCase);
                    curCase.setUpload_or_not(true);
                    curCase.setIs_first(false);
                }
            }
        }
    }

    /**
     * 通过索引删除case
     * @param index
     */
    public void deleteCasesByIndex(int index){
        Case tmp = cases.remove(index);
        ArrayList<Case> tmps = new ArrayList<>();
        tmps.add(tmp);
        Log.d(TAG,"开始执行删除index="+Integer.valueOf(index).toString()+"的case的操作");
        httpRequestDeleteCase(tmps);
        caseDAO.delete(tmps);
        cigaretteDAO.deleteByNum(tmp.getNumber());
    }

    /**
     * http请求删除case
     * @param cases
     */
    private void httpRequestDeleteCase(List<Case> cases){
        String url = HttpUrlConstant.DELETE_CASES;
        String signUrl = "/user/delCases";
        String timeStamp = Long.valueOf(DataUtil.getCurrentTimeStamp()).toString();
        String sign = DataUtil.getSign(MyApplication.mContext,signUrl,timeStamp,true);
        JSONObject json = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<cases.size();i++){
                jsonArray.put(cases.get(i).getNumber());
            }
            json.put("caseList",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        url = url + "?request_uid="+PreferenceData.getUserID(MyApplication.mContext)
                + "&time="+timeStamp
                + "&sign=" + sign
                + "&from_platform=" + "app";
        Log.d(TAG,"url:"+url);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,url ,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"case http request result:"+response.toString());
                        String result = response.optString("success");
                        if (result.equals("1")){
                            Log.d(TAG,"服务器成功删除case数据");
                            publisherCaseList();
                        }else {
                            Log.d(TAG,"服务器删除case数据失败");
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"case http request error");
                Log.e(TAG, error.getMessage(), error);
            }
        }
        );
        MyApplication.getRequestQueue().add(request);
    }

    /**
     * 获取该case的详细信息
     * @param index
     */
    public Case getCaseDetailByIndex(int index){
        Log.d(TAG,"获取单个case的详细信息");
        Case tmp = cases.get(index);
        if (tmp.getCigarettes().size()>0){
            //内存有数据
            Log.d(TAG,"内存中有数据");
            if (DataUtil.isDataOverdue(tmp.getQueryTimeStamp(),OVERDUE_TIME)){
                //内存数据过期,http请求数据
                Log.d(TAG,"内存数据过期,http请求数据");
                httpRequestGetCaseDetailByNum(tmp);
            }
            return tmp;
        }else {
            //内存中没有数据
            Log.d(TAG,"内存中没有数据");
            Case temp = caseDAO.queryCaseByNumber(tmp.getNumber());
            if (temp!=null) {
                //从本地读取卷烟信息
                ArrayList<Cigarette> cigarettes = (ArrayList<Cigarette>) cigaretteDAO.queryAllNewCigarette(temp.getNumber());
                if (cigarettes != null) {
                    for (int i = 0; i < cigarettes.size(); i++) {
                        temp.addCigarette(cigarettes.get(i));
                    }
                }
            }
            if (temp!=null&&temp.getCigarettes()!=null&&temp.getCigarettes().size()>0){
                //本地有数据
                Log.d(TAG,"本地有数据，http请求数据");
                //if (DataUtil.isDataOverdue(temp.get(0).getTimeStamp(),OVERDUE_TIME)){
                //本地数据过期，http请求数据
                httpRequestGetCaseDetailByNum(temp);
                //}
                Case.clone(temp,tmp);
                return tmp;
            }else {
                //本地没有数据,http请求数据
                Log.d(TAG,"本地没有数据");
                httpRequestGetCaseDetailByNum(tmp);
                return tmp;
            }
        }
    }

    private void httpRequestGetCaseDetailByNum(final Case c){
        HttpRequestImplementation.getCaseDetailByNum(c.getNumber(), new HttpRequestImplementation.HttpGetDataListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.d(TAG,"caseDetail获取成功："+json.toString());
                c.setQueryTimeStamp(DataUtil.getCurrentTimeStamp());
                String result = json.optString("success");
                if(result.equals("1")){
                    JSONArray jsonArray1 = json.optJSONArray("data");
                    if (jsonArray1.length()!=0) {
                        JSONArray jsonArray = jsonArray1.optJSONArray(0);
                        Log.d(TAG, "http获取到的卷烟数量：" + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.optJSONObject(i);
                            Cigarette cigarette = Cigarette.getCigaretteFromJson(jsonObject);
                            c.addCigaretteWithoutNum(cigarette);
                        }
                    }
                    publisherCaseDetail(c);
                }
            }

            @Override
            public void onFail() {

            }
        });
    }

    /**
     * 添加新的案件
     * @param c
     * @return 该添加的案件在案件列表中的索引值
     */
    public int addCase(Case c){
        cases.add(c);
        //发布数据更新广播
        publisherCaseList();
        //httpAddCase(c);
        ArrayList<Case> cs = new ArrayList<>();
        cs.add(c);
        caseDAO.insertCaseValue(cs);    //插入本地数据库
        return cases.size()-1;
    }

    /**
     * 添加完卷烟信息后更新cases数据
     * @param c
     * @param cigarette
     */
    public void updateCase(Case c,Cigarette cigarette){
        c.setUpload_or_not(true);
        cigarette.setUpload_or_not(true);
        publisherCaseList();
        caseDAO.updateCase(c,0);
        cigaretteDAO.insertCigarette(cigarette);
    }
    /**
     * 通过索引获取case
     * @param index
     * @return
     */
    public Case getCaseByIndex(int index){

        return cases.get(index);
    }

    public ArrayList<Case> searchByNum(String num){
        ArrayList<Case> tmp = new ArrayList<>();
        searchIndexs=new int[cases.size()];
        int j=0;
        for(int i=0;i<cases.size();i++){
            Case c = cases.get(i);
            if(DataUtil.isMatch(num,c.getNumber())){
                tmp.add(c);
                searchIndexs[j]=i;
                j++;
            }
        }
        return tmp;
    }

    /**
     * 上传案件到服务器
     * @param index
     */
    public void uploadCases(int index){
        Case c = getCaseByIndex(index);
        ArrayList<Cigarette> cigarettes = (ArrayList<Cigarette>) cigaretteDAO.queryAllNewCigarette(c.getNumber());
        if (cigarettes!=null){
            for(int i=0;i<cigarettes.size();i++){
                c.addCigarette(cigarettes.get(i));
            }
        }
        Log.d(TAG,"上传案件:"+c.toString());
        if (c.is_first()){
            Log.d(TAG,"添加新的案件");
            httpAddCase(c);
        }else {
            Log.d(TAG,"更新新的案件");
            httpUpdateCase(c);
        }
    }

    /**
     * http请求添加案件数据
     * @param c
     */
    private void httpAddCase(final Case c){
        HttpRequestImplementation.insertCase(c, new HttpRequestImplementation.HttpGetDataListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.d(TAG,"http添加数据请求成功");
                Log.d(TAG,json.toString());
                String result = json.optString("success");
                if (result.equals("1")){
                    Log.d(TAG,"数据成功保存到服务器");
                    c.setIs_first(false);
                    c.setUpload_or_not(false);
                    publisherCaseList();
                    publisherUploadMessage(true);
                    caseDAO.updateCase(c,0);
                    cigaretteDAO.deleteByNum(c.getNumber());
                }else {
                    Log.d(TAG,"数据保存到服务器失败");
                    publisherUploadMessage(false);
                }
            }

            @Override
            public void onFail() {
                Log.d(TAG,"http添加数据失败");
                //httpAddCase(c);
                publisherUploadMessage(false);
            }
        });
    }

    /**
     * http更新案件（案件已有）
     * @param c
     */
    private void httpUpdateCase(final Case c){
        String signUrl = "/user/editCase";
        JSONObject json = new JSONObject();
        try {
            json.put("date",c.getDate());
            json.put("year",c.getYear());
            json.put("num",c.getNumber());
            json.put("department_id",c.getDepartmentID());
            json.put("user_id",PreferenceData.getUserID(MyApplication.mContext));
            json.put("total_num",c.getTotalNum());
            JSONArray jsonArray = new JSONArray();
            ArrayList<Cigarette> cigarettes=c.getCigarettes();
            for(int i=0;i<cigarettes.size();i++){
                Cigarette cigarette = cigarettes.get(i);
                if(cigarette.isUpload_or_not()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", cigarette.getName());
                    jsonObject.put("price", cigarette.getPrice());
                    jsonObject.put("barcode", cigarette.getBarcode());
                    Log.d(TAG, "更新的卷烟的barcode：" + cigarette.getBarcode());
                    jsonObject.put("image1", cigarette.getPic1());
                    jsonObject.put("image2", cigarette.getPic2());
                    jsonObject.put("laserCodeNum", cigarette.getLasercode());
                    jsonObject.put("laserCodeImg", cigarette.getLasercodeImgUrl());
                    jsonArray.put(jsonObject);
                }
            }
            json.put("cigaretteList",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequestImplementation.httpGetData(HttpUrlConstant.UPDATE_CASE, signUrl, json, new HttpRequestImplementation.HttpGetDataListener() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.d(TAG,"http更新案件信息成功");
                Log.d(TAG,json.toString());
                String result = json.optString("success");
                if (result.equals("1")){
                    Log.d(TAG,"数据成功保存到服务器");
                    c.setUpload_or_not(false);
                    c.setIs_first(false);
                    if(c.getCigarettes()!=null){
                        for (int i=0;i<c.getCigarettes().size();i++){
                            c.getCigarettes().get(i).setUpload_or_not(false);
                        }
                    }
                    publisherCaseList();
                    publisherUploadMessage(true);
                    caseDAO.updateCase(c,0);
                    cigaretteDAO.deleteByNum(c.getNumber());
                }else {
                    Log.d(TAG,"数据保存到服务器失败");
                    publisherUploadMessage(false);
                }
            }

            @Override
            public void onFail() {
                Log.d(TAG,"http更新案件信息失败");
                publisherUploadMessage(false);
            }
        });
    }
    public void registerCaseListPublisher(IObserver o){
        caseListPublisher.register(o);
    }
    public void unRisterCaseListPublisher(IObserver o){
        caseListPublisher.unRegister(o);
    }
    public void registerCaseDetailPublisher(IObserver o){
        caseDetailPublisher.register(o);
    }
    public void unRegisterCaseDetailPublisher(IObserver o){
        caseDetailPublisher.unRegister(o);
    }
    public void registerUploadPublisher(IObserver o){
        uploadPublisher.register(o);
    }
    public void unRegisterUploadPublisher(IObserver o){
        uploadPublisher.unRegister(o);
    }
    private void publisherCaseList(){
        Log.d(TAG,"发布caseList数据更新的消息");
        caseListPublisher.publish(cases);
    }
    private void publisherCaseDetail(Case c){
        Log.d(TAG,"发布case的详细数据更新的消息");
        caseDetailPublisher.publish(c);
    }
    private void publisherUploadMessage(boolean isSuccess){
        Log.d(TAG,"发布上传数据是否成功的消息");
        uploadPublisher.publish(isSuccess);
    }
    private void init(){
        for(int i=0;i<8;i++){
            Case c=new Case();
            c.setDate("2016年10月3日");
            c.setUserID("1546512345");
            c.setDepartmentID("452145661324");
            c.setNumber("154212457542"+Integer.valueOf(i).toString());
            c.setUpload_or_not(DataUtil.getRandomBool());
            c.setTotalNum(DataUtil.getIntRandom(100,200));
            cases.add(c);
        }
    }
    //测试DB
    private void testDB1(){
        CaseDAO caseDAO = new CaseDAO();
        caseDAO.insertCaseValue(cases);

        cases.get(2).setTotalNum(300);
        cases.get(3).setTotalNum(33333);
        caseDAO.updateCaseValue(cases,0);

        List<Case> temp = caseDAO.queryAllCaseValue();
        for(int i = 0;i<temp.size();i++){
           Log.d(TAG,temp.get(i).toString());
        }
        caseDAO.deleteAll();
    }
    private void testDB2(){
        ArrayList<Cigarette> cs = new ArrayList<>();
        String num ="2434345353";
        for(int i=0;i<8;i++){
            Cigarette c=new Cigarette();
            c.setNum(num);
            c.setName("中华牌香烟");
            c.setPrice(23);
            c.setUpload_or_not(false);
            c.setBarcode("23414234235");
            c.setLasercode("2433534643523");
            cs.add(c);
        }
        CigaretteDAO cigaretteDAO=new CigaretteDAO();
        cigaretteDAO.insertCigaretteValue(cs);
        cs = (ArrayList<Cigarette>)cigaretteDAO.queryCigaretteByNum(num);
        cs.get(1).setName("长白山香烟");
        cs.get(4).setName("长白山香烟");

        cigaretteDAO.updateCigaretteValue(cs);

        ArrayList<Cigarette> temp = (ArrayList<Cigarette>) cigaretteDAO.queryCigaretteByNum(num);
        if (temp!=null) {
            for (int i = 0; i < temp.size(); i++) {
                Log.d(TAG, temp.get(i).toString());
            }
        }else {
            Log.d(TAG,"cigrette  temp 是空的");
        }
        cigaretteDAO.deleteAll();
    }


    //数据过期的时间，单位是秒
    private static final long OVERDUE_TIME = 4*60*60;   //4个小时
}
