package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class Signup extends Fragment {

    Spinner type;
    ImageView close, back;
    Button signup;
    EditText firstname, lastname, email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signup, container, false);

        type=(Spinner)view.findViewById(R.id.typespinnerid);
        String[] items = new String[]{"Earn Money","Post Task"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        close=(ImageView)view.findViewById(R.id.closepinverifybtnid);
        back=(ImageView)view.findViewById(R.id.backpinverifybtnid);
        signup=(Button)view.findViewById(R.id.signupbtnid);
        firstname=(EditText) view.findViewById(R.id.firstnaemid);
        lastname=(EditText) view.findViewById(R.id.lastnameid);
        email=(EditText) view.findViewById(R.id.emailid);
        password=(EditText) view.findViewById(R.id.passwordid);




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



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });




        return view;
    }
}
