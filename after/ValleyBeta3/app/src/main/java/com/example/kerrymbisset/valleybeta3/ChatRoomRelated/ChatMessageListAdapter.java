package com.example.kerrymbisset.valleybeta3.ChatRoomRelated;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupDetails;
import com.example.kerrymbisset.valleybeta3.models.Chat_Messages;

import java.util.ArrayList;

import static com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupDetails.KEY;

public class ChatMessageListAdapter extends RecyclerView.Adapter<ChatMessageListAdapter.SmallGroupViewHolder> {

    private final Context mContext;
    private final LayoutInflater mInflator;
    private final ArrayList<Chat_Messages> mChatObject;

    private String mMessage;


    @NonNull
    @Override
    public SmallGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View mItemView = mInflator.inflate(R.layout.chat_list, parent, false);
        return new SmallGroupViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallGroupViewHolder holder, int position) {
        Chat_Messages mCurrent = mChatObject.get(position);
        mMessage = mCurrent.getMessage();
        holder.mSmallGroupNameTextView.setText(mMessage);

    }

    @Override
    public int getItemCount() {
        return mChatObject.size();
    }


    class SmallGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mSmallGroupNameTextView;

        public SmallGroupViewHolder(View itemView, ChatMessageListAdapter adapter) {
            super(itemView);
            mSmallGroupNameTextView = itemView.findViewById(R.id.text_small_group) ;
            mSmallGroupNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();


        }


    }

    public ChatMessageListAdapter(Context context, ArrayList<Chat_Messages> mChatroomName){
        mContext =context;
        mInflator = LayoutInflater.from(context);
        this.mChatObject = mChatroomName;



    }
}
