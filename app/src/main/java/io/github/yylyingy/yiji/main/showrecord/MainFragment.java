package io.github.yylyingy.yiji.main.showrecord;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.base.BaseFragment;
import io.github.yylyingy.yiji.tools.YiJiUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    protected Toolbar mToolbar;
    @BindView(R.id.materialViewPager)
    MaterialViewPager materialViewPager;
    ShowChartsAdapter mShowChartsAdapter;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);
        mToolbar = materialViewPager.getToolbar();
        mShowChartsAdapter = new ShowChartsAdapter(getFragmentManager());
        materialViewPager.getViewPager().setOffscreenPageLimit(mShowChartsAdapter.getCount());
        materialViewPager.getViewPager().setAdapter(mShowChartsAdapter);
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorAndDrawable(
                        YiJiUtil.GetTagColor(page - 2),
                        YiJiUtil.GetTagDrawable(-3)
                );
            }
        });
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }
}
