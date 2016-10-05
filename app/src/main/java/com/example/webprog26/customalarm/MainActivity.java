package com.example.webprog26.customalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.webprog26.customalarm.adapters.AlarmsListAdapter;
import com.example.webprog26.customalarm.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.customalarm.models.Alarmer;
import com.example.webprog26.customalarm.providers.AlarmProvider;
import com.example.webprog26.customalarm.providers.DaysProvider;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TimeDialog.TimeCommunicator {

    private static final String LOG_TAG = "CustomAlarmMainActivity";
    private static final String TIME_DIALOG_TAG = "timeDialog";
    public static final String ALARM_TURNED_ON = "true";
    public static final String ALARM_TURNED_OFF = "false";
    public static final String REPEAT_ON = "true";
    public static final String REPEAT_OFF = "false";
    public static final String VIBRATION_ON = "true";
    public static final String VIBRATION_OFF = "false";



    //just for test
    public static final String ALARM_MELODY_TITLE = "Funny moves";

    public static final int TIMER_REQUEST_CODE = 101;


    private RecyclerView mAlarmsRecyclerView;
    private Button mBtnAddNewSignal;
    private List<Alarmer> mAlarmsList;


    private TimeDialog mTimeDialog;

    private AlarmProvider mAlarmProvider;

    private AlarmsListAdapter mAlarmsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmProvider = new AlarmProvider(this);//defining AlarmProvider

        mAlarmsList = mAlarmProvider.getAlarmsList();//getting <List<Alarmer> from AlarmProvider

        mAlarmsRecyclerView = (RecyclerView) findViewById(R.id.alarmsList);
        mAlarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlarmsListAdapter = new AlarmsListAdapter(this, mAlarmsList, new OnAlarmsListItemClickListener() {
            @Override
            public void onAlarmsListItemClick(Alarmer alarmer) {
                Log.i(LOG_TAG, "alarm with id " + alarmer.getId() + " was clicked!");
            }
        }, mAlarmProvider);
        mAlarmsRecyclerView.setAdapter(mAlarmsListAdapter);
        mAlarmsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mBtnAddNewSignal = (Button) findViewById(R.id.btnAddSignal);

        mBtnAddNewSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeDialog = new TimeDialog();
                mTimeDialog.show(getSupportFragmentManager(), TIME_DIALOG_TAG);
            }
        });
    }

    /**
     * Gets requestCode, hour and minutes from TimePicker dialog, creates new Alarmer & adds it to AlarmListAdapter
     * @param code
     * @param hour
     * @param minute
     */
    @Override
    public void sendTime(int code, int hour, int minute) {
        if(code == TIMER_REQUEST_CODE)
        {
            Alarmer alarmer = new Alarmer(hour, minute, ALARM_TURNED_ON, REPEAT_OFF, VIBRATION_ON, ALARM_MELODY_TITLE);

            alarmer.setId(mAlarmProvider.insertAlarmToDataBase(alarmer));
            mAlarmsListAdapter.addItemToList(alarmer);
        }
    }
}
