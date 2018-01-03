package io.github.mlstudio.yiji.main.showrecord;


import android.content.Context;
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
import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.base.BaseFragment;
import io.github.mlstudio.yiji.tools.YiJiUtil;
import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment{
    protected Toolbar mToolbar;
    @BindView(R.id.materialViewPager)
    MaterialViewPager materialViewPager;
    ShowChartsAdapter mShowChartsAdapter;
    OnBindToolbar mBindToolbar;
    public MainFragment(){
        PhotoView a;

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
        mShowChartsAdapter = new ShowChartsAdapter(getChildFragmentManager());
        materialViewPager.getViewPager().setOffscreenPageLimit(mShowChartsAdapter.getCount());
        materialViewPager.getViewPager().setAdapter(mShowChartsAdapter);
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
        materialViewPager.setMaterialViewPagerListener(page -> HeaderDesign.fromColorAndDrawable(
                YiJiUtil.GetTagColor(page - 2),
                YiJiUtil.GetTagDrawable(-3)
        ));
        if (mContext instanceof OnBindToolbar){
            ((OnBindToolbar)mContext).bindToolbar(mToolbar);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    public void dataSet(){
        mShowChartsAdapter.dataSet();
    }

    public interface OnBindToolbar {
        void bindToolbar(Toolbar toolbar);
    }

}
