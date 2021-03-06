package com.ayoolamasha.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MessageFragment extends Fragment {

    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ImageView deleteAll;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatViewModel = new ViewModelProvider(this,
                new ViewModelFactory(Objects.requireNonNull(getActivity()).getApplication())).get(ChatViewModel.class);
        chatViewModel.getAllLastChatViewModel().observe(this, new Observer<List<ChatMessagePojo>>() {
            @Override
            public void onChanged(List<ChatMessagePojo> chatMessagePojos) {
                messageAdapter.submitList(chatMessagePojos);
            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_layout, container, false);


        initView(view);


        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatViewModel.deleteAllChatViewModel();
            }
        });


        messageAdapter.setOnClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChatMessagePojo chatMessagePojo) {
        

                ChatFragment chatFragment = ChatFragment.newInstance(chatMessagePojo.getReceiverId());
                loadFragment(chatFragment);

                Toast.makeText(getActivity(), "Message Clicked", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.messageRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageAdapter = new MessageAdapter();
        recyclerView.setAdapter(messageAdapter);
        deleteAll = view.findViewById(R.id.imageView3);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.isAddToBackStackAllowed();
        transaction.commit();

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
