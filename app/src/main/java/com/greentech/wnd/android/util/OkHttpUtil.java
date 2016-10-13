package com.greentech.wnd.android.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhoufazhan on 2016/3/2.
 */
public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
    //    private OkHttpUtil(){
//        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).build();
//    }
//    public static OkHttpUtil getInstance(){
//        if(instance ==null){
//            synchronized (OkHttpUtil.class){
//               instance = new OkHttpUtil();
//            }
//        }
//        return instance;
//    }
    private static Call mCall;

    /**
     * 不开启异步线程
     *
     * @param request
     * @return
     * @throws IOException
     * @author wangsong 2015-10-9
     */
    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问，访问结果自行处理
     *
     * @param request
     * @param responseCallback
     * @author wangsong 2015-10-9
     */
    public static void enqueue(Request request, Callback responseCallback) {
//        mOkHttpClient.newCall(request).enqueue(responseCallback);
        Call call = mOkHttpClient.newCall(request);
        mCall = call;
        call.enqueue(responseCallback);
    }

    public static void cancle() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * 开启异步线程访问,不对访问结果进行处理
     *
     * @param request
     * @author wangsong 2015-10-9
     */
    public static void enqueue(Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 为HttpGet请求拼接一个参数
     *
     * @param url
     * @param name
     * @param value
     * @author wangsong 2015-10-9
     */
    public static String jointURL(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    /**
     * 为HttpGet请求拼接多个参数
     *
     * @param url
     * @author wangsong 2015-10-9
     */
    public static String jointURL(String url, Map<String, String> values) {
        StringBuffer result = new StringBuffer();
        result.append(url).append("?");
        Set<String> keys = values.keySet();
        for (String key : keys) {
            result.append(key).append("=").append(values.get(key)).append("&");
        }
        return result.toString().substring(0, result.toString().length() - 1);
    }

}
