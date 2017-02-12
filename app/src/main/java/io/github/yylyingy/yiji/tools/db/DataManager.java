package io.github.yylyingy.yiji.tools.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import io.github.yylyingy.yiji.javabeans.Tag;
import io.github.yylyingy.yiji.javabeans.YiJiRecord;

/**
 * Created by Yangyl on 2016/12/21.
 */

public class DataManager {
    private DB db;
    private volatile static DataManager sInstance = null;
    private static DataManager checkSInstanceIsInit = null;
    public static List<Tag>TAGS ;
    public static List<YiJiRecord> RECORDS;
    private DataManager(Context context) throws IOException {
        db = DB.getInstance(context);
        TAGS = new LinkedList<>();
        RECORDS = new LinkedList<>();
        SharedPreferences preferences =
                context.getSharedPreferences("Values", Context.MODE_PRIVATE);
        if (preferences.getBoolean("FIRST_TIME", true)) {
            createTags();
            SharedPreferences.Editor editor =
                    context.getSharedPreferences("Values", Context.MODE_PRIVATE).edit();
            editor.putBoolean("FIRST_TIME", false);
            editor.apply();
        }
        initTAGS();
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

    private void initTAGS(){
        db.initTags();
    }

    private void createTags(){
        db.saveTag(new Tag(-1, "Meal",                -1));
        db.saveTag(new Tag(-1, "Clothing & Footwear", 1));
        db.saveTag(new Tag(-1, "Home",                2));
        db.saveTag(new Tag(-1, "Traffic",             3));
        db.saveTag(new Tag(-1, "Vehicle Maintenance", 4));
        db.saveTag(new Tag(-1, "Book",                5));
        db.saveTag(new Tag(-1, "Hobby",               6));
        db.saveTag(new Tag(-1, "Internet",            7));
        db.saveTag(new Tag(-1, "Friend",              8));
        db.saveTag(new Tag(-1, "Education",           9));
        db.saveTag(new Tag(-1, "Entertainment",      10));
        db.saveTag(new Tag(-1, "Medical",            11));
        db.saveTag(new Tag(-1, "Insurance",          12));
        db.saveTag(new Tag(-1, "Donation",           13));
        db.saveTag(new Tag(-1, "Sport",              14));
        db.saveTag(new Tag(-1, "Snack",              15));
        db.saveTag(new Tag(-1, "Music",              16));
        db.saveTag(new Tag(-1, "Fund",               17));
        db.saveTag(new Tag(-1, "Drink",              18));
        db.saveTag(new Tag(-1, "Fruit",              19));
        db.saveTag(new Tag(-1, "Film",               20));
        db.saveTag(new Tag(-1, "Baby",               21));
        db.saveTag(new Tag(-1, "Partner",            22));
        db.saveTag(new Tag(-1, "Housing Loan",       23));
        db.saveTag(new Tag(-1, "Pet",                24));
        db.saveTag(new Tag(-1, "Telephone Bill",     25));
        db.saveTag(new Tag(-1, "Travel",             26));
        db.saveTag(new Tag(-1, "Lunch",              -2));
        db.saveTag(new Tag(-1, "Breakfast",          -3));
        db.saveTag(new Tag(-1, "MidnightSnack",      0));
    }

    public void saveRecord(YiJiRecord record){
        RECORDS.add(record);
        db.saveRecord(record);
    }


}
