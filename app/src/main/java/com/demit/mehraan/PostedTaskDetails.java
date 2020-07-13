package com.demit.mehraan;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;


public class PostedTaskDetails extends Fragment {

    RecyclerView myofferlist, mycommentslist;
    TextView myopen, myassigned, mycompleted, myreviewed, mytaskname, mypostername, mytasklocation, mypostime, myshowonmap, mytaskdate, mytaskdetails;
    EditText mywritecomment;
    ImageView mysendbtn, mybackbtn;
    Button changestatust;
    CircleImageView myposterimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_posted_task_details, container, false);

        myopen=view.findViewById(R.id.myopenid);
        myassigned=view.findViewById(R.id.myassignedid);
        mycompleted=view.findViewById(R.id.mycompletedid);
        myreviewed=view.findViewById(R.id.myreviewedid);
        mytaskname=view.findViewById(R.id.mytasknameid);
        mypostername=view.findViewById(R.id.myposternameid);
        mytasklocation=view.findViewById(R.id.mytasklocationid);
        mypostime=view.findViewById(R.id.myposttimeid);
        myshowonmap=view.findViewById(R.id.myshowonmapbtn);
        mytaskdate=view.findViewById(R.id.mytaskdateid);
        mytaskdetails=view.findViewById(R.id.mytaskdetailsid);
        mywritecomment=view.findViewById(R.id.mytypecommentid);
        mysendbtn=view.findViewById(R.id.mysendbtnid);
        mybackbtn=view.findViewById(R.id.backmytaskid);
        changestatust=view.findViewById(R.id.changestatusbtnid);
        myofferlist=view.findViewById(R.id.myofferlistid);
        mycommentslist=view.findViewById(R.id.mycommentslistid);

        myofferlist.setLayoutManager(new LinearLayoutManager(getContext()));
        mycommentslist.setLayoutManager(new LinearLayoutManager(getContext()));



        String[] name={"Person 1"};
        String[] review={"2"};
        int[] rating={4};
        String[] time={"4 hrs ago"};
        String[] comment={"COmment 1"};
        int[] dp={R.drawable.ic_launcher_background};
        boolean[] email={true};
        boolean[] phone={true};
        boolean[] cnic={true};
        boolean[] payment={false};

        myofferlist.setAdapter(new MyOfferAdapter(name,review,time,rating,dp,email,phone,payment,cnic));
        mycommentslist.setAdapter(new CommentsAdapter(comment, time, name,dp));



        return view;
    }
}
