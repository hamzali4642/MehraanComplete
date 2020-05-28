package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class EarnMoney extends Fragment {

    RecyclerView earnmoneylist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_earn_money, container, false);
       earnmoneylist=view.findViewById(R.id.tasklistid);

        earnmoneylist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Task number 1","Task number 2","Task number 3","Task number 4","Task number 5","Task number 6","Task number 7",};
        String[] price={"120","450","200","120","450","200","500"};
        String[] location={"task location 1","task location 2","task location 3","task location 4","task location 5","task location 6","task location 7"};
        String[] comments={"12","45","20","12","50","0","50"};
        String[] offers={"10","50","20","10","40","0","0"};
        int[] image={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

        earnmoneylist.setAdapter(new EarnMoneyAdapter(name,location,price,comments,offers,image));


        return view;
    }
}
