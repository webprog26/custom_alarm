package com.example.webprog26.customalarm;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import android.widget.TimePicker;


import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by webprog26 on 30.09.2016.
 */
public class TimeDialog extends DialogFragment {

    private static final String LOG_TAG = "TimeDialog";

    private static final boolean IS_24_HOURS_VIEW = true;

    public interface TimeCommunicator{
        void sendTime(int code, int hour, int minute);
    }

    public TimeCommunicator timeCommunicator;



    private Calendar mCalendar;

    @SuppressWarnings("deprecation")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        timeCommunicator = (TimeCommunicator) getActivity();

        View v = getActivity().getLayoutInflater().inflate(R.layout.timepicker, null);

        mCalendar = new GregorianCalendar();

        final TimePicker timePicker = (TimePicker) v.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(IS_24_HOURS_VIEW);

        if (isApiOver23())
        {
            timePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(mCalendar.get(Calendar.MINUTE));
        } else {
            timePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour, minute;
                        if(isApiOver23())
                        {
                            hour = timePicker.getHour();
                            minute = timePicker.getMinute();
                        } else {
                            hour = timePicker.getCurrentHour();
                            minute = timePicker.getCurrentMinute();
                        }
                        Log.i(LOG_TAG, "Hour: " + hour + ", minute: " + minute);
                        if(timeCommunicator != null)
                        {
                            timeCommunicator.sendTime(MainActivity.TIMER_REQUEST_CODE, hour, minute);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    private boolean isApiOver23()
    {
        return Build.VERSION.SDK_INT >= 23;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            timeCommunicator = (TimeCommunicator) activity;
        } catch (ClassCastException cce)
        {
            throw new ClassCastException(activity.toString() + " must implement TimeCommunicator.");
        }
    }
}
