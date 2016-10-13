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
import com.greentech.wnd.android.adapter.NewsStringAdapter;
import com.greentech.wnd.android.bean.News;
import com.greentech.wnd.android.constant.Constant;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.OkHttpUtil;
import com.greentech.wnd.android.view.AutoListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class NewsActivity extends Activity implements AutoListView.OnRefreshListener, AutoListView.OnLoadListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    private AutoListView mListView;
    private AsyncHttpClient client;
    private RequestParams params;
    private List<News> list = new ArrayList<News>();
    private Integer pageNum = 1;
    private String url = Constant.SERVIER_PATH + "/json/getNews.action";
    private NewsStringAdapter mAdapter;
    private TextView textTitle;//标题
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mListView = (AutoListView) findViewById(R.id.autolistview);
        textTitle = (TextView) findViewById(R.id.texttitle);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        mAdapter = new NewsStringAdapter(this, list);
        mListView.setAdapter(mAdapter);
        client = new AsyncHttpClient();
        params = new RequestParams();
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);
        mListView.setOnItemClickListener(this);
        textTitle.setText("农业部信息联播");
        loadData(AutoListView.LOAD);
    }

    public void loadData(final int what) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("pageNum", pageNum+"");

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<News> result;
                            JsonObject jsonObj = (JsonObject) GsonUtil.parse(json);
                            result = GsonUtil.fromJson(jsonObj.get("result"),
                                    new TypeToken<List<News>>() {
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
//                List<News> result;
//                String json = new String(bytes);
//                JsonObject jsonObj = (JsonObject) GsonUtil.parse(json);
//                result = GsonUtil.fromJson(jsonObj.get("result"),
//                        new TypeToken<List<News>>() {
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
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, NewsDetailActivity.class);
        News hq = list.get(position);
        String title = hq.getTitle();
        String content = hq.getContent();
        i.putExtra("title", title);
        i.putExtra("content", content);
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
