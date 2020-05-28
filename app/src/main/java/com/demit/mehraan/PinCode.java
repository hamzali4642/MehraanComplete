package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class PinCode extends Fragment {

    Button resendpin, verifypin;
    ImageView close, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_pin_code, container, false);

        close=(ImageView)view.findViewById(R.id.closepinverifybtnid);
        back=(ImageView)view.findViewById(R.id.backpinverifybtnid);
        resendpin=(Button)view.findViewById(R.id.resendpinid);
        verifypin=(Button)view.findViewById(R.id.verifypinid);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(),Dashboard.class);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification()).commit();

            }
        });



        resendpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });





        return view;
    }
}
