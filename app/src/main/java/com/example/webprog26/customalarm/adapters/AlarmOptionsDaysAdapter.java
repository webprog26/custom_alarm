package com.example.webprog26.customalarm.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.webprog26.customalarm.R;
import com.example.webprog26.customalarm.interfaces.OnDaysListItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by webprog26 on 03.10.2016.
 */
public class AlarmOptionsDaysAdapter extends RecyclerView.Adapter<AlarmOptionsDaysAdapter.AlarmActiveDaysViewholder> implements OnDaysListItemClickListener{

       @Override
    public void onDaysListItemClick(String dayTitle) {

    }

    public class AlarmActiveDaysViewholder extends RecyclerView.ViewHolder{

        private TextView mTvDayTitle;

        public AlarmActiveDaysViewholder(View itemView) {
            super(itemView);

            mTvDayTitle = (TextView) itemView.findViewById(R.id.tvDayShortTitle);
            if(!isMapHasActiveDays())
            {
                mTvDayTitle.setVisibility(View.GONE);
            }
        }

        public void bind(final String dayTitle, final OnDaysListItemClickListener listener)
        {
                if(mDaysTitlesMap.get(dayTitle))
                {
                    mTvDayTitle.setPaintFlags(mTvDayTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                }
                mTvDayTitle.setText(dayTitle);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onDaysListItemClick(dayTitle);
                    }
                });

        }
    }

    private Activity mActivity;
    private Map<String, Boolean> mDaysTitlesMap;
    private List<String> mDaysTitlesList;
    private OnDaysListItemClickListener listener;
    //private DaysListProvider mDaysListProvider;


    public AlarmOptionsDaysAdapter(Activity mActivity, Map<String, Boolean> mDaysTitlesMap, OnDaysListItemClickListener listener) {
        this.mActivity = mActivity;
        this.mDaysTitlesList = new ArrayList<>();
        this.mDaysTitlesMap = mDaysTitlesMap;
        this.listener = listener;

        if(isMapHasActiveDays())
        {
            for(String day: mDaysTitlesMap.keySet())
            {
                mDaysTitlesList.add(day);
            }
        }
    }

    @Override
    public AlarmActiveDaysViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.active_days_list_item, parent, false);
        return new AlarmActiveDaysViewholder(view);
    }

    @Override
    public void onBindViewHolder(AlarmActiveDaysViewholder holder, int position) {
        holder.bind(mDaysTitlesList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mDaysTitlesList.size();
    }

    public void updateAdapter() {
        notifyDataSetChanged();
    }

    private boolean isMapHasActiveDays()
    {
        return mDaysTitlesMap.size() > 0;
    }
}
