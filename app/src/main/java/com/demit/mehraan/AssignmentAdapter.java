package com.demit.mehraan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private  String[] name, location, price, comments, offers,taskIds,tasksttus,datetimes,duedates,detail,posterames;
    private  String[]  dp,direction;
    int a;
    Context context;
    public AssignmentAdapter(String[] name,
                             String[] location,
                             String[] price,
                             String[] comments,
                             String[] offers, String[] image, String[] taskIds, String[] tasksttus, String[] datetimes, String[] duedates, String[] detail, String[] posterames,int a
                            ,String[] direction) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.comments = comments;
        this.offers = offers;
        this.taskIds = taskIds;
        this.tasksttus = tasksttus;
        this.datetimes = datetimes;
        this.duedates = duedates;
        this.detail = detail;
        this.posterames = posterames;
        this.dp = image;
        this.direction=direction;
        this.a=a;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.assignment_view,parent,false);
        context=parent.getContext();
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        String nametxt=name[position];
        holder.taskname.setText(nametxt);

        String locationtxt=location[position];
        holder.location.setText(locationtxt);


        String dptxt=dp[position];
        Glide.with(context).load(Constants.ImageUrl+dptxt).into(holder.dp);

        Log.d("statusss",tasksttus[position]);
        holder.assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Details.class);
                intent.putExtra("taskid",taskIds[position]);
                intent.putExtra("taskstatus",tasksttus[position]);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String[] da=duedates[position].split("T");
                String finaldate;
                String dat=da[0].replaceAll("-","/");
                try {
                    Date date = format.parse(dat);

                    String[] datw=date.toString().split(" ");
                    finaldate=datw[0]+","+datw[1]+" "+datw[2];
                    Log.d("datedarbar",finaldate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    finaldate=duedates[position];
                }
                intent.putExtra("duedates",finaldate);
                intent.putExtra("detail",detail[position]);
                intent.putExtra("postername",posterames[position]);
                intent.putExtra("price",price[position]);
                intent.putExtra("location",location[position]);
                intent.putExtra("taskname",name[position]);
                intent.putExtra("userimage",dptxt);
                String[] daa=datetimes[position].split("T");
                String finaldatee;
                String datt=daa[0].replaceAll("-","/");
                try {
                    Date date = format.parse(datt);

                    String[] datw=date.toString().split(" ");
                    finaldatee=datw[0]+","+datw[1]+" "+datw[2];
                    Log.d("datedarbar",finaldate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    finaldatee=datetimes[position];
                }
                intent.putExtra("datetime",finaldatee);
                intent.putExtra("direction",direction[position]);


                intent.putExtra("a",a);

                intent.putExtra("next",a);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        CardView assignment;
        CircleImageView dp;
        TextView taskname, location;


        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            dp=itemView.findViewById(R.id.taskdpid);
            taskname=itemView.findViewById(R.id.tasknameid);
            location=itemView.findViewById(R.id.tasklocationid);
            assignment=itemView.findViewById(R.id.assignmentid);


        }
    }
}
