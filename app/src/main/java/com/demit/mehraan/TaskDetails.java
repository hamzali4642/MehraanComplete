package com.demit.mehraan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.$Gson$Preconditions;

import de.hdodenhof.circleimageview.CircleImageView;


public class TaskDetails extends Fragment {

    RecyclerView offerlist, commentslist;
    TextView open, assigned, completed, reviewed, taskname, postername, tasklocation, postime, showonmap, taskdate, taskbudget, taskdetails;
    EditText writecomment;
    ImageView sendbtn, backbtn;
    Button makeoffer;
    CircleImageView posterimage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task_details, container, false);

        open=view.findViewById(R.id.openid);
        assigned=view.findViewById(R.id.assignedid);
        completed=view.findViewById(R.id.completedid);
        reviewed=view.findViewById(R.id.reviewedid);
        taskname=view.findViewById(R.id.tasknameid);
        postername=view.findViewById(R.id.posternameid);
        tasklocation=view.findViewById(R.id.tasklocationid);
        postime=view.findViewById(R.id.posttimeid);
        showonmap=view.findViewById(R.id.showonmapbtn);
        taskdate=view.findViewById(R.id.taskdateid);
        taskbudget=view.findViewById(R.id.estimatedbudgetid);
        taskdetails=view.findViewById(R.id.taskdetailsid);
        writecomment=view.findViewById(R.id.typecommentid);
        sendbtn=view.findViewById(R.id.sendbtnid);
        backbtn=view.findViewById(R.id.backtasdetailsid);
        makeoffer=view.findViewById(R.id.makeofferbtnid);
        makeoffer=view.findViewById(R.id.makeofferbtnid);
        posterimage=view.findViewById(R.id.posterdp);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        makeoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        showonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       offerlist=view.findViewById(R.id.offerlistid);
       commentslist=view.findViewById(R.id.commentslistid);

       offerlist.setLayoutManager(new LinearLayoutManager(getContext()));
        commentslist.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] name={"Person 1","Person 2","Person 3"};
        String[] review={"2","1","0"};
        int[] rating={4,3,0};
        String[] time={"4 hrs ago","6 hrs ago","20 hrs ago"};
        String[] comment={"COmment 1","Comment 2","Comment 3"};
        int[] dp={R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
        boolean[] email={true,false,false};
        boolean[] phone={true,true,true};
        boolean[] cnic={true,false,true};
        boolean[] payment={false,false,false};

        offerlist.setAdapter(new OfferAdapter(name,dp,time,rating,review,email,phone,payment,cnic));

        commentslist.setAdapter(new CommentsAdapter(comment, time, name,dp));



        return view;
    }
}
