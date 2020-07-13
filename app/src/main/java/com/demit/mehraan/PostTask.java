package com.demit.mehraan;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;

import static android.R.style.Theme_DeviceDefault_Light_Dialog;


public class PostTask extends Fragment {

    EditText title, description, address, price;
    Button post;
    RadioButton online, physical;
    TextView duedate;
    DatePickerDialog.OnDateSetListener duedateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post_task, container, false);
        title=view.findViewById(R.id.tasktitleid);
        duedate=view.findViewById(R.id.taskduedateid);
        description=view.findViewById(R.id.taskdescriptionid);
        address=view.findViewById(R.id.taskadressid);
        price=view.findViewById(R.id.taskbudgetid);
        post=view.findViewById(R.id.posttaskid);
        physical=view.findViewById(R.id.radiophysicalid);
        post=view.findViewById(R.id.radioonlineid);


        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal= Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(getContext(), Theme_DeviceDefault_Light_Dialog,duedateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(225,255,255,255)));
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
            }
        };


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });





        return view;
    }
}
