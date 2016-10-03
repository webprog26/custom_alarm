package com.example.webprog26.customalarm.models;

import java.util.List;

/**
 * Created by webprog26 on 30.09.2016.
 */
public class Alarmer {

    private long mId;
    private int mAlarmHours;
    private int mAlarmMinutes;
    private List<Boolean> mDaysActive;

    private String isAlarmActive;

    public Alarmer(int mAlarmHours, int mAlarmMinutes, String isAlarmActive) {
        this.mAlarmHours = mAlarmHours;
        this.mAlarmMinutes = mAlarmMinutes;
        this.isAlarmActive = isAlarmActive;
    }

//    public Alarmer(long mId, int mAlarmHours, int mAlarmMinutes, List<Boolean> mDaysActive, boolean isAlarmActive) {
//        this.mId = mId;
//        this.mAlarmHours = mAlarmHours;
//        this.mAlarmMinutes = mAlarmMinutes;
//        this.mDaysActive = mDaysActive;
//        this.isAlarmActive = isAlarmActive;
//    }


    public Alarmer(String isAlarmActive, int mAlarmMinutes, int mAlarmHours, long mId) {
        this.isAlarmActive = isAlarmActive;
        this.mAlarmMinutes = mAlarmMinutes;
        this.mAlarmHours = mAlarmHours;
        this.mId = mId;
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
}
