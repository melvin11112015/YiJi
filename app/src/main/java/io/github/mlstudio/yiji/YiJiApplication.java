package io.github.mlstudio.yiji;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import io.github.mlstudio.yiji.tools.ThreadPoolTool;
import io.github.mlstudio.yiji.tools.Toaster;
import io.github.mlstudio.yiji.tools.YiJiUtil;
import io.github.mlstudio.yiji.tools.db.DataManager;

/**
 * Created by Yangyl on 2016/11/29.
 */

public class YiJiApplication extends Application {

    public static final String TAG = YiJiApplication.class.getSimpleName();
    private static final Object lock = new Object();
    private static getApp mGetApp;
    private static Toaster sToaster;
    private ArrayList<Activity> mArrayList = new ArrayList<>();
    private RefWatcher refWatcher;
    public static RefWatcher getRefWatcher(Context context) {
        YiJiApplication application = (YiJiApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static Context getAppContext() {
        return mGetApp.application;
    }

    public static void showToast(String msg) {
        if (sToaster == null)
            sToaster = new Toaster();
        sToaster.showToast(msg);
    }
//    public void exitApp(){
//        new ExitAppThread(this).start();
//    }
//
//    public void addActivity(Activity activity){
//        synchronized (lock) {
//            mArrayList.add(activity);
//        }
//    }
//
//    public void removeActivity(Activity activity){
//        synchronized (lock) {
//            for (int i = 0; i < mArrayList.size(); i++) {
//                if (activity == mArrayList.get(i)) {
//                    mArrayList.remove(i);
//                    break;
//                }
//            }
//        }
//    }

    public static String getAndroidId() {
        return Settings.Secure.getString(
                getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        ThreadPoolTool.exeTask(() -> {
            Logger.init("YiJi-Logger");
            Fresco.initialize(getApplicationContext());
            mGetApp = new getApp((YiJiApplication) getApplicationContext());
            Log.d(TAG,"init");
            refWatcher = LeakCanary.install((Application) YiJiApplication.getAppContext());
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
            strategy.setAppChannel("myChannel")
                    .setAppVersion("v1.0.3");
            CrashReport.initCrashReport(getApplicationContext(),"a9c67cbbf9",true,strategy);
            //      Bmob init
            BmobConfig config =new BmobConfig.Builder(getApplicationContext())
                    //设置appkey
                    .setApplicationId("243fb1c1acf0854f98033d44bb89129d")
                    //请求超时时间（单位为秒）：默认15s
                    .setConnectTimeout(30)
                    //文件分片上传时每片的大小（单位字节），默认512*1024
                    .setUploadBlockSize(1024*1024)
                    //文件的过期时间(单位为秒)：默认1800s
                    .setFileExpiration(2500)
                    .build();
            Bmob.initialize(config);
            YiJiUtil.init(YiJiApplication.this);
            DataManager.getsInstance(getApplicationContext());
        });
    }

    private static final class getApp {
        private YiJiApplication application;
        private getApp(YiJiApplication app){
            application = new WeakReference<>(app).get();
        }
//        @Override
//        public void run() {
//            for (Activity activity: application.mArrayList){
//                if (!activity.isFinishing()){
//                    activity.finish();
//                }
//            }
//        }
    }
}
