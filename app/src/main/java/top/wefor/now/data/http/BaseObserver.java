package top.wefor.now.data.http;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import io.github.yylyingy.yiji.YiJiApplication;
import io.github.yylyingy.yiji.base.zhihubase.BaseResult;
import rx.Observer;

/**
 * 网络请求返回需要的模型
 * Created by ice on 3/3/16.
 */
public abstract class BaseObserver<T extends BaseResult> implements Observer<T> {

    protected abstract void onSucceed(T result);

    protected void onFailed(String msg) {
        if (!TextUtils.isEmpty(msg))
            YiJiApplication.showToast(msg);
    }

    @Override
    public void onCompleted() {
        Logger.t("xyz").i("xyz", "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.i("xyz", "onError");
        onFailed(null);
    }

    @Override
    public void onNext(T result) {
        if (result != null)
            onSucceed(result);
        else
            onFailed(null);

    }
}
