package com.example.kerrymbisset.valleybeta3.SmallGroupRelated;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kerrymbisset.valleybeta3.IDialogListener;
import com.example.kerrymbisset.valleybeta3.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SmallGroupDialogAdapter extends RecyclerView.Adapter<SmallGroupDialogAdapter.ViewHolder>  {
    private static final String TAG = "SGDRecyclerViewAdapter";
    private final ArrayList<String> mSmallGroupName;
    private final ArrayList<String> mSmallGroupNumber;
    private final Context mContext;
    private IDialogListener listener;

    public SmallGroupDialogAdapter(Context context, ArrayList<String> SmallGroupNames, ArrayList<String> SmallGroupNumber) {
        this.mSmallGroupName = SmallGroupNames;
        this.mSmallGroupNumber = SmallGroupNumber;
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        try {
            listener = (IDialogListener) mContext;
        } catch (ClassCastException e) {
            throw  new ClassCastException(recyclerView.toString() +"something went wrong");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_group_list, parent, false);
        ViewHolder holder = new ViewHolder(views);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mSmallGroupHolder.setText(mSmallGroupName.get(position));
        holder.cardView.setOnClickListener(v -> {

            Log.d(TAG, "onClick: clicked on: " + mSmallGroupName.get(position));

            listener.getSmallGroupData(mSmallGroupName.get(position), mSmallGroupNumber.get(position));

        });

    }


    @Override
    public int getItemCount() {
        return mSmallGroupName.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        public final TextView mSmallGroupHolder;
        CardView cardView;

        public ViewHolder (View itemView)
        {
            super(itemView);
            mSmallGroupHolder =itemView.findViewById(R.id.text_small_group);
            cardView = itemView.findViewById(R.id.sg_card_view);
        }


    }

}
