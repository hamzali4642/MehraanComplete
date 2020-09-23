package com.demit.mehraan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.$Gson$Preconditions;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class TaskDetails extends Fragment {

    RecyclerView offerlist, commentslist;
    TextView open, assigned, completed, reviewed, taskname, postername, tasklocation, postime, showonmap, taskdate, taskbudget, taskdetails;
    EditText writecomment;
    ImageView sendbtn, backbtn;
    Button makeoffer;
    CircleImageView posterimage;
    String detail;
    String taskstatus;
    String duedates;
    String taskid;
    String posternames,price;
    SharedPreferences sharedpreferences;
    String userid;
    int a;
    String token="";
    String offerdetail="";
    String location,userimage,date;

    public TaskDetails(String detail, String taskstatus, String duedates, String taskid, String postername,String price,String location,String userimage,String date) {
        this.detail=detail;
        this.taskstatus=taskstatus;
        this.duedates=duedates;
        this.taskid=taskid;
        this.posternames=postername;
        this.price=price;
        this.userimage=userimage;
        this.date=date;
        this.location=location;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_details, container, false);

        open=view.findViewById(R.id.openid);
        assigned=view.findViewById(R.id.assignedid);
        completed=view.findViewById(R.id.completedid);
        reviewed=view.findViewById(R.id.reviewedid);
        taskname=view.findViewById(R.id.tasknameid);
        postername=view.findViewById(R.id.posternameid);
        tasklocation=view.findViewById(R.id.tasklocationid);
        postime=view.findViewById(R.id.posttimeid);
        showonmap=view.findViewById(R.id.showonmapbtn);
        taskdate=view.findViewById(R.id.taskdateid);
        taskbudget=view.findViewById(R.id.estimatedbudgetid);
        taskdetails=view.findViewById(R.id.taskdetailsid);
        writecomment=view.findViewById(R.id.typecommentid);
        sendbtn=view.findViewById(R.id.sendbtnid);
        backbtn=view.findViewById(R.id.backtasdetailsid);
        makeoffer=view.findViewById(R.id.makeofferbtnid);
        makeoffer=view.findViewById(R.id.makeofferbtnid);
        posterimage=view.findViewById(R.id.posterdp);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        token= sharedpreferences.getString("token","");
        try {
            jwtverifier(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        open.setEnabled(false);
        assigned.setEnabled(false);
        completed.setEnabled(false);
        reviewed.setEnabled(false);
        if(taskstatus.equals("Open")){
            open.setEnabled(true);
        }
        if(taskstatus.equals("Assigned")){
            assigned.setEnabled(true);
        }
        if(taskstatus.equals("Completed")){
            completed.setEnabled(true);
        }
        if(taskstatus.equals("Reviewed")){
            reviewed.setEnabled(true);
        }
        postername.setText(posternames);
        taskdetails.setText(detail);
        taskbudget.setText(price);
        String date = duedates;
        try{
            Glide.with(getContext()).load(Constants.ImageUrl+userimage).into(posterimage);}
        catch (Exception e){}
        tasklocation.setText(location);
        String[] parts = date.split("T");
        System.out.println("Date: " + parts[0]);
//        System.out.println("Time: " + parts[1] + " " + parts[2]);
        taskdate.setText(parts[0]);
        postime.setText(date);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment=writecomment.getText().toString();
                if(!comment.equals("")){
                    comment(comment);
                    writecomment.setText("");
                }

            }
        });

        makeoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        AlertDialog.Builder builder= new AlertDialog.Builder((view.getContext()));
                        builder.setTitle("Make Offer");
                        builder.setMessage("Enter amount of offer");
                        final EditText offer= new EditText(view.getContext());
                        offer.setInputType(InputType.TYPE_CLASS_NUMBER);
                        offer.setGravity(Gravity.CENTER_HORIZONTAL);
                        offer.setPadding(20,0,20,10);
                        offer.setHint(price);
                        builder.setView(offer);
                        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String groupname= offer.getText().toString();
                                if (TextUtils.isEmpty(groupname)){
                                    Toast.makeText(view.getContext(),"Please enter Some amount", Toast.LENGTH_SHORT).show();
                                }else if(groupname.length()>7){
                                    Toast.makeText(view.getContext(),"Invalid amount", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    offerdetail=groupname;
                                    offer(offerdetail);
                                }

                            }


            });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    }});
                dialog.show();
//                builder.show();
        }});

        showonmap.setVisibility(View.GONE);
        showonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       offerlist=view.findViewById(R.id.offerlistid);
       commentslist=view.findViewById(R.id.commentslistid);

       offerlist.setLayoutManager(new LinearLayoutManager(getContext()));
        commentslist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Person 1","Person 2","Person 3"};
        String[] review={"2","1","0"};
        int[] rating={4,3,0};
        String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
        String[] comment={"COmment 1","Comment 2","Comment 3"};
        int[] dp={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
        boolean[] email={true,false,false};
        boolean[] phone={true,true,true};
        boolean[] cnic={true,false,true};
        boolean[] payment={false,false,false};

        //offerlist.setAdapter(new OfferAdapter(name,dp,time,rating,review,email,phone,payment,cnic));

    //    commentslist.setAdapter(new CommentsAdapter(comment, time, name,dp));



        return view;
    }
    public void jwtverifier(String token) throws JSONException {
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
        userid= object.getString("Id");
        Log.d("Venture","asD"+userid);
        getcomments();
        getoffers();


    }
    public void offer(String offerdetail){
        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();
        try {

        String URL = Constants.BaseUrl+"posttask/posttaskoffer";
           // URL="https://httpbin.org/post";


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("OfferedAmount",Integer.valueOf(offerdetail));
            //       jsonBody.put("UserId",Integer.valueOf(userid ));
            jsonBody.put("TaskId",Integer.valueOf(taskid));






            //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loading.dismiss();
                     Log.d("yewalaresponse123",response.toString());

                    //TODO: handle success

                    getFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price,location,userimage,date)).commit();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    loading.dismiss();
                    Log.d("yewalaresponse",error.toString());
                    //TODO: handle failure
                }
            })
            {
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

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void comment(String commentdetail){

        try {

            String URL = "https://mehraan-oh3.conveyor.cloud/api/basictask/postcomment";
            URL=Constants.BaseUrl+"basictask/postcomment";
            //URL="https://httpbin.org/post";


            JSONObject jsonBody = new JSONObject();
            jsonBody.put("CommentDetail",commentdetail);
     //       jsonBody.put("UserId",Integer.valueOf(userid ));
            jsonBody.put("TaskId",Integer.valueOf(taskid));





            //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Log.d("yewalaresponse123",response.toString());

                    //TODO: handle success

                    getFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price,location,userimage,date)).commit();
//                    onDetach();
//                    onAttach(getContext());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.d("yewalaresponse",error.toString());
                    //TODO: handle failure
                }
            })
            {
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

        }catch (JSONException e) {
            e.printStackTrace();
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

            String[] usernamearname={"Person 1","Person 2","Person 3"};
            String[] review={"2","1","0"};
            int[] rating={4,3,0};
            String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
           // String[] comment={"COmment 1","Comment 2","Comment 3"};
            int[] dp={R.drawable.melogo,R.drawable.melogo,R.drawable.melogo};
            boolean[] email={true,false,false};
            boolean[] phone={true,true,true};
            boolean[] cnic={true,false,true};
            boolean[] payment={false,false,false};

                offerlist.setAdapter(new OfferAdapter(usernamear,userprofileimagear,datear,rating,review,email,phone,payment,cnic,getContext()));




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




                commentslist.setAdapter(new CommentsAdapter(commentdetailar, datear, usernamear,userprofileimagear,getContext()));







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




}


