package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.greentech.wnd.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 基Activity类，
 *
 * @author zhoufazhan
 * @since 2016-09-26
 */
public abstract class BaseActivity extends Activity {
    private Toast toast;
   protected Handler handler  =new Handler();
    public BaseActivity() {
        super();
//		gsonb.setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(Date.class, new DateTypeAdapter());
    }
    public void toastShow(String info) {
        toast = Toast.makeText(getApplicationContext(),
                info, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
