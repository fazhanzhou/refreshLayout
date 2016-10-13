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

/**
 * 商户资料
 */
public class ShglActivity extends Activity {
    //种养大户
    @Bind(R.id.first)
    TextView first;
    //经纪人
    @Bind(R.id.second)
    TextView second;
    //专业合作社
    @Bind(R.id.third)
    TextView third;
    //批发市场
    @Bind(R.id.four)
    TextView four;
    //批发市场
    @Bind(R.id.five)
    TextView five;
    //批发市场
    @Bind(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shgl);
        ButterKnife.bind(this);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShglActivity.this, ShglDetailLessActivity.class).putExtra("type", "种养大户"));
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShglActivity.this, ShglDetailLessActivity.class).putExtra("type", "经纪人"));
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShglActivity.this, ShglDetailActivity.class).putExtra("type", "专业合作社"));
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShglActivity.this, ShglDetailActivity.class).putExtra("type", "批发市场"));
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShglActivity.this, ShglDetailActivity.class).putExtra("type", "涉农企业"));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShglActivity.this.finish();
            }
        });
    }
}
