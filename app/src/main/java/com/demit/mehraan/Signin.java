package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class Signin extends Fragment {


    TextView signup,forgetPass;
    Button login;
    EditText phone, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin, container, false);

        login=(Button)view.findViewById(R.id.loginbtnid);
        phone=(EditText) view.findViewById(R.id.loginphoneid);
        password=(EditText) view.findViewById(R.id.loginpasswordid);
        signup=(TextView)view.findViewById(R.id.loginsignupid);
        forgetPass=(TextView)view.findViewById(R.id.loginforgetpassid);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification()).commit();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(),Dashboard.class);
                startActivity(intent);


            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification()).commit();

            }
        });



        return view;
    }
}
