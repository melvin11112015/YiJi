package io.github.yylyingy.yiji.sync;

import com.orhanobut.logger.Logger;

import io.github.yylyingy.yiji.base.BaseIPresenter;

/**
 * Created by Yangyl on 2017/2/21.
 */

public class SyncPresenter implements ISyncPresenter{
    private ISyncView mView;
    private SyncModel mSyncModel;
    private boolean isSyncing = false;
    public SyncPresenter(){
        mSyncModel = new SyncModel();

    }
    @Override
    public void bindView(ISyncView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mSyncModel.stopSync();
        mSyncModel = null;
    }

    @Override
    public void startSync() {
        if (!isSyncing){
            mSyncModel.sync(new ISyncModel() {
                @Override
                public void syncFail() {
                    mView.syncFail();
                    isSyncing = false;
                }

                @Override
                public void syncSuccess() {
                    mView.syncSuccess();
                    isSyncing = false;
                }

                @Override
                public void interruptSync() {
                    mView.syncFail();
                    isSyncing = false;
                }

                /**
                 *
                 */
                @Override
                public void startedSync() {
                    mView.hasStartedSync();
                    isSyncing = true;
                }
            });
        }
    }
}
