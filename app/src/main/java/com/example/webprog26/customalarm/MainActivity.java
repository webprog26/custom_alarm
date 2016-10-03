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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimeDialog.TimeCommunicator {

    private static final String LOG_TAG = "CustomAlarmMainActivity";
    private static final String TIME_DIALOG_TAG = "timeDialog";
    private static final String ALARM_TURNED_ON = "true";
    private static final String ALARM_TURNED_OFF = "false";

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

        mAlarmProvider = new AlarmProvider(this);

        mAlarmsList = mAlarmProvider.getAlarmsList();

        if(mAlarmsList.size() > 0)
        {
            for(Alarmer alarmer: mAlarmsList)
            {
                Log.i(LOG_TAG, "from list: " + alarmer.getAlarmHours());
            }
        }

        mAlarmsRecyclerView = (RecyclerView) findViewById(R.id.alarmsList);
        mAlarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlarmsListAdapter = new AlarmsListAdapter(this, mAlarmsList, new OnAlarmsListItemClickListener() {
            @Override
            public void onAlarmsListItemClick(Alarmer alarmer) {
                //Todo
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

    @Override
    public void sendTime(int code, int hour, int minute) {
        if(code == TIMER_REQUEST_CODE)
        {
            Alarmer alarmer = new Alarmer(hour, minute, ALARM_TURNED_ON);
            Log.i(LOG_TAG,
                    "alarmer hour " + alarmer.getAlarmHours()
                    + "alarmer minutes " + alarmer.getAlarmMinutes()
                    + "iAlarmOn " + alarmer.isAlarmActive());
            mAlarmProvider.insertAlarmToDataBase(alarmer);
            mAlarmsListAdapter.addItemToList(alarmer);
            Log.i(LOG_TAG, "item_count in adapter: " + mAlarmsListAdapter.getItemCount());
            Log.i(LOG_TAG, "from DB\n");
            LinkedList<Alarmer> alarmsList = mAlarmProvider.getAlarmsList();

            for(Alarmer alarmer1: alarmsList)
            {
                Log.i(LOG_TAG, "alarm time: " + alarmer1.getAlarmHours() + ":" + alarmer1.getAlarmMinutes());
            }
        }
    }


}
