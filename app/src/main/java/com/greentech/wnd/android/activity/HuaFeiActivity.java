package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greentech.wnd.android.R;

/**
 * 化肥
 */
public class HuaFeiActivity extends Activity implements View.OnClickListener{
    private Button price, market, yuanliao, factory, guoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hua_fei);
        findViewById(R.id.price).setOnClickListener(this);
        findViewById(R.id.market).setOnClickListener(this);
        findViewById(R.id.yuanliao).setOnClickListener(this);
        findViewById(R.id.factory).setOnClickListener(this);
        findViewById(R.id.guoji).setOnClickListener(this);
        Dialog dialog=new Dialog(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DiscoveryListActivity.class);
        switch (v.getId()){
            case R.id.price:
                intent.putExtra("type", "化肥价格行情");
                startActivity(intent);
                this.finish();
                break;
            case R.id.market:
                intent.putExtra("type", "化肥市场分析");
                startActivity(intent);
                this.finish();
                break;
            case R.id.yuanliao:
                intent.putExtra("type", "化肥原料价格");
                startActivity(intent);
                this.finish();
                break;
            case R.id.factory:
                intent.putExtra("type", "化肥厂家报价");
                startActivity(intent);
                this.finish();
                break;
            case R.id.guoji:
                intent.putExtra("type", "化肥国际市场");
                startActivity(intent);
                this.finish();
                break;

        }
    }
}
