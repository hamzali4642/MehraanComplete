package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class Mobile_Verification extends Fragment {

    Button verifynumber;
    ImageView close, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_mobile__verification, container, false);
       close=(ImageView)view.findViewById(R.id.closemblverifybtnid);
       back=(ImageView)view.findViewById(R.id.backmblverifybtnid);
       verifynumber=(Button)view.findViewById(R.id.verifynumberid);

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

                Intent intent= new Intent(getContext(),Dashboard.class);
                startActivity(intent);

            }
        });



        verifynumber.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               getFragmentManager().beginTransaction().replace(R.id.signupfragid,new PinCode()).commit();

           }
       });



       return view;
    }
}
