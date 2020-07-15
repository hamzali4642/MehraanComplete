package com.demit.mehraan;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private  String[] name, location, price, comments, offers;
    private  int[]  dp;


    public AssignmentAdapter(String[] name, String[] location, String[] price, String[] comments, String[] offers, int[] dp) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.comments = comments;
        this.offers = offers;
        this.dp = dp;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.task_list_view,parent,false);

        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
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

                Intent intent=new Intent(v.getContext(),Details.class);
                intent.putExtra("next",3);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        CircleImageView dp;
        TextView taskname, location, commentsoffers, price;
        Button open;

        public AssignmentViewHolder(@NonNull View itemView) {
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
