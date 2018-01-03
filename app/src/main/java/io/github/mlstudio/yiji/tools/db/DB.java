package io.github.mlstudio.yiji.tools.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;

import io.github.mlstudio.yiji.BuildConfig;
import io.github.mlstudio.yiji.javabeans.Tag;
import io.github.mlstudio.yiji.javabeans.YiJiRecord;

/**
 * Created by Yangyl on 2016/12/19.
 */

public class DB {
    public static final String DB_NAME_STRING = "YiJi Database.db";
    public static final String RECORD_DB_NAME_STRING = "RECORD";
    public static final String TAG_DB_NAME_STRING = "TAG";
    public static final int VERSION = 2;
    private static final String QUERY_TAG = "SELECT * FROM TAG;";
    private static final String QUERY_RECORD = "SELECT * FROM RECORD ORDER BY TIME";
    private static volatile DB db  = null;
    private static DB checkDBIsInit = null;
    private SQLiteDatabase mSQLiteDatabase;
    private DBHelper        mDBHelper;
    private DB(Context context){
        mDBHelper = new DBHelper(context,DB_NAME_STRING,null,VERSION);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }
    public static DB getInstance(Context context)  {
        if (checkDBIsInit == null){
            initInstance(context.getApplicationContext());
        }
        return db;
    }
    private synchronized static void initInstance(Context context){
        if (checkDBIsInit == null){
            db = new DB(context);
            checkDBIsInit = db;
        }
    }

    public void prepareData(){
        Cursor cursor = mSQLiteDatabase.rawQuery(QUERY_RECORD,null);
        try {
            while (cursor.moveToNext()) {
                YiJiRecord yiJiRecord = new YiJiRecord();
                yiJiRecord.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                yiJiRecord.setMoney(cursor.getFloat(cursor.getColumnIndex("MONEY")));
                yiJiRecord.setCurrency(cursor.getString(cursor.getColumnIndex("CURRENCY")));
                yiJiRecord.setTag(cursor.getInt(cursor.getColumnIndex("TAG")));
                yiJiRecord.setCalendar(cursor.getString(cursor.getColumnIndex("TIME")));
                yiJiRecord.setRemark(cursor.getString(cursor.getColumnIndex("REMARK")));
                yiJiRecord.setUserId(cursor.getString(cursor.getColumnIndex("USER_ID")));
                yiJiRecord.setObjectId(cursor.getString(cursor.getColumnIndex("OBJECT_ID")));
                yiJiRecord.setIsUploaded(
                        cursor.getInt(cursor.getColumnIndex("IS_UPLOADED")) != 0);
                DataManager.RECORDS.add(yiJiRecord);
                if (BuildConfig.DEBUG)
                    Log.d("CoCoin Debugger", "Load " + yiJiRecord.toString() + " S");
            }
        }finally {
            cursor.close();
            DataManager.sortRecords();
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

    public void saveRecord(YiJiRecord record){
        String insertSql = ("INSERT INTO RECORD (" +
                "\"MONEY\"," +
                "\"CURRENCY\"," +
                "\"TAG\"," +
                "\"TIME\"," +
                "\"REMARK\"," +
                "\"USER_ID\"," +
                "\"OBJECT_ID\"," +
                "\"IS_UPLOADED\"," +
                "\"HASH_CODE\")" +
                "VALUES (?,?,?,?,?,?,?,?,?);");
        mSQLiteDatabase.execSQL(insertSql, new Object[]{
                record.getMoney(),
                record.getCurrency(),
                record.getTag(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .format(record.getCalendar().getTime()),
                record.getRemark(),
                record.getUserId(),
                record.getObjectId(),
                record.getIsUploaded(),
                record.getHashCode()
        });
    }

    public void updateRecordObjectId(final YiJiRecord record){
        String insertSql = " UPDATE RECORD SET OBJECT_ID = \"" +
                record.getObjectId() +
                "\" where ID = " +
                record.getId() +
                " ;";
        mSQLiteDatabase.execSQL(insertSql);
    }


}















