package com.example.webprog26.customalarm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by webprog26 on 02.10.2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper{

    //DB name & version
    private static final String DB_NAME = "alarms_db";
    private static final int DB_VERSION = 1;

    //DB tables titles
    public static final String ALARMS_TABLE_TITLE = "alarms_table";
    public static final String DAYS_TABLE_TITLE = "days_table";

    //Alarms table columns
    public static final String ALARM_ID = "_id";
    public static final String ALARM_HOUR = "alarm_hour";
    public static final String ALARM_MINUTE = "alarm_minute";
    public static final String IS_ALARM_ACTIVE = "is_alarm_on";

    //Days table columns
    public static final String DAY_ID = "_id";
    public static final String CURRENT_ALARM_ID = "alarm_id";
    public static final String MONDAY = "monday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    public static final String THURSDAY  = "thursday";
    public static final String FRIDAY = "friday";
    public static final String SATURDAY = "saturday";
    public static final String SUNDAY = "sunday";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static final String TAG = "SQLiteHelper";


    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating ALARMS_TABLE
        db.execSQL("create table " + ALARMS_TABLE_TITLE + "("
        + ALARM_ID + " integer primary key autoincrement, "
        + ALARM_HOUR + " integer, "
        + ALARM_MINUTE + " integer, "
        + IS_ALARM_ACTIVE + " varchar(10))");

        //creating DAYS_TABLE
        db.execSQL("create table " + DAYS_TABLE_TITLE + "("
                + DAY_ID + " integer primary key autoincrement, "
                + CURRENT_ALARM_ID + " integer, "
                + MONDAY + " varchar(10), "
                + TUESDAY + " varchar(10), "
                + WEDNESDAY + " varchar(10), "
                + THURSDAY + " varchar(10), "
                + FRIDAY + " varchar(10), "
                + SATURDAY + " varchar(10), "
                + SUNDAY + " varchar(20))");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo
    }
}
