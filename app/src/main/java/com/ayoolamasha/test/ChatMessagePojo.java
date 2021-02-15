package com.ayoolamasha.test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "chat_table")
public class ChatMessagePojo {

    @PrimaryKey(autoGenerate = true)
    private int chatId;
    private String messages;
    private String timer;


    public ChatMessagePojo(String messages, String timer) {
        this.messages = messages;
        this.timer = timer;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }
}
