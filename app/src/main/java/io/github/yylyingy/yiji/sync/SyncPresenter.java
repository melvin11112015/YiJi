package io.github.yylyingy.yiji.sync;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

import io.github.yylyingy.yiji.base.BaseIPresenter;
import io.github.yylyingy.yiji.tools.db.DataManager;

/**
 * Created by Yangyl on 2017/2/21.
 */

public class SyncPresenter implements ISyncPresenter,Handler.Callback{
    public static final int SYNC_FAIL = 1;
    public static final int SYNC_SUCCESS = 2;
    public static final int HAS_STARTED_SYNC = 3;
    private ISyncView mView;
    private SyncModel mSyncModel;
    private boolean isSyncing = false;
    private Handler mHandler;
    public SyncPresenter(){
        mSyncModel = new SyncModel();
        mHandler = new Handler(Looper.getMainLooper(),this);

    }
    @Override
    public void bindView(ISyncView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSyncModel != null){
            mSyncModel.stopSync();
            mSyncModel = null;
        }
    }

    @Override
    public void stopSync() {
        if (mSyncModel != null){
            mSyncModel.stopSync();
        }
    }

    @Override
    public void startSync() {
        if (!isSyncing){
            if (mSyncModel == null){
                mSyncModel = new SyncModel();
            }
            mSyncModel.sync(new ISyncModel() {
                @Override
                public void syncFail() {
                    mHandler.obtainMessage(SYNC_FAIL).sendToTarget();
                    isSyncing = false;
                }

                @Override
                public void syncSuccess() {
                    mHandler.obtainMessage(SYNC_SUCCESS).sendToTarget();
                    isSyncing = false;
                }

                @Override
                public void interruptSync() {
                    mHandler.obtainMessage(SYNC_FAIL).sendToTarget();
                    isSyncing = false;
                }

                /**
                 *
                 */
                @Override
                public void startedSync() {
                    mHandler.obtainMessage(HAS_STARTED_SYNC).sendToTarget();
                    isSyncing = true;
                }
            });
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean handled ;
        switch (msg.what){
            case SYNC_FAIL:
                if(mView != null){
                    mView.syncFail();
                }
                isSyncing = false;
                handled = true;
                break;
            case SYNC_SUCCESS:
                if(mView != null){
                    DataManager.sortRecords();
                    mView.syncSuccess();
                }
                handled = true;
                break;
            case HAS_STARTED_SYNC:
                if(mView != null){
                    mView.hasStartedSync();
                }
                handled = true;
                break;
            default:
                handled = false;
                break;
        }
        return handled;
    }
}
