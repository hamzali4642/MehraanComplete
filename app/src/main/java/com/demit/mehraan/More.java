package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class More extends Fragment {
    TextView name, edit, skills, changepass, verification, contactus, tandc,logout;
    CircleImageView dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more, container, false);

        name=view.findViewById(R.id.accountnameid);
        edit=view.findViewById(R.id.editbtnid);
        skills=view.findViewById(R.id.skillsid);
        changepass=view.findViewById(R.id.changepassid);
        verification=view.findViewById(R.id.verificationid);
        contactus=view.findViewById(R.id.contactusid);
        tandc=view.findViewById(R.id.tandcid);
        logout=view.findViewById(R.id.logoutid);
        dp=view.findViewById(R.id.accountdpid);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        return view;
    }
}
