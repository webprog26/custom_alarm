package com.example.webprog26.customalarm.adapters;

import android.app.Activity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.webprog26.customalarm.MainActivity;
import com.example.webprog26.customalarm.R;
import com.example.webprog26.customalarm.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.customalarm.interfaces.OnDaysListItemClickListener;
import com.example.webprog26.customalarm.models.Alarmer;
import com.example.webprog26.customalarm.providers.AlarmProvider;
import com.example.webprog26.customalarm.providers.DaysProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by webprog26 on 02.10.2016.
 */
public class AlarmsListAdapter extends RecyclerView.Adapter<AlarmsListAdapter.AlarmsViewHolder> implements OnAlarmsListItemClickListener{


    @Override
    public void onAlarmsListItemClick(Alarmer alarmer) {

    }

    public class AlarmsViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvTime;
        private Switch mAlarmSwitch;
        private ImageButton mBtnShowOptions, mBtnHideOptions;
        private ImageButton mBtnDeleteAlarm;
        private AppCompatCheckBox mChbRepeatAlarm, mChbIsVibrationOn;
        private LinearLayout mLlAlarmMelodyChooserContainer;
        private TextView mTvAlarmMelodyTitle;
        private Map<String, Boolean> mDaysActiveMap;
        private DaysProvider mDaysProvider;

        private AlarmOptionsDaysAdapter daysAdapter;

        public AlarmsViewHolder(View itemView) {
            super(itemView);

            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mAlarmSwitch = (Switch) itemView.findViewById(R.id.alarmSwitch);

            mBtnDeleteAlarm = (ImageButton) itemView.findViewById(R.id.btnDeleteAlarm);

            //**************animated options layout views
            mBtnShowOptions = (ImageButton) itemView.findViewById(R.id.btnShowOptions);
            mBtnHideOptions = (ImageButton) itemView.findViewById(R.id.btnHideOptions);
            mChbRepeatAlarm = (AppCompatCheckBox) itemView.findViewById(R.id.chbRepeatAlarm);
            mChbIsVibrationOn = (AppCompatCheckBox) itemView.findViewById(R.id.chbIsVibrationOn);
            mLlAlarmMelodyChooserContainer = (LinearLayout) itemView.findViewById(R.id.lLalarmMelodyChooserContainer);
            mTvAlarmMelodyTitle = (TextView) itemView.findViewById(R.id.tvAlarmMelodyTitle);



            mDaysProvider = new DaysProvider(mActivity);

            //**************settingOnClickListeners to view in animated options layout

            mBtnShowOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(LOG_TAG, "show options clicked");
                }
            });

            mBtnHideOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(LOG_TAG, "hide options clicked");
                }
            });

            mBtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlarmProvider.deleteAlarm(mAlarmsList.get((int)v.getTag()).getId());
                    mAlarmsList.remove((int)v.getTag());
                    notifyItemRemoved((int)v.getTag());

                }
            });
        }

        /**
         * fills every alarm-item with data received from DB
         * @param alarmer
         * @param listener
         */
        public void bind(final Alarmer alarmer, final OnAlarmsListItemClickListener listener)
        {
            //**************initializing RecyclerView that contains list of "alarm-active" days
            final RecyclerView daysRecyclerView = (RecyclerView) itemView.findViewById(R.id.daysRecyclerView);

            //getting Map<String, Boolean> from DaysProvider. Map contains days of week alarm will be repeating on
            mDaysActiveMap = mDaysProvider.getDaysActiveMapFromDB(alarmer.getId());
            Log.i(LOG_TAG, "mChbRepeatAlarm is checked and uploaded map is " + mDaysActiveMap);

            Log.i(LOG_TAG, "in AlarmListAdapter alarm id is " + alarmer.getId() + " " +  alarmer.getAlarmHours() + ":" + alarmer.getAlarmMinutes());
            mTvTime.setText(getStringTime(alarmer));
            mAlarmSwitch.setChecked(Boolean.valueOf(alarmer.isAlarmActive()));
            mChbRepeatAlarm.setChecked(Boolean.valueOf(alarmer.getIsRepeatOn()));
            mChbIsVibrationOn.setChecked(Boolean.valueOf(alarmer.getIsVibrationOn()));
            mBtnDeleteAlarm.setTag(this.getAdapterPosition());


            mChbRepeatAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked)
                    {
                        if(mDaysActiveMap.size() == 0) {                                                 //if there are no days in
                            String[] dayTitles = mActivity.getResources().getStringArray(R.array.days);  //the map, this the first
                                                                                                         //initialization of the newly-created
                            for (String dayTitle : dayTitles)                                            //alarm. So it fills with days titles
                            {                                                                            //and then DaysProvider receives it with
                                mDaysActiveMap.put(dayTitle, Boolean.valueOf(MainActivity.REPEAT_ON));   //alarmId & puts to DB
                            }

                            mDaysProvider.insertDaysToDB(alarmer.getId(), mDaysActiveMap);


                            Log.i(LOG_TAG, "filled map is " + mDaysActiveMap + " alarmerId = " + alarmer.getId());
                        }else {
                            mDaysActiveMap = mDaysProvider.getDaysActiveMapFromDB(alarmer.getId());      //if DB already has a map
                        }                                                                                //for this alarmId
                                                                                                         //app receives it from
                                                                                                         //DaysAdapter
                        //updating alarms' repeat status in DB
                        mAlarmProvider.updateAlarmRepeatMode(alarmer.getId(), isChecked);

                        //show previously hidden RecylerView with days of the week
                        if(daysRecyclerView.getVisibility() == View.GONE)
                        {
                            daysRecyclerView.setVisibility(View.VISIBLE);
                        }

                        daysRecyclerView.setItemAnimator(new DefaultItemAnimator());                      //here we should initialize
                        daysRecyclerView.setHasFixedSize(true);                                           //separate
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity,            // AlarmOptionsDaysAdapter
                                                                LinearLayoutManager.HORIZONTAL,           //for every alarm
                                                                                    false);
                        layoutManager.scrollToPosition(0);
                        daysRecyclerView.setLayoutManager(layoutManager);

                        //defining AlarmOptionsDaysAdapter for current RecyclerView
                        daysAdapter = new AlarmOptionsDaysAdapter(mActivity, mDaysActiveMap, new OnDaysListItemClickListener() {
                            @Override
                            public void onDaysListItemClick(String dayTitle) {
                                Log.i(LOG_TAG, "dayTitle " + dayTitle + " was clicked! Where alarmId is " + alarmer.getId());
                            }
                        });
                        daysRecyclerView.setAdapter(daysAdapter);
                    } else {
                        //repeat unChecked hide RecylerView
                        mAlarmProvider.updateAlarmRepeatMode(alarmer.getId(), isChecked);
                        if(daysRecyclerView.getVisibility() == View.VISIBLE)
                        {
                            daysRecyclerView.setVisibility(View.GONE);
                        }
                    }
                }
            });

            mChbIsVibrationOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(LOG_TAG, "vibration on checked state changed to " + isChecked);
                }
            });

            mLlAlarmMelodyChooserContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(LOG_TAG, "melody chooser clicked");
                }
            });


            if(!mChbRepeatAlarm.isChecked())
            {
                daysRecyclerView.setVisibility(View.GONE);
            } else {
                daysRecyclerView.setVisibility(View.VISIBLE);
            }



            mChbIsVibrationOn.setChecked(Boolean.valueOf(alarmer.getIsVibrationOn()));
            mChbRepeatAlarm.setChecked(Boolean.valueOf(alarmer.getIsRepeatOn()));
            mTvAlarmMelodyTitle.setText(alarmer.getmAlarmMelodyFilePath());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAlarmsListItemClick(alarmer);
                }
            });
        }

        private String getStringTime(Alarmer alarmer) {
            StringBuilder builder = new StringBuilder();

            if(alarmer.getAlarmHours() < 10) builder.append("0");

            builder.append(String.valueOf(alarmer.getAlarmHours()));
            builder.append(":");

            if(alarmer.getAlarmMinutes() < 10) builder.append("0");
            builder.append(String.valueOf(alarmer.getAlarmMinutes()));

            return builder.toString();

        }
    }

    private static final String LOG_TAG = "AlarmsListAdapter";

    private Activity mActivity;
    private List<Alarmer> mAlarmsList;
    private OnAlarmsListItemClickListener mListener;
    private AlarmProvider mAlarmProvider;

    public AlarmsListAdapter(Activity mActivity, List<Alarmer> mAlarmsList, OnAlarmsListItemClickListener mListener, AlarmProvider alarmProvider) {
        this.mActivity = mActivity;
        this.mAlarmsList = mAlarmsList;
        this.mListener = mListener;
        this.mAlarmProvider = alarmProvider;
    }

    @Override
    public AlarmsListAdapter.AlarmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.alarm_list_item, parent, false);
        return new AlarmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmsListAdapter.AlarmsViewHolder holder, int position) {
        holder.bind(mAlarmsList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mAlarmsList.size();
    }

    public void addItemToList(Alarmer alarmer)
    {
        mAlarmsList.add(alarmer);
        notifyDataSetChanged();
    }
}
