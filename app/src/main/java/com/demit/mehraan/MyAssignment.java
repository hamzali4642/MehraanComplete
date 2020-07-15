package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAssignment extends Fragment {

    TextView asstaskname, assprice, asspostername, assposttime, asslocation, assduedate, asstaskstatus;
    ImageView assbackbtn;
    Button asschangestatust;
    CircleImageView assposterimage;
    Fragment assmap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_assignment, container, false);
        asstaskname=view.findViewById(R.id.asstasknameid);
        assprice=view.findViewById(R.id.commitedprice);
        asspostername=view.findViewById(R.id.assposternameid);
        assposttime=view.findViewById(R.id.assposttimeid);
        asslocation=view.findViewById(R.id.asstasklocationid);
        assduedate=view.findViewById(R.id.asstaskdateid);
        asstaskstatus=view.findViewById(R.id.asstxttttt);
        asschangestatust=view.findViewById(R.id.asschangestatusbtnid);
        assposterimage=view.findViewById(R.id.assposterdp);
        assbackbtn=view.findViewById(R.id.backasstaskid);

        assbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Dashboard.class);
                intent.putExtra("back",2);
                startActivity(intent);

            }
        });

        asschangestatust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



        return view;
    }
}
