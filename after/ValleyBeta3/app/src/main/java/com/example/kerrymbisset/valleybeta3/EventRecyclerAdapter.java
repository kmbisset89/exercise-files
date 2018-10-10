package com.example.kerrymbisset.valleybeta3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private final LayoutInflater mInflator;
    List<Events> mEvents;
    private static ClickListener clickListener;


    EventRecyclerAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = mInflator.inflate(R.layout.event_list, parent, false);
        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mEvents != null) {
            Events current = mEvents.get(position);
            holder.mEventTitle.setText(current.getEventTitle());
            holder.mEventDate.setText(current.getEventDate());
        }
    }

        /**
         * Associates a list of words with this adapter
         */
        void setEvents (List < Events > events) {
            mEvents = events;
            notifyDataSetChanged();
        }

        /**
         * getItemCount() is called many times, and when it is first called,
         * mWords has not been updated (means initially, it's null, and we can't return null).
         */
        @Override
        public int getItemCount () {
            if (mEvents != null)
                return mEvents.size();
            else return 0;
        }

    public Events getEventAtPosition(int position) {
        return mEvents.get(position);
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mEventTitle;
            public final TextView mEventDate;


            private ViewHolder(final View itemView) {
                super(itemView);
                mEventTitle = itemView.findViewById(R.id.event_title);
                mEventDate = itemView.findViewById(R.id.event_date);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v, getAdapterPosition());
                    }
                });
            }


        }
        public void setOnItemClickListener (ClickListener clickListener){
            EventRecyclerAdapter.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(View v, int position);
        }
    }
