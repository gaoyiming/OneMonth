package com.mrgao.onemonth.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;



/**
 * Created by mr.gao on 2017/3/27.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    protected T mPresenter;
    private E mModel;

    private boolean changeColor;
    private boolean hideStatusBarBackground;
    private String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        //    setStatusBar(R.color.statu_bar_bg, false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);

//        initContentView();
//
//
//        //  setStatusBar(R.color.white,changeColor);
//        initInject();
//        //ButterKnife.bind(this);
//
//        initUIandListener();
//
//
//        AppManager.getAppManager().addActivity(this);

    }


    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

//        setTheme(ThemeUtil.themeArr[SpUtil.getThemeIndex(this)][
//                SpUtil.getNightModel(this) ? 1 : 0]);

        BaseActivity mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView){ mPresenter.attachVM(this, mModel);}
        initContentView();
        initData();
        this.initView(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);

    }

    protected void initData() {
    }

    protected abstract void initView(Bundle savedInstanceState);

    public void jumpTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        //  overridePendingTransition(0, 0);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void jumpTo(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("bundle", bundle);
        startActivity(intent);

//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

    }

    public void setStatusBar(int color, boolean changeColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hideStatusBarBackground) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //黑字
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.setStatusBarColor(getResources().getColor(color));

                if (Color.WHITE == color) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && changeColor) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                }

            }
        }
    }

    /**
     * 初始化布局
     *
     * @return id
     */
    protected abstract void initContentView();

    /**
     * 初始化注入
     */
    //public abstract void initInject();

    /**
     * 初始化监听
     */
    // public abstract void initUIandListener();


    /**
     * 从新载入
     */
    public void reLoad() {
        Intent intent = new Intent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
//        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            finish();
            // overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }

        return false;

    }

    public Context getContext() {
        return this;
    }

}
