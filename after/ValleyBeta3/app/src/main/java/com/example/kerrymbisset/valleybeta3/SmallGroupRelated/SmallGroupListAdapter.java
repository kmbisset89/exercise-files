package com.example.kerrymbisset.valleybeta3.SmallGroupRelated;

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

import static com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupDetails.KEY;

public class SmallGroupListAdapter extends RecyclerView.Adapter<SmallGroupListAdapter.SmallGroupViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflator;
    private final ArrayList<String> mSmallGroupList;
    private final ArrayList<String> mSmallGroupKeys;

    @NonNull
    @Override
    public SmallGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View mItemView = mInflator.inflate(R.layout.small_group_list, parent, false);
        return new SmallGroupViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallGroupViewHolder holder, int position) {
        String mCurrent = mSmallGroupList.get(position);
        holder.mSmallGroupNameTextView.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return mSmallGroupList.size();
    }


    class SmallGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mSmallGroupNameTextView;

        public SmallGroupViewHolder(View itemView, SmallGroupListAdapter adapter) {
            super(itemView);
            mSmallGroupNameTextView = itemView.findViewById(R.id.text_small_group) ;
            mSmallGroupNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();

            Intent intent = new Intent(mContext, SmallGroupDetails.class);
            intent.putExtra(KEY, mSmallGroupKeys.get(mPosition));
            mContext.startActivity(intent);

        }


    }

    public SmallGroupListAdapter(Context context, ArrayList<String> smallGroupList, ArrayList<String> smallGroupKeys){
        mContext =context;
        mInflator = LayoutInflater.from(context);
        this.mSmallGroupList = smallGroupList;
        this.mSmallGroupKeys = smallGroupKeys;
    }
}
