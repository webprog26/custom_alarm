package com.example.webprog26.customalarm.models;

import java.util.List;
import java.util.Map;

/**
 * Created by webprog26 on 30.09.2016.
 */
public class Alarmer {

    private long mId;
    private int mAlarmHours;
    private int mAlarmMinutes;
    private Map<String, Boolean> mDaysActiveMap;
    private String isAlarmActive;
    private String isRepeatOn;
    private String isVibrationOn;
    private String mAlarmMelodyFilePath;

    //default constructor to add alarm to DB without repeat
    public Alarmer(int mAlarmHours,
                   int mAlarmMinutes,
                   String isAlarmActive,
                   String isRepeatOn,
                   String isVibrationOn,
                   String mAlarmMelodyFilePath)
    {
        this.mAlarmHours = mAlarmHours;
        this.mAlarmMinutes = mAlarmMinutes;
        this.isAlarmActive = isAlarmActive;
        this.isRepeatOn = isRepeatOn;
        this.isVibrationOn = isVibrationOn;
        this.mAlarmMelodyFilePath = mAlarmMelodyFilePath;
    }

    //constructor to return alarm from DB with alarmId but without repeat
    public Alarmer(long id,
                   int mAlarmHours,
                   int mAlarmMinutes,
                   Map<String, Boolean> daysActiveMap,
                   String isAlarmActive,
                   String isRepeatOn,
                   String isVibrationOn,
                   String mAlarmMelodyFilePath)
    {
        this.mId = id;
        this.mAlarmHours = mAlarmHours;
        this.mAlarmMinutes = mAlarmMinutes;
        this.mDaysActiveMap = daysActiveMap;
        this.isAlarmActive = isAlarmActive;
        this.isRepeatOn = isRepeatOn;
        this.isVibrationOn = isVibrationOn;
        this.mAlarmMelodyFilePath = mAlarmMelodyFilePath;
    }

//    //constructor to return alarm from DB with alarmId and with repeat
//    public Alarmer(long alarmId,
//                   int mAlarmHours,
//                   int mAlarmMinutes,
//                   Map<String, Boolean> daysActiveMap,
//                   String isAlarmActive,
//                   String isRepeatOn,
//                   String isVibrationOn,
//                   String mAlarmMelodyFilePath) {
//        this.mId = alarmId;
//        this.mAlarmHours = mAlarmHours;
//        this.mAlarmMinutes = mAlarmMinutes;
//        this.mDaysActiveMap = daysActiveMap;
//        this.isAlarmActive = isAlarmActive;
//        this.isRepeatOn = isRepeatOn;
//        this.isVibrationOn = isVibrationOn;
//        this.mAlarmMelodyFilePath = mAlarmMelodyFilePath;
//    }


    public int getAlarmHours() {
        return mAlarmHours;
    }

    public int getAlarmMinutes() {
        return mAlarmMinutes;
    }

    public String isAlarmActive() {
        return isAlarmActive;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setmDaysActiveMap(Map<String, Boolean> mDaysActiveMap) {
        this.mDaysActiveMap = mDaysActiveMap;
    }

    public Map<String, Boolean> getDaysActiveMap() {
        return mDaysActiveMap;
    }

    public String getIsRepeatOn() {
        return isRepeatOn;
    }

    public String getIsVibrationOn() {
        return isVibrationOn;
    }

    public String getmAlarmMelodyFilePath() {
        return mAlarmMelodyFilePath;
    }
}
