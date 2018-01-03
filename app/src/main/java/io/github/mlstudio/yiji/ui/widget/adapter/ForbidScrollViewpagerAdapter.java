package io.github.mlstudio.yiji.ui.widget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.mlstudio.yiji.main.account.AccountFragment;
import io.github.mlstudio.yiji.main.showrecord.MainFragment;
import io.github.mlstudio.yiji.main.zhihu.ZhihuListFragment;

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
