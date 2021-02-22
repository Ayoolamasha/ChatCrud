package com.ayoolamasha.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatFragment extends Fragment implements View.OnClickListener{

    private static ChatFragment INSTANCE;
    private RecyclerView recyclerView;
    private Button sendMessage;
    private TextInputEditText inputText;
    private String mReceiverId;
    private ChatAdapter chatAdapter;
    private String message, leftTimer;

    private ChatViewModel mChatViewModel;


    public static ChatFragment newInstance(String receiverId) {
        if (INSTANCE == null) {
            return new ChatFragment(receiverId);
        }
        return INSTANCE;
    }

    private ChatFragment(String receiverId) {
        this.mReceiverId = receiverId;
    }

    private ChatFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChatViewModel = new ViewModelProvider(this, new ViewModelFactory(getActivity().getApplication(),mReceiverId))
                .get(ChatViewModel.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_layout, container, false);

        initViews(view);

        mChatViewModel.getAllChatViewModel().observe(this, new Observer<List<ChatMessagePojo>>() {
            @Override
            public void onChanged(List<ChatMessagePojo> chatMessagePojos) {
                chatAdapter.submitList(chatMessagePojos);
            }
        });

        sendMessage.setOnClickListener(this);

        return view;
    }

    private void initViews(View view){
        recyclerView = view.findViewById(R.id.chatsRecycler);
        inputText = view.findViewById(R.id.typeMessage);
        sendMessage = view.findViewById(R.id.sendMessage);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatAdapter = new ChatAdapter();
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendMessage:
                saveChat();
                break;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ChatFragment chatFragment = new ChatFragment();
        MessageFragment messageFragment = new MessageFragment();
        loadFragmentWithoutBackstack(chatFragment, messageFragment);

    }


    private void saveChat(){

        message = inputText.getText().toString().trim();

        if (message!= null && !TextUtils.isEmpty(message)){

            // time sent
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
            leftTimer = simpleDateFormat.format(date);

            // message
            ChatMessagePojo messageSent = new ChatMessagePojo(message, leftTimer,"id3");
            mChatViewModel.insertChatViewModel(messageSent);
            Toast.makeText(getActivity(), "Messaged Saved " + message, Toast.LENGTH_SHORT).show();
            inputText.setText("");
            if (chatAdapter != null)
                chatAdapter.notifyDataSetChanged();

        }else{
            Toast.makeText(getActivity(), "No Message", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.isAddToBackStackAllowed();
        transaction.commit();

    }

    private void loadFragmentWithoutBackstack(Fragment currentFragment, Fragment nextFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, nextFragment);
        transaction.remove(currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
