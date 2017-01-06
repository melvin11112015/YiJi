package io.github.yylyingy.yiji.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import io.github.yylyingy.yiji.YiJiApplication;

import butterknife.Unbinder;

/**
 * Created by Yangyl on 2016/11/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder mUnbinder = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YiJiApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
//        ((YiJiApplication)getApplication()).removeActivity(this);
    }
}
