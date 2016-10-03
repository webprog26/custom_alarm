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
    private DaysProvider mDaysProvider;

    public AlarmProvider(Activity mActivity) {
        this.mActivity = mActivity;
        mSqLiteHelper = new SQLiteHelper(mActivity);
        mDaysProvider = new DaysProvider(mActivity);
    }

    public long insertAlarmToDataBase(Alarmer alarmer)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.ALARM_HOUR, alarmer.getAlarmHours());
        contentValues.put(SQLiteHelper.ALARM_MINUTE, alarmer.getAlarmMinutes());
        contentValues.put(SQLiteHelper.IS_ALARM_ACTIVE, alarmer.isAlarmActive());
        contentValues.put(SQLiteHelper.IS_REPEAT_ON, alarmer.getIsRepeatOn());
        contentValues.put(SQLiteHelper.IS_VIBRATION_ON, alarmer.getIsVibrationOn());
        contentValues.put(SQLiteHelper.MELODY_FILE_PATH, alarmer.getmAlarmMelodyFilePath());
        Log.i(LOG_TAG, "writing to DB alarm state: " + alarmer.isAlarmActive());

        return mSqLiteHelper.getWritableDatabase().insert(SQLiteHelper.ALARMS_TABLE_TITLE, null, contentValues);
    }

    public LinkedList<Alarmer> getAlarmsList()
    {
        LinkedList<Alarmer> alarmsArrayList = new LinkedList<>();

        Cursor cursor = mSqLiteHelper.getReadableDatabase().query(SQLiteHelper.ALARMS_TABLE_TITLE, null, null, null, null, null, SQLiteHelper.ALARM_ID);

        while(cursor.moveToNext())
        {
            Alarmer alarmer = new Alarmer(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.ALARM_ID)),
                                          cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_HOUR)),
                                          cursor.getInt(cursor.getColumnIndex(SQLiteHelper.ALARM_MINUTE)),
                                          mDaysProvider.getDaysActiveMapFromDB(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.ALARM_ID))),
                                          cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_ALARM_ACTIVE)),
                                          cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_REPEAT_ON)),
                                          cursor.getString(cursor.getColumnIndex(SQLiteHelper.IS_VIBRATION_ON)),
                                          cursor.getString(cursor.getColumnIndex(SQLiteHelper.MELODY_FILE_PATH)));
            alarmsArrayList.add(alarmer);
            Log.i(LOG_TAG, "getting from DB alarm state: " + alarmer.isAlarmActive());
        }
        cursor.close();
        return alarmsArrayList;
    }

    public void deleteAlarm(long alarmId)
    {
        mSqLiteHelper.getWritableDatabase().delete(SQLiteHelper.ALARMS_TABLE_TITLE, SQLiteHelper.ALARM_ID + " = " + alarmId, null);
    }
}
