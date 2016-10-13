package com.greentech.wnd.android.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.greentech.wnd.android.R;

import java.util.List;

/**
 * Created by zhoufazhan on 2016/9/22.
 */
public class ImagePagerAdapter extends BaseAdapter {
    private Context context;
    private List<String> imageIdList;
    private List<String> linkUrlArray;
    private List<String> urlTitlesList;
    private int size;
    private boolean isInfiniteLoop;
//    private ImageLoader imageLoader;
//    private DisplayImageOptions options;

    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList) {
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报错
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//        options = new DisplayImageOptions.Builder()
//                .showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.drawable.meinv) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.meinv) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                .build();

    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.ad_item, null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String url = imageIdList.get(getPosition(position));
        Glide.with(context).load(url).skipMemoryCache(true).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (getPosition(position) == 0) {

                    //判断是否安装
                    PackageInfo packageInfo;
                    try {
                        packageInfo = context.getPackageManager().getPackageInfo("com.greentech.wnd.android", 0);

                    } catch (PackageManager.NameNotFoundException e) {
                        packageInfo = null;
                        e.printStackTrace();
                    }
                    if (packageInfo == null) {
                        //没有安装,打开浏览器安装
                        Uri uri = Uri.parse("market://details?id=com.greentech.wnd.android");//id为包名
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(it);
                    } else {
                        Intent intent = new Intent("com.greentech.wnd.android");
                        ComponentName componentName = new ComponentName("com.greentech.wnd.android", "com.greentech.wnd.android.activity.WelcomActivity");
                        intent.setComponent(componentName);
                        context.startActivity(intent);
                    }
                }
            }
        });

        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

}
