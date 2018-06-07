package com.mrgao.onemonth.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by mr.gao on 2017/11/1.
 */

public abstract class BaseDatabindActivity<B extends ViewDataBinding> extends AppCompatActivity

{
    private Context mContext;
    protected B mViewBinding;

    //  protected final PublishSubject lifecycleSubject = PublishSubject.create();
    private boolean changeColor;
    private boolean hideStatusBarBackground;
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setStatusBar(R.color.statu_bar_bg, false);
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId());
//
        // initStateBar();
        mContext = this;

        // initToolBar();
        initData();
        initView();
        initListener();
        // AppManager.getAppManager().addActivity(this);
    }

    protected void initData() {
    }

    private void initListener() {

    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    //   protected abstract void initStateBar();

    //
//    protected void initPresenter() {
//    }
    public Context getContext() {
        return mContext;
    }

//    private void initToolBar() {
//        titilebar = (CustomToolBar) findViewById(R.id.titlebar);
//
//        if (titilebar != null) {
//            setTitleBar(titilebar);
//        }
//    }

    //
    // protected abstract void setTitleBar(CustomToolBar titlebar);
    // public void reload() {
//        AppCompatDelegate.setDefaultNightMode(SpUtil.isNight() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
//        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
//        recreate();
//    }

//    public void setContentView(int layoutResID, View rootView) {
//        //  boolean isNotSwipeBack = layoutResID == R.layout.activity_main || layoutResID == R.layout.activity_flash;
//        super.setContentView(rootView);
//    }

    // private View getContainer(View rootView) {
//        rootView.setBackgroundColor(getResources().getColor(R.color.alpha_white));
//        View container = getLayoutInflater().inflate(R.layout.activity_base, null, false);
//        SwipeBackLayout swipeBackLayout = (SwipeBackLayout) container.findViewById(R.id.swipeBackLayout);
//        View ivShadow = container.findViewById(R.id.iv_shadow);
//        swipeBackLayout.addView(rootView);
//        swipeBackLayout.setOnScroll((fs) -> ivShadow.setAlpha(1 - fs));
//        return rootView;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (-1 != getMenuId()) getMenuInflater().inflate(getMenuId(), menu);
        return true;
    }

    private int getMenuId() {
        return -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void jumpTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        //  overridePendingTransition(0, 0);
        // overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void jumpTo(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("bundle", bundle);
        startActivity(intent);

        //  overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void jumpToService(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("bundle", bundle);
        startService(intent);


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
}