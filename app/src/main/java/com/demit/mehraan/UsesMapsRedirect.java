package com.demit.mehraan;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsesMapsRedirect extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener   {
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    private GoogleMap mMap;
    TextView destination, current, estTime;
    Bitmap BitMapMarker;
    float Bearing = 0;
    String taskid;
    boolean AnimationStatus = false;
    private PolylineOptions polylineOptions,blackPolylineOptions;
    private Polyline blackPolyline,greyPolyline;
    private List<LatLng> polylineList;
    private Marker marker;
    private LatLng startPosition,endPosition;
    private int index,next;
    TextView start, end;
    Button openmap;
    String src="asd";
    String des= "Fortress Stadium, Aziz Bhatti Rd, Saddar Town, Lahore, Punjab";
    LatLng deslatlng;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uses_maps_redirect);

        destination=findViewById(R.id.startid);
        current=findViewById(R.id.currentid);
        openmap=findViewById(R.id.previewbtnid);
        estTime=findViewById(R.id.estimatedtimeid);
        estTime.setVisibility(View.INVISIBLE);




        taskid=getIntent().getStringExtra("taskid");
        des=getIntent().getStringExtra("location");
        destination.setText(des);
        SharedPreferences sharedPreferences = getSharedPreferences("taskid",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("id",taskid);
        editor.apply();
        startService(new Intent(UsesMapsRedirect.this,LocService.class));


        String latilongi=getIntent().getStringExtra("direction");
        String[] latlongi=latilongi.split("@");
        deslatlng = new LatLng(Double.parseDouble(latlongi[0]), Double.parseDouble(latlongi[1]));
        polylineList=new ArrayList<>();


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("TaskLocation");

        if(!taskid.equals(""))
        {
            reference.child(taskid).child("DestinationLocation").setValue(deslatlng).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){

                    }else{
                    }
                }
            });
        }



        if(!taskid.equals(""))
        {

        }

      //  BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_pin_on_24);
       // Bitmap b = bitmapdraw.getBitmap();
        Bitmap b = meth(R.drawable.ic_pin_on_24);
        BitMapMarker = b;
        if(requestSinglePermission()){

            SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                    .findFragmentById(R.id.mapfragfragid);
            mapFragment.getMapAsync(this);
            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            checkLocation();





        }


        openmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String src= current.getText().toString().trim();
                String des= destination.getText().toString().trim();

                if (src.equals(" ") && des.equals("")){

//                    Toast.makeText(getApplicationContext(),"You need to enter both location",Toast.LENGTH_SHORT).show();
                }
                else {

                    Displaytrack(src, des);

                }


            }
        });

    }

    private void Displaytrack(String src, String des) {
        try{

            Uri uri= Uri.parse("https://www.google.co.in/maps/dir/"+ src +"/" + des);
            Intent intent= new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e) {

            AlertDialog.Builder builder = new AlertDialog.Builder(UsesMapsRedirect.this);
            builder.setTitle("You do not have Google Maps in your Device")
                    .setMessage("Download Google Maps?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }).create();
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(0,0,0));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(0,0,0));
                }});
            dialog.show();

        }

    }
    private boolean checkLocation() {

        if(!isLocationEnabled()){
            showAlert();
        }
        return isLocationEnabled();

    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
    private boolean isLocationEnabled() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }


                }).check();

        return isPermission;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(mGoogleApiClient !=null){
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();



        latLng = new LatLng(location.getLatitude(), location.getLongitude());

//        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference().child("TaskLocation");
//        reference1.child(taskid).child("UserLocation").setValue(latLng).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()){
//
//                }else{
//                }
//            }
//        });

        try {
            returnAddress(location.getLatitude() , location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.mapfragfragid);
        mapFragment.getMapAsync(this);
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
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("TAG", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("TAG", "Can't find style. Error: ", e);
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        if(!flag){
        try {
            Location location1 = new Location("");
            location1.setLatitude(latLng.latitude);
            location1.setLongitude(latLng.longitude);
            Location location2 = new Location("");
            location2.setLatitude(deslatlng.latitude);
            location2.setLongitude(deslatlng.longitude);
            float distanceInMeters = location1.distanceTo(location2);
            int speedIs10MetersPerMinute = 100;
            float estimatedDriveTimeInMinutes = distanceInMeters / speedIs10MetersPerMinute;
            int hours=(int) estimatedDriveTimeInMinutes/60;
            int minutes=(int) (estimatedDriveTimeInMinutes-hours*60);
            String time;

            int kms=(int) distanceInMeters/1000;
//            estTime.setText(hours+"hr, "+minutes+"min");
            estTime.setText(kms+"Kms, "+hours+"hr, "+minutes+"min");
            estTime.setVisibility(View.VISIBLE);

            Log.d("khati",String.valueOf(estimatedDriveTimeInMinutes));


            String destination = des.replace(" ", "+");
           // requestUrl = "https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving&" +
             //       "origin=" + latLng.latitude + "," + latLng.longitude + "&" + "destination=" + destination + "&" + "key=" + GOOGLE_MAPS_API_KEY;
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving&"+
                    "origin="+latLng.latitude+","+latLng.longitude+"&"+"destination="+destination+"&"+"key="+getResources().getString(R.string.google_maps_key);

            StringRequest request = new StringRequest(Request.Method.GET,requestUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // Hiding the progress dialog after all task complete.

                            try {
                                Log.d("phoole",response);
                                JSONObject jsonObject=new JSONObject(response.toString());
                                JSONArray jsonArray=jsonObject.getJSONArray("routes");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject route=jsonArray.getJSONObject(i);
                                    JSONObject poly=route.getJSONObject("overview_polyline");
                                    String polyline=poly.getString("points");
                                    polylineList=decodePoly(polyline);


                                }
                                //adjusting Bounds
                                LatLngBounds.Builder builder=new LatLngBounds.Builder();
                                for(LatLng latLng:polylineList)
                                    builder.include(latLng);
                                LatLngBounds bounds=builder.build();
                                CameraUpdate mCameraUpdate=CameraUpdateFactory.newLatLngBounds(bounds,2);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions=new PolylineOptions();
                                polylineOptions.color(Color.BLUE);
                                polylineOptions.width(5);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polylineList);
                                greyPolyline=mMap.addPolyline(polylineOptions);


                                blackPolylineOptions=new PolylineOptions();
                                blackPolylineOptions.color(Color.WHITE);
                                blackPolylineOptions.width(5);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(JointType.ROUND);
                                blackPolylineOptions.addAll(polylineList);
                                blackPolyline=mMap.addPolyline(blackPolylineOptions);
                                mMap.addMarker(new MarkerOptions().position(polylineList.get(polylineList.size()-1)));
                                //Animator
                                final ValueAnimator polylineAnimator=ValueAnimator.ofInt(0,100);
                                polylineAnimator.setDuration(2000);
                                polylineAnimator.setInterpolator(new LinearInterpolator());
                                polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        List<LatLng> points = greyPolyline.getPoints();
                                        int percentValue=(int)valueAnimator.getAnimatedValue();
                                        int Size=points.size();
                                        int newPoints=(int) (Size*(percentValue/100.0f));
                                        List<LatLng> p=points.subList(0,newPoints);
                                        blackPolyline.setPoints(p);
                                    }
                                });

                                polylineAnimator.start();

                                marker=mMap.addMarker(new MarkerOptions().position(latLng).flat(true).icon(BitmapDescriptorFactory.fromBitmap(BitMapMarker)));
//car moving
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            }

                            // Showing response message coming from server.
                            // Log.d("yewalaresponse", ServerResponse);


                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.

                            // Showing error message if something goes wrong.
                            Log.d("yewalaerror2", volleyError.toString());

                        }
                    }) {


            };


            Volley.newRequestQueue(getApplicationContext()).add(request);


            mMap.addMarker(new MarkerOptions().position(latLng).title("My Location").icon(BitmapDescriptorFactory.fromBitmap(BitMapMarker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14F));
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(googleMap.getCameraPosition().target)
                    .zoom(googleMap.getCameraPosition().zoom)
                    .bearing(30)
                    .tilt(45)
                    .build()));
            flag=true;
        }

        catch (Exception e){}}


        }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Getting Location", Toast.LENGTH_SHORT).show();
        }
    }
    private void startLocationUpdates() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

    }
    public void returnAddress(double latitude , double longitude) throws Exception {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else
        String area = addresses.get(0).getAdminArea();


        if(src.equals("asd")){
//            Toast.makeText(this, "Address " + address + "\nArea " + area + "\nState = " + state + "\nCountry = " + country, Toast.LENGTH_SHORT).show();

            //Biryani Express, Allah Hoo Chowk, Lahore, Pakistan
            src=address;
            current.setText(src);
        }
    }
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
    public Bitmap meth(int i){
        try {
            Bitmap bitmap;

            Drawable drawable=getDrawable(i);
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }


}