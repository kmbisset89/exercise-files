package com.example.kerrymbisset.valleybeta3.EventRelated;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kerrymbisset.valleybeta3.R;

import java.util.ArrayList;

public class EventInfoAdapter extends RecyclerView.Adapter<EventInfoAdapter.EventViewHolder> {

    private final ArrayList<String> mEventList;
    private final ArrayList<String> mEventDate;
    private final ArrayList<String> mEventKeys;
    private final LayoutInflater mInflater;
    public static final String EVENTKEY = "com.example.kerrymbisset.valleybeta3.EventRelated.EVENTKEY";
    private final Context mContext;

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(
                R.layout.event_list, parent, false);
        return new EventViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        String mCurrent = mEventList.get(position);
        String mDateCurrent = mEventDate.get(position);
        // Add the data to the view holder.
        holder.mEventTitle.setText(mCurrent);
        holder.mEventDate.setText(mDateCurrent);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mEventTitle;
        public final TextView mEventDate;
        final EventInfoAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter  The adapter that manages the the data and views
         *                 for the RecyclerView.
         */
        public EventViewHolder(View itemView, EventInfoAdapter adapter) {
            super(itemView);
            mEventTitle = itemView.findViewById(R.id.event_title);
            mEventDate = itemView.findViewById(R.id.event_date);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            Intent intent = new Intent(mContext, Event_Details.class);
                intent.putExtra(EVENTKEY, mEventKeys.get(mPosition));
            mContext.startActivity(intent);

        }
    }

    public EventInfoAdapter(Context context, ArrayList<String> eventList, ArrayList<String> eventDates, ArrayList<String> eventKeys) {
        mContext= context;
        mInflater = LayoutInflater.from(context);
        this.mEventList = eventList;
        this.mEventDate = eventDates;
        this.mEventKeys = eventKeys;

    }
}
