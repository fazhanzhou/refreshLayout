package com.greentech.wnd.android.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.activity.HuaFeiActivity;
import com.greentech.wnd.android.activity.MarketActivity;
import com.greentech.wnd.android.activity.NewsActivity;
import com.greentech.wnd.android.activity.NongYaoActivity;
import com.greentech.wnd.android.bean.HotUrl;
import com.greentech.wnd.android.constant.Constant;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.OkHttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

//发现
public class DiscoveryFragment1 extends Fragment implements View.OnClickListener {
    private ImageButton huafei, nongyao, hangqing, news, hot;
    final String[] categorys = {"分析预测", "市场动态", "国际动态"};
    final String[] types = {"粮食", "菜果", "油料", "棉花", "畜水产品", "其他", "综合"};
    String category;
    String type;
    List<HotUrl> urls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dis, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
        view.findViewById(R.id.huafei).setOnClickListener(this);
        view.findViewById(R.id.nongyao).setOnClickListener(this);
        view.findViewById(R.id.hangqing).setOnClickListener(this);
        view.findViewById(R.id.news).setOnClickListener(this);
        view.findViewById(R.id.hot).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.huafei:
                final Intent intent = new Intent(getActivity(), HuaFeiActivity.class);
                startActivity(intent);
                break;
            case R.id.nongyao:
                Intent intent1 = new Intent(getActivity(), NongYaoActivity.class);
                startActivity(intent1);
                break;
            case R.id.hangqing:
                final Intent intent2 = new Intent(getActivity(), MarketActivity.class);

                new AlertDialog.Builder(getActivity()).setItems(categorys, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            new AlertDialog.Builder(getActivity()).setItems(types, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    category = categorys[0];
                                    type = types[which];
                                    intent2.putExtra("category", category);
                                    intent2.putExtra("type", type);
                                    startActivity(intent2);
                                }
                            }).show();
                        }
                        if (which == 1) {
                            category = categorys[1];
                            intent2.putExtra("category", category);
                            startActivity(intent2);
                        }
                        if (which == 2) {
                            category = categorys[2];
                            intent2.putExtra("category", category);
                            startActivity(intent2);
                        }
                    }
                }).show();

                break;
            case R.id.news:
                final Intent intent3 = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent3);
                break;
            case R.id.hot:
                //今日头条
//                final Intent intent4 = new Intent(getActivity(), HotActivity.class);
//                startActivity(intent4);
                getHotUrl();
                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String getHotUrl() {
//        FormBody.Builder builder = new FormBody.Builder();
        Request request = new Request.Builder().url(Constant.SERVIER_PATH + "/json/getHotUrl.action").build();


        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String str = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jsonObj = (JsonObject) GsonUtil.parse(str);
                            urls = GsonUtil.fromJson(jsonObj.get("result"), new TypeToken<List<HotUrl>>() {
                            }.getType());
                            Uri uri = Uri.parse(urls.get(0).getUrl());
                            Intent iw = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(iw);
                        }
                    });
                }
            }
        });
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(Constant.SERVIER_PATH + "/json/getHotUrl.action", new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String string = new String(bytes);
//                JsonObject jsonObj = (JsonObject) GsonUtil.parse(string);
//                urls = GsonUtil.fromJson(jsonObj.get("result"), new TypeToken<List<HotUrl>>() {
//                }.getType());
//                Uri uri = Uri.parse(urls.get(0).getUrl());
//                Intent iw = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(iw);
//
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });
        return null;
    }

}
