package io.github.mlstudio.yiji.sync;

import io.github.mlstudio.yiji.base.BaseIView;

/**
 * Created by Yangyl on 2017/2/21.
 */

public interface ISyncView extends BaseIView {
    /**
     * Start sync data local to cloud,and download old data which local don't exist.
     * Called back from another thread.
     */
    void hasStartedSync();

    /**
     * Sync fail.
     * Called back from another thread.
     */
    void syncFail();

    /**
     * Sync succeed.
     * Called back from another thread.
     */
    void syncSuccess();
}
