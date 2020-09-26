package com.demit.mehraan.ContextClass;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

public class SendNotification {
    Context context;
    String userid,Title,Message;
    Boolean check;
    public SendNotification(Context context, String userid, String title, String message) {
        this.context = context;
        this.userid = userid;
        Title = title;
        Message = message;
        check=true;
    }


    public void send(){

                    final String[] child_token = new String[1];
                    DatabaseReference mreference = FirebaseDatabase.getInstance().getReference("Users")
                            .child(userid);
                    mreference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //Toast.makeText(Chat.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                            if(dataSnapshot.exists())
                            {
//                                Toast.makeText(context, userid+"\n"+child_token[0], Toast.LENGTH_SHORT).show();
                                child_token[0] = dataSnapshot.child("token").getValue().toString();
                                if (child_token[0] != null) {

                                    if(check){
                                        FirebaseNotificationHelper.initialize
                                                ("AAAApQwQsk4:APA91bFThYxZwYxY-2tthXBCssc9RJwfrPVi8_RGvRnm7XKRtfiKBzGx1ZC44VmTbPSxDdTiGzjjm96Cf76PPwJgpcnc51PkBrmUkPTG6leiRUD8JfybHAm8i5UtKbQRl1AoBX8VaDTn")
                                                .defaultJson(true, null)
                                                .title(Title)
                                                .message(Message)
                                                .receiverFirebaseToken(child_token[0])
                                                .send();
                                        check=false;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
}
