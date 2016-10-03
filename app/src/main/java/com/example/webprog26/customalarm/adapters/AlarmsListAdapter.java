package com.example.webprog26.customalarm.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.webprog26.customalarm.R;
import com.example.webprog26.customalarm.interfaces.OnAlarmsListItemClickListener;
import com.example.webprog26.customalarm.models.Alarmer;
import com.example.webprog26.customalarm.providers.AlarmProvider;

import java.util.List;

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
        private ImageButton mBtnDeleteAlarm;

        public AlarmsViewHolder(View itemView) {
            super(itemView);

            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mAlarmSwitch = (Switch) itemView.findViewById(R.id.alarmSwitch);

            mBtnDeleteAlarm = (ImageButton) itemView.findViewById(R.id.btnDeleteAlarm);

            mBtnDeleteAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(LOG_TAG, "delete button clicked" + v.getTag());
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
