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
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yangyl on 2017/1/9.
 */

public class BaseFragment extends Fragment {
    protected Unbinder mUnbinder;
    protected Context mContext;
    private CompositeSubscription mCompositeSubscription;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);
        Logger.d("Fragmeng onViewCreated");
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

    @Override
    public void onDetach() {
        super.onDetach();
        mUnbinder.unbind();
        mContext = null;
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
        YiJiApplication.getRefWatcher(getContext()).watch(this);
    }
}
