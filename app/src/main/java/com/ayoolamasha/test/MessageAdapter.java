package com.ayoolamasha.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends ListAdapter<ChatMessagePojo, MessageAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    public MessageAdapter() {
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
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        ChatMessagePojo messagePojoClass = getItem(position);
        holder.lastMessage.setText(messagePojoClass.getMessages());
        holder.lastMessageTime.setText(messagePojoClass.getTimer());

    }

    public ChatMessagePojo getMessageAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lastMessage, lastMessageTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lastMessage = itemView.findViewById(R.id.textView7);
            lastMessageTime = itemView.findViewById(R.id.textView8);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION){
                    onItemClickListener.onItemClick(getItem(position));
                }
            });
        }
    }


    public interface OnItemClickListener{
        void onItemClick(ChatMessagePojo chatMessagePojo);
    }

    public void setOnClickListener(OnItemClickListener onClickListener){
        this.onItemClickListener = onClickListener;
    }
}
