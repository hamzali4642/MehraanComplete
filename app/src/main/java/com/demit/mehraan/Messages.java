package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Messages extends Fragment {
    RecyclerView chatlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_messages, container, false);

        chatlist=view.findViewById(R.id.chatlistid);
        chatlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Person 1","Person 2","Person 3"};
        String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
        String[] msgs={"Recent Message","Recent Message","Recent Message"};
        int[] dp={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

        chatlist.setAdapter(new ChatListAdapter (msgs, time, name,dp));

        return view;
    }
}
