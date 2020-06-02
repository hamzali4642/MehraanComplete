package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class PostTask extends Fragment {

    EditText title, description, address, price;
    Button post;
    RadioButton online, physical;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post_task, container, false);
        title=view.findViewById(R.id.tasktitleid);
        description=view.findViewById(R.id.tasktitleid);
        address=view.findViewById(R.id.tasktitleid);
        price=view.findViewById(R.id.tasktitleid);
        post=view.findViewById(R.id.posttaskid);
        physical=view.findViewById(R.id.radiophysicalid);
        post=view.findViewById(R.id.radioonlineid);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });





        return view;
    }
}
