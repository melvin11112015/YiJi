package io.github.yylyingy.yiji.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.addrecord.AddRecordFragment;
import io.github.yylyingy.yiji.base.BaseActivity;
import io.github.yylyingy.yiji.ui.TagChooseFragment;


public class AddRecordActivity extends BaseActivity implements TagChooseFragment.OnTagItemSelectedListener {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    AddRecordFragment   mRecordFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        setSupportActionBar(mToolbar);
        if (mToolbar != null){
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        DrawerArrowDrawable arrowDrawable = new DrawerArrowDrawable(this);
        arrowDrawable.setColor(getResources().getColor(R.color.colorWhite));
        mToolbar.setNavigationIcon(arrowDrawable);
        mRecordFragment = new AddRecordFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.container,mRecordFragment)
                .show(mRecordFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(AddRecordActivity.this,MainActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initButterKnife() {
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void onTagItemPicked(int position) {
        mRecordFragment.editMoneyFragment.setTag(mRecordFragment.tagViewPager.getCurrentItem() * 8 + position + 2);
    }

    @Override
    public void onAnimationStart(int id) {

    }
}