package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.MarketContent;
import com.greentech.wnd.android.constant.Constant;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.OkHttpUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class MarketContentActivity extends Activity {
    TextView title;
    WebView wv;
    String titleStr;
    int contentId;
    ImageView back;
    private AsyncHttpClient client;
    private RequestParams params;
    private final String URL = Constant.SERVIER_PATH + "/json/getContentById.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_content_activity);
        Intent intent = getIntent();
        titleStr = intent.getStringExtra("title");
        contentId = intent.getIntExtra("content", 0);

        wv = (WebView) this.findViewById(R.id.content);
        title = (TextView) this.findViewById(R.id.title);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketContentActivity.this.finish();
            }
        });
        title.setText(titleStr);
        initData();
    }

    public void initData() {
        FormBody.Builder builder = new FormBody.Builder();
//        client = new AsyncHttpClient();
//        params = new RequestParams();
        builder.add("contentId", contentId+"");
        Request request = new Request.Builder().url(URL).post(builder.build()).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String str = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jsonObj = (JsonObject) GsonUtil.parse(str);
                            MarketContent result = GsonUtil.fromJson(jsonObj.get("result"),
                                    new TypeToken<MarketContent>() {
                                    }.getType());
                            wv.loadDataWithBaseURL(null, result.getContent(), "text/html", "utf-8", null);
                        }
                    });
                }
            }
        });
//        client.get(URL, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String json = new String(bytes);
//                JsonObject jsonObj = (JsonObject) GsonUtil.parse(json);
//                MarketContent result = GsonUtil.fromJson(jsonObj.get("result"),
//                        new TypeToken<MarketContent>() {
//                        }.getType());
//                wv.loadDataWithBaseURL(null, result.getContent(), "text/html", "utf-8", null);
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });
    }
}
