package io.github.mlstudio.yiji.sync;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import io.github.mlstudio.yiji.YiJiApplication;
import io.github.mlstudio.yiji.javabeans.YiJiRecord;
import io.github.mlstudio.yiji.tools.ThreadPoolTool;
import io.github.mlstudio.yiji.tools.db.DataManager;

/**
 * Created by Yangyl on 2017/2/21.
 */

public class SyncModel implements Runnable{

    private boolean isSyncOneItemSuccess = false;
    private boolean isSyncFinish = false;
    /**
     * Is stop sync
     */
    private boolean isStopSync      = false;
    private ISyncModel mISyncModel;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        isSyncOneItemSuccess = false;
        isStopSync = false;
        isSyncFinish = false;
        mISyncModel.startedSync();
        for (int i = 0; i< DataManager.RECORDS.size(); i ++){
            if (isStopSync){
                if (mISyncModel != null){
                    mISyncModel.interruptSync();
                }
                break;
            }
            if (i == (DataManager.RECORDS.size() - 1)){
                isStopSync = true;//wait to break loop
            }
            final YiJiRecord record = DataManager.RECORDS.get(i);
            if (record.getObjectId() == null){
                record.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e == null){
                            Logger.d("返回数据：" + objectId);
                            record.setObjectId(objectId);
                            DataManager.getsInstance(YiJiApplication.getAppContext())
                                    .updateRecordObjectId(record);
                            isSyncOneItemSuccess = true;
                        }else{
                            isStopSync = true;
                            isSyncOneItemSuccess = false;
                            if (mISyncModel != null){
                                mISyncModel.syncFail();
                            }
                            Logger.d(e);
                        }
                    }
                });
                //Wait upload one Item to cloud successful,or interrupt sync
                try {
                    while (!isSyncOneItemSuccess || !isStopSync) {
                        Thread.sleep(5);
                    }
                    isSyncOneItemSuccess = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ThreadPoolTool.exeTask(new DownloadDataFromBmob(SyncModel.this));

    }

    public void stopSync(){
        isStopSync = true;
    }
    public boolean sync(ISyncModel syncModel){
        mISyncModel = syncModel;
        ThreadPoolTool.exeTask(this);
        return isSyncFinish;
    }

    private static class DownloadDataFromBmob implements Runnable{
        boolean isDownloadOnePage = false;
        boolean isDownAllData = false;
        boolean isDownFailed = false;
        private SyncModel mSyncModel;
        private DownloadDataFromBmob(SyncModel syncModel){
            mSyncModel = syncModel;
        }
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            Logger.d("start download !");
            //Download data from cloud
            BmobQuery<YiJiRecord> query = new BmobQuery<>();
            final int dataSizeFromCloud = 50;
//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(dataSizeFromCloud);
            int i = 0;
            isDownloadOnePage = false;
            isDownAllData = false;
            isDownFailed = false;
            while (!isDownAllData) {
                //download fail,exit download thread
                if (isDownFailed){
                    break;
                }
                query.setSkip(dataSizeFromCloud * (i ++));
                //执行查询方法
//                query.findObjectsObservable()
                query.findObjects(new FindListener<YiJiRecord>() {
                    @Override
                    public void done(List<YiJiRecord> object, BmobException e) {
                        if (e == null) {
                            if (object.size() > 0) {
                                if (DataManager.RECORDS.size() == 0){
                                    for (YiJiRecord jiRecord : object){
                                        DataManager.getsInstance(YiJiApplication.getAppContext()).saveRecord(jiRecord);
                                    }
                                }else {
                                    for (YiJiRecord jiRecord : object) {
                                        Logger.d(jiRecord.getObjectId());

                                        for (int j = 0;j < DataManager.RECORDS.size();j ++){
                                            if (jiRecord.getObjectId().equals(DataManager.RECORDS.get(j).getObjectId())){
                                                break;
                                            }
                                            //All of RECORDS doesn't equals to yiRecord from cloud
                                            if ((j == (DataManager.RECORDS.size() - 1))
                                                    && !jiRecord.getObjectId().equals(DataManager.RECORDS.get(j).getObjectId())){
                                                DataManager.getsInstance(YiJiApplication.getAppContext()).saveRecord(jiRecord);
                                            }
                                        }

                                    }
                                }
                                if (object.size() < dataSizeFromCloud) {
                                    isDownAllData = true;
                                }
                            } else {
                                isDownAllData = true;
                            }
                            isDownloadOnePage = true;
                        }else {
                            isDownFailed = true;
                            Logger.d(e);
                        }
                    }
                });
                //wait for download one page.
                try {
                    while (!isDownloadOnePage && !isDownFailed){
                        Thread.sleep(20);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //clear flag
                isDownloadOnePage = false;
            }
            mSyncModel.isSyncFinish = true;
            if (mSyncModel.mISyncModel != null){
                mSyncModel.mISyncModel.syncSuccess();
            }
            mSyncModel = null;
        }


    }
}
