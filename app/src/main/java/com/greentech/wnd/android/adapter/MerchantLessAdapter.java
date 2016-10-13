package com.greentech.wnd.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greentech.wnd.android.MyApplication;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.Merchant;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhoufazhan on 2016/9/19.
 * 供求查询内容显示adapter
 */
public class MerchantLessAdapter extends BaseAdapter {
    List<Merchant> sdsResult;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    public MerchantLessAdapter(List<Merchant> sdsResult) {
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
        TextView name;
        TextView people;
        TextView tel;
        TextView address;
        if (convertView == null) {
            convertView = LayoutInflater.from(MyApplication.getContextObject())
                    .inflate(R.layout.merchant_less_item, null);
        }
        people = (TextView) convertView.findViewById(R.id.second);
        tel = (TextView) convertView.findViewById(R.id.third);
        address = (TextView) convertView.findViewById(R.id.four);

        people.setText(sdsResult.get(position).getContactor()==null?"":sdsResult.get(position).getContactor());
        tel.setText(sdsResult.get(position).getTel()==null?"":sdsResult.get(position).getTel());
        address.setText(sdsResult.get(position).getAddress()==null?"":sdsResult.get(position).getProvince());
        return convertView;
    }
}
