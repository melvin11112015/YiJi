package io.github.yylyingy.yiji.tools.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import io.github.yylyingy.yiji.BuildConfig;
import io.github.yylyingy.yiji.javabeans.Tag;

/**
 * Created by Yangyl on 2016/12/19.
 */

public class DB {
    public static final String DB_NAME_STRING = "YiJi Database.db";
    public static final String RECORD_DB_NAME_STRING = "RECORD";
    public static final String TAG_DB_NAME_STRING = "TAG";

    private static final String QUERY_TAG = "SELECT * FROM TAG;";
    public static final int VERSION = 1;

    private static volatile DB db  = null;
    private static DB checkDBIsInit = null;
    private SQLiteDatabase mSQLiteDatabase;
    private DBHelper        mDBHelper;
    private DB(Context context)throws IOException{
        mDBHelper = new DBHelper(context,DB_NAME_STRING,null,VERSION);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }
    public static DB getInstance(Context context) throws IOException {
        if (checkDBIsInit == null){
            initInstance(context);
        }
        return db;
    }
    private synchronized static void initInstance(Context context) throws IOException {
        if (checkDBIsInit == null){
            db = new DB(context);
            checkDBIsInit = db;
        }
    }

    public void initTags(){
        Cursor cursor = mSQLiteDatabase.rawQuery(QUERY_TAG,null);
        while (cursor.moveToNext()){
            Tag tag = new Tag();
            tag.setId(cursor.getInt(cursor.getColumnIndex("ID")) - 1);
            tag.setName(cursor.getString(1));
            tag.setWeight(cursor.getInt(2));
            DataManager.TAGS.add(tag);
        }
        cursor.close();
    }

    public void saveTag(Tag tag){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TAG(" + "NAME,WEIGHT)" + "VALUES" + "(").append("\"" + tag.getName() + "\"").append(",").append("\"" + tag.getWeight() + "\"").append(");");
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("NAME",tag.getName());
//        contentValues.put("WEIGHT",tag.getWeight());
        if (BuildConfig.DEBUG){
            Log.d("",sql.toString());
        }
        Logger.d(sql);
        mSQLiteDatabase.execSQL(sql.toString());
    }

}
