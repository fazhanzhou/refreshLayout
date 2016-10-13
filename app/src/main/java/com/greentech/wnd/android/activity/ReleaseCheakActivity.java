package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.adapter.GQAdapter;
import com.greentech.wnd.android.bean.SupplyDemand;
import com.greentech.wnd.android.util.CommonUtil;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.LocationUtil;
import com.greentech.wnd.android.util.OkHttpUtil;
import com.greentech.wnd.android.util.ProvinceDialog;
import com.zhoufazhan.lib.refreshlayout.RefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


public class ReleaseCheakActivity extends Activity implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, ProvinceDialog.ProvinceChange {
    @Bind(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.edit)
    EditText editText;
    @Bind(R.id.submit)
    TextView submit;
    @Bind(R.id.address)
    Button address;
    int mPageNum = 1;
    int mPageCount = 0;
    Request request;
    String mProvince = "江苏省";
    String type = "";
    GQAdapter adapter;
    List<SupplyDemand> sdsResult = new ArrayList<SupplyDemand>();
    ProvinceDialog provinceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_cheak);
        ButterKnife.bind(this);
        address.setText(LocationUtil.getProvince(this));
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        provinceDialog = new ProvinceDialog(ReleaseCheakActivity.this);
        provinceDialog.setProvinceChangeListener(this);
        adapter = new GQAdapter(sdsResult);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int sdId = sdsResult.get(position).getId();
                Intent intent = new Intent();
                intent.putExtra("sdId", sdId);
                intent.setClass(ReleaseCheakActivity.this,
                        SDDetailActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = editText.getText().toString();
                onRefresh();
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                provinceDialog.show();
            }
        });
        onRefresh();
    }

    public void loadData() {
        request = new Request.Builder().url("http://njy.agri114.cn/hqt/json/findSdByAddressAndType.action").post(new FormBody.Builder()
                .add("address", mProvince)
                .add("type", type)
                .add("pageNum", mPageNum + "").build()).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    JsonObject jsonObject = (JsonObject) GsonUtil.parse(str);
                    String status = jsonObject.get("status").getAsString();
                    if (status.equals("success")) {
                        final List<SupplyDemand> sd = GsonUtil.fromJson(jsonObject.get("result"), new TypeToken<List<SupplyDemand>>() {
                        }.getType());
                        mPageCount = jsonObject.get("pageCount").getAsInt();
                        sdsResult.addAll(sd);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                if (sd.size() == 0) {
                                    refreshLayout.setRefreshing(false);
                                    refreshLayout.setLoading(false);
                                }
                                if (mPageNum == 1) {
                                    refreshLayout.setRefreshing(false);
                                } else {
                                    refreshLayout.setLoading(false);
                                }
                            }
                        });

                    }
                }
            }
        });
    }

    @Override
    public void onLoad() {
        ++mPageNum;
        if (mPageNum > mPageCount) {
            refreshLayout.setLoading(false);
        } else {
            loadData();
        }
    }

    @Override
    public void onRefresh() {
        mPageNum = 1;
        sdsResult.clear();
        loadData();
    }

    @Override
    public void setProvince(String province) {
        this.mProvince = province;
        address.setText(province);
    }
}
