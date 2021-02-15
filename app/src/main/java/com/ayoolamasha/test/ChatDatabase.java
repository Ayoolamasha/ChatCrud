package com.ayoolamasha.test;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ChatMessagePojo.class}, version = 2)
public abstract class ChatDatabase  extends RoomDatabase{

    public abstract ChatDao chatDao();
    private static ChatDatabase Instance;
    private static final String DATABASE_NAME = "chat_table";
    public static final int NUMBER_THREADS = 5;
    public static ExecutorService databaseWriter = Executors.newFixedThreadPool(NUMBER_THREADS);

    public static synchronized ChatDatabase getInstance(Context context){

        if (Instance == null){
            Instance = Room.databaseBuilder(context.getApplicationContext(),
                    ChatDatabase.class, DATABASE_NAME)
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return Instance;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
