package com.ayoolamasha.test;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ChatRepository {
    private ChatDao chatDao;
    private LiveData<List<ChatMessagePojo>> messagePojoList;
    private LiveData<List<ChatMessagePojo>> lastMessagePojoList;



    public ChatRepository(Application application,String receiverId) {
        ChatDatabase chatDatabase = ChatDatabase.getInstance(application);
        chatDao = chatDatabase.chatDao();
        messagePojoList = chatDao.getAllChat(receiverId);
        lastMessagePojoList = chatDao.getAllLastChat();
    }




    public void saveChatRepo(ChatMessagePojo chatMessagePojo){
        ChatDatabase.databaseWriter.execute(() -> chatDao.insertChatDao(chatMessagePojo));
    }

    public void updateChatRepo(ChatMessagePojo chatMessagePojo){
        ChatDatabase.databaseWriter.execute(() -> chatDao.updateChatDao(chatMessagePojo));
    }

    public void deleteAChatRepo(ChatMessagePojo chatMessagePojo){
        ChatDatabase.databaseWriter.execute(() -> chatDao.deleteChatDao(chatMessagePojo));
    }

    public void deleteAllChatRepo(){
        ChatDatabase.databaseWriter.execute(() -> chatDao.deleteAllChatDao());
    }

    public LiveData<List<ChatMessagePojo>> getAllMessagePojoListRepo(){
        return messagePojoList;
    }

    public LiveData<List<ChatMessagePojo>> getAllLastMessagePojoListRepo(){
        return lastMessagePojoList;
    }
}
