package com.ayoolamasha.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {

    private ChatRepository repository;
    private LiveData<List<ChatMessagePojo>> listLiveData;
    private LiveData<List<ChatMessagePojo>> getListChatLiveData;




    public ChatViewModel(@NonNull Application application, String params) {
        super(application);
        repository = new ChatRepository(application,params);
        listLiveData = repository.getAllMessagePojoListRepo();
        getListChatLiveData = repository.getAllLastMessagePojoListRepo();
    }

    public void insertChatViewModel(ChatMessagePojo chatMessagePojo){
        repository.saveChatRepo(chatMessagePojo);
    }

    public void updateChatViewModel(ChatMessagePojo chatMessagePojo){
        repository.updateChatRepo(chatMessagePojo);
    }

    public void deleteAChatViewModel(ChatMessagePojo chatMessagePojo){
        repository.deleteAChatRepo(chatMessagePojo);
    }

    public void deleteAllChatViewModel(){
        repository.deleteAllChatRepo();
    }

    public LiveData<List<ChatMessagePojo>> getAllChatViewModel() {
        return listLiveData;
    }

    public LiveData<List<ChatMessagePojo>> getAllLastChatViewModel() {
        return getListChatLiveData;
    }
}
