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
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.johnpersano.supertoasts.SuperToast;
import com.orhanobut.logger.Logger;

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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.base.BaseActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.yylyingy.yiji.javabeans.User;
import io.github.yylyingy.yiji.javabeans.YiJiRecord;
import io.github.yylyingy.yiji.main.account.AccountFragment;
import io.github.yylyingy.yiji.main.showrecord.MainFragment;
import io.github.yylyingy.yiji.main.zhihu.ZhihuListFragment;
import io.github.yylyingy.yiji.sync.ISyncView;
import io.github.yylyingy.yiji.sync.SyncPresenter;
import io.github.yylyingy.yiji.tools.MessageEvent;
import io.github.yylyingy.yiji.tools.ThreadPoolTool;
import io.github.yylyingy.yiji.tools.YiJiToast;
import io.github.yylyingy.yiji.tools.YiJiUtil;
import io.github.yylyingy.yiji.tools.db.DataManager;
import io.github.yylyingy.yiji.ui.widget.ForbidScrollViewPager;
import io.github.yylyingy.yiji.ui.widget.adapter.ForbidScrollViewpagerAdapter;

public class MainActivity extends BaseActivity implements MainFragment.OnBindToolbar,
        ISyncView {
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
    private SyncPresenter mSyncPresenter;

//    ShowChartsAdapter mShowChartsAdapter;
//    MainFragment mFragment;
//    ZhihuListFragment mZhihuListFragment;
//    AccountFragment  mAccountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String [] TABLIST = {
                getResources().getString(R.string.homePage),
                getResources().getString(R.string.zhihuDaily),
                getResources().getString(R.string.me)
        };
        mSyncPresenter = new SyncPresenter();
        mSyncPresenter.bindView(this);
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
        mSyncPresenter.detachView();
        mSyncPresenter = null;
    }

    @OnClick(R.id.exit)
    protected void exitApp(){
//        ((YiJiApplication)getApplication()).exitApp();
        EventBus.getDefault().post(new MessageEvent("stop"));
    }

//    boolean isDownloadOnePage = false;
//    boolean isDownAllData = false;
//    boolean isDownFailed = false;
    @OnClick(R.id.sync)
    public void sync(){
        YiJiToast.getInstance().showToast("toast",SuperToast.Background.RED);
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null){
            mForbidScrollViewPager.setCurrentItem(2);
            YiJiToast.getInstance().showToast("请登录",SuperToast.Background.RED);
            return;
        }
        mSyncPresenter.startSync();
//        ThreadPoolTool.exeTask(() ->{
//            Logger.d("start download !");
//            //Download data from cloud
//            BmobQuery<YiJiRecord> query = new BmobQuery<>();
//            final int dataSizeFromCloud = 6;
////返回50条数据，如果不加上这条语句，默认返回10条数据
//            query.setLimit(dataSizeFromCloud);
//            int i = 0;
//            isDownloadOnePage = false;
//            isDownAllData = false;
//            isDownFailed = false;
//            while (!isDownAllData) {
//                //download fail,exit download thread
//                if (isDownFailed){
//                    break;
//                }
//                query.setSkip(dataSizeFromCloud * (i ++));
//                //执行查询方法
////                query.findObjectsObservable()
//                query.findObjects(new FindListener<YiJiRecord>() {
//                    @Override
//                    public void done(List<YiJiRecord> object, BmobException e) {
//                        if (e == null) {
//                            if (object.size() > 0) {
//                                for (YiJiRecord jiRecord : object) {
//                                    Logger.d(jiRecord.getObjectId());
//                                }
//                                if (object.size() < dataSizeFromCloud) {
//                                    isDownAllData = true;
//                                }
//                            } else {
//                                isDownAllData = true;
//                            }
//                            isDownloadOnePage = true;
//                        }else {
//                            isDownFailed = true;
//                            Logger.d(e);
//                        }
//                    }
//                });
//                //wait for download one page.
//                try {
//                    while (!isDownloadOnePage && !isDownFailed){
//                        Thread.sleep(20);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //clear flag
//                isDownloadOnePage = false;
//            }
//        });


//        ThreadPoolTool.exeTask(() ->{
//                for (int i = 0; i< DataManager.RECORDS.size();i ++){
//                    final YiJiRecord record = DataManager.RECORDS.get(i);
//                    if (record.getObjectId() == null){
//                        record.save(new SaveListener<String>() {
//                            @Override
//                            public void done(String objectId, BmobException e) {
//                                if(e==null){
//                                    Logger.d("返回数据：" + objectId);
//                                    record.setObjectId(objectId);
//                                    DataManager.getsInstance(getApplicationContext())
//                                            .updateRecordObjectId(record);
//                                }else{
//                                    Logger.d(e);
//                                }
//                            }
//                        });
//                    }
//                }
//
//        });



    }

    /**
     * Start sync data local to cloud,and download old data which local don't exist.
     */
    @Override
    public void hasStartedSync() {
        Logger.d("has startedSynced");

    }

    /**
     * Sync fail.
     * Called back from another thread.
     */
    @Override
    public void syncFail() {
        Logger.d("同步失败");
    }

    /**
     * Sync succeed.
     * Called back from another thread.
     */
    @Override
    public void syncSuccess() {
        Logger.d("同步成功");

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
