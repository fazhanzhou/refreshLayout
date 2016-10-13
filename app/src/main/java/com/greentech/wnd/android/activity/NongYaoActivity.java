package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greentech.wnd.android.R;

public class NongYaoActivity extends Activity implements View.OnClickListener {
    private Button price, baike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nong_yao);
        findViewById(R.id.ny_price).setOnClickListener(this);
        findViewById(R.id.baike).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DiscoveryListActivity.class);
        switch (v.getId()) {
            case R.id.ny_price:
                intent.putExtra("type", "农药价格行情");
                startActivity(intent);
                this.finish();
                break;
            case R.id.baike:
                intent.putExtra("type", "农药百科");
                startActivity(intent);
                this.finish();
                break;

        }
    }
}
