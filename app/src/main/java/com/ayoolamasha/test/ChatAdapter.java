package com.ayoolamasha.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends ListAdapter<ChatMessagePojo, ChatAdapter.CustomViewHolder> {
    private ArrayList<ChatMessagePojo> chatMessagePojoArrayList = new ArrayList<>();


    public ChatAdapter() {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<ChatMessagePojo> diffCallback = new DiffUtil.ItemCallback<ChatMessagePojo>() {
        @Override
        public boolean areItemsTheSame(@NonNull ChatMessagePojo oldItem, @NonNull ChatMessagePojo newItem) {
            return oldItem.getChatId() == newItem.getChatId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ChatMessagePojo oldItem, @NonNull ChatMessagePojo newItem) {
            return oldItem.getMessages().equals(newItem.getMessages()) &&
                    oldItem.getTimer().equals(newItem.getTimer());
        }
    };


    @NonNull
    @Override
    public ChatAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_receive, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.CustomViewHolder holder, int position) {
        ChatMessagePojo chatMessagePojo = getItem(position);
        holder.message.setText(chatMessagePojo.getMessages());
        holder.timeLeft.setText(chatMessagePojo.getTimer());

    }

    /*@Override
    public int getItemCount() {
        return chatMessagePojoArrayList.size();
    }*/

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView message, timeLeft;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.messageReceived);
            timeLeft = itemView.findViewById(R.id.timeSent);
        }
    }
}
