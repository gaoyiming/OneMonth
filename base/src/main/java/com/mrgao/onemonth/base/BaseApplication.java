package com.mrgao.onemonth.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Looper;
import android.util.DisplayMetrics;

import java.util.Locale;


/**
 * @ClassName: ${BaseApplication}
 * @Description: ${todo}
 * @author: ${Mr.gao}
 * @date: ${date} ${time}
 * <p/>
 * ${
 * 网络框架
 * 注解框架
 * 图片框架
 * <p>
 * <p>
 * }
 */
public abstract class BaseApplication extends Application {
    private static Context context;
    private static BaseApplication application;
    private String laguage;
    private Configuration config;
    private DisplayMetrics dm;
    private Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        resources = getContext().getResources();
//        dm = resources.getDisplayMetrics();
//        config = resources.getConfiguration();
//        config.locale = Locale.CHINA;
//        resources.updateConfiguration(config, dm);

        // CrashHandler.getInstance().init(this);
        // LeakCanary.install(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
        // LeakCanary.install(this);
        // Normal app init code...
        //  registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
        application = this;
        initNetFrame();
        initThird();


    }

    private void initThird() {
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
//        PlatformConfig.setWeixin(Constants.WINXIN_ID, Constants.AppSecret);
//        //微信 appid appsecret
//        PlatformConfig.setSinaWeibo(Constants.SINA_ID, Constants.SINA_SIGN);
//        //新浪微博 appkey appsecret
//        PlatformConfig.setQQZone(Constants.QQ_ID, Constants.QQ_SIGN);
        // QQ和Qzone appid appkey
    }

    private void initNetFrame() {

    }

    public static synchronized BaseApplication getInstance() {
        return application;
    }

    @Override
    public Looper getMainLooper() {
        return super.getMainLooper();
    }

    public abstract String setUrl();

    public static Context getContext() {
        return context;
    }
//    public static File CacheDir() {
//         new File(context.getExternalCacheDir()+Constants.CACHENET);
//    }

    //  private ActivityLifecycleHelper mActivityLifecycleHelper;


//    public ActivityLifecycleHelper getActivityLifecycleHelper() {
//        return mActivityLifecycleHelper;
//    }
//
//    public void onSlideBack(boolean isReset, float distance) {
//        if (mActivityLifecycleHelper != null) {
//            Activity lastActivity = mActivityLifecycleHelper.getPreActivity();
//            if (lastActivity != null) {
//                View contentView = lastActivity.findViewById(android.R.id.content);
//                if (isReset) {
//                    contentView.setX(contentView.getLeft());
//                } else {
//                    final int width = getResources().getDisplayMetrics().widthPixels;
//                    contentView.setX(-width / 3 + distance / 3);
//                }
//            }
//        }
//    }

}
