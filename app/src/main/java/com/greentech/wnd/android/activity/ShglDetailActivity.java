package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.adapter.MerchantAdapter;
import com.greentech.wnd.android.bean.Merchant;
import com.greentech.wnd.android.constant.Constant;
import com.greentech.wnd.android.util.CommonUtil;
import com.greentech.wnd.android.util.CustomDialog;
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

public class ShglDetailActivity extends Activity implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener, ProvinceDialog.ProvinceChange {
    @Bind(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.choice)
    TextView choice;
    @Bind(R.id.back)
    ImageView back;
    int mPageNum = 1;
    int mPageCount = 0;
    Request request;
    String mProvince = "全国";
    String type = "";
    List<Merchant> sdsResult = new ArrayList<Merchant>();
    ProvinceDialog provinceDialog;
    MerchantAdapter adapter;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shgl_detail);
        ButterKnife.bind(this);
        mProvince=LocationUtil.getProvince(this);
        type = getIntent().getStringExtra("type");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShglDetailActivity.this.finish();
            }
        });
        dialog = CustomDialog.createLoadingDialog(this, "正在加载...");
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        provinceDialog = new ProvinceDialog(ShglDetailActivity.this);
        provinceDialog.setProvinceChangeListener(this);
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provinceDialog.show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = sdsResult.get(position).getName() == null ? "" : sdsResult.get(position).getName();
                String type = sdsResult.get(position).getType() == null ? "" : sdsResult.get(position).getType();
                String qq = sdsResult.get(position).getQq_msn() == null ? "" : sdsResult.get(position).getQq_msn();
                String contactor = sdsResult.get(position).getContactor() == null ? "" : sdsResult.get(position).getContactor();
                String tel = sdsResult.get(position).getTel() == null ? "" : sdsResult.get(position).getTel();
                String mobile = sdsResult.get(position).getMobile() == null ? "" : sdsResult.get(position).getMobile();
                String address = sdsResult.get(position).getAddress() == null ? "" : sdsResult.get(position).getAddress();
                String content = sdsResult.get(position).getContent() == null ? "" : sdsResult.get(position).getContent();
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("type", type);
                intent.putExtra("qq", qq);
                intent.putExtra("contactor", contactor);
                intent.putExtra("tel", tel);
                intent.putExtra("mobile", mobile);
                intent.putExtra("address", address);
                intent.putExtra("content", content);
                intent.setClass(ShglDetailActivity.this,
                        ShglDDActivity.class);
                startActivity(intent);
            }
        });
            adapter = new MerchantAdapter(sdsResult);
            listView.setAdapter(adapter);
        onRefresh();

    }

    public void loadData() {
        dialog.show();
        request = new Request.Builder().url(Constant.SERVIER_PATH + "/json/find.action").post(new FormBody.Builder()
                .add("province", mProvince)
                .add("type", type)
                .add("pageNum", mPageNum + "").build()).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    JsonObject jsonObject = (JsonObject) GsonUtil.parse(str);
                    final List<Merchant> sd = GsonUtil.fromJson(jsonObject.get("result"), new TypeToken<List<Merchant>>() {
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
        onRefresh();
    }
}
