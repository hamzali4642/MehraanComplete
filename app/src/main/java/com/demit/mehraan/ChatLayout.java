package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class ChatLayout extends Fragment {

    ChatView chatView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_chat_layout, container, false);

       chatView= (ChatView) view.findViewById(R.id.chat_view);

        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                // perform actual message sending
                return true;
            }

        });


        chatView.setTypingListener(new ChatView.TypingListener(){
            @Override
            public void userStartedTyping(){
                // will be called when the user starts typing
            }

            @Override
            public void userStoppedTyping(){
                // will be called when the user stops typing
            }
        });


       return view;
    }
}
