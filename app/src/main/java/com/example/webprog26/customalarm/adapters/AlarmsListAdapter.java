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

import com.example.webprog26.customalarm.R;
import com.example.webprog26.customalarm.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.customalarm.interfaces.OnDaysListItemClickListener;
import com.example.webprog26.customalarm.models.Alarmer;
import com.example.webprog26.customalarm.providers.AlarmProvider;
import com.example.webprog26.customalarm.providers.DaysProvider;

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
        private RecyclerView mDaysRecyclerView;
        private LinearLayout mLlAlarmMelodyChooserContainer;
        private TextView mTvAlarmMelodyTitle;
        private Map<String, Boolean> mDaysActiveMap;

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
            mDaysRecyclerView = (RecyclerView) itemView.findViewById(R.id.daysRecyclerView);
            mLlAlarmMelodyChooserContainer = (LinearLayout) itemView.findViewById(R.id.lLalarmMelodyChooserContainer);
            mTvAlarmMelodyTitle = (TextView) itemView.findViewById(R.id.tvAlarmMelodyTitle);

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

            mChbRepeatAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i(LOG_TAG, "repeat alarm checked state changed to " + isChecked);
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




            mBtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlarmProvider.deleteAlarm(mAlarmsList.get((int)v.getTag()).getId());
                    mAlarmsList.remove((int)v.getTag());
                    notifyItemRemoved((int)v.getTag());

                }
            });
        }

        public void bind(final Alarmer alarmer, final OnAlarmsListItemClickListener listener)
        {
            mTvTime.setText(getStringTime(alarmer));
            mAlarmSwitch.setChecked(Boolean.parseBoolean(alarmer.isAlarmActive()));
            mBtnDeleteAlarm.setTag(this.getAdapterPosition());

            //**************initializing RecyclerView that contains list of "alarm-active" days
            mDaysActiveMap  = alarmer.getmDaysActiveMap();
            mDaysRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mDaysRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            layoutManager.scrollToPosition(0);
            mDaysRecyclerView.setLayoutManager(layoutManager);


            AlarmOptionsDaysAdapter daysAdapter = new AlarmOptionsDaysAdapter(mActivity, mDaysActiveMap, new OnDaysListItemClickListener() {
                @Override
                public void onDaysListItemClick(String dayTitle) {
                    Log.i(LOG_TAG, "dayTitle " + dayTitle + " was clicked! Where alarmId is " + alarmer.getId());
                }
            });

            mDaysRecyclerView.setAdapter(daysAdapter);

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
    private DaysProvider mDaysProvider;

    public AlarmsListAdapter(Activity mActivity, List<Alarmer> mAlarmsList, OnAlarmsListItemClickListener mListener, AlarmProvider alarmProvider, DaysProvider daysProvider) {
        this.mActivity = mActivity;
        this.mAlarmsList = mAlarmsList;
        this.mListener = mListener;
        this.mAlarmProvider = alarmProvider;
        this.mDaysProvider = daysProvider;
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
