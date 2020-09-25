package com.demit.mehraan;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


public class EarnMoney extends Fragment {

    RecyclerView earnmoneylist;
    ImageView tasksettings;
//taskId,taskName,price,location,dateTime,taskStatus
    ArrayList<String> userid = new ArrayList<>();
    ArrayList<String> taskID=new ArrayList<>();
    ArrayList<String> taskName=new ArrayList<>();
    ArrayList<String> taskStatus=new ArrayList<>();
    ArrayList<String> price=new ArrayList<>();
    ArrayList<String> location=new ArrayList<>();
    ArrayList<String> dateTime=new ArrayList<>();
    ArrayList<String> duedate=new ArrayList<>();
    ArrayList<String> detail=new ArrayList<>();
    ArrayList<String> postername=new ArrayList<>();
    ArrayList<String> comments=new ArrayList<>();
    ArrayList<String> offers=new ArrayList<>();
    ArrayList<String> profimage=new ArrayList<>();

    Integer minprice , maxprice,radius;
    Double latitudee,longitudee;
    SharedPreferences sharedpreferences;
    EarnMoneyAdapter1 adapter;
    int namesize;

    SwipeRefreshLayout refreshLayout;


    public EarnMoney(Integer minprice, Integer maxprice , Double latitude,Double longitude,Integer radius) {
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.latitudee=latitude;
        this.longitudee=longitude;
        this.radius=radius;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_earn_money, container, false);
       earnmoneylist=view.findViewById(R.id.tasklistid);
       tasksettings=view.findViewById(R.id.tasksettingsbtnid);
        refreshLayout=view.findViewById(R.id.refreshlayoutid);

//        Toast.makeText(getActivity(), latitudee+"\n"+longitudee, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        Log.d("tokenn",sharedPreferences.getString("token",""));
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
       tasksettings.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               getFragmentManager().beginTransaction().replace(R.id.dashfragid,new TaskFilters()).commit();

           }
       });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.dashfragid)).commit();
                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(minprice,maxprice,latitudee,longitudee,radius)).commit();
            }
        });

        earnmoneylist.setLayoutManager(new LinearLayoutManager(getContext()));


        ArrayList<String> names = new ArrayList<>();

        int tasknamesize = sharedpreferences.getInt("tasknamesize", 0);
        namesize=tasknamesize;
        for(int i=0;i<tasknamesize;i++)
        {
            names.add(sharedpreferences.getString("taskname" + i, null));
        }

        ArrayList<String> user =new ArrayList<>();
        int usersize =sharedPreferences.getInt("useridsize",0);
        for(int i=0;i<usersize;i++)
        {
            user.add(sharedpreferences.getString("userid" + i, null));
        }
        ArrayList<String> prices = new ArrayList<>();

        int pricesize = sharedpreferences.getInt("taskpricesize", 0);

        for(int i=0;i<pricesize;i++)
        {
            prices.add(sharedpreferences.getString("taskprice" + i, null));
        }
        ArrayList<String> commentss = new ArrayList<>();

        int commentsize = sharedpreferences.getInt("commentssize", 0);

        for(int i=0;i<commentsize;i++)
        {
            commentss.add(sharedpreferences.getString("commentsize" + i, null));
        }
        ArrayList<String> profimags = new ArrayList<>();

        int profimagesize = sharedpreferences.getInt("profimagesize", 0);

        for(int i=0;i<profimagesize;i++)
        {
            profimags.add(sharedpreferences.getString("profimage" + i, null));
        }
        ArrayList<String> offerss = new ArrayList<>();

        int offersize = sharedpreferences.getInt("offerssize", 0);

        for(int i=0;i<offersize;i++)
        {
            offerss.add(sharedpreferences.getString("offersize" + i, null));
        }
        ArrayList<String> locations = new ArrayList<>();

        int locationsize = sharedpreferences.getInt("tasklocationsize", 0);

        for(int i=0;i<locationsize;i++)
        {
            locations.add(sharedpreferences.getString("tasklocation" + i, null));
        }
        ArrayList<String> taskid = new ArrayList<>();

        int taskidsize = sharedpreferences.getInt("taskidsize", 0);

        for(int i=0;i<taskidsize;i++)
        {
            taskid.add(sharedpreferences.getString("taskid" + i, null));
        }

        ArrayList<String> taskstatus = new ArrayList<>();

        int taskstatussize = sharedpreferences.getInt("taskstatussize", 0);

        for(int i=0;i<taskstatussize;i++)
        {
            taskstatus.add(sharedpreferences.getString("taskstatus" + i, null));
        }
        ArrayList<String> datetime = new ArrayList<>();

        int datetimesize = sharedpreferences.getInt("datetimesize", 0);

        for(int i=0;i<datetimesize;i++)
        {
            datetime.add(sharedpreferences.getString("datetime" + i, null));
        }
        ArrayList<String> duedate = new ArrayList<>();

        int duedatesize = sharedpreferences.getInt("duedatesize", 0);

        for(int i=0;i<duedatesize;i++)
        {
            duedate.add(sharedpreferences.getString("duedate" + i, null));
        }
        ArrayList<String> details = new ArrayList<>();

        int detailsize = sharedpreferences.getInt("detailsize", 0);

        for(int i=0;i<detailsize;i++)
        {
            details.add(sharedpreferences.getString("detail" + i, null));
        }
        ArrayList<String> posternames = new ArrayList<>();

        int posternamesize = sharedpreferences.getInt("posternamesize", 0);

        for(int i=0;i<posternamesize;i++)
        {
            posternames.add(sharedpreferences.getString("postername" + i, null));
        }

        String[] name=names.toArray(new String[names.size()]);
        String[] price=prices.toArray(new String[prices.size()]);
        String[] location=locations.toArray(new String[locations.size()]);
        String[] taskIds=taskid.toArray(new String[taskid.size()]);
        String[] tasksttus=taskstatus.toArray(new String[taskstatus.size()]);
        String[] datetimes=datetime.toArray(new String[datetime.size()]);
        String[] duedates=duedate.toArray(new String[duedate.size()]);
        String[] detail=details.toArray(new String[details.size()]);
        String[] posterames=posternames.toArray(new String[posternames.size()]);
        String[] comment=commentss.toArray(new String[commentss.size()]);
        String[] offer=offerss.toArray(new String[offerss.size()]);
        String[] image=profimags.toArray(new String[profimags.size()]);
        String[] useridd=user.toArray(new String[user.size()]);

        //int[] image={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

adapter=new EarnMoneyAdapter1(name,location,price,comment,offer,image,
        taskIds,tasksttus,datetimes,duedates,detail,posterames,getContext(),1,2,useridd);
earnmoneylist.setAdapter(adapter);



        String URL= "https://my-json-server.typicode.com/devsheikh3/jo/db";
        URL=Constants.BaseUrl+"basictask/gettask";
        StringRequest request = new StringRequest(Request.Method.GET,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.

                        try {
                            gotresult(ServerResponse);
                        } catch (Exception e) {
                            Log.d("yewalaerror",e.toString());
                        }

                        // Showing response message coming from server.
                        // Log.d("yewalaresponse", ServerResponse);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.

                        // Showing error message if something goes wrong.
                        Log.d("yewalaerror2", volleyError.toString());

                    }
                }) {
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


        Volley.newRequestQueue(getContext()).add(request);




        return view;
    }
    private void gotresult(String serverResponse) {
        try {
            Log.d("serverresponse",serverResponse);
            JSONObject jsonObject = new JSONObject(serverResponse);
            Log.d("(*&^",jsonObject.getJSONArray("response").toString());
            JSONArray jsonArray=  jsonObject.getJSONArray("response");



            for(int i=0;i<jsonArray.length();i++) {

                String pricee = jsonArray.getJSONObject(i).get("price").toString();
//                if(minprice==0 && maxprice==0 ){
                    userid.add(jsonArray.getJSONObject(i).getString("userid"));
                    taskID.add(jsonArray.getJSONObject(i).get("taskId").toString());
                    taskName.add(jsonArray.getJSONObject(i).get("name").toString());
                    taskStatus.add(jsonArray.getJSONObject(i).get("taskStatus").toString());
                    location.add(jsonArray.getJSONObject(i).get("location").toString());
                    dateTime.add(jsonArray.getJSONObject(i).get("datetime").toString());
                    price.add(jsonArray.getJSONObject(i).get("price").toString());


                    duedate.add(jsonArray.getJSONObject(i).get("duedate").toString());
                    detail.add(jsonArray.getJSONObject(i).get("detail").toString());
                    postername.add(jsonArray.getJSONObject(i).get("userName").toString());
                    comments.add(jsonArray.getJSONObject(i).get("commentsCount").toString());
                    offers.add(jsonArray.getJSONObject(i).get("offersCount").toString());
                    String img = jsonArray.getJSONObject(i).get("userprofileimage").toString();
                    if (img.equals("null") || img == null || img.equals("")) {
                        img = "http://tcap.pbworks.com/f/1435170280/myAvatar.png";
                    }

                    profimage.add(img);

                    Log.d("pindi_*", "123" + taskID.toString() + taskName.toString() + taskID.toString());
//                }




//                else
//                {
//                if ((Integer.valueOf(pricee) <= maxprice && Integer.valueOf(pricee) >= minprice)){
//
//                    if(latitudee==0.0 && longitudee== 0.0){
//
//                        Log.d ("check",latitudee+"\n0 wali\n"+longitudee);
//                taskID.add(jsonArray.getJSONObject(i).get("taskId").toString());
//                taskName.add(jsonArray.getJSONObject(i).get("name").toString());
//                taskStatus.add(jsonArray.getJSONObject(i).get("taskStatus").toString());
//                location.add(jsonArray.getJSONObject(i).get("location").toString());
//                dateTime.add(jsonArray.getJSONObject(i).get("datetime").toString());
//                price.add(jsonArray.getJSONObject(i).get("price").toString());
//
//                duedate.add(jsonArray.getJSONObject(i).get("duedate").toString());
//                detail.add(jsonArray.getJSONObject(i).get("detail").toString());
//                postername.add(jsonArray.getJSONObject(i).get("userName").toString());
//                comments.add(jsonArray.getJSONObject(i).get("commentsCount").toString());
//                offers.add(jsonArray.getJSONObject(i).get("offersCount").toString());
//                String img = jsonArray.getJSONObject(i).get("userprofileimage").toString();
//                if (img.equals("null") || img == null || img.equals("")) {
//                    img = "http://tcap.pbworks.com/f/1435170280/myAvatar.png";
//                }
//
//                profimage.add(img);
//
//                Log.d("pindi_*", "123" + taskID.toString() + taskName.toString() + taskID.toString());
//                }else{
//
//                        taskName.clear();
//                        price.clear();
//                        location.clear();
//                        taskID.clear();
//                        taskStatus.clear();
//                        dateTime.clear();
//                        duedate.clear();
//                        detail.clear();
//                        postername.clear();
//                        comments.clear();
//                        profimage.clear();
//                        offers.clear();
//
//
//                        String directionn = jsonArray.getJSONObject(i).get("directions").toString();
//                        String[] directionss=directionn.split("@");
//                        Log.d("tag",directionss.length+"");
//                if(checklocation(Double.parseDouble(directionss[0]),Double.parseDouble(directionss[1]))){
////                    Toast.makeText(getActivity(), "abc", Toast.LENGTH_SHORT).show();
//                    taskID.add(jsonArray.getJSONObject(i).get("taskId").toString());
//                    taskName.add(jsonArray.getJSONObject(i).get("name").toString());
//                    taskStatus.add(jsonArray.getJSONObject(i).get("taskStatus").toString());
//                    location.add(jsonArray.getJSONObject(i).get("location").toString());
//                    dateTime.add(jsonArray.getJSONObject(i).get("datetime").toString());
//                    price.add(jsonArray.getJSONObject(i).get("price").toString());
//
//                    duedate.add(jsonArray.getJSONObject(i).get("duedate").toString());
//                    detail.add(jsonArray.getJSONObject(i).get("detail").toString());
//                    postername.add(jsonArray.getJSONObject(i).get("userName").toString());
//                    comments.add(jsonArray.getJSONObject(i).get("commentsCount").toString());
//                    offers.add(jsonArray.getJSONObject(i).get("offersCount").toString());
//                    String img = jsonArray.getJSONObject(i).get("userprofileimage").toString();
//                    if (img.equals("null") || img == null || img.equals("")) {
//                        img = "http://tcap.pbworks.com/f/1435170280/myAvatar.png";
//                    }
//
//                    profimage.add(img);
//
//                    Log.d("pindi_*", "123" + taskID.toString() + taskName.toString() + taskID.toString());
//                }
//                }
//             }
//
//            }

            }

            Log.d("pindi+*","123"+taskID.toString()+taskName.toString()+taskID.toString());
            String[] name=taskName.toArray(new String[taskName.size()]);
            String[] prices=price.toArray(new String[price.size()]);
            String[] locations=location.toArray(new String[location.size()]);
            String[] taskIds=taskID.toArray(new String[taskID.size()]);
            String[] taskstatus=taskStatus.toArray(new String[taskStatus.size()]);
            String[] datetime=dateTime.toArray(new String[dateTime.size()]);
            String[] duedates=duedate.toArray(new String[duedate.size()]);
            String[] details=detail.toArray(new String[detail.size()]);
            String[] userids = userid.toArray(new String[userid.size()]);
            String[] posterames=postername.toArray(new String[postername.size()]);
            int[] image={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
            String[] comment=comments.toArray(new String[comments.size()]);
            String[] profimag=profimage.toArray(new String[profimage.size()]);
            String[] offer=offers.toArray(new String[offers.size()]);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putInt("profimagesize", profimag.length);
            for(int i=0;i<profimag.length;i++)
            {
                editor.remove("profimage" + i);
                editor.putString("profimage" + i,  profimag[i]);
            }


            editor.putInt("useridsize", profimag.length);
            for(int i=0;i<userids.length;i++)
            {
                editor.remove("userid" + i);
                editor.putString("userid" + i,  userids[i]);
            }

            editor.putInt("commentssize", comment.length);
            for(int i=0;i<comment.length;i++)
            {
                editor.remove("commentsize" + i);
                editor.putString("commentsize" + i,  comment[i]);
            }
            editor.putInt("offerssize", comment.length);
            for(int i=0;i<offer.length;i++)
            {
                editor.remove("offersize" + i);
                editor.putString("offersize" + i,  offer[i]);
            }
            editor.putInt("taskidsize", taskIds.length);
            for(int i=0;i<taskIds.length;i++)
            {
                editor.remove("taskid" + i);
                editor.putString("taskid" + i,  taskIds[i]);
            }
            editor.putInt("taskstatussize", taskstatus.length);
            for(int i=0;i<taskstatus.length;i++)
            {
                editor.remove("taskstatus" + i);
                editor.putString("taskstatus" + i,  taskstatus[i]);
            }
            editor.putInt("datetimesize", datetime.length);
            for(int i=0;i<datetime.length;i++)
            {
                editor.remove("datetime" + i);
                editor.putString("datetime" + i,  datetime[i]);
            }
            editor.putInt("duedatesize", duedates.length);
            for(int i=0;i<duedates.length;i++)
            {
                editor.remove("duedate" + i);
                editor.putString("duedate" + i,  duedates[i]);
            }
            editor.putInt("detailsize", details.length);
            for(int i=0;i<details.length;i++)
            {
                editor.remove("detail" + i);
                editor.putString("detail" + i,  details[i]);
            }
            editor.putInt("posternamesize", posterames.length);
            for(int i=0;i<posterames.length;i++)
            {
                editor.remove("postername" + i);
                editor.putString("postername" + i,  posterames[i]);
            }
            editor.putInt("tasknamesize", name.length);
            for(int i=0;i<name.length;i++)
            {
                editor.remove("taskname" + i);
                editor.putString("taskname" + i,  name[i]);
            }
            editor.putInt("taskpricesize", prices.length);
            for(int i=0;i<prices.length;i++)
            {
                editor.remove("taskprice" + i);
                editor.putString("taskprice" + i,  prices[i]);
            }
            editor.putInt("tasklocationsize", locations.length);
            for(int i=0;i<locations.length;i++)
            {
                editor.remove("tasklocation" + i);
                editor.putString("tasklocation" + i,  locations[i]);
            }

            editor.apply();

            Log.d("TAGG",namesize+"   "+name.length);
           if (namesize!=name.length)
            getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(minprice,maxprice,latitudee,longitudee,radius)).commit();







           // e.setCats(cats)mm;
            //String[] catarray=  cats.toArray(new String[cats.size()]);

            //     getresult();

           // Log.d("(*&^",cats.toString());}catch (Exception e){
         //   Log.d("yewa",e.toString());
        }
        catch (Exception e){
            Log.e("Duhai",e.toString());
        }
    }
    boolean checklocation(Double lati, Double longi){
        try{
            double dblat= lati;
            double dblng= longi;

//            Log.i(LOGSERVICE, "lat2 " + dblat);
//            Log.i(LOGSERVICE, "lng2 " + dblng);
            double lat1 = latitudee;
            double lon1 = longitudee;
            double incrlng = 0.85;
            double incrlat = (radius)/ (Math.cos(lat1/57.3)*69.1);
            //here below lat1 is your latitude and lon1 is your longitude
            double  north = lat1 + incrlat;
            double west = lon1 - incrlng;
            double south = lat1 - incrlat;
            double east  = lon1 + incrlng;
            double latitude = dblat;
            double longitude = dblng;
            // check your latitude and longitude with the above co-ordinates,like below
            if(latitude >= south && latitude  <= north && longitude <= east && longitude >= west)// check the user latitude and longitude satisfies the if condition, if so he is within the 1 KM radius.
            {

//                Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                Log.i("LOGSERVICE", "merabhai");
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
            }
    }

}
//taskId,taskName,price,location,dateTime,taskStatus
// "name": "plumber required",
//            "price": 3400,
//            "userName": "umerkhaldi",
//            "datetime": "2020-07-10T17:15:55.2270139",
//            "duedate": "0001-01-01T00:00:00",
//            "detail": "plumber bht acha hona chaye",
//            "location": "384-E Johar Town Lahore، Block E Phase 1 Johar Town, Lahore, Punjab 54700, Pakistan",
//            "posterName": "umerkhaldi",
//            "taskID":0,