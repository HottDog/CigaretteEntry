package com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.uploadfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.Case;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.CaseListAdapter;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/4/8.
 */

public class UploadCaseListAdapter extends BaseAdapter{
    private LayoutInflater mInflate;
    private ArrayList<Case> cases;
    private Context mContext;
    private UploadFragmentContract.Presenter presenter;
    public UploadCaseListAdapter(Context context,UploadFragmentContract.Presenter presenter){
        mContext=context;
        this.presenter=presenter;
        this.mInflate=LayoutInflater.from(context);
        this.cases=new ArrayList<>();
    }
    public UploadCaseListAdapter(Context context, ArrayList<Case> cases){
        mContext=context;
        this.mInflate=LayoutInflater.from(context);
        if (null!=null) {
            this.cases = cases;
        }else {
            this.cases=new ArrayList<>();
        }
    }
    @Override
    public int getCount() {
        return cases.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final UploadCaseListAdapter.ViewHolder holder;
        if(convertView==null)
        {
            convertView=mInflate.inflate(R.layout.case_list, null);
            holder=new UploadCaseListAdapter.ViewHolder();
            holder.serial_num = (TextView)convertView.findViewById(R.id.serial_num);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.departmentID =(TextView)convertView.findViewById(R.id.department_id);
            holder.userID = (TextView)convertView.findViewById(R.id.user_id);
            holder.totalNum = (TextView)convertView.findViewById(R.id.total_num);
            holder.enter = (Button)convertView.findViewById(R.id.enter);
            holder.number=(TextView)convertView.findViewById(R.id.number) ;
            holder.uploadOrNot=(ImageView)convertView.findViewById(R.id.upload_or_not);
            convertView.setTag(holder);
        } else {
            holder=(UploadCaseListAdapter.ViewHolder)convertView.getTag();
        }
        holder.serial_num.setText(Integer.valueOf(position).toString()+INDEX_POINT_TX);
        holder.date.setText(DATE_TX+cases.get(position).getDate());
        holder.number.setText(NUMBER_TX+cases.get(position).getNumber());
        holder.departmentID.setText(DEPARTMENTID_TX+cases.get(position).getDepartmentID());
        holder.userID.setText(USERID_TX+cases.get(position).getUserID());
        holder.totalNum.setText(TOTALNUM_TX+Integer.valueOf(cases.get(position).getTotalNum()).toString());
        if (cases.get(position).isUpload_or_not()){
            holder.uploadOrNot.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_unupload));
        }else {
            holder.uploadOrNot.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_upload));
        }
        holder.enter.setText(UPLOAD_ENTER_BN_TX);
        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }
    public void setCases(ArrayList<Case> cases){
        this.cases=cases;
    }
    private final class ViewHolder {
        public TextView serial_num;
        public TextView date;
        public TextView number;
        public TextView departmentID;
        public TextView userID;
        public TextView totalNum;
        public ImageView uploadOrNot;
        public Button enter;
    }
    public final static String INDEX_POINT_TX=".";
    public final static String DATE_TX="立案时间：";
    public final static String NUMBER_TX = "案件编号：";
    public final static String DEPARTMENTID_TX="所在部门ID：";
    public final static String USERID_TX="用户ID：";
    public final static String TOTALNUM_TX="总数：";
    public final static String CASENUM_TX="案件数量：";
    public final static String UPLOAD_ENTER_BN_TX="点击上传";
}
