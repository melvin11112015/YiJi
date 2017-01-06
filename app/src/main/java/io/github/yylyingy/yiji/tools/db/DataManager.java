package io.github.yylyingy.yiji.tools.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

/**
 * Created by Yangyl on 2016/12/21.
 */

public class DataManager {
    private DB mDB;
    private volatile static DataManager sInstance = null;
    private static DataManager checkSInstanceIsInit = null;
    private DataManager(Context context) throws IOException {
        mDB = DB.getInstance(context);
        SharedPreferences preferences =
                context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        if (preferences.getBoolean("FIRST_TIME", true)) {
//            createTags();
            SharedPreferences.Editor editor =
                    context.getSharedPreferences("Values", Context.MODE_PRIVATE).edit();
            editor.putBoolean("FIRST_TIME", false);
            editor.apply();
        }
    }
    public static DataManager getsInstance(Context context) throws IOException {
        if (checkSInstanceIsInit == null){
            initInstance(context);
        }
        return sInstance;
    }

    private synchronized static void initInstance(Context context) throws IOException {
        if (checkSInstanceIsInit == null){
            sInstance = new DataManager(context);
            checkSInstanceIsInit = sInstance;
        }
    }
}
