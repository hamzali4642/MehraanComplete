package com.demit.mehraan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class EarnMoneyAdapter extends RecyclerView.Adapter<EarnMoneyAdapter.EarnMoneyViewHolder> {

    private  String[] name, location, price, comments, offers;
    private  int[]  dp;


    public EarnMoneyAdapter(String[] name, String[] location, String[] price, String[] comments, String[] offers, int[] dp) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.comments = comments;
        this.offers = offers;
        this.dp = dp;
    }




    @NonNull
    @Override
    public EarnMoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.task_list_view,parent,false);

        return new EarnMoneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarnMoneyViewHolder holder, int position) {

        String nametxt=name[position];
        holder.taskname.setText(nametxt);

        String locationtxt=location[position];
        holder.location.setText(locationtxt);

        String commenttxt=comments[position];
        String offerstxt=offers[position];
        holder.commentsoffers.setText(commenttxt+"   Comments       "+ offerstxt+"   Offers");

        int dptxt=dp[position];
        holder.dp.setImageResource(dptxt);

        String pricetxt=price[position];
        holder.price.setText("Rs "+pricetxt);


        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



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
