package com.greentech.wnd.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.greentech.wnd.android.MyApplication;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.SupplyDemand;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhoufazhan on 2016/9/19.
 * 供求查询内容显示adapter
 */
public class GQAdapter extends BaseAdapter {
    List<SupplyDemand> sdsResult;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    public GQAdapter(List<SupplyDemand> sdsResult) {
        this.sdsResult = sdsResult;
    }

    @Override
    public int getCount() {
        return sdsResult.size();
    }

    @Override
    public Object getItem(int position) {
        return sdsResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;
        TextView time;
        if (convertView == null) {
            convertView = LayoutInflater.from(MyApplication.getContextObject())
                    .inflate(R.layout.list_item, null);
        }
        title = (TextView) convertView.findViewById(R.id.item_title);
        time = (TextView) convertView.findViewById(R.id.item_date);
        title.setText(sdsResult.get(position).getName());
        if (sdsResult.get(position).getReleaseTime() != null) {

            time.setText(sf.format(sdsResult.get(position).getReleaseTime()));
        }
        return convertView;
    }
}
