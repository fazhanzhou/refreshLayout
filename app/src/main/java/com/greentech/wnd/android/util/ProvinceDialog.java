package com.greentech.wnd.android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.adapter.ProvinceChoiseAdapter;
import com.greentech.wnd.android.bean.Province;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhoufazhan on 2016/5/30.
 */
public class ProvinceDialog extends AlertDialog {
    ListView listView;
    private List<Province> areaList = new ArrayList<Province>();//地区数据源
    private ProvinceChoiseAdapter areaAdapter;
    private Context mContext;
    public String province;
    public ProvinceChange provinceChange;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public ProvinceDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ProvinceDialog(Context context, int themeResId) {

        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_province_choice);
        listView = (ListView) findViewById(R.id.provinceList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                province = areaList.get(position).getName();
                setProvince(province);
                provinceChange.setProvince(province);
                ProvinceDialog.this.dismiss();
            }
        });
        loadAreaData();
    }

    public void loadAreaData() {
        Request request = new Request.Builder().url("http://njy.agri114.cn/hqt/json/findAllProvince.action").build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    final JsonObject json = (JsonObject) GsonUtil.parse(result);

                    areaList = GsonUtil.fromJson(json.get("provinces"), new TypeToken<List<Province>>() {
                    }.getType());
                    removeItem(areaList);
                    areaAdapter = new ProvinceChoiseAdapter(areaList, mContext);
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(areaAdapter);
                        }
                    });


                }
            }
        });
    }

    //删除没有数据的省份
    public void removeItem(List<Province> list) {

        Iterator<Province> provinceIterator = list.iterator();
        while (provinceIterator.hasNext()) {
            Province province = provinceIterator.next();
            if (province.getName().equals("香港特别行政区")) {
                provinceIterator.remove();
            }
            if (province.getName().equals("澳门特别行政区")) {
                provinceIterator.remove();
            }
            if (province.getName().equals("台湾省")) {
                provinceIterator.remove();
            }
            if (province.getName().equals("全国")) {
                provinceIterator.remove();
            }
        }
    }

  public   interface ProvinceChange {
        void setProvince(String province);
    }

    public void setProvinceChangeListener(ProvinceChange item) {
        this.provinceChange = item;
    }

}
