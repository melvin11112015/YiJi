package io.github.yylyingy.yiji.tools;

import android.graphics.Color;

import com.github.johnpersano.supertoasts.SuperToast;

import io.github.yylyingy.yiji.YiJiApplication;

/**
 * Created by Weiping on 2015/11/30.
 */
public class YiJiToast {
    private static YiJiToast ourInstance = new YiJiToast();

    public static YiJiToast getInstance() {
        return ourInstance;
    }

    private YiJiToast() {
    }

    public void showToast(int text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(YiJiApplication.getAppContext());
        superToast.setAnimations(YiJiUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(YiJiApplication.getAppContext().getResources().getString(text));
        superToast.setBackground(color);
        superToast.getTextView().setTypeface(YiJiUtil.typefaceLatoLight);
        superToast.show();
    }

    public void showToast(String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(YiJiApplication.getAppContext());
        superToast.setAnimations(YiJiUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(color);
        superToast.getTextView().setTypeface(YiJiUtil.typefaceLatoLight);
        superToast.show();
    }
}
