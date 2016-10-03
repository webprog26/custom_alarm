package com.example.webprog26.customalarm.providers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.webprog26.customalarm.R;
import com.example.webprog26.customalarm.db.SQLiteHelper;
import com.example.webprog26.customalarm.models.Alarmer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by webprog26 on 03.10.2016.
 */
public class DaysProvider {

    private static final String LOG_TAG = "DaysProvider";

    private SQLiteHelper mSqLiteHelper;
    private Activity mActivity;

    public DaysProvider(Activity mActivity) {
        this.mActivity = mActivity;
        mSqLiteHelper = new SQLiteHelper(mActivity);
    }

    public long insertDaysToDB(Alarmer alarmer)
    {
        Log.i(LOG_TAG, "alarmer hour in DaysProvider " + alarmer.getAlarmHours());
        ContentValues contentValues = new ContentValues();

        contentValues.put(SQLiteHelper.CURRENT_ALARM_ID, alarmer.getId());
        for(String dayTitle: alarmer.getmDaysActiveMap().keySet())
        {
            contentValues.put(dayTitle, String.valueOf(alarmer.getmDaysActiveMap().get(dayTitle)));
        }
        return mSqLiteHelper.getWritableDatabase().insert(SQLiteHelper.DAYS_TABLE_TITLE, null, contentValues);
    }

    public long updateDay(long id, String dayTitle, Boolean isActive)
    {

        String strFilter = SQLiteHelper.CURRENT_ALARM_ID + " = " + String.valueOf(id);

        ContentValues contentValues = new ContentValues();
        contentValues.put(dayTitle, String.valueOf(isActive));

        return mSqLiteHelper.getWritableDatabase().update(SQLiteHelper.DAYS_TABLE_TITLE, contentValues, strFilter, null);
    }

    public Map<String, Boolean> getDaysActiveMapFromDB(long id)
    {
        Log.i(LOG_TAG, "in daysProvider alarm id " + id);
        Map<String, Boolean> retMap = new LinkedHashMap<>();
        Cursor cursor = mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.DAYS_TABLE_TITLE, null, null, null, null, null, null);
//        Cursor cursor = mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.DAYS_TABLE_TITLE,
//                new String[]{
//                        SQLiteHelper.MO,
//                        SQLiteHelper.TU,
//                        SQLiteHelper.WE,
//                        SQLiteHelper.TH,
//                        SQLiteHelper.FR,
//                        SQLiteHelper.SA,
//                        SQLiteHelper.SU
//                }, SQLiteHelper.CURRENT_ALARM_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

       while(cursor.moveToNext())
       {
           retMap.put(SQLiteHelper.MO, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.MO))));
           retMap.put(SQLiteHelper.TU, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.TU))));
           retMap.put(SQLiteHelper.WE, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.WE))));
           retMap.put(SQLiteHelper.TH, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.TH))));
           retMap.put(SQLiteHelper.FR, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.FR))));
           retMap.put(SQLiteHelper.SA, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.SA))));
           retMap.put(SQLiteHelper.SU, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteHelper.SU))));
       }

        Log.i(LOG_TAG, "map: " + retMap);

        cursor.close();
        return retMap;
    }
}
