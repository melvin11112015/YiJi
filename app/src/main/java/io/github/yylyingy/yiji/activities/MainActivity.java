package io.github.yylyingy.yiji.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.yylyingy.yiji.main.showrecord.MainFragment;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected Toolbar mToolbar;
    private List<String> tabList ;//= Arrays.asList(TABLIST);
//    @BindView(R.id.materialViewPager)
//    MaterialViewPager materialViewPager;
    @BindView(R.id.exit)
    MaterialRippleLayout exit;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

//    ShowChartsAdapter mShowChartsAdapter;
    MainFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String [] TABLIST = {
                getResources().getString(R.string.homePage),
                getResources().getString(R.string.zhihuDaily),
                getResources().getString(R.string.me)
        };
        tabList = Arrays.asList(TABLIST);
        mFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer,mFragment)
                .commit();
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
//                        mViewPager.setCurrentItem(index);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        mToolbar = mFragment.getToolbar();
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        setTitle("");
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mToolbar = null;
    }

    @OnClick(R.id.exit)
    protected void exitApp(){
        ((YiJiApplication)getApplication()).exitApp();
    }
}
