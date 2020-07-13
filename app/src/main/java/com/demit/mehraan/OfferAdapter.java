package com.demit.mehraan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private  String[] name,  review, time;
    private int[] rating,dp ;
    private boolean[] email, phone, payment, cnic;

    public OfferAdapter(String[] name, int[] dp, String[] time, int[] rating, String[] review, boolean[] email, boolean[] phone, boolean[] payment, boolean[] cnic) {
        this.name = name;
        this.time = time;
        this.dp = dp;
        this.rating = rating;
        this.review = review;
        this.email = email;
        this.phone = phone;
        this.payment = payment;
        this.cnic = cnic;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.offer_view,parent,false);

        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {


        String nametxt=name[position];
        holder.name.setText(nametxt);

        int dptx=dp[position];
        holder.persondp.setImageResource(dptx);

        String reviewtxt=review[position];
        holder.reviews.setText(reviewtxt);

        String timetxt=time[position];
        holder.time.setText(timetxt);


        boolean phonetxt=phone[position];
        if(phonetxt==false){

            holder.phone.setAlpha((float) 0.5);

        }

        boolean paymenttxt=payment[position];
        if(paymenttxt==false){

            holder.payment.setAlpha((float) 0.5);

        }

        boolean cnictxt=cnic[position];
        if(cnictxt==false){

            holder.cnic.setAlpha((float) 0.5);

        }




    }

    @Override
    public int getItemCount()
    {
        return name.length;
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout email, phone, payment, cnic, nooffer;
        CircleImageView persondp;
        RatingBar ratingBar;
        TextView name, reviews, time;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);


            phone=itemView.findViewById(R.id.phoneverificationid);
            payment=itemView.findViewById(R.id.paymentverificationid);
            cnic=itemView.findViewById(R.id.cnicverificationid);
            persondp=itemView.findViewById(R.id.personofferdp);
            ratingBar=itemView.findViewById(R.id.raatingofferid);
            name=itemView.findViewById(R.id.personnameid);
            reviews=itemView.findViewById(R.id.reviewsofferid);
            time=itemView.findViewById(R.id.timeofferid);



        }
    }
}
