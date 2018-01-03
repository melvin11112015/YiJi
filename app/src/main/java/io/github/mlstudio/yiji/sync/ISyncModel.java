package io.github.mlstudio.yiji.sync;

/**
 * Created by Yangyl on 2017/2/21.
 */

public interface ISyncModel {

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

    /**
     * sync has interrupted
     */
    void interruptSync();

    /**
     *
     */
    void startedSync();
}
