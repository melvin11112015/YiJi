package io.github.yylyingy.yiji.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.yylyingy.yiji.YiJiApplication;

/**
 * Created by Yangyl on 2017/1/9.
 */

public class BaseFragment extends Fragment {
    protected Unbinder mUnbinder;
    protected Context mContext;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);
        Logger.d("Fragmeng onViewCreated");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUnbinder.unbind();
        mContext = null;
        YiJiApplication.getRefWatcher(getContext()).watch(this);
    }
}
