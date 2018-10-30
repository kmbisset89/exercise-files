package com.example.kerrymbisset.valleybeta3.MemberRelated;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kerrymbisset.valleybeta3.List_Activity;

import com.example.kerrymbisset.valleybeta3.MemberInfo;
import com.example.kerrymbisset.valleybeta3.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class MemberInfoAdapter extends RecyclerView.Adapter<MemberInfoAdapter.MemberViewHolder> {

    private final ArrayList<String> mMemberList;
    private final LayoutInflater mInflater;
    private final Context mContext;

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(
                R.layout.member_list, parent, false);
        return new MemberViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        String mCurrent = mMemberList.get(position);
        // Add the data to the view holder.
        holder.MemberItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView MemberItemView;
        final MemberInfoAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter  The adapter that manages the the data and views
         *                 for the RecyclerView.
         */
        public MemberViewHolder(View itemView, MemberInfoAdapter adapter) {
            super(itemView);
            MemberItemView = itemView.findViewById(R.id.member_name);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            Intent intent = new Intent(mContext, MemberDetailActivity.class);
                intent.putExtra("MemberName", "Kerry Bisset");
            mContext.startActivity(intent);

        }
    }

    public MemberInfoAdapter(Context context, ArrayList<String> memberList) {
        mContext= context;
        mInflater = LayoutInflater.from(context);
        this.mMemberList = memberList;

    }
}
