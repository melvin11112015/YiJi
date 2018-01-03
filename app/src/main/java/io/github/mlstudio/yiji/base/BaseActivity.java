package io.github.mlstudio.yiji.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.github.johnpersano.supertoasts.SuperToast;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mlstudio.yiji.YiJiApplication;
import io.github.mlstudio.yiji.tools.MessageEvent;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yangyl on 2016/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder mUnbinder = null;
    protected FragmentManager mFragmentManager;
    private CompositeSubscription mCompositeSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        ((YiJiApplication)getApplication()).addActivity(this);
        mFragmentManager = getSupportFragmentManager();
        String TAG = this.getLocalClassName();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("Activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SuperToast.cancelAllSuperToasts();
    }

    /**
     * 解决Subscription内存泄露问题
     * @param s
     */
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusFinishActivity(MessageEvent e){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription = null;
        SuperToast.cancelAllSuperToasts();
        EventBus.getDefault().unregister(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
//        ((YiJiApplication)getApplication()).removeActivity(this);
        YiJiApplication.getRefWatcher(getApplicationContext()).watch(this);
    }

}
