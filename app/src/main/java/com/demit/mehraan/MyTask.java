package com.demit.mehraan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyTask extends Fragment {

    RecyclerView assignedlist, postedlist;
    SharedPreferences sharedpreferences;
String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_task, container, false);

        assignedlist=view.findViewById(R.id.tasksassignedlistid);
        postedlist=view.findViewById(R.id.taskspostedlistid);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        token=sharedpreferences.getString("token","");
        assignedlist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        postedlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Task number 5","Task number 6","Task number 7",};
        String[] price={"120","450","200"};
        String[] location={"task location 3","task location 4","task location 5"};
        String[] comments={"12","45","20","12","50","0","50"};
        String[] offers={"10","50","20","10","40","0","0"};
        int[] image={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

getusertask();
getassignedtask();
        //assignedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));
       // postedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));

        int a=2;
      //  assignedlist.setAdapter(new AssignmentAdapter(name,location,price,comments,offers,image));
    //    postedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image,a));






        return view;
    }

    private void getassignedtask() {
        String URL = "https://mehraan-oh3.conveyor.cloud/api/basictask/taskassignedtome";
        // URL="https://httpbin.org/post";
        URL=Constants.BaseUrl+"basictask/taskassignedtome";


        //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("yewalaresponse12345",response.toString());
                gotassignedresult(response.toString());
                //TODO: handle success

                //   getFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price)).commit();

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
                Map<String, String>  params = new HashMap<String, String>();
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



    public void getusertask(){

        String URL = "https://mehraan-oh3.conveyor.cloud/api/posttask/getusertask";
        URL=Constants.BaseUrl+"posttask/getusertask";
        // URL="https://httpbin.org/post";


        //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                 Log.d("yewalaresponse12345",response.toString());
                gotuserresult(response.toString());
                //TODO: handle success

             //   getFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,posternames,price)).commit();

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
            Map<String, String>  params = new HashMap<String, String>();
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
    private void gotuserresult(String serverResponse) {
       //postedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));
        try {
            JSONObject jsonObject = new JSONObject(serverResponse);
            Log.d("(*&^",jsonObject.getJSONArray("response").toString());
            JSONArray jsonArray=  jsonObject.getJSONArray("response");


            ArrayList<String> name=new ArrayList<>();
            ArrayList<String> taskid=new ArrayList<>();
            ArrayList<String> userName=new ArrayList<>();
            ArrayList<String> datetime=new ArrayList<>();
            ArrayList<String> duedate=new ArrayList<>();
            ArrayList<String> detail=new ArrayList<>();
            ArrayList<String> location=new ArrayList<>();
            ArrayList<String> comments=new ArrayList<>();
            ArrayList<String> price=new ArrayList<>();
            ArrayList<String> profimage=new ArrayList<>();
            ArrayList<String> offerscount=new ArrayList<>();
            ArrayList<String> taskstatus=new ArrayList<>();


            for(int i=0;i<jsonArray.length();i++)
            {


                name.add(jsonArray.getJSONObject(i).get("name").toString());
                String image=jsonArray.getJSONObject(i).get("userprofileimage").toString();
                if(image.equals("null")||image.equals(null)||image.equals("")){
                    image="http://tcap.pbworks.com/f/1435170280/myAvatar.png";
                }
                profimage.add(image);
                taskid.add(jsonArray.getJSONObject(i).get("taskid").toString());
                userName.add(jsonArray.getJSONObject(i).get("userName").toString());
                String dd = jsonArray.getJSONObject(i).get("duedate").toString();
                String[] parts = dd.split("T");
                duedate.add(parts[0]);
                detail.add(jsonArray.getJSONObject(i).get("detail").toString());
                location.add(jsonArray.getJSONObject(i).get("location").toString());
                comments.add(jsonArray.getJSONObject(i).get("commentsCount").toString());
                offerscount.add(jsonArray.getJSONObject(i).get("offersCount").toString());
                price.add(jsonArray.getJSONObject(i).get("price").toString());
                taskstatus.add(jsonArray.getJSONObject(i).get("taskstatus").toString());
//                taskstatus.add("Assigned");
                String dt = jsonArray.getJSONObject(i).get("datetime").toString();
                String[] partsr = dt.split("T");
                datetime.add(partsr[0]);




                //  date.add(jsonArray.getJSONObject(i).get("date").toString());




            }


            String[] namear=name.toArray(new String[name.size()]);
            String[] profimagear=profimage.toArray(new String[profimage.size()]);
            String[] taskidar=taskid.toArray(new String[taskid.size()]);
            String[] duedatear=duedate.toArray(new String[duedate.size()]);
            String[] locationar=location.toArray(new String[location.size()]);
            String[] pricear=price.toArray(new String[price.size()]);
            String[] commentsar=comments.toArray(new String[comments.size()]);
            String[] offersar=offerscount.toArray(new String[offerscount.size()]);
            String[] usersnamear=userName.toArray(new String[userName.size()]);
            String[] detailar=detail.toArray(new String[detail.size()]);
            String[] taskstatusar=taskstatus.toArray(new String[taskstatus.size()]);
            String[] datetimear=datetime.toArray(new String[datetime.size()]);
         //   adapter=new EarnMoneyAdapter(namear,locationar,pricear,commentsar,offersar,profimagear,taskidar,detailar,detailar,duedatear,detailar,usersnamear,getContext(),2);

            postedlist.setAdapter(new EarnMoneyAdapter(namear,locationar,pricear,commentsar,offersar,profimagear,taskidar,taskstatusar,datetimear,duedatear,detailar,usersnamear,getContext(),2,2));


// String[] name={"Task number 1","Task number 2","Task number 3","Task number 4","Task number 5","Task number 6","Task number 7",};
//        String[] price={"120","450","200","120","450","200","500"};
//        String[] location={"task location 1","task location 2","task location 3","task location 4","task location 5","task location 6","task location 7"};
//        String[] comments={"12","45","20","12","50","0","50"};
//        String[] offers={"10","50","20","10","40","0","0"};
//        int[] image





//            commentslist.setAdapter(new CommentsAdapter(commentdetailar, datear, usernamear,userprofileimagear,getContext()));








        }
        catch (Exception e){
            Log.e("Duhai",e.toString());
        }
    }
    private void gotassignedresult(String serverResponse) {
        //postedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));
        try {
            JSONObject jsonObject = new JSONObject(serverResponse);
            Log.d("(*&^",jsonObject.getJSONArray("response").toString());
            JSONArray jsonArray=  jsonObject.getJSONArray("response");


            ArrayList<String> name=new ArrayList<>();
            ArrayList<String> taskid=new ArrayList<>();
            ArrayList<String> userName=new ArrayList<>();
            ArrayList<String> datetime=new ArrayList<>();
            ArrayList<String> duedate=new ArrayList<>();
            ArrayList<String> detail=new ArrayList<>();
            ArrayList<String> location=new ArrayList<>();
            ArrayList<String> comments=new ArrayList<>();
            ArrayList<String> price=new ArrayList<>();
            ArrayList<String> profimage=new ArrayList<>();
            ArrayList<String> offerscount=new ArrayList<>();
            ArrayList<String> taskstatus=new ArrayList<>();
            ArrayList<String> direction=new ArrayList<>();

            for(int i=0;i<jsonArray.length();i++)
            {
//07-20 07:51:21.332 10822-10822/com.demit.mehraan D/(*&^: [{"postername":"UmerKhaldi","name":"asd","finalprice":589,"taskId":5,"taskOwnerId":2,"taskAssignedToUserId":2,"taskOwnerProfileImage":null,"assigneddatetime":"2020-07-20T15:59:20.0719934","duedate":"2020-07-22T00:00:00","detail":"asd","location":"Data Darbar Rd, Data Gunj Buksh Town, Lahore, Punjab 54000, Pakistan","directions":null}]

                name.add(jsonArray.getJSONObject(i).get("name").toString());
                String image=jsonArray.getJSONObject(i).get("taskOwnerProfileImage").toString();
                if(image.equals("null")||image.equals(null)||image.equals("")){
                    image="http://tcap.pbworks.com/f/1435170280/myAvatar.png";
                }
                profimage.add(image);
                taskid.add(jsonArray.getJSONObject(i).get("taskId").toString());
                userName.add(jsonArray.getJSONObject(i).get("postername").toString());
                String dd = jsonArray.getJSONObject(i).get("duedate").toString();
                String[] parts = dd.split("T");
                duedate.add(parts[0]);
                String add = jsonArray.getJSONObject(i).get("assigneddatetime").toString();
                String[] partss = dd.split("T");
                datetime.add(partss[0]);
                detail.add(jsonArray.getJSONObject(i).get("detail").toString());
                location.add(jsonArray.getJSONObject(i).get("location").toString());
                comments.add("-");
                offerscount.add("-");
                price.add(jsonArray.getJSONObject(i).get("finalprice").toString());
                direction.add(jsonArray.getJSONObject(i).get("directions").toString());



                //  date.add(jsonArray.getJSONObject(i).get("date").toString());




            }


            String[] namear=name.toArray(new String[name.size()]);
            String[] profimagear=profimage.toArray(new String[profimage.size()]);
            String[] taskidar=taskid.toArray(new String[taskid.size()]);
            String[] duedatear=duedate.toArray(new String[duedate.size()]);
            String[] assdatear=datetime.toArray(new String[datetime.size()]);
            String[] locationar=location.toArray(new String[location.size()]);
            String[] pricear=price.toArray(new String[price.size()]);
            String[] commentsar=comments.toArray(new String[comments.size()]);
            String[] offersar=offerscount.toArray(new String[offerscount.size()]);
            String[] usersnamear=userName.toArray(new String[userName.size()]);
            String[] directionar=direction.toArray(new String[direction.size()]);
            String[] detailar=detail.toArray(new String[detail.size()]);
            //   adapter=new EarnMoneyAdapter(namear,locationar,pricear,commentsar,offersar,profimagear,taskidar,detailar,detailar,duedatear,detailar,usersnamear,getContext(),2);

           // postedlist.setAdapter(new EarnMoneyAdapter(namear,locationar,pricear,commentsar,offersar,profimagear,taskidar,detailar,detailar,duedatear,detailar,usersnamear,getContext(),2));
            assignedlist.setAdapter(new AssignmentAdapter(namear,locationar,pricear,commentsar,offersar,profimagear,taskidar,detailar,assdatear,duedatear,detailar,usersnamear,3,directionar));
 //   public EarnMoneyAdapter(String[] name, String[] location, String[] price, String[] comments, String[] offers, String[] image, String[] taskIds, String[] tasksttus, String[] datetimes, String[] duedates, String[] detail, String[] posterames, Context
   //         context,int a ) {


// String[] name={"Task number 1","Task number 2","Task number 3","Task number 4","Task number 5","Task number 6","Task number 7",};
//        String[] price={"120","450","200","120","450","200","500"};
//        String[] location={"task location 1","task location 2","task location 3","task location 4","task location 5","task location 6","task location 7"};
//        String[] comments={"12","45","20","12","50","0","50"};
//        String[] offers={"10","50","20","10","40","0","0"};
//        int[] image





//            commentslist.setAdapter(new CommentsAdapter(commentdetailar, datear, usernamear,userprofileimagear,getContext()));








        }
        catch (Exception e){
            Log.e("Duhaihai",e.toString());
        }
    }

}
