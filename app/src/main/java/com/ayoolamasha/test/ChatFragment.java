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

    private static final String EXTRA_ID = "com.ayoolamasha.test.EXTRA_ID";
    private static final String EXTRA_MESSAGE = "com.ayoolamasha.test.EXTRA_MESSAGE";
    private static final String EXTRA_TIME = "com.ayoolamasha.test.EXTRA_TIME";


    private static ChatFragment INSTANCE;
    private RecyclerView recyclerView;
    private Button sendMessage, mBackArrow, fetchGallery;
    //private EditText inputText;
    private TextInputEditText inputText;
    private String mReceiverId;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessagePojo> chatMessagePojoArrayList = new ArrayList<>();
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

        mChatViewModel = new ViewModelProvider(this, new ViewModelFactory(getActivity().getApplication(),mReceiverId)).get(ChatViewModel.class);



//        mChatViewModel.getAllChatViewModel().observe(getViewLifecycleOwner(), chatMessagePojos ->
//                chatMessagePojoArrayList.add((ChatMessagePojo) chatMessagePojos));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_layout, container, false);

        initViews(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatAdapter = new ChatAdapter(chatMessagePojoArrayList);
        recyclerView.setAdapter(chatAdapter);

        mChatViewModel.getAllChatViewModel().observe(this, new Observer<List<ChatMessagePojo>>() {
            @Override
            public void onChanged(List<ChatMessagePojo> chatMessagePojos) {
                if (chatMessagePojos == null){

                    Toast.makeText(getActivity(), "Message EMPTY" , Toast.LENGTH_SHORT).show();

                }else{
                    chatAdapter.submitList(chatMessagePojos);
                    Toast.makeText(getActivity(), "Message FETECHED" , Toast.LENGTH_SHORT).show();

                }

            }
        });






        sendMessage.setOnClickListener(this);
//
//        Bundle bundle = this.getArguments();
//        assert bundle != null;
//        bundle.getString("Message");
//        if (bundle.getString("id").equals(EXTRA_ID)){
//            mChatViewModel.getAllChatViewModel().observe(this, new Observer<List<ChatMessagePojo>>() {
//                @Override
//                public void onChanged(List<ChatMessagePojo> chatMessagePojos) {
//                    chatAdapter.submitList(chatMessagePojos);
//                }
//            });
//
//
//        }




        return view;
    }

    private void initViews(View view){
        recyclerView = view.findViewById(R.id.chatsRecycler);
        inputText = view.findViewById(R.id.typeMessage);
        sendMessage = view.findViewById(R.id.sendMessage);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendMessage:

                saveChat();

//
//                Bundle bundle = this.getArguments();
//                assert bundle != null;
//                //bundle.getString("Message");
//                int id = bundle.getInt("id", -1);
//                if (id == -1){
//
//                    Toast.makeText(getActivity(), "Messaged Cannot be updated", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    updateChat();
//                }



                break;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        INSTANCE = null;
        ChatFragment chatFragment = new ChatFragment();
        MessageFragment messageFragment = new MessageFragment();
        loadFragmentWithoutBackstack(chatFragment, messageFragment);

        //passChat();
    }


    private void saveChat(){

        message = inputText.getText().toString().trim();
        if (message!= null && !TextUtils.isEmpty(message)){

            // time sent
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            leftTimer = simpleDateFormat.format(date);
//                    chatList.add(new String[]{message, leftTimer});

            // message
            ChatMessagePojo messageSent = new ChatMessagePojo(message, leftTimer,"id2");
            chatMessagePojoArrayList.add(messageSent);
            //chatAdapter.notifyDataSetChanged();
            //mChatViewModel.sendMessage(messageSent);
            mChatViewModel.insertChatViewModel(messageSent);
            Toast.makeText(getActivity(), "Messaged Saved " + message, Toast.LENGTH_SHORT).show();
            inputText.setText("");
            if (chatAdapter != null)
                chatAdapter.notifyDataSetChanged();

        }else{
            Toast.makeText(getActivity(), "No Message", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateChat(){
        message = inputText.getText().toString().trim();

        if (message!= null && !TextUtils.isEmpty(message)){

            // time sent
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            leftTimer = simpleDateFormat.format(date);
//                    chatList.add(new String[]{message, leftTimer});

            // message
            ChatMessagePojo messageSent = new ChatMessagePojo(message, leftTimer,"id");
            chatMessagePojoArrayList.add(messageSent);
            //chatAdapter.notifyDataSetChanged();
            //mChatViewModel.sendMessage(messageSent);
            mChatViewModel.updateChatViewModel(messageSent);
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
