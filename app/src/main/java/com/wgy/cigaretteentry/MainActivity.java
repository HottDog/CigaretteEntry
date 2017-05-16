package com.wgy.cigaretteentry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wgy.cigaretteentry.data.http.HttpUrlConstant;
import com.wgy.cigaretteentry.data.local.PreferenceData;
import com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo.DetailCaseInfoContract;
import com.wgy.cigaretteentry.util.DataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Button login_bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        login_bn=(Button)findViewById(R.id.login_bn);
        login_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHttpRequest();
            }
        });
    }
    private void loginHttpRequest(){
        JSONObject json = new JSONObject();
        try {
            json.put("username","name2");
            json.put("password","des" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST,HttpUrlConstant.LOGIN ,json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"case http request result:"+response.toString());
                        processData(response);
                        Intent intent=new Intent(MainActivity.this, WorkbenchActivity.class);
                        startActivity(intent);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }
        );/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("username","name1" );
                map.put("password","des" );
                return map;
            }
        };*/
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void processData(JSONObject json){
        String result = json.optString("success");
        if (result.equals("1")){
            JSONArray jsonArray = json.optJSONArray("data");
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(0);
                PreferenceData.setUserID(this,jsonObject.optString("uid"));
                PreferenceData.setToken(this,jsonObject.optString("app_token"));
                Log.d(TAG,"UID:"+PreferenceData.getUserID(this));
                Log.d(TAG,"token:"+PreferenceData.getToken(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
