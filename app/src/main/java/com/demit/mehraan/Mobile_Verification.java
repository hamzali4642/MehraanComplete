package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Mobile_Verification extends Fragment {

    Button verifynumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_mobile__verification, container, false);

       //fuck you

       verifynumber=(Button)view.findViewById(R.id.verifynumberid);
        verifynumber.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               getFragmentManager().beginTransaction().replace(R.id.signupfragid,new PinCode()).commit();

           }
       });



       return view;
    }
}
