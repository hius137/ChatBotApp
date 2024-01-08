package com.minhhieu.chatbotapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.minhhieu.chatbotapp.R;
import com.minhhieu.chatbotapp.activity.ChatBotActivity;


public class HomeChatFragment extends Fragment {
    Activity context;

    public HomeChatFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_home_chat, container, false);

        return view;
    }
    public void onStart(){
        super.onStart();
        ImageView img_start_new_conver = context.findViewById(R.id.img_start_new_conver);

        img_start_new_conver.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatBotActivity.class);
            startActivity(intent);

        });
    }

}