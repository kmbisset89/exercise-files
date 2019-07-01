package com.example.kerrymbisset.valleybeta3.RoomDataModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kerrymbisset.valleybeta3.R;

import java.util.List;

public class SmallGroupFilterRecyclerAdapter extends RecyclerView.Adapter<SmallGroupFilterRecyclerAdapter.ViewHolder> {
    private final LayoutInflater mInflator;
    List<SGFilter> mSGFilter;
    private static ClickListener clickListener;


    public SmallGroupFilterRecyclerAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = mInflator.inflate(R.layout.sg_filter_list, parent, false);
        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mSGFilter != null) {
            SGFilter current2 = mSGFilter.get(position);
            holder.mSGFilterName.setText(current2.getFilterName());

        }
    }

        /**
         * Associates a list of words with this adapter
         */
        public void setSGFilter(List<SGFilter> sgFilter) {
            mSGFilter = sgFilter;
            notifyDataSetChanged();
        }

        /**
         * getItemCount() is called many times, and when it is first called,
         * mWords has not been updated (means initially, it's null, and we can't return null).
         */
        @Override
        public int getItemCount () {
            if (mSGFilter != null)
                return mSGFilter.size();
            else return 0;
        }

    public SGFilter getSGAtPosition(int position) {
        return mSGFilter.get(position);
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mSGFilterName;



            private ViewHolder(final View itemView) {
                super(itemView);
                mSGFilterName = itemView.findViewById(R.id.text_sg_filter);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v, getAdapterPosition());
                    }
                });
            }


        }
        public void setOnItemClickListener (ClickListener clickListener){
            SmallGroupFilterRecyclerAdapter.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(View v, int position);
        }
    }
