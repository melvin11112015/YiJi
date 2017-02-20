package io.github.yylyingy.yiji.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.johnpersano.supertoasts.SuperToast;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.greenrobot.eventbus.EventBus;

import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.yylyingy.yiji.main.account.AccountFragment;
import io.github.yylyingy.yiji.main.showrecord.MainFragment;
import io.github.yylyingy.yiji.main.zhihu.ZhihuListFragment;
import io.github.yylyingy.yiji.tools.MessageEvent;
import io.github.yylyingy.yiji.tools.YiJiToast;
import io.github.yylyingy.yiji.tools.YiJiUtil;
import io.github.yylyingy.yiji.ui.widget.ForbidScrollViewPager;
import io.github.yylyingy.yiji.ui.widget.adapter.ForbidScrollViewpagerAdapter;

public class MainActivity extends BaseActivity implements MainFragment.OnBindToolbar{
    public static final String TAG = MainActivity.class.getSimpleName();
    private List<String> tabList ;//= Arrays.asList(TABLIST);
//    @BindView(R.id.materialViewPager)
//    MaterialViewPager materialViewPager;
    @BindView(R.id.forbidScrollViewpager)
    ForbidScrollViewPager                mForbidScrollViewPager;
    ForbidScrollViewpagerAdapter        mForbidScrollViewpagerAdapter;
    @BindView(R.id.exit)
    MaterialRippleLayout exit;
    @BindView(R.id.sync)
    MaterialRippleLayout sync;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    TestWeakReference mTestWeakReference;

//    ShowChartsAdapter mShowChartsAdapter;
//    MainFragment mFragment;
//    ZhihuListFragment mZhihuListFragment;
//    AccountFragment  mAccountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTestWeakReference = new TestWeakReference(this);
        setContentView(R.layout.activity_main);
        final String [] TABLIST = {
                getResources().getString(R.string.homePage),
                getResources().getString(R.string.zhihuDaily),
                getResources().getString(R.string.me)
        };
        tabList = Arrays.asList(TABLIST);
//        mFragment =          new MainFragment();
//        mZhihuListFragment = ZhihuListFragment.newInstance();
//        mAccountFragment   = new AccountFragment();
//        mFragmentList.add(mFragment);
//        mFragmentList.add(mZhihuListFragment);
//        mFragmentList.add(mAccountFragment);
        mForbidScrollViewpagerAdapter = new ForbidScrollViewpagerAdapter(
                mFragmentManager
        );
        mForbidScrollViewPager.setAdapter(mForbidScrollViewpagerAdapter);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentContainer,mZhihuListFragment)
//                .commit();


//        mToolbar = materialViewPager.getToolbar();
//        if (mToolbar != null){
//            setSupportActionBar(mToolbar);
//            final ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null){
//                actionBar.setDisplayHomeAsUpEnabled(true);
//                actionBar.setDisplayShowHomeEnabled(true);
//                actionBar.setDisplayShowTitleEnabled(true);
//                actionBar.setDisplayUseLogoEnabled(false);
//                actionBar.setHomeButtonEnabled(true);
//            }
//        }
//        setTitle("");
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
//                this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close
//        );
//        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        mShowChartsAdapter = new ShowChartsAdapter(getSupportFragmentManager());
//        materialViewPager.getViewPager().setOffscreenPageLimit(mShowChartsAdapter.getCount());
//        materialViewPager.getViewPager().setAdapter(mShowChartsAdapter);
//        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
//        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
//            @Override
//            public HeaderDesign getHeaderDesign(int page) {
//                return HeaderDesign.fromColorAndDrawable(
//                        YiJiUtil.GetTagColor(page - 2),
//                        YiJiUtil.GetTagDrawable(-3)
//                );
//            }
//        });
        if (mMagicIndicator != null) {
            mMagicIndicator.setBackgroundColor(Color.WHITE);
        }
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return tabList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                // load custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final MaterialIconView titleImg = (MaterialIconView) customLayout.findViewById(R.id.title_img);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);
//                titleImg.setImageResource(R.mipmap.icon);
                titleImg.setColor(getResources().getColor(R.color.toolbarColor));
                switch (index){
                    case 0:
                        titleImg.setIcon(MaterialDrawableBuilder.IconValue.HOME);
                        break;
                    case 1:
//                        titleImg.setIcon(MaterialDrawableBuilder.IconValue.CURSOR_DEFAULT_OUTLINE);
                        break;
                    case 2:
                        titleImg.setIcon(MaterialDrawableBuilder.IconValue.ACCOUNT);
                        break;
                    default:
                        titleImg.setIcon(MaterialDrawableBuilder.IconValue.HOME);
                        break;
                }
                titleText.setText(tabList.get(index));
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(getResources().getColor(R.color.toolbarColor));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.LTGRAY);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mForbidScrollViewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator,mForbidScrollViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.exit)
    protected void exitApp(){
//        ((YiJiApplication)getApplication()).exitApp();
        EventBus.getDefault().post(new MessageEvent("stop"));
    }

    @OnClick(R.id.sync)
    protected void sync(){
        if (mTestWeakReference.getActivity() != null){
            YiJiToast.getInstance().showToast("非null", R.color.friend_header);
        }else {
            YiJiToast.getInstance().showToast("null", R.color.friend_header);
        }
    }

    private static class TestWeakReference{
        WeakReference<MainActivity> mReference;
        private TestWeakReference(MainActivity activity){
            mReference = new WeakReference<>(activity);
        }
        public MainActivity getActivity(){
            return mReference.get();
        }
    }

    @Override
    public void bindToolbar(Toolbar toolbar) {
        if (toolbar != null){
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
                toolbar.setFitsSystemWindows(true);
                toolbar.setTitleMarginTop(100);
            }
        }
        setTitle("");
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
