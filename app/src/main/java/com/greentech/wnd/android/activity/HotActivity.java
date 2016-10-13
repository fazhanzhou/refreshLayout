package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.greentech.wnd.android.R;

import java.io.File;

//import static com.baidu.location.d.j.R;

//import com.github.barteksc.pdfviewer.PDFView;


public class HotActivity extends Activity {
    TextView title;
//    PDFView pdfView;
    String titleStr;
    String content;
    ImageView back;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);
        wv = (WebView) findViewById(com.greentech.wnd.android.R.id.webview);
        wv.loadUrl("http://m.toutiao.com/i6303357228560679426/?tt_from=weixin&utm_campaign=client_share&from=groupmessage&app=news_article&utm_source=weixin&isappinstalled=1&iid=3426430804&utm_medium=toutiao_android&wxshare_count=1");
        title = (TextView) this.findViewById(R.id.title);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotActivity.this.finish();
            }
        });
        title.setText(titleStr);


    }
}
