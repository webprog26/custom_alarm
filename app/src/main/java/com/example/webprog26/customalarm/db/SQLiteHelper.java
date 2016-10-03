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
    public static final String IS_REPEAT_ON = "is_repeat_on";
    public static final String IS_VIBRATION_ON = "is_vibration_on";
    public static final String MELODY_FILE_PATH = "melody_file_path";

    //Days table columns
    public static final String DAY_ID = "_id";
    public static final String CURRENT_ALARM_ID = "alarm_id";
    public static final String MO = "MO";
    public static final String TU = "TU";
    public static final String WE = "WE";
    public static final String TH = "TH";
    public static final String FR = "FR";
    public static final String SA = "SA";
    public static final String SU = "SU";

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
        + IS_ALARM_ACTIVE + " varchar(10), "
        + IS_REPEAT_ON + " varchar(10), "
        + IS_VIBRATION_ON + " varchar(10), "
        + MELODY_FILE_PATH + " varchar(100))");

        //creating DAYS_TABLE
        db.execSQL("create table " + DAYS_TABLE_TITLE + "("
                + DAY_ID + " integer primary key autoincrement, "
                + CURRENT_ALARM_ID + " integer, "
                + MO + " varchar(10), "
                + TU + " varchar(10), "
                + WE + " varchar(10), "
                + TH + " varchar(10), "
                + FR + " varchar(10), "
                + SA + " varchar(10), "
                + SU + " varchar(10))");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo
    }
}
