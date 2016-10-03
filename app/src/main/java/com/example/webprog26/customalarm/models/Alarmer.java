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

    public Alarmer(int mAlarmHours, int mAlarmMinutes, Map<String, Boolean> mDaysActiveMap, String isAlarmActive, String isRepeatOn, String isVibrationOn, String mAlarmMelodyFilePath) {
        this.mAlarmHours = mAlarmHours;
        this.mAlarmMinutes = mAlarmMinutes;
        this.mDaysActiveMap = mDaysActiveMap;
        this.isAlarmActive = isAlarmActive;
        this.isRepeatOn = isRepeatOn;
        this.isVibrationOn = isVibrationOn;
        this.mAlarmMelodyFilePath = mAlarmMelodyFilePath;
    }

    public Alarmer(long alarmId, int mAlarmHours, int mAlarmMinutes, Map<String, Boolean> mDaysActiveMap, String isAlarmActive, String isRepeatOn, String isVibrationOn, String mAlarmMelodyFilePath) {
        this.mId = alarmId;
        this.mAlarmHours = mAlarmHours;
        this.mAlarmMinutes = mAlarmMinutes;
        this.mDaysActiveMap = mDaysActiveMap;
        this.isAlarmActive = isAlarmActive;
        this.isRepeatOn = isRepeatOn;
        this.isVibrationOn = isVibrationOn;
        this.mAlarmMelodyFilePath = mAlarmMelodyFilePath;
    }


    public int getAlarmHours() {
        return mAlarmHours;
    }

    public int getAlarmMinutes() {
        return mAlarmMinutes;
    }

    public String isAlarmActive() {
        return isAlarmActive;
    }

    public long getId() {
        return mId;
    }

    public Map<String, Boolean> getmDaysActiveMap() {
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
