package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class TaskFilters extends Fragment {

    ImageView backfilter;
    CrystalRangeSeekbar rangeSeekbar;
    TextView textrange, radius;
    Button save, cancel;


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

                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney()).commit();

            }
        });

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                String min=String.valueOf(minValue);
                String max=String.valueOf(maxValue);
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

                    }
                });  bottomSheet.findViewById(R.id.tenkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("10 km");
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.onefivekmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("15 km");
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.twofivekmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("25 km");
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.fiftykmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("50 km");
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.hundredkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("100 km");
                        bottomSheetDialog.hide();

                    }
                });  bottomSheet.findViewById(R.id.allkmid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        radius.setText("Everywhere");
                        bottomSheetDialog.hide();

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

                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney()).commit();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney()).commit();

            }
        });


        return view;
    }
}
