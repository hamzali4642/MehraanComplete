package com.demit.mehraan;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAssignment extends Fragment implements OnMapReadyCallback {

    TextView asstaskname, assprice, asspostername, assposttime, asslocation, assduedate, asstaskstatus;
    ImageView assbackbtn;
    Button asschangestatust;
    CircleImageView assposterimage;
    Fragment assmap;
    private GoogleMap mMap;
    RelativeLayout assignmentleave;
    String detail;
    String taskstatus;
    String duedates;
    String taskid;
    String posternames,price;
    String location,date;
    String taskname,userimage;
    String direction;

    View view;
    public MyAssignment(String detail, String taskstatus, String duedates, String taskid, String postername,String price,String location,String taskname,String userimage,String date,String direction ) {
        this.detail=detail;
        this.taskstatus=taskstatus;
        this.duedates=duedates;
        this.taskid=taskid;
        this.posternames=postername;
        this.price=price;
        this.location=location;
        this.taskname=taskname;
        this.userimage=userimage;
        this.date=date;
        this.direction=direction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_assignment, container, false);
        this.view=view;

        asstaskname=view.findViewById(R.id.asstasknameid);
        assprice=view.findViewById(R.id.commitedprice);
        asspostername=view.findViewById(R.id.assposternameid);
        assposttime=view.findViewById(R.id.assposttimeid);
        asslocation=view.findViewById(R.id.asstasklocationid);
        assduedate=view.findViewById(R.id.asstaskdateid);
        asstaskstatus=view.findViewById(R.id.asstxttttt);
        asschangestatust=view.findViewById(R.id.asschangestatusbtnid);
        assposterimage=view.findViewById(R.id.assposterdp);
        assbackbtn=view.findViewById(R.id.backasstaskid);
        asslocation.setText(location);
        assduedate.setText(duedates);
        assignmentleave=view.findViewById(R.id.assleavingbtnid);
        asspostername.setText(posternames);
        asstaskname.setText(taskname);
        assprice.setText(price);
        assposttime.setText(date);
        try{
            Glide.with(getContext()).load(Constants.ImageUrl+userimage).into(assposterimage);}
        catch (Exception e){}
        SupportMapFragment mapFragment =((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.assmapfrag));
        mapFragment.getMapAsync(this);


        assbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Dashboard.class);
                intent.putExtra("back",2);
                startActivity(intent);

            }
        });

        if(direction.equalsIgnoreCase("Online Task") || direction == null || direction.equals("null")){
            assignmentleave.setVisibility(View.GONE);
        }
        asschangestatust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),UsesMapsRedirect.class);
                intent.putExtra("taskid",taskid);
                intent.putExtra("location",location);
                intent.putExtra("direction",direction);
                startActivity(intent);

            }
        });


        if(taskstatus.equals("Assigned")){

            assignmentleave.setVisibility(View.VISIBLE);
            view.findViewById(R.id.assmapfrag).setVisibility(View.VISIBLE);
        }else{

            view.findViewById(R.id.assmapfrag).setVisibility(View.GONE);
            assignmentleave.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String requestUrl=null;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e("TAG", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("TAG", "Can't find style. Error: ", e);
        }

        try {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setTrafficEnabled(false);
            mMap.setIndoorEnabled(false);
            mMap.setBuildingsEnabled(false);
            String[] latlongi = direction.split("@");
            LatLng deslatlng = new LatLng(Double.parseDouble(latlongi[0]), Double.parseDouble(latlongi[1]));
            mMap.addMarker(new MarkerOptions().position(deslatlng).title("Destination"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(deslatlng));
        }catch (Exception e){
            view.findViewById(R.id.assmapfrag).setVisibility(View.GONE);
        }
    }
}
