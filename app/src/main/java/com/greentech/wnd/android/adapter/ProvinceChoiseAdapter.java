package com.greentech.wnd.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.Province;

import java.util.List;

/**
 * Created by zhoufazhan on 2016/4/20.
 */
public class ProvinceChoiseAdapter extends BaseAdapter {
    List<Province> areaList;
    Context mContext;

    public ProvinceChoiseAdapter(List<Province> areaList, Context mContext) {
        this.areaList = areaList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return areaList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.focus_product_name_item, null);
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(areaList.get(position).getName());
        return convertView;
    }
}
