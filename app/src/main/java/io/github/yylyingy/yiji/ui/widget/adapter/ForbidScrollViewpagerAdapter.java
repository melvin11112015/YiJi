package io.github.yylyingy.yiji.ui.widget.adapter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.github.yylyingy.yiji.main.account.AccountFragment;
import io.github.yylyingy.yiji.main.showrecord.MainFragment;
import io.github.yylyingy.yiji.main.zhihu.ZhihuListFragment;

/**
 * Created by Yangyl on 2017/2/17.
 */

public class ForbidScrollViewpagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> mList = new ArrayList<>();
    public ForbidScrollViewpagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        this.mList.add(new MainFragment());
        this.mList.add(ZhihuListFragment.newInstance());
        this.mList.add(new AccountFragment());
    }
    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }


    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    public void notifyDataChanged(){
        ((MainFragment)mList.get(0)).dataSet();
    }
}
