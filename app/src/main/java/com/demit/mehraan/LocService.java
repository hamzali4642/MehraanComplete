package com.demit.mehraan;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class LocService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final String LOGSERVICE = "#######";
    ArrayList<String> locs = new ArrayList<String>(1);
    ArrayList<String> notifiableloc = new ArrayList<String>(1);
    SharedPreferences mSharedPreference;


    private LocationManager locationManager;
    private LocationListener locationListener;
    private FusedLocationProviderClient fusedLocationClient;
    private Handler handlerr = new Handler();
    private Runnable runnablee;

    boolean check=false;

    String taskno;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();

        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Log.i(LOGSERVICE, "onCreate");
        handler.post(runnable);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOGSERVICE, "onStartCommand");

        sharedPreferences=getSharedPreferences("taskid",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        taskno=sharedPreferences.getString("id","");

        check=true;
        //Set<String> set = mSharedPreference.getStringSet("locse", null);
        // Set<String> set1 = mSharedPreference.getStringSet("notloc", null);

        notifiableloc.clear();

        simplenotification();




//        int size = mSharedPreference.getInt("notlocSize", 0);
//
//        for (int i = 0; i < size; i++) {
//            notifiableloc.add(mSharedPreference.getString("notlocSize" + i, null));
//        }
//        //notifiableloc.addAll(set1);
//        locs.clear();
//        size = mSharedPreference.getInt("locseSize", 0);
//
//        for (int i = 0; i < size; i++) {
//            locs.add(mSharedPreference.getString("locse" + i, null));
//        }
        // locs.addAll(set);

        get();

        handlerr.postDelayed(runnablee = new Runnable() {
            @Override
            public void run() {
                handlerr.postDelayed(runnablee,3000);
               if(check)
                get();

            }
        },3000);

///////////////////////////////////////////

//        if (!mGoogleApiClient.isConnected())
//            mGoogleApiClient.connect();
        return START_STICKY;
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOGSERVICE, "onConnected" + bundle);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (l != null) {
            Log.i(LOGSERVICE, "lat " + l.getLatitude());
            Log.i(LOGSERVICE, "lng " + l.getLongitude());

        }

        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOGSERVICE, "onConnectionSuspended " + i);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOGSERVICE, "lat1 " + location.getLatitude());
        Log.i(LOGSERVICE, "lng1 " + location.getLongitude());

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
//        Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("TaskLocation");
//
//        reference.child(taskno).child("UserLocation").setValue(latLng).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if(task.isSuccessful()){
//
//                }else{
//                }
//            }
//        });

      //  EventBus.getDefault().post(mLocation);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(LOGSERVICE, "onDestroy - Estou sendo destruido ");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOGSERVICE, "onConnectionFailed ");

    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }




    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
           Log.d(LOGSERVICE,"ab"+ notifiableloc);
           notifiableloc.clear();
            SharedPreferences.Editor editor = mSharedPreference.edit();
            Set<String> set1 = new HashSet<String>();
            set1.add("a");
           // editor.putStringSet("notloc",set1);
            editor.putInt("notlocSize", set1.size());
            for(int i=0;i<set1.size();i++)
            {
                editor.remove("notloc" + i);
                editor.putString("notloc" + i,  "a");
            }
            editor.apply();
            // Repeat every 10 seconds
            handler.postDelayed(runnable, 43200000);
        }
    };

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent rsi =new Intent(getApplicationContext(),this.getClass());
        rsi.setPackage(getPackageName());
        startService(rsi);
        super.onTaskRemoved(rootIntent);
    }

    private void simplenotification(){
        int notificationid=0;

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.melogo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.melogo))
                .setContentTitle("You are on the way")
                .setContentText("Click when you reached")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        registerReceiver(stopServiceReceiver, new IntentFilter("myFilter"));
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, new Intent("myFilter"), PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
//        builder.addAction(0,"",contentIntent);
        NotificationManager notificationManage=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            String Channelid="Notification";
            NotificationChannel channel=new NotificationChannel(Channelid,"Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManage.createNotificationChannel(channel);
            builder.setChannelId(Channelid);
        }
        notificationManage.notify(notificationid,builder.build());
    }

    protected BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "ajkshdjkah", Toast.LENGTH_SHORT).show();
            check=false;
            editor.clear();
            editor.apply();
            stopService(new Intent(context,LocService.class));
        }
    };



    public void get() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        } else {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(android.location.Location location) {

                            // Got last known location. In some rare situations this can be null.

                            if (location != null) {
                                // Logic to handle location object

                                Double lat = location.getLatitude();
                                Double log = location.getLongitude();

                                LatLng lm = new LatLng(lat,log);


                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("TaskLocation");

                                if(!taskno.equals(""))
                                {
                                    reference.child(taskno).child("UserLocation").setValue(lm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                        }else{
                                        }
                                    }
                                });
                                }

                                location.reset();

                            } else {
                            }

                        }

                    });

        }
    }
}