package com.demit.mehraan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOfferAdapter extends RecyclerView.Adapter<MyOfferAdapter.MyOfferViewholder> {
    private  String[] name,  review, time;
    private int[] rating,dp ;
    private boolean[] email, phone, payment, cnic;

    public MyOfferAdapter(String[] name, String[] review, String[] time, int[] rating, int[] dp, boolean[] email, boolean[] phone, boolean[] payment, boolean[] cnic) {
        this.name = name;
        this.review = review;
        this.time = time;
        this.rating = rating;
        this.dp = dp;
        this.email = email;
        this.phone = phone;
        this.payment = payment;
        this.cnic = cnic;
    }

    @NonNull
    @Override
    public MyOfferViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.my_offers_view,parent,false);

        return new MyOfferViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOfferViewholder holder, int position) {

        String nametxt=name[position];
        holder.name.setText(nametxt);

        int dptx=dp[position];
        holder.persondp.setImageResource(dptx);

        String reviewtxt=review[position];
        holder.reviews.setText(reviewtxt);

        String timetxt=time[position];
        holder.time.setText(timetxt);

        boolean emailtxt=email[position];
        if(emailtxt==false){

            holder.email.setAlpha((float) 0.5);

        }

        boolean phonetxt=phone[position];
        if(phonetxt==false){

            holder.phone.setAlpha((float) 0.5);

        }

        boolean paymenttxt=payment[position];
        if(paymenttxt==false){

            holder.payment.setAlpha((float) 0.5);

        }

        boolean cnictxt=cnic[position];
        if(emailtxt==false){

            holder.cnic.setAlpha((float) 0.5);

        }

        holder.assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class MyOfferViewholder extends RecyclerView.ViewHolder{

        RelativeLayout email, phone, payment, cnic;
        CircleImageView persondp;
        RatingBar ratingBar;
        TextView name, reviews, time;
        FloatingActionButton msg;
        Button assign;

        public MyOfferViewholder(@NonNull View itemView) {
            super(itemView);


            phone=itemView.findViewById(R.id.myphoneverificationid);
            payment=itemView.findViewById(R.id.mypaymentverificationid);
            cnic=itemView.findViewById(R.id.mycnicverificationid);
            persondp=itemView.findViewById(R.id.mypersonofferdp);
            ratingBar=itemView.findViewById(R.id.myraatingofferid);
            name=itemView.findViewById(R.id.mypersonnameid);
            reviews=itemView.findViewById(R.id.myreviewsofferid);
            time=itemView.findViewById(R.id.mytimeofferid);
            msg=itemView.findViewById(R.id.mymsgbtnid);
            assign=itemView.findViewById(R.id.myassignbtnid);


        }
    }
}
