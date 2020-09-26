package com.demit.mehraan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demit.mehraan.Dashboard;
import com.demit.mehraan.Model.ChatModel;
import com.demit.mehraan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {
    private Context context;
    private List<ChatModel> list;
    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private String recieverid;
    public ChatAdapter(Context context, List<ChatModel> list,String recieverid) {
        this.context = context;
        this.list = list;
        this.recieverid =recieverid;
                //recieverid;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_messages_layout,parent,false);

        auth = FirebaseAuth.getInstance();
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String token;
        token=sharedpreferences.getString("token","");
        String messageSenderId="";
        try {
            messageSenderId = jwtverifier(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ChatModel messages = list.get(position);

        String fromuserid = messages.getFrom();
        String frommessagetype = messages.getType();

//        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromuserid);
//
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("profile_image")){
//                    String recimage = dataSnapshot.child("profile_image").getValue().toString();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        if (frommessagetype.equals("text")){

            holder.sendertextll.setVisibility(View.GONE);
            holder.recievertextll.setVisibility(View.GONE);
            if (fromuserid.equals(messageSenderId)){
                holder.sendertextll.setVisibility(View.VISIBLE);
                holder.senderMessageText.setText(messages.getMessage());


                if(messages.status.equals("true")){
                    holder.textstatus.setImageResource(R.drawable.doubletick);
                }else{



                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                    reference.child("Messages").child(messageSenderId).child(recieverid)
                            .child(messages.messageId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child("status").getValue().toString().equals("true")){
                                holder.textstatus.setImageResource(R.drawable.doubletick);
                            }else{

                                holder.textstatus.setImageResource(R.drawable.tick);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                holder.messagesenderdate.setText(messages.date + " "+ messages.time);
            }
            else {
                holder.recievertextll.setVisibility(View.VISIBLE);
                holder.recieverMessageText.setText(messages.getMessage());

                holder.messagerecieverdate.setText(messages.date + " "+ messages.time);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
         TextView senderMessageText,recieverMessageText,messagesenderdate,messagerecieverdate;//,imagesenderdate,imagerecieverdate;
        ImageView /*senderimage,recieveimage,*/imagestatus,textstatus;

        LinearLayout /*senderimagell,recieverimagell,*/sendertextll,recievertextll;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = itemView.findViewById(R.id.sender_message_text);
            recieverMessageText = itemView.findViewById(R.id.reciever_message_text);

            //-----------------------------------------------
            messagerecieverdate=itemView.findViewById(R.id.reciever_message_textdate);
            messagesenderdate=itemView.findViewById(R.id.sender_message_textdate);
            recievertextll=itemView.findViewById(R.id.reciever_message_textll);
            sendertextll=itemView.findViewById(R.id.sender_message_textll);


            imagestatus=itemView.findViewById(R.id.imagereadstatus);
            textstatus=itemView.findViewById(R.id.msgreadstatus);

            //------------------------------------------------
        }
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
