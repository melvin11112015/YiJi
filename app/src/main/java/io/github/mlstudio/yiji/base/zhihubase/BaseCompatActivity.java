package io.github.mlstudio.yiji.base.zhihubase;

import io.github.mlstudio.yiji.base.BaseActivity;

//import com.umeng.analytics.MobclickAgent;

/**
 * Created by ice on 15/10/25.
 */
public abstract class BaseCompatActivity extends BaseActivity {

    protected String pass(String string) {
        if (string == null) string = "";
        return string;
    }

    protected String pass(Integer integer) {
        if (integer == null) return "";
        return "" + integer;
    }

    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

}
