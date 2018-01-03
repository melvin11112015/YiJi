package io.github.mlstudio.yiji.base.zhihubase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.YiJiApplication;
import io.github.mlstudio.yiji.base.BaseFragment;
import io.github.mlstudio.yiji.tools.Constants;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;
import top.wefor.now.ui.widget.LoadMoreRecyclerView;

/**
 * Created by ice on 15/10/28.
 */
public abstract class BaseListFragment<T> extends BaseFragment implements LoadMoreRecyclerView.OnLoadMoreListener {
    protected final int PAGE_SIZE = Constants.LIST_PAGE_SIZE;
    protected LoadMoreRecyclerView mRecyclerView;
    protected int mPage = Constants.LIST_FIRST_PAGE;
    protected RealmConfiguration mRealmConfig;
    protected Realm mRealm;
    protected List<T> mList = new ArrayList<>();


    public abstract void getData();

    public abstract void showList();

    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealmConfig = new RealmConfiguration.Builder(YiJiApplication.getAppContext().getApplicationContext()).build();

        try {
            mRealm = Realm.getInstance(mRealmConfig);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(mRealmConfig);
                mRealm = Realm.getInstance(mRealmConfig);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.mOnLoadMoreListener = this;
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null)
            mRealm.close();
    }

    @Override
    public void loadMore() {
        //如果页数*分页数目大于列表长度，那么只能是列表正在加载或数据库数据见底，两种情况都不需要继续加载。
        if ((mPage - 1) * Constants.LIST_PAGE_SIZE > mList.size())
            return;
        showList();
    }

}
