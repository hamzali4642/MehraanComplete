package com.demit.mehraan;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demit.mehraan.Adapter.MessageAdapter;
import com.demit.mehraan.Model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Messages extends Fragment {
    RecyclerView chatlist;
    List<UserInfo> user;
    ChatListAdapter chatListAdapter;
    String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
    String[] msgs={"Recent Message","Recent Message","Recent Message"};
    int[] dp={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

    String userid;


    List<MessageModel> mlist=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_messages, container, false);

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String token;
        token=sharedpreferences.getString("token","");

        try {
            userid = jwtverifier(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user=new ArrayList<>();
        readUser();

        chatlist=view.findViewById(R.id.chatlistid);
        chatlist.setLayoutManager(new LinearLayoutManager(getContext()));

//''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
        DatabaseReference chatsreference = FirebaseDatabase.getInstance().getReference().child("Messages").child(userid);

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users");

                chatsreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Log.d(dataSnapshot.toString());
//                mlist.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mlist.clear();
                    String id=snapshot.getKey();
                    userref.child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            MessageModel modelInbox=new MessageModel();//=dataSnapshot.getValue(MessageModel.class);

                            modelInbox.setId
                                    (dataSnapshot.child("id").getValue().toString());

                            modelInbox.setName
                                    (dataSnapshot.child("name").getValue().toString());

                            modelInbox.setToken
                                    (dataSnapshot.child("token").getValue().toString());

                            if(dataSnapshot.hasChild("lastmsg"))
                            {

                            modelInbox.setLastmessagetime
                                    (dataSnapshot.child("lastmsg").child(userid).child("lastmessagetime").getValue().toString());

                            modelInbox.setLastmsgtime
                                    (dataSnapshot.child("lastmsg").child(userid).child("last").getValue().toString());
                            modelInbox.setLastmsg
                                    (dataSnapshot.child("lastmsg").child(userid).child("lastmessage").getValue().toString());





                                mlist.add(modelInbox);

                                try{
                                    if(mlist.size()>1) {
                                        for (int i = 0; i < mlist.size() - 1; i++) {

                                            if (mlist.get(mlist.size()-1).getId().equals(mlist.get(i).getId())) {
                                                mlist.set(i,mlist.get(mlist.size()-1));
                                                mlist.remove(mlist.size()-1);
                                            } else {


                                chatlist.setLayoutManager(new LinearLayoutManager(getContext()));
                                                MessageAdapter adapter=new MessageAdapter(quickSort(mlist,0,mlist.size()-1));
                                                chatlist.setAdapter(adapter);

                                                adapter.notifyDataSetChanged();
                                            }

                                        }
                                    }else {
                                        MessageAdapter adapter=new MessageAdapter(mlist);
                                        adapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){

                                }
//                                List<MessageModel> list=;


                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//.......................................


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





    static int partition(List<MessageModel> array, int begin, int end) {
        int pivot = end;

        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (Long.parseLong(array.get(i).getLastmessagetime()) > Long.parseLong(array.get(pivot).getLastmessagetime())) {
                MessageModel temp = array.get(counter);
                array.set(counter,array.get(i))  ;
                array.set(i ,temp);
                counter++;
            }
        }
        MessageModel temp = array.get(pivot);
        array.set(pivot, array.get(counter));
        array.set(counter, temp);

        return counter;
    }

    public static List<MessageModel> quickSort(List<MessageModel> array, int begin, int end) {
        if (end <= begin) return array;
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot-1);
        quickSort(array, pivot+1, end);

        return array;
    }

    public String jwtverifier(String token) throws JSONException {
        String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        // Base64 base64Url = new Base64(true);

        String header = new String(Base64.decode(base64EncodedHeader,Base64.DEFAULT));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(Base64.decode(base64EncodedBody,Base64.DEFAULT));
        System.out.println("JWT Body : "+body);
        JSONObject object=new JSONObject(body);
        return  object.getString("Id");
    }
}
