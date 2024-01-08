package com.minhhieu.chatbotapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhhieu.chatbotapp.R;
import com.minhhieu.chatbotapp.myclass.ResponsePic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResponsePicAdapter extends RecyclerView.Adapter<ResponsePicAdapter.MyViewHolder>{

    List<ResponsePic> responsePics;
    public ResponsePicAdapter(List<ResponsePic> responsePics) {
        this.responsePics = responsePics;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ResponsePic> responsePics){
        this.responsePics = responsePics;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResponsePicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View chatView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.iteme_chat_generate, null);
        ResponsePicAdapter.MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResponsePicAdapter.MyViewHolder holder, int position) {
        ResponsePic responsePic = responsePics.get(position);
        if (responsePic.getSentBy().equals(ResponsePic.SENT_BY_ME)){
            holder.left_chat_response.setVisibility(View.GONE);
            holder.right_pic_view.setVisibility(View.VISIBLE);
            holder.right_chat_pic_view.setText(responsePic.getMessage());
        }else {
            holder.right_pic_view.setVisibility(View.GONE);
            holder.left_chat_response.setVisibility(View.VISIBLE);
            Picasso.get().load(responsePic.getImageUrl()).into(holder.left_chat_response);

        }
    }

    @Override
    public int getItemCount() {
        return responsePics.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView left_chat_response;
        TextView right_chat_pic_view;
        LinearLayout right_pic_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            left_chat_response = itemView.findViewById(R.id.left_chat_response);
            right_pic_view = itemView.findViewById(R.id.right_pic_view);
            right_chat_pic_view = itemView.findViewById(R.id.right_chat_pic_view);


        }
    }
}