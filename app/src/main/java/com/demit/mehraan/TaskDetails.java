package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class TaskDetails extends Fragment {

    RecyclerView offerlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_details, container, false);

       offerlist=view.findViewById(R.id.offerlistid);

       offerlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Person 1","Person 2","Person 3",};
        int[] review={2,1,0};
        int[] rating={4,3,0};
        String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
        int[] dp={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
        boolean[] email={true,false,false};
        boolean[] phone={true,true,true};
        boolean[] cnic={true,false,true};
        boolean[] payment={false,false,false};

        offerlist.setAdapter(new OfferAdapter(name,dp,time,rating,review,email,phone,payment,cnic));






        return view;
    }
}
