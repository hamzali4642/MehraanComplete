package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends Fragment {
    CircleImageView dp;
    TextView updatedp,username,password, phone, changepassword;
    EditText email;
    Button update;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_profile, container, false);

        dp=view.findViewById(R.id.profileimageid);
        updatedp=view.findViewById(R.id.profiledpupdateid);
        username=view.findViewById(R.id.profilenaemid);
        password=view.findViewById(R.id.profilepassid);
        phone=view.findViewById(R.id.profilecontactid);
        changepassword=view.findViewById(R.id.profilechangepassid);
        email=view.findViewById(R.id.profileemailid);
        update=view.findViewById(R.id.updatebtnid);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updatedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
