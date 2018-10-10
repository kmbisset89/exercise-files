package com.example.kerrymbisset.valleybeta3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MembersFilterRecyclerAdapter extends RecyclerView.Adapter<MembersFilterRecyclerAdapter.ViewHolder> {
    private final LayoutInflater mInflator;
    List<MemberFilter> MemberFilter;
    private static ClickListener clickListener;


    MembersFilterRecyclerAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = mInflator.inflate(R.layout.member_filter_list, parent, false);
        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (MemberFilter != null) {
            MemberFilter current2 = MemberFilter.get(position);
            holder.mMemberFilterName.setText(current2.getFilterName());

        }
    }

        /**
         * Associates a list of words with this adapter
         */
        void setSGFilter (List < MemberFilter > memberFilter) {
            MemberFilter = memberFilter;
            notifyDataSetChanged();
        }

        /**
         * getItemCount() is called many times, and when it is first called,
         * mWords has not been updated (means initially, it's null, and we can't return null).
         */
        @Override
        public int getItemCount () {
            if (MemberFilter != null)
                return MemberFilter.size();
            else return 0;
        }

    public MemberFilter getMemberAtPosition(int position) {
        return MemberFilter.get(position);
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mMemberFilterName;



            private ViewHolder(final View itemView) {
                super(itemView);
                mMemberFilterName = itemView.findViewById(R.id.text_member_filter);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v, getAdapterPosition());
                    }
                });
            }


        }
        public void setOnItemClickListener (ClickListener clickListener){
            MembersFilterRecyclerAdapter.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(View v, int position);
        }
    }
