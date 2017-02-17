package io.github.yylyingy.yiji.tools;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class App extends Application {

    private static App sApp;

    private static Toaster sToaster;

    public static App getInstance() {
        if (sApp == null) {
            sApp = new App();
        }
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        Fresco.initialize(getApplicationContext());
    }

    public static void showToast(String msg) {
        if (sToaster == null)
            sToaster = new Toaster();
        sToaster.showToast(msg);
    }

}
