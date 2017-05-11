package com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.CigarettesNum;
import com.wgy.cigaretteentry.model.codeCopyModel.codeCopy.listfragment.CaseListAdapter;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/5/7.
 */

public class DetailCaseInfoNumAdapter extends BaseAdapter{
    private static final String TAG = "DetailCaseInfoNumAdapte";
    private LayoutInflater mInflate;
    private Context mContext;
    private ArrayList<CigarettesNum> cigarettesNa;
    public DetailCaseInfoNumAdapter (Context context){
        mContext=context;
        this.mInflate=LayoutInflater.from(context);
        cigarettesNa=new ArrayList<>();
    }
    public void setData(ArrayList<CigarettesNum> c){
        if (c==null) return;
        this.cigarettesNa=c;
    }
    @Override
    public int getCount() {
        return cigarettesNa.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DetailCaseInfoNumAdapter.ViewHolder holder;
        if(convertView==null)
        {
            convertView=mInflate.inflate(R.layout.cigarette_num_item, null);
            holder=new DetailCaseInfoNumAdapter.ViewHolder();
            holder.left = (TextView)convertView.findViewById(R.id.left);
            holder.right = (TextView) convertView.findViewById(R.id.right);
            convertView.setTag(holder);
        } else {
            holder=(DetailCaseInfoNumAdapter.ViewHolder)convertView.getTag();
        }
        if (cigarettesNa.get(position).getLeftnum()!=0){
            holder.left.setText(cigarettesNa.get(position).getLeftname()
                    +TEXT+Integer.valueOf(cigarettesNa.get(position).getLeftnum()).toString());
        }
        if(cigarettesNa.get(position).getRightnum()!=0){
            holder.right.setText(cigarettesNa.get(position).getRightname()
                    +TEXT+Integer.valueOf(cigarettesNa.get(position).getRightnum()).toString());
        }
        return convertView;
    }
    private final class ViewHolder {
        public TextView left;
        public TextView right;

    }
    private static final String TEXT="卷烟数量：";
}
