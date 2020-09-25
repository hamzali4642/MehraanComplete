package com.demit.mehraan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.demit.mehraan.ContextClass.JWTget;
import com.demit.mehraan.ContextClass.SendNotification;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyOfferAdapter extends RecyclerView.Adapter<MyOfferAdapter.MyOfferViewholder> {
    private  String[] name,  review, time,userids;
    private int[] rating ;
    String[] dp;
    private boolean[] email, phone, payment, cnic;
    Context context;
    String[] offeredamount;
    String taskid;
    boolean check=true;
    public MyOfferAdapter(String[] name, String[] review, String[] time, int[] rating, String[] dp, boolean[] email, boolean[] phone, boolean[] payment, boolean[] cnic, Context context,String[] offeredamount,String taskid,String[] userids) {
        this.name = name;
        this.review = review;
        this.time = time;
        this.rating = rating;
        this.dp = dp;
        this.email = email;
        this.phone = phone;
        this.payment = payment;
        this.cnic = cnic;
        this.context=context;
        this.offeredamount=offeredamount;
        this.taskid=taskid;
        this.userids=userids;
    }

    @NonNull
    @Override
    public MyOfferViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.my_offers_view,parent,false);
        context=parent.getContext();
        return new MyOfferViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOfferViewholder holder, int position) {

        String nametxt=name[position];
        holder.name.setText(nametxt);

        String dptx=dp[position];

        try{
            Glide.with(context).load(Constants.ImageUrl+dptx).into(holder.persondp);}
        catch (Exception e ){}
       // holder.persondp.setImageResource(dptx);

        String reviewtxt=review[position];
        holder.reviews.setText(reviewtxt);

        String timetxt=time[position];
        holder.time.setText(timetxt);

        String oamount=offeredamount[position];

        holder.offeredamount.setText("Rs-/ "+oamount);


        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=userids[position];
                try {
                    assigntask(userid,oamount,name[position],dp[position]);
                }
                catch (Exception e){}

            }
        });

        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Details.class);
                intent.putExtra("next",4);
                intent.putExtra("id",userids[position]);
                intent.putExtra("name",name[position]);
                context.startActivity(intent);
//                Fragment selectedFragment=new ChatLayout(userids[position],name[position]);
//                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,selectedFragment).commit();
                  //getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price)).commit();


            }
        });



    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class MyOfferViewholder extends RecyclerView.ViewHolder{

        RelativeLayout email, phone, payment, cnic;
        CircleImageView persondp;
        RatingBar ratingBar;
        TextView name, reviews, time;
        FloatingActionButton msg;
        Button assign;
        TextView offeredamount;

        public MyOfferViewholder(@NonNull View itemView) {
            super(itemView);


            phone=itemView.findViewById(R.id.myphoneverificationid);
            payment=itemView.findViewById(R.id.mypaymentverificationid);
            cnic=itemView.findViewById(R.id.mycnicverificationid);
            persondp=itemView.findViewById(R.id.mypersonofferdp);
            ratingBar=itemView.findViewById(R.id.myraatingofferid);
            name=itemView.findViewById(R.id.mypersonnameid);
            reviews=itemView.findViewById(R.id.myreviewsofferid);
            time=itemView.findViewById(R.id.mytimeofferid);
            msg=itemView.findViewById(R.id.mymsgbtnid);
            assign=itemView.findViewById(R.id.myassignbtnid);
            offeredamount=itemView.findViewById(R.id.offeredamount);


        }
    }
    public void assigntask(String userid,String price,String name,String image) throws JSONException {
        SweetAlertDialog loading=new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

      String  URL1="https://mehraan-oh3.conveyor.cloud/api/basictask/assigntask";

      URL1=Constants.BaseUrl+"basictask/assigntask";
        // URL="https://httpbin.org/post";


        JSONObject jsonBody = new JSONObject();

        jsonBody.put("TaskId",Integer.valueOf(taskid));
        jsonBody.put("AssignedToUserrId",Integer.valueOf(userid));
        jsonBody.put("FinalPrice",Integer.valueOf(price));





        //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL1, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response**","asd"+response.toString());

                loading.dismiss();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                Map<String , String> params=new HashMap<>();
                params.put("name",name);
                params.put("image",image);
                reference.child("Assigned task").child(taskid).setValue(params);

                context.startActivity(new Intent(context,BridgeActivity.class));

                try {
                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                    String token =sharedPreferences.getString("token","");
                    String name= new JWTget(context).jwtverifier(token,"unique_name");

                    SendNotification sendNotification =new SendNotification(context,
                            userid,"Assigned Task",name +"assigned you task.");
                    sendNotification.send();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                loading.dismiss();
                Log.d("yewalaresponse",error.toString());
                //TODO: handle failure
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
                String token=sharedPreferences.getString("token","");
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("User-Agent", "Nintendo Gameboy");
                //   params.put("Accept-Language", "fr");
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };

        jsonRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        Volley.newRequestQueue(context).add(jsonRequest);
    }


    private void sendNotification(String userid){

        check=true;

        final String[] child_token = new String[1];
        DatabaseReference mreference = FirebaseDatabase.getInstance().getReference("Users")
                .child(userid);
        mreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Toast.makeText(Chat.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                if(dataSnapshot.exists())
                {
                    child_token[0] = dataSnapshot.child("token").getValue().toString();
                    if (child_token[0] != null) {

                        if(check){
                            FirebaseNotificationHelper.initialize
                                    ("AAAAeT9NimI:APA91bG1afyJB_0GgG97-527fqluDcPGo3_yBpr7jv22edHjVhLLlv82wmAYFnNZOHaTLPy5Qlw22Ot6N7fuzbP--XKQI-zU_bfjYTjHSspudB7NyyRbu9ut6P-7h21tXh23vO4osHP7")
                                    .defaultJson(true, null)
                                    .title("Mehraan")
                                    .message("A task assigned to you")
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
