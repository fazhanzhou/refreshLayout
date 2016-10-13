package com.greentech.wnd.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.greentech.wnd.android.R;
import com.greentech.wnd.android.bean.SDProduct;
import com.greentech.wnd.android.constant.Constant;
import com.greentech.wnd.android.util.CommonUtil;
import com.greentech.wnd.android.util.CustomDialog;
import com.greentech.wnd.android.util.GsonUtil;
import com.greentech.wnd.android.util.LocationUtil;
import com.greentech.wnd.android.util.OkHttpUtil;
import com.greentech.wnd.android.util.UserInfo;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class ReleaseActivity extends Activity implements View.OnClickListener {
    LinearLayout date;//时间选择
    TextView time;//时间选择
    ImageButton back;//返回
    EditText title;// 标题
    EditText price;// 单价
    EditText count;// 数量
    EditText level;// 等级
    EditText describe;// 描述
    EditText contact;// 联系人
    EditText tel;// 电话
    Spinner category;// 种类
    Spinner type;// 子类
    Spinner unit;// 单位
    RadioGroup radio;
    RadioButton rd;
    Button submit;
    Button cancel;
    private List<SDProduct> products;
    SDProduct product = new SDProduct();
    int year;
    int month;
    int day;
    String validTime;
    private String[] categorys = new String[]{"水产", "水果", "畜禽", "粮油", "药材",
            "蔬菜", "其他"};
    private String[] units = new String[]{"元/斤", "元/千克", "元/克", "元/吨"};
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        back = (ImageButton) findViewById(R.id.sd_back);
        date = (LinearLayout) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        title = (EditText) findViewById(R.id.release_sd_name);
        price = (EditText) findViewById(R.id.sd_price);
        count = (EditText) findViewById(R.id.sd_amount);
        level = (EditText) findViewById(R.id.sd_norm);
        describe = (EditText) findViewById(R.id.sd_content);
        contact = (EditText) findViewById(R.id.sd_contacter);
        tel = (EditText) findViewById(R.id.sd_mobile);
        category = (Spinner) findViewById(R.id.sd_spinner_category);
        type = (Spinner) findViewById(R.id.sd_spinner_type);
        unit = (Spinner) findViewById(R.id.sd_spinner_unit);
        radio = (RadioGroup) findViewById(R.id.release_sd_rg);
        rd = (RadioButton) findViewById(R.id.release_sd_rdS);
        submit = (Button) findViewById(R.id.sd_button);
        cancel = (Button) findViewById(R.id.sd_cancel);


        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        date.setOnClickListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                ReleaseActivity.this, R.layout.spinner_item1, R.id.textView, units);
        unit.setAdapter(arrayAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ReleaseActivity.this, R.layout.spinner_item1, R.id.textView, categorys);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = categorys[position];
                loadType(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                product = products.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rd = (RadioButton) findViewById(checkedId);

            }
        });
//        LocationUtil location = LocationUtil.getInstance(ReleaseActivity.this);
//        location.setOnLocationListener(new LocationUtil.OnLocationListener() {
//            @Override
//            public void setLocation(String province, String city, String district) {
//                mProvince = province;
////                CommonUtil.showToask(ReleaseActivity.this,province);
////                Constant.province=province;
//            }
//        });

    }

    public void loadType(String category) {
        OkHttpUtil.enqueue(new Request.Builder().url("http://182.92.165.86/ynznApp/json/loadSDType.action").post(new FormBody.Builder().add("category", category).build()).build(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    JsonArray jsonObject = (JsonArray) GsonUtil.parse(str);
                    products = GsonUtil.fromJson(jsonObject, new TypeToken<List<SDProduct>>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(
                                    ReleaseActivity.this, R.layout.spinner_item1, R.id.textView,
                                    SDProduct.PToStr(products));
                            type.setAdapter(arrayAdapter);
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sd_back:
                this.finish();
                break;
            case R.id.sd_button:
                if (title.getText().toString().trim().equals("")) {
                    CommonUtil.showToask(this, "请输入标题!");
                    return;
                }
                if (contact.getText().toString().trim().equals("")) {
                    CommonUtil.showToask(this, "请输入联系人!");
                    return;
                }
                if (tel.getText().toString().trim().equals("")) {
                    CommonUtil.showToask(this, "请输入手机号!");
                    return;
                }
                if (price.getText().toString().trim().equals("")) {
                    CommonUtil.showToask(this, "请输入产品价格!");
                    return;
                }
                if (count.getText().toString().trim().equals("")) {
                    CommonUtil.showToask(this, "请输入产品数量!");
                    return;
                }
                submit();
                break;
            case R.id.sd_cancel:
                this.finish();
                break;
            case R.id.date:
                final AlertDialog builder = new AlertDialog.Builder(ReleaseActivity.this)
                        .create();
                builder.show();
                builder.getWindow().setContentView(R.layout.datepicker);
                DatePicker datePicker = (DatePicker) builder.findViewById(R.id.datepicker);
                //设置有效期的最小时间
//                datePicker.setMinDate(Calendar.getInstance().getTime().getTime());
//                //设置有效期的最大时间 5年内
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.YEAR, 5);
//                datePicker.setMaxDate(calendar.getTime().getTime());
                // 获取当前的年、月、日、小时、分钟
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        time.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        validTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                });
                break;
        }
    }

    public void submit() {
        dialog = CustomDialog.createLoadingDialog(this, "正在上传");
        dialog.show();
        FormBody formBody = new FormBody.Builder()
                .add("sd.name", title.getText().toString().trim())
                .add("sd.category", category.getSelectedItem().toString().trim())
                .add("sd.content", describe.getText().toString().trim())
                .add("sd.type", rd == null ? "供应" : rd.getText().toString().trim())
                .add("sd.province", LocationUtil.getProvince(ReleaseActivity.this))
                .add("sd.contacter", contact.getText().toString().trim())
                .add("sd.mobile", tel.getText().toString().trim())
                .add("sd.productId", product.getId() + "")
                .add("validtime", validTime)
                .add("sd.userId", UserInfo.getUserId(ReleaseActivity.this) + "")
                .build();
        Request request = new Request.Builder().url("http://njy.agri114.cn/hqt/json/addGQ.action").post(formBody).build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        CommonUtil.showToask(ReleaseActivity.this, "发布失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            dialog.dismiss();
                            ReleaseActivity.this.finish();
                            CommonUtil.showToask(ReleaseActivity.this, "发布成功");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            ReleaseActivity.this.finish();
                            CommonUtil.showToask(ReleaseActivity.this, "发布失败");
                        }
                    });
                }
            }
        });
    }
}
