package io.github.yylyingy.yiji.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import io.github.yylyingy.yiji.YiJiApplication;

import butterknife.Unbinder;

/**
 * Created by Yangyl on 2016/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder mUnbinder = null;
    protected FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YiJiApplication)getApplication()).addActivity(this);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);
        initButterKnife();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("Activity onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        ((YiJiApplication)getApplication()).removeActivity(this);
        YiJiApplication.getRefWatcher(getApplicationContext()).watch(this);
    }
    protected  void initButterKnife(){
        mUnbinder = ButterKnife.bind(this);
    }
}
