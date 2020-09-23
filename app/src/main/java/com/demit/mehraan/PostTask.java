package com.demit.mehraan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.R.style.Theme_DeviceDefault_Light_Dialog;


public class PostTask extends Fragment {

    EditText title, description, price;
    Button post;
    String titlestr,descriptionstr,addressstr,getAddressstr="",pricestr,typestr="";
    RadioButton online, physical;
    String token;
    TextView duedate;
    DatePickerDialog.OnDateSetListener duedateSetListener;
    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyAcxInhB-GFouGG3fTgyc60KWabj_n3kPs";
    SharedPreferences mSharedPreference;
    String dates="";
    String latlng,getLatlng;
    LatLng ll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post_task, container, false);

        title=view.findViewById(R.id.tasktitleid);
        duedate=view.findViewById(R.id.taskduedateid);
        description=view.findViewById(R.id.taskdescriptionid);
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getContext());

        // address=view.findViewById(R.id.taskadressid);
        price=view.findViewById(R.id.taskbudgetid);
        post=view.findViewById(R.id.posttaskid);
        physical=view.findViewById(R.id.radiophysicalid);
        online=view.findViewById(R.id.radioonlineid);
        token=mSharedPreference.getString("token","");
        typestr="Physical Task";
        setupAutoCompleteFragment();


        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAddressstr="Online Task";
                getLatlng="Online Task";
                typestr="Online Task";

            }
        });
        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddressstr="";
                typestr="Physical Task";

            }
        });
        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal= Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(getContext(), Theme_DeviceDefault_Light_Dialog,duedateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(225,255,255,255)));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-100);
                dialog.show();

            }
        });

        duedateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                Log.d("TAG", "onDateSet: date:" + day + "/" + month + "/" + year );

                String date = day + "/" + month + "/" +year;
                duedate.setText(date);
                if(day<10)
                {
                    String as="0"+day;
                    day=Integer.parseInt(as);
                }
                if(month<10){
                    if(day<10)
                    {
                        dates= year+"-0"+month +"-0"+day;
                    }
                    else
                    {dates= year+"-0"+month +"-"+day;}
                }
                else {  dates= year+"-"+month +"-"+day   ;
                    if(day<10)
                    {
                        dates= year+"-0"+month +"-0"+day;
                    }}

            }
        };


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlestr=title.getText().toString();
                descriptionstr=description.getText().toString();

                pricestr=price.getText().toString();
//                if( getAddressstr== null){
//                    Toast.makeText(getContext(),"Please Enter your address, as you mentioned your Task is Physical",Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (!getAddressstr.equals("Online Task"))
                {  getAddressstr=addressstr;
                    if(getAddressstr==null){
                        getAddressstr="";
                    }
                    getLatlng=latlng;
                }



                if(!titlestr.equals("")&&!descriptionstr.equals("")&&!pricestr.equals("")&&!getAddressstr.equals("")
                && !dates.equals("")){

                    if(pricestr.length()>7){
                        Toast.makeText(getContext(), "Invalid Amount", Toast.LENGTH_SHORT).show();
                    }

                    senddata();
                }
                else {

                    if(!getAddressstr.equals("") ){
                        Toast.makeText(getContext(),"Please Complete all the fields",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getContext(),"Please Enter your address, as you mentioned your Task is Physical",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





        return view;
    }
    public void senddata(){
        try {

            SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
            loading.setCancelable(false);
            loading.setTitleText("Loading....");
            loading.show();
            String URL = "https://mehraan-oh3.conveyor.cloud/api/posttask/posttask";
            //   URL= "https://httpbin.org/post";
            URL=Constants.BaseUrl+"posttask/posttask";
            //{"Details":"as","DueDate":"2020-08-26T00:00:00","Location":"Online Task","Price":11,"TaskName":"asd","TaskType":"Online Task"}
            //{\r\n    \"TaskName\":\"TaskName\",\r\n    \"Location\":\"adadaw\",\r\n    \"Price\":1200,\r\n    \"Details\":\"adwda\",\r\n    \"TaskType\":\"adwaw\",\r\n    \"TaskStatus\":\"fghj\"\r\n}
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("TaskName",titlestr );
            jsonBody.put("Details",descriptionstr );
            jsonBody.put("Location",getAddressstr);
            jsonBody.put("Price" ,Integer.valueOf(pricestr ));
            jsonBody.put("Directions" ,getLatlng);
            String myStrDate =dates+"T00:00:00";
            //   dates="2020-07-10T17:15:55.2270139";
            Log.d("asd",myStrDate);

            jsonBody.put("DueDate" ,myStrDate);

            jsonBody.put("TaskType", typestr );

            // jsonBody.put("Author", "BNK");
            final String requestBody = jsonBody.toString();

            //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("yewalaresponse",response.toString());

                    loading.dismiss();
                    //TODO: handle success
                    title.setText("");
                    description.setText("");
                    //   address.setText("");
                    price.setText("");
                    duedate.setText("");
                    dates="";

                    startActivity(new Intent(getActivity(),BridgeActivity.class));
                    Toast.makeText(getContext(),"Task Posted",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    loading.dismiss();
                    //TODO: handle failure
                }
            }

            )
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

    private void setupAutoCompleteFragment() {
        AutocompleteSupportFragment autoCompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment);

        if ( !Places.isInitialized() ) {
            Places.initialize( getContext(), GOOGLE_MAPS_API_KEY );
            autoCompleteFragment.setPlaceFields( Arrays.asList( Place.Field.ID,
                    Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG ) );
            autoCompleteFragment.setCountry( "PK" );
        }
        ImageView ivClear = autoCompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button);
        // ivClear.setImageResource(R.drawable.ic_close_white_24dp);
        ivClear.setImageResource(R.drawable.ic_close_black_48dp);
        EditText et = autoCompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input);
        et.setTextColor(Color.WHITE);
        et.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        autoCompleteFragment.setOnPlaceSelectedListener( new PlaceSelectionListener() {




            @Override
            public void onPlaceSelected(@NonNull Place place) {

                addressstr=place.getAddress().toString();
                //latlng=place.getLatLng().toString();
                ll=place.getLatLng();
                latlng=String.valueOf(ll.latitude)+"@"+String.valueOf(ll.longitude);
                String[] parts=latlng.split("@");
                String lat=parts[0];
                String lng=parts[1];

                //       getFragmentManager().beginTransaction().replace(R.id.dashfragid,new MapsFragment(lat,lng)).commit();

                Log.d("TAG",latlng);

            }

            @Override
            public void onError(Status status) {
                Log.i( "TAG", "PlaceSelectionListener >>> An error occurred: " + status );
            }

        });
    }
}
//0001-01-01T00:00:00