package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.greentech.wnd.android.R;

/**
 * 全国农业信息联播 详情
 */
public class NewsDetailActivity extends Activity {
    TextView title;
    WebView wv;
    String titleStr;
    String content;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        titleStr = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        wv = (WebView) this.findViewById(R.id.content);
        title = (TextView) this.findViewById(R.id.title);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.this.finish();
            }
        });
        title.setText(titleStr);
        wv.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }
}
