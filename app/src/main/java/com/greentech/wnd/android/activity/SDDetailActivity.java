package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.SupplyDemand;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.OkHttpUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class SDDetailActivity extends Activity {
    private TextView name;
    private TextView sdType;
    private TextView category;
    private TextView province;
    private TextView content;
    private TextView releaseTime;
    private TextView validTime;
    private TextView contacter;
    private TextView telphone;
    private TextView mobile;
    private TextView qqMsn;
    private TextView address;
    private ImageButton back;
    private SupplyDemand sd;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private int sdId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sddetail);
        sdId = getIntent().getIntExtra("sdId", 1);
        init();
        loadData();
    }

    private void init() {
        name = (TextView) this.findViewById(R.id.sd_detail_name);
        sdType = (TextView) this.findViewById(R.id.sd_detail_sdType);
        category = (TextView) this.findViewById(R.id.sd_detail_category);
        province = (TextView) this.findViewById(R.id.sd_detail_province);
        content = (TextView) this.findViewById(R.id.sd_detail_content);
        releaseTime = (TextView) this.findViewById(R.id.sd_detail_releaseTime);
        validTime = (TextView) this.findViewById(R.id.sd_detail_validTime);
        contacter = (TextView) this.findViewById(R.id.sd_detail_contacter);
        telphone = (TextView) this.findViewById(R.id.sd_detail_telephone);
        mobile = (TextView) this.findViewById(R.id.sd_detail_mobile);
        qqMsn = (TextView) this.findViewById(R.id.sd_detail_qqMsn);
        address = (TextView) this.findViewById(R.id.sd_detail_address);
        back = (ImageButton) this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDDetailActivity.this.finish();
            }
        });
    }

    public void loadData() {
        Request request = new Request.Builder().url("http://njy.agri114.cn/hqt/json/findSdDetail.action").post(new FormBody.Builder()
                .add("id", sdId + "").build()).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    JsonObject jsonObject = (JsonObject) GsonUtil.parse(str);
                    sd = GsonUtil.fromJson(jsonObject, new TypeToken<SupplyDemand>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(sd.getName());
                            sdType.setText(sd.getType());
                            category.setText(sd.getCategory());
                            province.setText(sd.getProvince());
                            content.setText(sd.getContent());
                            if (sd.getReleaseTime() != null) {

                                releaseTime.setText(sf.format(sd
                                        .getReleaseTime()));
                            }
                            if (sd.getValidTime() != null) {

                                validTime.setText(sf.format(sd
                                        .getValidTime()));
                            }
                            contacter.setText(sd.getContacter());
                            telphone.setText(sd.getTelephone());
                            mobile.setText(sd.getMobile());
                            qqMsn.setText(sd.getQqMsn());
                            address.setText(sd.getAddress());
                        }
                    });
                }
            }
        });

    }
}
