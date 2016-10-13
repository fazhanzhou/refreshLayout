package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.adapter.MarketStringAdapter;
import com.greentech.wnd.android.bean.Market;
import com.greentech.wnd.android.constant.Constant;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.OkHttpUtil;
import com.greentech.wnd.android.view.AutoListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class MarketActivity extends Activity implements AutoListView.OnRefreshListener, AutoListView.OnLoadListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    private String type;//在数据库查询的类别
    private String category;//在数据库查询的类别
    private AutoListView mListView;
    private AsyncHttpClient client;
    private RequestParams params;
    private List<Market> list = new ArrayList<Market>();
    private Integer pageNum = 1;
    private String url;
    private MarketStringAdapter mAdapter;
    private TextView textTitle;//标题
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        mListView = (AutoListView) findViewById(R.id.autolistview);
        textTitle = (TextView) findViewById(R.id.texttitle);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);
        mListView.setOnItemClickListener(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        category = intent.getStringExtra("category");
        textTitle.setText(category);
        if (category.equals("分析预测")) {
            url = Constant.SERVIER_PATH + "/json/alalysis.action";
        }
        if (category.equals("市场动态")) {
            url = Constant.SERVIER_PATH + "/json/market.action";
        }
        if (category.equals("国际动态")) {
            url = Constant.SERVIER_PATH + "/json/guoji.action";
        }
        if (StringUtils.isNotBlank(category)) {
            textTitle.setText(category);
        }
        client = new AsyncHttpClient();
        mAdapter = new MarketStringAdapter(this, list);
        mListView.setAdapter(mAdapter);
        loadData(AutoListView.LOAD);
    }

    public void loadData(final int what) {

        FormBody.Builder builder = new FormBody.Builder();
//        params = new RequestParams();
        builder.add("category", category);
        if (StringUtils.isNotBlank(type)) {
            builder.add("type", type);
        }
        builder.add("pageNum", pageNum+"");
        Request request = new Request.Builder().url(url).post(builder.build()).build();
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
                            List<Market> result;
                            JsonObject jsonObj = (JsonObject) GsonUtil.parse(str);
                            result = GsonUtil.fromJson(jsonObj.get("result"),
                                    new TypeToken<List<Market>>() {
                                    }.getType());
                            switch (what) {
                                case AutoListView.LOAD:
                                    mListView.onLoadComplete();
                                    list.addAll(result);
                                    break;
                                case AutoListView.REFRESH:
                                    mListView.onRefreshComplete();
                                    list.clear();
                                    list.addAll(result);
                                    break;
                            }
                            mListView.setResultSize(result.size());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
//        client.get(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                List<Market> result;
//                String json = new String(bytes);
//                JsonObject jsonObj = (JsonObject) GsonUtil.parse(json);
//                result = GsonUtil.fromJson(jsonObj.get("result"),
//                        new TypeToken<List<Market>>() {
//                        }.getType());
//                switch (what) {
//                    case AutoListView.LOAD:
//                        mListView.onLoadComplete();
//                        list.addAll(result);
//                        break;
//                    case AutoListView.REFRESH:
//                        mListView.onRefreshComplete();
//                        list.clear();
//                        list.addAll(result);
//                        break;
//                }
//                mListView.setResultSize(result.size());
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, MarketContentActivity.class);
        Market hq = list.get(position);
        String title = hq.getTitle();
        int contentId = hq.getContentId();
        i.putExtra("title", title);
        i.putExtra("content", contentId);
        startActivity(i);
    }

    @Override
    public void onLoad() {
        ++pageNum;
        loadData(AutoListView.LOAD);
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        loadData(AutoListView.REFRESH);
    }
}
