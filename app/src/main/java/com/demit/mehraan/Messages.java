package com.demit.mehraan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Messages extends Fragment {
    RecyclerView chatlist;
    List<UserInfo> user;
    ChatListAdapter chatListAdapter;
    String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
    String[] msgs={"Recent Message","Recent Message","Recent Message"};
    int[] dp={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_messages, container, false);

        user=new ArrayList<>();
        readUser();

        chatlist=view.findViewById(R.id.chatlistid);
        chatlist.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void readUser() {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserInfo userInfo= dataSnapshot.getValue(UserInfo.class);
                    assert userInfo != null;
                    if (!userInfo.getDisplayName().equals(firebaseUser.getUid())) {
                        user .add(userInfo);

                    }
                }

                chatListAdapter=new ChatListAdapter(getContext(), msgs, time, user ,dp);
                chatlist.setAdapter(chatListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
