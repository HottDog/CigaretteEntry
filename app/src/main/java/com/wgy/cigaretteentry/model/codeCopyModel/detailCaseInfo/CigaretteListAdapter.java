package com.wgy.cigaretteentry.model.codeCopyModel.detailCaseInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wgy.cigaretteentry.R;
import com.wgy.cigaretteentry.data.bean.Cigarette;
import com.wgy.cigaretteentry.data.http.HttpRequestImplementation;

import java.util.ArrayList;

/**
 * Created by 袁江超 on 2017/5/7.
 */

public class CigaretteListAdapter extends BaseAdapter{
    private static final String TAG = "CigaretteListAdapter";
    private ArrayList<Cigarette> cigarettes;
    private Context mContext;
    private LayoutInflater mInflate;
    public CigaretteListAdapter(Context context){
        mContext=context;
        this.mInflate=LayoutInflater.from(context);
        cigarettes=new ArrayList<>();
    }
    public void setData(ArrayList<Cigarette> cigarettes){
        if(cigarettes!=null){
            this.cigarettes=cigarettes;
        }
    }
    @Override
    public int getCount() {
        return cigarettes.size();
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
        final CigaretteListAdapter.ViewHolder holder;
        if(convertView==null)
        {
            convertView=mInflate.inflate(R.layout.cigarette_list, null);
            holder=new CigaretteListAdapter.ViewHolder();
            holder.priceTx = (TextView)convertView.findViewById(R.id.price_tx);
            holder.nameTx = (TextView) convertView.findViewById(R.id.name_tx);
            holder.laserCodeTx = (TextView) convertView.findViewById(R.id.laser_code_tx);
            holder.indexTx = (TextView) convertView.findViewById(R.id.index_tx);
            convertView.setTag(holder);
        } else {
            holder=(CigaretteListAdapter.ViewHolder)convertView.getTag();
        }
        holder.indexTx.setText(Integer.valueOf(position).toString());
        holder.nameTx.setText(cigarettes.get(position).getName());
        holder.priceTx.setText(PRICE_TX+Double.valueOf(cigarettes.get(position).getPrice()).toString());
        holder.laserCodeTx.setText(LASER_CODE_TX+cigarettes.get(position).getLasercode());
        return convertView;
    }
    private final class ViewHolder {
        public TextView priceTx;
        public TextView nameTx;
        public TextView indexTx;
        public TextView laserCodeTx;
    }
    private static final String POINT=".";
    private static final String PRICE_TX="价格：¥";
    private static final String LASER_CODE_TX="激光码：";
}
