package io.github.yylyingy.yiji.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import io.github.yylyingy.yiji.YiJiApplication;

import butterknife.Unbinder;
import io.github.yylyingy.yiji.tools.MessageEvent;

/**
 * Created by Yangyl on 2016/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private   String TAG ;
    protected Unbinder mUnbinder = null;
    protected FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        ((YiJiApplication)getApplication()).addActivity(this);
        mFragmentManager = getSupportFragmentManager();
        TAG = this.getLocalClassName();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusFinishActivity(MessageEvent e){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
//        ((YiJiApplication)getApplication()).removeActivity(this);
        YiJiApplication.getRefWatcher(getApplicationContext()).watch(this);
    }

}
