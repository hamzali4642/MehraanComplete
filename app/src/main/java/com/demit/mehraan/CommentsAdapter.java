package com.demit.mehraan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private  String[] comment, time, name;
    private int[] dp;

    public CommentsAdapter(String[] comment, String[] time, String[] name, int[] dp) {
        this.comment = comment;
        this.time = time;
        this.name = name;
        this.dp = dp;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.comment_view,parent,false);

        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {


        String nametxt=name[position];
        holder.name.setText(nametxt);

        int dptx=dp[position];
        holder.commentdp.setImageResource(dptx);

        String cmnttxt=comment[position];
        holder.comment.setText(cmnttxt);

        String timetxt=time[position];
        holder.time.setText(timetxt);


    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{


        CircleImageView commentdp;
        TextView comment, time, name;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            commentdp=itemView.findViewById(R.id.personcmntdpid);
            comment=itemView.findViewById(R.id.textcommentid);
            name=itemView.findViewById(R.id.commentpersonnameid);
            time=itemView.findViewById(R.id.timecommentid);


        }
    }

}
