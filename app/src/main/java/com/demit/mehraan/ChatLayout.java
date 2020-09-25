package com.demit.mehraan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demit.mehraan.Adapter.ChatAdapter;
import com.demit.mehraan.Model.ChatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class ChatLayout extends Fragment {

    ChatView chatView ;
    ImageView backchat;
    String senderid,recieverid,sendername,recievername;
    DatabaseReference Rootref,RootRef;
    boolean check=true;
    ChatAdapter chatListAdapter;
    List<ChatModel> list=new ArrayList<>();
    EditText messageet;
    TextView personname;
    FloatingActionButton send;
    RecyclerView chatlist;

    boolean check1;
    public ChatLayout(String recieverid, String recievername) {
        this.recieverid = recieverid;
        this.recievername = recievername;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_chat_layout, container, false);
        personname=view.findViewById(R.id.txt);
        personname.setText(recievername);
       check=true;
        backchat=view.findViewById(R.id.backchatid);
        chatView= (ChatView) view.findViewById(R.id.chat_view);
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        String token;
        token=sharedpreferences.getString("token","");

        try {
            senderid=jwtverifier(token,"Id");
            sendername=jwtverifier(token,"unique_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        send=view.findViewById(R.id.btn_send);
        chatlist=view.findViewById(R.id.chatlistt);
        messageet=view.findViewById(R.id.text_content);
        Rootref=FirebaseDatabase.getInstance().getReference();
        RootRef=FirebaseDatabase.getInstance().getReference();


        list = new ArrayList<>();
        chatlist.setLayoutManager(new LinearLayoutManager(getContext()));
        chatListAdapter = new ChatAdapter(getContext(), list,recieverid);
        chatlist.setAdapter(chatListAdapter);



        backchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getContext(),Dashboard.class);
                intent.putExtra("back",3);
                startActivity(intent);


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
                messageet.setText("");
            }
        });
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


    @Override
    public void onStart() {
        super.onStart();




        list.clear();
        RootRef.child("Messages").child(senderid).child(recieverid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatModel messages = dataSnapshot.getValue(ChatModel.class);
                    if(check)
                    {if(messages.from.equals(recieverid))
                    {
                        if(messages.status.equals("false")){
                            RootRef.child("Messages").child(senderid).child(recieverid)
                                    .child(messages.messageId).child("status").setValue("true");

                            RootRef.child("Messages").child(recieverid).child(senderid)
                                    .child(messages.messageId).child("status").setValue("true");
                        }
                    }}
                    list.add(messages);


//                        try{
//                            if(list.size()>1) {
//                                for (int i = 0; i < list.size() - 1; i++) {
//
//                                    if (list.get(list.size()-1).getMessageId().equals(list.get(i).getMessageId())) {
//                                        list.remove(list.size()-1);
//                                    } else {
//
//                                        chatListAdapter.notifyDataSetChanged();
//                                    }
//
//                                }
//                            }else {
                    chatListAdapter.notifyDataSetChanged();
//                            }
//                        }catch (Exception e){
//
//                        }


                    //myAdapter.notifyDataSetChanged();

                    chatlist.smoothScrollToPosition(chatlist.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Rootref.child("Messages").child(senderid).child(recieverid)
//                .addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
////
//
//                        ChatModel messages = dataSnapshot.getValue(ChatModel.class);
//                        if(check)
//                        {if(messages.from.equals(recieverid))
//                        {
//                            if(messages.status.equals("false")){
//                                RootRef.child("Messages").child(senderid).child(recieverid)
//                                        .child(messages.messageId).child("status").setValue("true");
//
//                                RootRef.child("Messages").child(senderid).child(recieverid)
//                                        .child(messages.messageId).child("status").setValue("true");
//                            }
//                        }}
//                        list.add(messages);
//
//
////                        try{
////                            if(list.size()>1) {
////                                for (int i = 0; i < list.size() - 1; i++) {
////
////                                    if (list.get(list.size()-1).getMessageId().equals(list.get(i).getMessageId())) {
////                                        list.remove(list.size()-1);
////                                    } else {
////
////                                        chatListAdapter.notifyDataSetChanged();
////                                    }
////
////                                }
////                            }else {
//                                chatListAdapter.notifyDataSetChanged();
////                            }
////                        }catch (Exception e){
////
////                        }
//
//
//                        //myAdapter.notifyDataSetChanged();
//
//                        chatlist.smoothScrollToPosition(chatlist.getAdapter().getItemCount());
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


    }







    private void SendMessage() {

        check1=true;
        final String messageText = messageet.getText().toString().trim();
        if (TextUtils.isEmpty(messageText)) {
//            Toast.makeText(this, "Please write message first", Toast.LENGTH_SHORT).show();
        } else {
            String messageSenderRef = "Messages/" + senderid + "/" + recieverid;
            String messageRecieverRef = "Messages/" + recieverid + "/" + senderid;

            DatabaseReference userMessageKeyRef = RootRef.child("Messages")
                    .child(senderid).child(recieverid).push();

            String savecurrenttime, savecurrentdate;
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd ");
            savecurrentdate = currentdate.format(calendar.getTime());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat currenttime= new SimpleDateFormat("hh:mm a");
            savecurrenttime = currenttime.format(calendar.getTime());




            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", senderid);
            messageTextBody.put("messageId",messagePushID);
            messageTextBody.put("time",savecurrenttime);
            messageTextBody.put("date",savecurrentdate);
            messageTextBody.put("status","false");

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put(messageRecieverRef + "/" + messagePushID, messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
//                        Toast.makeText(getContext(), "Message Sent Successfully ....", Toast.LENGTH_SHORT).show();

                        final String[] child_token = new String[1];
                        DatabaseReference mreference = FirebaseDatabase.getInstance().getReference("Users")
                                .child(recieverid);
                        mreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                //Toast.makeText(Chat.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                                if(dataSnapshot.exists())
                                {
                                    child_token[0] = dataSnapshot.child("token").getValue().toString();
                                if (child_token[0] != null) {

                                    if(check1){
                                    FirebaseNotificationHelper.initialize
                                            ("AAAApQwQsk4:APA91bFThYxZwYxY-2tthXBCssc9RJwfrPVi8_RGvRnm7XKRtfiKBzGx1ZC44VmTbPSxDdTiGzjjm96Cf76PPwJgpcnc51PkBrmUkPTG6leiRUD8JfybHAm8i5UtKbQRl1AoBX8VaDTn")
                                            .defaultJson(true, null)
                                            .title(sendername)
                                            .message(messageText)
                                            .receiverFirebaseToken(child_token[0])
                                            .send();
                                        check1=false;
                                    }
                                }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                        CharSequence s,a;
                        String date,time;
                        Date d = new Date();
                        s = DateFormat.format("dd MMM", d.getTime());
                        date = String.valueOf(s);
                        a = DateFormat.format("HH:mm", d.getTime());
                        time = String.valueOf(a);

                        DatabaseReference reference , reference1;
                        reference=FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(senderid);
                        reference1=FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(recieverid);
                        reference.child("lastmsg").child(recieverid).child("last").setValue(date+" "+time);
                        reference.child("lastmsg").child(recieverid).child("lastmessage").setValue(messageText);
                        reference.child("lastmsg").child(recieverid).child("lastmessagetime").setValue(System.currentTimeMillis());

                        reference1.child("lastmsg").child(senderid).child("last").setValue(date+" "+time);
                        reference1.child("lastmsg").child(senderid).child("lastmessage").setValue(messageText);
                        reference1.child("lastmsg").child(senderid).child("lastmessagetime").setValue(System.currentTimeMillis());





                    } else {
//                        Toast.makeText(getContext(), "Message Not Sent ....", Toast.LENGTH_SHORT).show();
                    }
                    //inputMessage.setText("");
                }
            });
        }
    }

    public String jwtverifier(String token,String key) throws JSONException {
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
        return  object.getString(key);
    }

    @Override
    public void onPause() {
        check=false;
        super.onPause();
    }

    @Override
    public void onResume() {
        check=true;
        super.onResume();
    }

    @Override
    public void onStop() {
        check=false;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        check=false;
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        check=true;
        super.onAttach(context);
    }


}
