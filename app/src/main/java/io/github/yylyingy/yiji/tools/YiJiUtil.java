package io.github.yylyingy.yiji.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.github.johnpersano.supertoasts.SuperToast;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import io.github.yylyingy.yiji.R;
import io.github.yylyingy.yiji.YiJiApplication;

/**
 * Created by Yangyl on 2017/1/10.
 */

public class YiJiUtil {
    public static Typeface typefaceLatoRegular = null;
    public static Typeface typefaceLatoHairline = null;
    public static Typeface typefaceLatoLight = null;
    public static SuperToast.Animations TOAST_ANIMATION = SuperToast.Animations.FLYIN;
    public static boolean WEEK_START_WITH_SUNDAY = false;

    private static String lastColor0, lastColor1, lastColor2;
    public static int TODAY_VIEW_TITLE[] = {
            R.string.today_view_today,
            R.string.today_view_yesterday,
            R.string.today_view_this_week,
            R.string.today_view_last_week,
            R.string.today_view_this_month,
            R.string.today_view_last_month,
            R.string.today_view_this_year,
            R.string.today_view_last_year
    };

    public static int[] TODAY_VIEW_EMPTY_TIP = {
            R.string.today_empty,
            R.string.yesterday_empty,
            R.string.this_week_empty,
            R.string.last_week_empty,
            R.string.this_month_empty,
            R.string.last_month_empty,
            R.string.this_year_empty,
            R.string.last_year_empty
    };

    public static int[] MONTHS_SHORT = {0,
            R.string.january_short,
            R.string.february_short,
            R.string.march_short,
            R.string.april_short,
            R.string.may_short,
            R.string.june_short,
            R.string.july_short,
            R.string.august_short,
            R.string.september_short,
            R.string.october_short,
            R.string.november_short,
            R.string.december_short
    };

    public static int[] TAG_COLOR = {
            R.color.my_blue,
            R.color.sum_header_pie,
            R.color.sum_header_histogram,
            R.color.meal_header,
            R.color.closet_header,
            R.color.home_header,
            R.color.traffic_header,
            R.color.vehicle_maintenance_header,
            R.color.book_header,
            R.color.hobby_header,
            R.color.internet_header,
            R.color.friend_header,
            R.color.education_header,
            R.color.entertainment_header,
            R.color.medical_header,
            R.color.insurance_header,
            R.color.donation_header,
            R.color.sport_header,
            R.color.snack_header,
            R.color.music_header,
            R.color.fund_header,
            R.color.drink_header,
            R.color.fruit_header,
            R.color.film_header,
            R.color.baby_header,
            R.color.partner_header,
            R.color.housing_loan_header,
            R.color.pet_header,
            R.color.telephone_bill_header,
            R.color.travel_header,
            R.color.lunch_header,
            R.color.breakfast_header,
            R.color.midnight_snack_header
    };

    private static String[] Colors = {"#F44336",
            "#E91E63",
            "#9C27B0",
            "#673AB7",
            "#3F51B5",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"
    };

    public static int[] WEEKDAY_SHORT_START_ON_SUNDAY = {
            0,
            R.string.sunday_short,
            R.string.monday_short,
            R.string.tuesday_short,
            R.string.wednesday_short,
            R.string.thursday_short,
            R.string.friday_short,
            R.string.saturday_short
    };

    public static int[] WEEKDAY_SHORT_START_ON_MONDAY = {
            0,
            R.string.monday_short,
            R.string.tuesday_short,
            R.string.wednesday_short,
            R.string.thursday_short,
            R.string.friday_short,
            R.string.saturday_short,
            R.string.sunday_short
    };
    public static void init(Context context) {

        typefaceLatoRegular = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Regular.ttf");
        typefaceLatoHairline = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Hairline.ttf");
        typefaceLatoLight = Typeface.createFromAsset(
                context.getAssets(), "fonts/LatoLatin-Light.ttf");
//        relativeSizeSpan = new RelativeSizeSpan(2f);
//        redForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ff5252"));
//        greenForegroundSpan = new ForegroundColorSpan(Color.parseColor("#4ca550"));
//        whiteForegroundSpan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
//
        lastColor0 = "";
        lastColor1 = "";
        lastColor2 = "";

//        random = new Random();
//
//        MY_BLUE = ContextCompat.getColor(CoCoinApplication.getAppContext(), R.color.my_blue);
    }

    public static String GetAxisDateName(int type, int position) {
        switch (type) {
            case Calendar.HOUR_OF_DAY:
                return position + "";
            case Calendar.DAY_OF_WEEK:
                if (WEEK_START_WITH_SUNDAY) return YiJiApplication.getAppContext().getResources()
                        .getString(WEEKDAY_SHORT_START_ON_SUNDAY[position + 1]);
                else return YiJiApplication.getAppContext().getResources()
                        .getString(WEEKDAY_SHORT_START_ON_MONDAY[position + 1]);
            case Calendar.DAY_OF_MONTH:
                return (position + 1) + "";
            case Calendar.MONTH:
                return YiJiApplication.getAppContext().getResources()
                        .getString(MONTHS_SHORT[position + 1]);
            default:
                return "";
        }
    }
    public static int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        while (Colors[p].equals(lastColor0)
                || Colors[p].equals(lastColor1)
                || Colors[p].equals(lastColor2)) {
            p = random.nextInt(Colors.length);
        }
        lastColor0 = lastColor1;
        lastColor1 = lastColor2;
        lastColor2 = Colors[p];
        return Color.parseColor(Colors[p]);
    }
    public static int GetTagColorResource(int tag) {
        return TAG_COLOR[tag + 2];
    }

    public static int GetTodayViewEmptyTip(int fragmentPosition) {
        return TODAY_VIEW_EMPTY_TIP[fragmentPosition];
    }
    public static Typeface GetTypeface() {
        if (typefaceLatoLight == null) init(YiJiApplication.getAppContext());
        if ("en".equals(Locale.getDefault().getLanguage()))
            return typefaceLatoLight;
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return Typeface.DEFAULT;
        return typefaceLatoLight;
    }
    public static String GetInMoney(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "¥" + money;
        else
            return "$" + money + " ";
    }
    public static String GetMonthShort(int i) {
        return YiJiApplication.getAppContext().getResources().getString(MONTHS_SHORT[i]);
    }

    public static String GetLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String GetSpendString(int money) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "消费 ¥" + money;
        else
            return "Spend $" + money + " ";
    }
    public static int[] TAG_ICON = {
            R.drawable.sum_pie_icon,
            R.drawable.sum_histogram_icon,
            R.drawable.meal_icon,
            R.drawable.closet_icon,
            R.drawable.home_icon,
            R.drawable.traffic_icon,
            R.drawable.vehicle_maintenance_icon,
            R.drawable.book_icon,
            R.drawable.hobby_icon,
            R.drawable.internet_icon,
            R.drawable.friend_icon,
            R.drawable.education_icon,
            R.drawable.entertainment_icon,
            R.drawable.medical_icon,
            R.drawable.insurance_icon,
            R.drawable.donation_icon,
            R.drawable.sport_icon,
            R.drawable.snack_icon,
            R.drawable.music_icon,
            R.drawable.fund_icon,
            R.drawable.drink_icon,
            R.drawable.fruit_icon,
            R.drawable.film_icon,
            R.drawable.baby_icon,
            R.drawable.partner_icon,
            R.drawable.housing_loan_icon,
            R.drawable.pet_icon,
            R.drawable.telephone_bill_icon,
            R.drawable.travel_icon,
            R.drawable.lunch_icon,
            R.drawable.breakfast_icon,
            R.drawable.midnight_snack_icon
    };
    public static int[] TAG_NAME = {
            R.string.tag_sum_pie,
            R.string.tag_sum_histogram,
            R.string.tag_meal,
            R.string.tag_closet,
            R.string.tag_home,
            R.string.tag_traffic,
            R.string.tag_vehicle_maintenance,
            R.string.tag_book,
            R.string.tag_hobby,
            R.string.tag_internet,
            R.string.tag_friend,
            R.string.tag_education,
            R.string.tag_entertainment,
            R.string.tag_medical,
            R.string.tag_insurance,
            R.string.tag_donation,
            R.string.tag_sport,
            R.string.tag_snack,
            R.string.tag_music,
            R.string.tag_fund,
            R.string.tag_drink,
            R.string.tag_fruit,
            R.string.tag_film,
            R.string.tag_baby,
            R.string.tag_partner,
            R.string.tag_housing_loan,
            R.string.tag_pet,
            R.string.tag_telephone_bill,
            R.string.tag_travel,
            R.string.tag_lunch,
            R.string.tag_breakfast,
            R.string.tag_midnight_snack
    };

    public static int[] TAG_SNACK = {
            R.drawable.snackbar_shape_undo,
            R.drawable.snackbar_shape_sum_pie,
            R.drawable.snackbar_shape_sum_histogram,
            R.drawable.snackbar_shape_meal,
            R.drawable.snackbar_shape_closet,
            R.drawable.snackbar_shape_home,
            R.drawable.snackbar_shape_traffic,
            R.drawable.snackbar_shape_vehicle_maintenance,
            R.drawable.snackbar_shape_book,
            R.drawable.snackbar_shape_hobby,
            R.drawable.snackbar_shape_internet,
            R.drawable.snackbar_shape_friend,
            R.drawable.snackbar_shape_education,
            R.drawable.snackbar_shape_entertainment,
            R.drawable.snackbar_shape_medical,
            R.drawable.snackbar_shape_insurance,
            R.drawable.snackbar_shape_donation,
            R.drawable.snackbar_shape_sport,
            R.drawable.snackbar_shape_snack,
            R.drawable.snackbar_shape_music,
            R.drawable.snackbar_shape_fund,
            R.drawable.snackbar_shape_drink,
            R.drawable.snackbar_shape_fruit,
            R.drawable.snackbar_shape_film,
            R.drawable.snackbar_shape_baby,
            R.drawable.snackbar_shape_partner,
            R.drawable.snackbar_shape_housing_loan,
            R.drawable.snackbar_shape_pet,
            R.drawable.snackbar_shape_telephone_bill,
            R.drawable.snackbar_shape_travel,
            R.drawable.snackbar_shape_lunch,
            R.drawable.snackbar_shape_breakfast,
            R.drawable.snackbar_shape_midnight_snack
    };

    public static int[] WEEKDAY_START_ON_MONDAY = {
            0,
            R.string.monday_short,
            R.string.tuesday_short,
            R.string.wednesday_short,
            R.string.thursday_short,
            R.string.friday_short,
            R.string.saturday_short,
            R.string.sunday_short
    };

    public static int[] WEEKDAY_START_ON_SUNDAY = {
            0,
            R.string.sunday_short,
            R.string.monday_short,
            R.string.tuesday_short,
            R.string.wednesday_short,
            R.string.thursday_short,
            R.string.friday_short,
            R.string.saturday_short
    };

    public static String GetWhetherBlank() {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "";
        else
            return " ";
    }

    public static String GetWeekDay(int position) {
        if (WEEK_START_WITH_SUNDAY) return YiJiApplication.getAppContext().getResources()
                .getString(WEEKDAY_START_ON_SUNDAY[position + 1]);
        else return YiJiApplication.getAppContext().getResources()
                .getString(WEEKDAY_START_ON_MONDAY[position + 1]);
    }
    public static String GetTagName(Context context,int tagId) {
        return context.getResources().getString(TAG_NAME[tagId + 2]);
    }
    public static String GetTagName(int tagId) {
        return YiJiApplication.getAppContext().getResources().getString(TAG_NAME[tagId + 2]);
    }

    public static String GetWhetherFuck() {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return "日";
        else
            return "";
    }

    public static String GetCalendarString(Context context, Calendar calendar) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        return (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
    }

    public static String GetCalendarStringRecordCheckDialog(Context context, Calendar calendar) {
        if ("en".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
        }
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + calendar.get(Calendar.DAY_OF_MONTH) + GetWhetherFuck() + " " + calendar.get(Calendar.YEAR);
        }
        return context.getResources().getString(MONTHS_SHORT[calendar.get(Calendar.MONTH) + 1]) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
    }

    public static Drawable GetTagIconDrawable(int tagId) {
        return ContextCompat.getDrawable(
                YiJiApplication.getAppContext(), TAG_ICON[tagId + 2]);
    }
    public static String GetPercentString(double percent) {
        if ("zh".equals(Locale.getDefault().getLanguage()))
            return " (占" + String.format("%.2f", percent) + "%)";
        else
            return " (takes " + String.format("%.2f", percent) + "%)";
    }

    public static int GetSnackBarBackground(int tagId) {
        return TAG_SNACK[tagId + 3];
    }


    public static Calendar GetTodayLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetTodayRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetYesterdayLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetYesterdayRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (YiJiUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek]);
        }
        return calendar;
    }

    public static Calendar GetThisWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetLastWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (YiJiUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] - 7);
        }
        return calendar;
    }

    public static Calendar GetLastWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetNextWeekLeftRange(Calendar today) {
        int nowDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        if (YiJiUtil.WEEK_START_WITH_SUNDAY) {
            int[] diff = new int[]{0, 0, -1, -2, -3, -4, -5, -6};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        } else {
            int[] diff = new int[]{0, -6, 0, -1, -2, -3, -4, -5};
            calendar.add(Calendar.DATE, diff[nowDayOfWeek] + 7);
        }
        return calendar;
    }

    public static Calendar GetNextWeekRightRange(Calendar today) {
        Calendar calendar = (Calendar) GetNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 7);
        return calendar;
    }

    public static Calendar GetNextWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetNextWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetThisWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetThisWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetLastWeekRightShownRange(Calendar today) {
        Calendar calendar = (Calendar) GetLastWeekLeftRange(today).clone();
        calendar.add(Calendar.DATE, 6);
        return calendar;
    }

    public static Calendar GetThisMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastMonthLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastMonthRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetThisYearRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastYearLeftRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar GetLastYearRightRange(Calendar today) {
        Calendar calendar = (Calendar)today.clone();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, 0);
        return calendar;
    }
    public static int GetTagIcon(int tagId) {
        return TAG_ICON[tagId + 2];
    }

    public static <K, V extends Comparable<V>> Map<K, V> SortTreeMapByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k1).compareTo(map.get(k2));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        TreeMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
