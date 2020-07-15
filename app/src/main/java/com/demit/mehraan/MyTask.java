package com.demit.mehraan;

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
        assignedlist.setLayoutManager(new LinearLayoutManager(getContext()));
        postedlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Task number 1","Task number 2","Task number 3","Task number 4","Task number 5","Task number 6","Task number 7",};
        String[] price={"120","450","200","120","450","200","500"};
        String[] location={"task location 1","task location 2","task location 3","task location 4","task location 5","task location 6","task location 7"};
        String[] comments={"12","45","20","12","50","0","50"};
        String[] offers={"10","50","20","10","40","0","0"};
        int[] image={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
getusertask();
        //assignedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));
       // postedlist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));





        return view;
    }
    public void getusertask(){

        String URL = "https://mehraan-oh3.conveyor.cloud/api/posttask/getusertask";
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
                comments.add(jsonArray.getJSONObject(i).get("commentCount").toString());
                offerscount.add(jsonArray.getJSONObject(i).get("offersCount").toString());
                price.add(jsonArray.getJSONObject(i).get("price").toString());




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
            adapter=new EarnMoneyAdapter(name,location,price,comment,offer,image,taskIds,tasksttus,datetimes,duedates,detail,posterames,getContext());

            postedlist.setAdapter(new EarnMoneyAdapter(namear,locationar,pricear,commentsar,offersar,profimagear,taskidar,duedatear,getContext()));


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

}
