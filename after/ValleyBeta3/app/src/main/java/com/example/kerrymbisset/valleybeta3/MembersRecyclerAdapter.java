package com.example.kerrymbisset.valleybeta3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MembersRecyclerAdapter extends RecyclerView.Adapter<MembersRecyclerAdapter.ViewHolder> {
    private final LayoutInflater mInflator;
    List<MemberInfo> MemberInfo;
    private static ClickListener clickListener;


    MembersRecyclerAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }



    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View itemView = mInflator.inflate(R.layout.member_list, parent, false);
        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (MemberInfo != null) {
            MemberInfo current = MemberInfo.get(position);
            holder.mMemberTitle.setText(current.getMemberName());

        }
    }

        /**
         * Associates a list of words with this adapter
         */
        void setMemberInfo (List < MemberInfo > memberInfo) {
            MemberInfo = memberInfo;
            notifyDataSetChanged();
        }

        /**
         * getItemCount() is called many times, and when it is first called,
         * mWords has not been updated (means initially, it's null, and we can't return null).
         */
        @Override
        public int getItemCount () {
            if (MemberInfo != null)
                return MemberInfo.size();
            else return 0;
        }

    public MemberInfo getMemberInfoAtPosition(int position) {
        return MemberInfo.get(position);
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView mMemberTitle;


            private ViewHolder(final View itemView) {
                super(itemView);
                mMemberTitle = itemView.findViewById(R.id.member_name);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v, getAdapterPosition());
                    }
                });
            }


        }
        public void setOnItemClickListener (ClickListener clickListener){
            MembersRecyclerAdapter.clickListener = clickListener;
        }

        public interface ClickListener {
            void onItemClick(View v, int position);
        }
    }
