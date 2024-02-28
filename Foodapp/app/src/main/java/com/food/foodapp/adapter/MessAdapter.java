package com.food.foodapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.food.foodapp.Message;
import com.food.foodapp.R;

import java.util.List;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.MyViewHolder> {
    List<Message> messageList;
    public MessAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatgpt,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSentBy().equals(Message.SENT_BY_ME)){
            holder.leftmess.setVisibility(View.GONE);
            holder.rightmess.setVisibility(View.VISIBLE);
            holder.rightmess.setText(message.getMessage());
        }else {
            holder.rightmess.setVisibility(View.GONE);
            holder.leftmess.setVisibility(View.VISIBLE);
            holder.leftmess.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout chatleft, chatright;
        TextView rightmess, leftmess;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rightmess = itemView.findViewById(R.id.rightmess);
            leftmess = itemView.findViewById(R.id.leftmess);
            chatright = itemView.findViewById(R.id.rightchat);
            chatleft = itemView.findViewById(R.id.leftchat);
        }
    }
}
