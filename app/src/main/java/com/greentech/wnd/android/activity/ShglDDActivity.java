package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greentech.wnd.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShglDDActivity extends Activity {
    String name;
    String type;
    String qq;
    String contactor;
    String tel;
    String mobile;
    String address;
    String content;
    @Bind(R.id.sd_detail_name)
    TextView textViewName;
    @Bind(R.id.sd_detail_sdType)
    TextView textViewType;
    @Bind(R.id.sd_detail_category)
    TextView textViewQq;
    @Bind(R.id.sd_detail_province)
    TextView textViewPeo;
    @Bind(R.id.text_sd_releaseTime)
    TextView textViewTel;
    @Bind(R.id.sd_detail_validTime)
    TextView textViewMobile;
    @Bind(R.id.sd_detail_contacter)
    TextView textViewAddress;
    @Bind(R.id.sd_detail_telephone)
    TextView textViewContent;
    @Bind(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shgl_dd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
        qq = intent.getStringExtra("qq");
        contactor = intent.getStringExtra("contactor");
        tel = intent.getStringExtra("tel");
        mobile = intent.getStringExtra("mobile");
        address = intent.getStringExtra("address");
        content = intent.getStringExtra("content");
        textViewName.setText(name);
        textViewType.setText(type);
        textViewQq.setText(qq);
        textViewPeo.setText(contactor);
        textViewTel.setText(tel);
        textViewMobile.setText(mobile);
        textViewAddress.setText(address);
        textViewContent.setText(content);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShglDDActivity.this.finish();
            }
        });
    }
}
