package io.github.yylyingy.yiji.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.yangyl.encryptdecrypt.secrect.EncryptDecryptTool;
import com.yangyl.encryptdecrypt.secrect.base64.BASE64Encoder;

import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.base.BaseActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.yylyingy.yiji.showrecord.ShowChartsAdapter;
import io.github.yylyingy.yiji.tools.YiJiUtil;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected Toolbar mToolbar;
    @BindView(R.id.materialViewPager)
    MaterialViewPager materialViewPager;
    @BindView(R.id.exit)
    MaterialRippleLayout exit;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    ShowChartsAdapter mShowChartsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = materialViewPager.getToolbar();
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
        mShowChartsAdapter = new ShowChartsAdapter(getSupportFragmentManager());
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

    @OnClick(R.id.exit)
    protected void exitApp(){
        ((YiJiApplication)getApplication()).exitApp();
    }
}
