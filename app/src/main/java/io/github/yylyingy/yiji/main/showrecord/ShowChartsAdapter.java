package io.github.yylyingy.yiji.main.showrecord;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.tools.YiJiUtil;

/**
 * Created by Yangyl on 2017/2/11.
 */

public class ShowChartsAdapter extends FragmentStatePagerAdapter {
    private static int TODAY_VIEW_FRAGMENT_NUMBER = 8;
    public ShowChartsAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return ShowChartsFragment.newInstance(position);
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
}
