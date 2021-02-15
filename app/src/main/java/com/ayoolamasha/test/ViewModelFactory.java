package com.ayoolamasha.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class ViewModelFactory implements ViewModelProvider.Factory {


    @NonNull
    private final Application application;

    @NonNull
    private final String params;


    public ViewModelFactory(@NonNull Application application) {

        this.application = application;
        params = null;
    }

    public ViewModelFactory(@NonNull Application application, @NonNull String params) {

        this.application = application;
        this.params = params;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


        if(modelClass == ChatViewModel.class) {
            return (T) new ChatViewModel(application);
        }
        return  null;
    }
}
