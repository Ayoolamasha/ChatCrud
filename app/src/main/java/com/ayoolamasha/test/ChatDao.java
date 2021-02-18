package com.ayoolamasha.test;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDao {

    @Insert
    void insertChatDao(ChatMessagePojo chatMessagePojo);

    @Update
    void updateChatDao(ChatMessagePojo chatMessagePojo);

    @Delete
    void deleteChatDao(ChatMessagePojo chatMessagePojo);

    @Query("DELETE FROM chat_table")
    void deleteAllChatDao();

    @Query("SELECT * FROM chat_table ORDER BY chatId ")
    LiveData<List<ChatMessagePojo>> getAllChatDao();

    @Query("SELECT * FROM chat_table ORDER BY timer DESC")
    LiveData<List<ChatMessagePojo>> getAllLastChatDao();

    @Query("SELECT * FROM chat_table WHERE receiverId = :userId ORDER BY chatId")
    LiveData<List<ChatMessagePojo>> getAllChat(String userId);

    @Query("SELECT * FROM chat_table group by receiverId ORDER BY timer DESC")
    LiveData<List<ChatMessagePojo>> getAllLastChat();
}
