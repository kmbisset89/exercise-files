package com.example.kerrymbisset.valleybeta3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EventFilterRecyclerAdapter extends RecyclerView.Adapter<EventFilterRecyclerAdapter.ViewHolder> {
    private final LayoutInflater mInflator;
    List<EventFilter> mEventFilter;
    private static ClickListener clickListener;


    EventFilterRecyclerAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = mInflator.inflate(R.layout.event_filter_list, parent, false);
        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mEventFilter != null) {
            EventFilter current2 = mEventFilter.get(position);
            holder.mEventFilterTitle.setText(current2.getFilterName());

        }
    }

        /**
         * Associates a list of words with this adapter
         */
        void setEventsFilter (List < EventFilter > eventsFilter) {
            mEventFilter = eventsFilter;
            notifyDataSetChanged();
        }

        /**
         * getItemCount() is called many times, and when it is first called,
         * mWords has not been updated (means initially, it's null, and we can't return null).
         */
        @Override
        public int getItemCount () {
            if (mEventFilter != null)
                return mEventFilter.size();
            else return 0;
        }

    public EventFilter getEventAtPosition(int position) {
        return mEventFilter.get(position);
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mEventFilterTitle;



            private ViewHolder(final View itemView) {
                super(itemView);
                mEventFilterTitle = itemView.findViewById(R.id.event_filter);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v, getAdapterPosition());
                    }
                });
            }


        }
        public void setOnItemClickListener (ClickListener clickListener){
            EventFilterRecyclerAdapter.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(View v, int position);
        }
    }
