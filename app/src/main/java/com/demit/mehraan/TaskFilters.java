package com.demit.mehraan;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TaskFilters extends Fragment {

    ImageView backfilter;
    CrystalRangeSeekbar rangeSeekbar;
    TextView textrange, radius;
    Button save, cancel;

    Double latitude=0.0,longitude=0.0;
    private FusedLocationProviderClient fusedLocationClient;
    Integer minprice,maxprice,radiuss=0;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_task_filters, container, false);

        backfilter=view.findViewById(R.id.backfilterid);
        rangeSeekbar= view.findViewById(R.id.rangeseekbarid);
        textrange=view.findViewById(R.id.textrangeid);
        radius=view.findViewById(R.id.radiusid);
        save=view.findViewById(R.id.savetsksettingsid);
        cancel=view.findViewById(R.id.canceltsksettingsid);

        backfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(0,0,0.0,0.0,radiuss)).commit();

            }
        });

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                String min=String.valueOf(minValue);
                String max=String.valueOf(maxValue);

                maxprice=Integer.valueOf(max);
                minprice=Integer.valueOf(min);

                textrange.setText("Price Range               RS."+min+"     -     Rs."+max);
            }
        });

        radius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(getContext(),R.style.BottomSheet);
                View bottomSheet=LayoutInflater.from(getContext()).inflate(R.layout.radius_bottom_layout, (LinearLayout)view.findViewById(R.id.bottomsheetcradius));

                bottomSheet.findViewById(R.id.fivekmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("5 km");
                        radiuss=5;
                        get();
                        bottomSheetDialog.hide();
                    }
                });  bottomSheet.findViewById(R.id.tenkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("10 km");
                        get();
                        radiuss=10;
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.onefivekmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("15 km");
                        get();
                        radiuss=15;
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.twofivekmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("25 km");
                        get();
                        radiuss=25;
                        bottomSheetDialog.hide();


                    }
                });  bottomSheet.findViewById(R.id.fiftykmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("50 km");
                        get();
                        radiuss=50;
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.hundredkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("100 km");
                        get();
                        radiuss=100;
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.allkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("Everywhere");
//                        bottomSheetDialog.hide();

                        latitude=0.0;
                        longitude=0.0;
                        radiuss=0;
                    }
                });  bottomSheet.findViewById(R.id.cancelkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bottomSheetDialog.hide();

                    }
                });



                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.show();


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(minprice,maxprice,latitude,longitude,radiuss)).commit();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(0,0,latitude,longitude,0)).commit();

            }
        });


        return view;
    }



    public void get() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


        if (ContextCompat.checkSelfPermission(getContext(),
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

                                latitude=lat;
                                longitude=log;
                                location.reset();

                            } else {
//                                Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

        }
    }
}
