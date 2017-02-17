package io.github.yylyingy.yiji;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import io.github.yylyingy.yiji.tools.ThreadPoolTool;
import io.github.yylyingy.yiji.tools.Toaster;
import io.github.yylyingy.yiji.tools.YiJiUtil;
import io.github.yylyingy.yiji.tools.db.DB;
import io.github.yylyingy.yiji.tools.db.DataManager;

/**
 * Created by Yangyl on 2016/11/29.
 */

public class YiJiApplication extends Application {

    public static final String TAG = YiJiApplication.class.getSimpleName();
    private ArrayList<Activity> mArrayList = new ArrayList<>();
    private static final Object lock = new Object();
    private static ExitAppThread mExitApp;
    private static Toaster sToaster;
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        mExitApp = new ExitAppThread(this);
        Log.d(TAG,"init");
//        LeakCanary.install(this);
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppChannel("myChannel")
                .setAppVersion("v0.0.2");
        CrashReport.initCrashReport(getApplicationContext(),"a9c67cbbf9",true,strategy);
//      Bmob init
        BmobConfig config =new BmobConfig.Builder(this)
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
        YiJiUtil.init(this);
        ThreadPoolTool.exeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    DB.getInstance(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    DataManager.getsInstance(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Context getAppContext(){
        return mExitApp.application;
    }

    public void exitApp(){
        new ExitAppThread(this).start();
    }

    public void addActivity(Activity activity){
        synchronized (lock) {
            mArrayList.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        synchronized (lock) {
            for (int i = 0; i < mArrayList.size(); i++) {
                if (activity == mArrayList.get(i)) {
                    mArrayList.remove(i);
                    break;
                }
            }
        }
    }

    public static void showToast(String msg) {
        if (sToaster == null)
            sToaster = new Toaster();
        sToaster.showToast(msg);
    }
    private static final class ExitAppThread extends Thread{
        private YiJiApplication application;
        private ExitAppThread(YiJiApplication app){
            application = new WeakReference<>(app).get();
        }
        @Override
        public void run() {
            for (Activity activity: application.mArrayList){
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        }
    }
}
