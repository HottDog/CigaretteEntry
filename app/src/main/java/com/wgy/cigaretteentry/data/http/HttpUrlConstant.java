package com.wgy.cigaretteentry.data.http;
/**
 * Created by 袁江超 on 2017/4/20.
 */

public class HttpUrlConstant {
    public static final String URL = "http://172.104.70.86:8080/bacco";
    public static final String LOGIN = URL+"/user/logincheck?from_platform=app";
    //需要拼接
    public static final String GET_CASES = URL + "/user/getUsersCaseList?pagenum=1&pagesize=1000";
    public static final String DELETE_CASES = URL+"/user/delCases";
    public static final String INSERT_CASE = URL + "/user/upLoadCases";
    public static final String UPDATE_CASE = URL+"/user/editCase";
    public static final String GET_CASE_DETAIL_BY_NUM = URL+"/user/getTobaccoListbyCaseNum";
}
