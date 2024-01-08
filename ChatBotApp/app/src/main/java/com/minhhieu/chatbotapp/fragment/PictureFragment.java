package com.minhhieu.chatbotapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.minhhieu.chatbotapp.R;
import com.minhhieu.chatbotapp.activity.MyLibraryActivity;


public class PictureFragment extends Fragment {
    Activity context;
    private ImageView img_my_library;

    public PictureFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        ImageView img_searching_pic = view.findViewById(R.id.img_searching_pic);

        img_searching_pic.setOnClickListener(v -> {
            Fragment frag = new SearchPicFragment();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, frag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        });

        return view;
    }
    public void onStart(){
        super.onStart();
        img_my_library = context.findViewById(R.id.img_my_library);

        img_my_library.setOnClickListener(v -> {
            Intent intent = new Intent(context, MyLibraryActivity.class);
            startActivity(intent);

        });
    }
}