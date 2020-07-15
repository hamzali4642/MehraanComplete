package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class TermAndConditions extends Fragment {

    ImageView backtandc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View view=inflater.inflate(R.layout.fragment_term_and_conditions, container, false);

           backtandc=view.findViewById(R.id.backtandcid);
           backtandc.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Intent intent=new Intent(getContext(),Dashboard.class);
                   intent.putExtra("back",4);
                   startActivity(intent);

               }
           });


        return view;
    }
}
