package io.github.mlstudio.yiji.sync;

import io.github.mlstudio.yiji.base.BaseIPresenter;

/**
 * Created by Yangyl on 2017/2/21.
 */

public interface ISyncPresenter extends BaseIPresenter{
    void bindView(ISyncView baseIPresenter);
    void detachView();
    void startSync();
    void stopSync();
}
