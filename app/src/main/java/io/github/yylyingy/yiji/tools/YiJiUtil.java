package io.github.yylyingy.yiji.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import io.github.yylyingy.yiji.R;

/**
 * Created by Yangyl on 2017/1/10.
 */

public class YiJiUtil {
    public static Typeface typefaceLatoRegular = null;
    public static Typeface typefaceLatoHairline = null;
    public static Typeface typefaceLatoLight = null;

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
//        lastColor0 = "";
//        lastColor1 = "";
//        lastColor2 = "";
//
//        random = new Random();
//
//        MY_BLUE = ContextCompat.getColor(CoCoinApplication.getAppContext(), R.color.my_blue);
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
    public static String GetTagName(Context context,int tagId) {
        return context.getResources().getString(TAG_NAME[tagId + 2]);
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
