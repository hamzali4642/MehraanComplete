package com.demit.mehraan;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class PostedTaskDetails extends Fragment {

    RecyclerView myofferlist, mycommentslist;
    TextView myopen, myassigned, mycompleted, myreviewed, mytaskname, mypostername, mytasklocation, mypostime, myshowonmap, mytaskdate, mytaskdetails;
    EditText mywritecomment;
    TextView name;
    ImageView mysendbtn, mybackbtn,direction;
    Button changestatust;
    CircleImageView myposterimage,userimagefb;
    RelativeLayout comptask;
    String detail;
    String taskstatus;
    CardView card;
    String duedates;
    String taskid;
    String posternames,price;
    String location,userimage,date;
    SharedPreferences sharedpreferences;
    String userid;
    int a;
    String token="";
    String offerdetail="";


    public PostedTaskDetails(String detail, String taskstatus, String duedates, String taskid, String postername,String price,String location,String userimage,String date ) {
        this.detail=detail;
        this.taskstatus=taskstatus;
        this.duedates=duedates;
        this.taskid=taskid;
        this.posternames=postername;
         this.userimage=userimage;
        this.location=location;
        this.date=date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_posted_task_details, container, false);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        token=sharedPreferences.getString("token","");

        card=view.findViewById(R.id.cardview);
        direction=view.findViewById(R.id.direction);
        myopen=view.findViewById(R.id.myopenid);
        name=view.findViewById(R.id.personnameid);
        userimagefb=view.findViewById(R.id.personofferdp);
        myassigned=view.findViewById(R.id.myassignedid);
        mycompleted=view.findViewById(R.id.mycompletedid);
        myreviewed=view.findViewById(R.id.myreviewedid);
        mytaskname=view.findViewById(R.id.mytasknameid);
        mypostername=view.findViewById(R.id.myposternameid);
        mytasklocation=view.findViewById(R.id.mytasklocationid);
        mypostime=view.findViewById(R.id.myposttimeid);
        myshowonmap=view.findViewById(R.id.myshowonmapbtn);
        mytaskdate=view.findViewById(R.id.mytaskdateid);
        mytaskdetails=view.findViewById(R.id.mytaskdetailsid);
        mywritecomment=view.findViewById(R.id.mytypecommentid);
        mysendbtn=view.findViewById(R.id.mysendbtnid);
        mybackbtn=view.findViewById(R.id.backmytaskid);
        changestatust=view.findViewById(R.id.changestatusbtnid);
        myofferlist=view.findViewById(R.id.myofferlistid);
        mycommentslist=view.findViewById(R.id.mycommentslistid);
        mypostername.setText(posternames);
        mytaskdetails.setText(detail);
        myposterimage=view.findViewById(R.id.myposterdp);
        comptask=view.findViewById(R.id.mybudgetid);
        comptask.setVisibility(View.GONE);
        myopen.setEnabled(false);
        myassigned.setEnabled(false);
        mycompleted.setEnabled(false);
        myreviewed.setEnabled(false);
        Toast.makeText(getContext(), taskstatus, Toast.LENGTH_SHORT).show();
        if(taskstatus.equals("Open")){
            myopen.setEnabled(true);
        }
        if(taskstatus.equals("Assigned")){
            myassigned.setEnabled(true);
            comptask.setVisibility(View.VISIBLE);
            myofferlist.setVisibility(View.GONE);
            card.setVisibility(View.VISIBLE);

            getFirebasedata();
        }
        if(taskstatus.equals("Completed")){
            myassigned.setEnabled(true);
            mycompleted.setEnabled(true);
            myofferlist.setVisibility(View.GONE);

        }
        if(taskstatus.equals("Reviewed")){
            myassigned.setEnabled(true);
            mycompleted.setEnabled(true);
            myreviewed.setEnabled(true);
            myofferlist.setVisibility(View.GONE);
        }
        String date = duedates;
        String[] parts = date.split("T");
        System.out.println("Date: " + parts[0]);
//        System.out.println("Time: " + parts[1] + " " + parts[2]);
        mytaskdate.setText(parts[0]);
        mytasklocation.setText(location);
        try{
            Glide.with(getContext()).load(Constants.ImageUrl+userimage).into(myposterimage);}
        catch (Exception e){}
        mypostime.setText(this.date);



        changestatust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setComplete(taskid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        myofferlist.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            getcomments();
            getoffers();
        } catch (JSONException e) {
            e.printStackTrace();
        }
             mycommentslist.setLayoutManager(new LinearLayoutManager(getContext()));



       //mycommentslist.setAdapter(new CommentsAdapter(comment, time, name,dps,getContext()));

        //commentslist.setAdapter(new CommentsAdapter(commentdetailar, datear, usernamear,userprofileimagear,getContext()));
        mybackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Details.class);
                intent.putExtra("back",2);
                startActivity(intent);

            }
        });


        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ShowRiderclass.class);
                intent.putExtra("taskid",taskid);
                startActivity(intent);
            }
        });

        return view;
    }
    public void  getcomments() throws JSONException {


        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();
        String URL1= "https://httpbin.org/post";

        URL1="https://mehraan-oh3.conveyor.cloud/api/basictask/gettaskcomment";

        URL1=Constants.BaseUrl+"basictask/gettaskcomment";
        // URL="https://httpbin.org/post";


        JSONObject jsonBody = new JSONObject();

        jsonBody.put("TaskId",Integer.valueOf(taskid));





        //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL1, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("gettaskcomment",response.toString());
                loading.dismiss();
                gotresult(response.toString());
                //TODO: handle success

                //  getFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price)).commit();

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
        Volley.newRequestQueue(getContext()).add(jsonRequest);

    }
    private void gotresult(String serverResponse) {
        try {
            JSONObject jsonObject = new JSONObject(serverResponse);
            Log.d("(*&^",jsonObject.getJSONArray("response").toString());
            JSONArray jsonArray=  jsonObject.getJSONArray("response");


            ArrayList<String> commentdetail=new ArrayList<>();
            ArrayList<String> userprofileimage=new ArrayList<>();
            ArrayList<String> username=new ArrayList<>();
            ArrayList<String> date=new ArrayList<>();

            for(int i=0;i<jsonArray.length();i++)
            {


                commentdetail.add(jsonArray.getJSONObject(i).get("commentDetail").toString());
                String image=jsonArray.getJSONObject(i).get("userprofileimage").toString();
                if(image.equals("null")||image.equals(null)||image.equals("")){
                    image="http://tcap.pbworks.com/f/1435170280/myAvatar.png";
                }
                userprofileimage.add(image);
                username.add(jsonArray.getJSONObject(i).get("username").toString());
                //  date.add(jsonArray.getJSONObject(i).get("date").toString());

                String dates = jsonArray.getJSONObject(i).get("date").toString();
                String[] parts = dates.split("T");
                System.out.println("Date: " + parts[0]);
                date.add(parts[0]);


                Log.d("pindi_*","123"+commentdetail.toString()+userprofileimage.toString()+username.toString());

            }


            String[] commentdetailar=commentdetail.toArray(new String[commentdetail.size()]);
            String[] userprofileimagear=userprofileimage.toArray(new String[userprofileimage.size()]);
            String[] usernamear=username.toArray(new String[username.size()]);
            String[] datear=date.toArray(new String[date.size()]);



            mycommentslist.setAdapter(new CommentsAdapter(commentdetailar, datear, usernamear,userprofileimagear,getContext()));







            // e.setCats(cats);
            //String[] catarray=  cats.toArray(new String[cats.size()]);

            //     getresult();

            // Log.d("(*&^",cats.toString());}catch (Exception e){
            //   Log.d("yewa",e.toString());
        }
        catch (Exception e){
            Log.e("Duhai",e.toString());
        }
    }
    public void  getoffers() throws JSONException {
        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();
        String URL1= "https://httpbin.org/post";

        URL1="https://mehraan-oh3.conveyor.cloud/api/basictask/gettaskoffers";

        URL1=Constants.BaseUrl+"basictask/gettaskoffers";
        // URL="https://httpbin.org/post";


        JSONObject jsonBody = new JSONObject();

        jsonBody.put("TaskId",Integer.valueOf(taskid));





        //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL1, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                Log.d("gettaskoffers",response.toString());
                gotoffersresult(response.toString());
                //TODO: handle success

                //  getFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price)).commit();

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
                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
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
        Volley.newRequestQueue(getContext()).add(jsonRequest);

    }
    private void gotoffersresult(String serverResponse) {
        try {
            JSONObject jsonObject = new JSONObject(serverResponse);
            Log.d("(*&^",jsonObject.getJSONArray("response").toString());
            JSONArray jsonArray=  jsonObject.getJSONArray("response");

//[{"ofFerID":2,"offeredAmmount":1000,"userId":2,"taskID":4,"userprofileimage":null,"username":"UmerKhaldi","date":"2020-07-20T11:08:47.3199538"}
            ArrayList<String> offeredAmmount=new ArrayList<>();
            ArrayList<String> userprofileimage=new ArrayList<>();
            ArrayList<String> username=new ArrayList<>();
            ArrayList<String> userId=new ArrayList<>();

            ArrayList<String> date=new ArrayList<>();

            for(int i=0;i<jsonArray.length();i++)
            {


                offeredAmmount.add(jsonArray.getJSONObject(i).get("offeredAmmount").toString());
                String image=jsonArray.getJSONObject(i).get("userprofileimage").toString();
                if(image.equals("null")||image.equals(null)||image.equals("")){
                    image="http://tcap.pbworks.com/f/1435170280/myAvatar.png";
                }
                userprofileimage.add(image);
                username.add(jsonArray.getJSONObject(i).get("username").toString());
                userId.add(jsonArray.getJSONObject(i).get("userId").toString());
                //  date.add(jsonArray.getJSONObject(i).get("date").toString());

                String dates = jsonArray.getJSONObject(i).get("date").toString();
                String[] parts = dates.split("T");
                System.out.println("Date: " + parts[0]);
                date.add(parts[0]);


                Log.d("rawalpindi_*","123"+offeredAmmount.toString()+userprofileimage.toString()+username.toString());

            }


            String[] offeredAmmountar=offeredAmmount.toArray(new String[offeredAmmount.size()]);
            String[] userprofileimagear=userprofileimage.toArray(new String[userprofileimage.size()]);
            String[] usernamear=username.toArray(new String[username.size()]);
            String[] datear=date.toArray(new String[date.size()]);
            String[] userids=userId.toArray(new String[userId.size()]);

            String[] name={"Person 1"};
            String[] review={"2","2","2","2","2","2","2","2","2"};
            int[] rating={4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};
            String[] time={"4 hrs ago"};
            String[] comment={"COmment 1"};
            int[] dp={R.drawable.ic_launcher_background};
            String[] dps={"http://tcap.pbworks.com/f/1435170280/myAvatar.png"};
            boolean[] email={true,true,true,true,true,true,true};
            boolean[] phone={true,true,true,true,true,true,true};
            boolean[] cnic={true,true,true,true,true,true,true};
            boolean[] payment={false,true,true,true,true,true,true};


            myofferlist.setAdapter(new MyOfferAdapter(usernamear,review,datear,rating,userprofileimagear,email,phone,payment,cnic,getContext(),offeredAmmountar,taskid,userids));




            //  commentslist.setAdapter(new CommentsAdapter(commentdetailar, datear, usernamear,userprofileimagear,getContext()));







            // e.setCats(cats);
            //String[] catarray=  cats.toArray(new String[cats.size()]);

            //     getresult();

            // Log.d("(*&^",cats.toString());}catch (Exception e){
            //   Log.d("yewa",e.toString());
        }
        catch (Exception e){
            Log.e("Duhai",e.toString());
        }
    }

    public  void getFirebasedata(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Assigned task").child(taskid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           if(snapshot.exists())
           {
               String namefb;
                namefb=snapshot.child("name").getValue().toString();
                name.setText(namefb);

                String imagefb;
                imagefb=snapshot.child("image").getValue().toString();
                Glide.with(getContext()).load(Constants.ImageUrl+imagefb).into(userimagefb);
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void setComplete(String taskid) throws JSONException {
        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        JSONObject object=new JSONObject();
        object.put("TaskId",Integer.valueOf(taskid));

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,Constants.BaseUrl+"posttask/Completetask",object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                showDialog();
                Log.d("Response:",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.d("Error",error.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }
    void setReview(String taskid,String comment , Float rating,Dialog dialog) throws JSONException {
        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        JSONObject object=new JSONObject();
        object.put("TaskId",Integer.valueOf(taskid));
        object.put("Comment",comment);
        object.put("review",rating);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,Constants.BaseUrl+"BasicTask/Postreview",object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                Log.d("Response:",response.toString());
                dialog.dismiss();

                startActivity(new Intent(getContext(),BridgeActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.d("Error",error.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }
    public void showDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_dialogue);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


        RatingBar ratingBar=dialog.findViewById(R.id.dialogueratingbarid);
        Button submit=dialog.findViewById(R.id.dialguepositivebtn);
        TextView textView=dialog.findViewById(R.id.dialogueratingvalueid);
        EditText comment=dialog.findViewById(R.id.rateet);



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textView.setText(rating+"");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ratingBar.getRating()>=1 && comment.getText().toString() != null){
                    try {
                        setReview(taskid,comment.getText().toString(),ratingBar.getRating() ,dialog);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
