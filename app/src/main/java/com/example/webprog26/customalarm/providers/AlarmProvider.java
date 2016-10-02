package com.example.webprog26.customalarm.providers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.webprog26.customalarm.db.SQLiteHelper;
import com.example.webprog26.customalarm.models.Alarmer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by webprog26 on 02.10.2016.
 */
public class AlarmProvider {

    private static final String LOG_TAG = "AlarmProvider";

    private SQLiteHelper mSqLiteHelper;
    private Activity mActivity;

    public AlarmProvider(Activity mActivity) {
        this.mActivity = mActivity;
        mSqLiteHelper = new SQLiteHelper(mActivity);
    }

    public long insertAlarmToDataBase(Alarmer alarmer)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.ALARM_HOUR, alarmer.getAlarmHours());
        contentValues.put(SQLiteHelper.ALARM_MINUTE, alarmer.getAlarmMinutes());
        contentValues.put(SQLiteHelper.IS_ALARM_ACTIVE, alarmer.isAlarmActive());
        Log.i(LOG_TAG, "writing to DB alarm state: " + alarmer.isAlarmActive());

        return mSqLiteHelper.getWritableDatabase().insert(SQLiteHelper.ALARMS_TABLE_TITLE, null, contentValues);
    }

    public LinkedList<Alarmer> getAlarmsList()
    {
        LinkedList<Alarmer> alarmsArrayList = new LinkedList<>();

        Cursor cursor = mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.ALARMS_TABLE_TITLE, null, null, null, null, null, SQLiteHelper.ALARM_ID);

        while(cursor.moveToNext())
        {
            Alarmer alarmer = new Alarmer(cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE)),
                                         cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE)),
                                         cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)),
                                         cursor.getLong(cursor.getColumnIndex(SQLiteHelper.ALARM_ID)));
            alarmsArrayList.add(alarmer);
            Log.i(LOG_TAG, "getting from DB alarm state: " + alarmer.isAlarmActive());
        }
        cursor.close();
        return alarmsArrayList;
    }
}
