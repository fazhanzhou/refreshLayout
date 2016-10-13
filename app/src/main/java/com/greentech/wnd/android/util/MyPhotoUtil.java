package com.greentech.wnd.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.greentech.wnd.android.constant.Constant;

import java.io.File;

/**
 * 拍照或者选择相册图片的工具类
 *
 * @author wlj 可以提供一个从相册中选择多张图片的方法，思路：便利查询所有相册图片得到其路径，然后将图片显示在自己定义的layout里面
 */
public final class MyPhotoUtil {

    public static int REQUEST_CODE_FROM_CAMERA = 1;// 表示从相机中获取
    public static int REQUEST_CODE_FROM_ALBUM = 2;// 表示从相册中获取
    public static int FROM_CAMERA = 0;// 表示从相机中获取
    public static int FROM_ALBUM = 1;// 表示从相册中获取

    private File file = null;
    private Activity activity = null;
    private Fragment fragment = null;
    private Context context = null;

    public MyPhotoUtil() {
    }

    /**
     * 从相册或者相机中获取图片
     *
     * @param from 传入调用者的实例，调用者只能是Activity及其子类的实例或者Fragment及其子类的实例
     * @param type 0表示从相机中获取，1表示从相册中获取
     * @throws Exception
     */
    public void startTakePhotoFromCameraOrAlbum(Object from, int type,File file,
                                                int width, int height) throws Exception {
        if (from instanceof Activity) {
            activity = (Activity) from;
            context = activity;
        } else if (from instanceof Fragment) {
            fragment = (Fragment) from;
            context = fragment.getActivity();
        } else {
            throw new Exception("from参数只能为Activity或Fragment及其子类的实例");
        }
        //从相机获取图片
        if (type == FROM_CAMERA) {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
//                String saveDir = Environment.getExternalStorageDirectory()
//                        .getPath() + "/mentou";
//                File savePath =ImageUtil.createMenTouDir();
//                //如果文件夹不存在就创建这个文件夹
//                if (!savePath.exists()) {
//                    savePath.mkdirs();
//                }
//                file = new File(saveDir, "temp.jpg");
//                file.delete();
//                if (!file.exists()) {
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(context, "照片创建失败!", Toast.LENGTH_SHORT)
//                                .show();
//                        return;
//                    }
//                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                if (activity != null) {
                    activity.startActivityForResult(intent,
                            REQUEST_CODE_FROM_CAMERA);
                    // activity.finish();
                } else {
                    fragment.startActivityForResult(intent,
                            REQUEST_CODE_FROM_CAMERA);
                }
            } else {
                Toast.makeText(context, "sdcard无效或没有插入!", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (type == FROM_ALBUM) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT", null);
            intent.setType("image/*");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            if (activity != null) {
                activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
            } else {
                fragment.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
            }
        }
    }

    //相机拍照
    public void tackPhotoFromCamera(Activity from, int width, int height) {
        activity = from;
        String state = Environment.getExternalStorageState();
        String saveDir = Environment.getExternalStorageDirectory()
                .getPath() + "/temp_image";
        File savePath = new File(saveDir);
        //如果文件夹不存在就创建这个文件夹
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 证件照的正面和背面的图片分别存储,如果之前有存储要先删除
            if (Constant.isFront) {

                //在这个文件夹下面创建文件
                file = new File(saveDir, "front.jpg");
                //删除这个图片文件，然后在创建一个空的图片路径，来存储新的图片
                file.delete();
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else if (Constant.isBack) {
                //在这个文件夹下面创建文件
                file = new File(saveDir, "back.jpg");
                //删除这个图片文件，然后在创建一个空的图片路径，来存储新的图片
                file.delete();
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                file = new File(Constant.headImagePath);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            if (activity != null) {
                        activity.startActivityForResult(intent,
                        REQUEST_CODE_FROM_CAMERA);
            }
        } else {
            Toast.makeText(context, "sdcard无效或没有插入!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 获得图片路径(拍照选图)
     *
     * @return
     */
    public String getPicturePath() {
        return file.getPath();
    }
    /**
     * 获得图片文件(拍照选图)
     *
     * @return
     */
    public File getPictureFile() {
        return file;
    }

    /**
     * 获得图片路径(相册选图)
     *
     * @return
     */
    public String getPicturePathFromUri(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        cursor.moveToFirst();
        String imgPath = cursor.getString(1); // 图片文件路径
        cursor.close();
        return imgPath;
    }

}
