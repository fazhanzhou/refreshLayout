package com.greentech.wnd.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.Market;

import java.util.List;

/**
 * Created by zhoufazhan on 2015/11/25.
 */
public class MarketStringAdapter extends BaseAdapter {
    private List<Market> list;
    private Context context;

    public MarketStringAdapter(Context context, List<Market> list) {
        this.list = list;
        this.context=context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_discovery,null);
        }
        textView=(TextView)convertView.findViewById(R.id.text_discovery);
        textView.setText(list.get(position).getTitle());
        return convertView;
    }
}
