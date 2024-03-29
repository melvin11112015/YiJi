package io.github.mlstudio.yiji.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class TagChooseFragmentAdapter extends FragmentPagerAdapter {

    private int count;

    public TagChooseFragmentAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return TagChooseFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return count;
    }
}
