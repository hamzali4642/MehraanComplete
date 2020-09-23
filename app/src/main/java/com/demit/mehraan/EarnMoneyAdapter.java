package com.demit.mehraan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EarnMoneyAdapter extends RecyclerView.Adapter<EarnMoneyAdapter.EarnMoneyViewHolder> {

    private  String[] name, location, price, comments, offers,taskIds,tasksttus,datetimes,duedates,detail,posterames;
    private  String[]  dp;
public Context context;
int a,layoutcheck;




    public EarnMoneyAdapter(String[] name,
                            String[] location,
                            String[] price,
                            String[] comments,
                            String[] offers, String[] image, String[] taskIds, String[] tasksttus, String[] datetimes, String[] duedates, String[] detail, String[] posterames,Context context,int a ,int layoutcheck) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.layoutcheck=layoutcheck;
        this.comments = comments;
        this.offers = offers;
        this.dp =image;
        this.taskIds=taskIds;
        this.tasksttus=tasksttus;
        this.datetimes=datetimes;
        this.duedates=duedates;
        this.detail=detail;
        this.posterames=posterames;
        this.context=context;

        this.a=a;
    }


    @NonNull
    @Override
    public EarnMoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view;
        if(layoutcheck==2){
            view=inflater.inflate(R.layout.task_list_view,parent,false);
        }else{
            view=inflater.inflate(R.layout.assignment_view,parent,false);
        }
        return new EarnMoneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarnMoneyViewHolder holder, int position) {

        if(position %2 ==0){
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.left_to_right1));
        }else{
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_left1));
        }
        try{
            String nametxt=name[position];
        holder.taskname.setText(nametxt);

        String locationtxt=location[position];
        holder.location.setText(locationtxt);

        String commenttxt=comments[position];
        String offerstxt=offers[position];
        holder.commentsoffers.setText(commenttxt+"   Comments       "+ offerstxt+"   Offers");

                String dptxt=dp[position];
            try{
           Glide.with(context).load(Constants.ImageUrl+dptxt).into(holder.dp);}
            catch (Exception e){}
     //  holder.dp.setImageResource(dptxt);

        String pricetxt=price[position];
        holder.price.setText("Rs "+pricetxt);




        holder.open.setOnClickListener(new View.OnClickListener() {
            private Context context;
            @Override
            public void onClick(View v) {
                context=v.getContext();
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



                    intent.putExtra("a",a);

                intent.putExtra("next",a);
                   context.startActivity(intent);

            }
        });
        }
        catch (Exception e ){e.printStackTrace();}


    }

    @Override
    public int getItemCount() {
        return name.length;
    }



    public class EarnMoneyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView dp;
        TextView taskname, location, commentsoffers, price;
        Button open;

        public EarnMoneyViewHolder(@NonNull View itemView) {
            super(itemView);

            dp=itemView.findViewById(R.id.taskdpid);
            taskname=itemView.findViewById(R.id.tasknameid);
            location=itemView.findViewById(R.id.tasklocationid);
            commentsoffers=itemView.findViewById(R.id.taskcomentsoffersid);
            price=itemView.findViewById(R.id.taskpriceid);
            open=itemView.findViewById(R.id.opentaskid);



        }
    }
}
