package io.github.mlstudio.yiji.main.showrecord;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.mlstudio.yiji.YiJiApplication;
import io.github.mlstudio.yiji.tools.YiJiUtil;

/**
 * Created by Yangyl on 2017/2/11.
 */

public class ShowChartsAdapter extends FragmentStatePagerAdapter
        implements Handler.Callback{
    private static final int DATA_SET_CHANGED = 1;
    private static int TODAY_VIEW_FRAGMENT_NUMBER = 8;
    private List<ShowChartsFragment> mList = new ArrayList<>();
    private Handler mHandler;

    public ShowChartsAdapter(FragmentManager fm) {
        super(fm);
        mHandler = new Handler(Looper.getMainLooper(),this);
        for (int i = 0;i < TODAY_VIEW_FRAGMENT_NUMBER;i ++){
            mList.add(ShowChartsFragment.newInstance(i));
        }
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
//        Fragment fragment = ShowChartsFragment.newInstance(position);
//        mList.add((ShowChartsFragment) fragment);
        return mList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return TODAY_VIEW_FRAGMENT_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return YiJiApplication.getAppContext().getResources().getString(
                YiJiUtil.TODAY_VIEW_TITLE[position % TODAY_VIEW_FRAGMENT_NUMBER]);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void dataSet(){
//        for (ShowChartsFragment fragment : mList){
//            fragment.dataSet();
//        }
        mHandler.obtainMessage(DATA_SET_CHANGED).sendToTarget();
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean handled = false;
        switch (msg.what){
            case DATA_SET_CHANGED:
                notifyDataSetChanged();
                handled = true;
                break;

        }
        return handled;
    }
}
