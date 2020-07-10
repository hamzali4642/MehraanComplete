package com.demit.mehraan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    public ChatListAdapter(String[] recentmsg, String[] time, String[] name, int[] dp) {
        this.recentmsg = recentmsg;
        this.time = time;
        this.name = name;
        this.dp = dp;
    }

    private  String[] recentmsg, time, name;
    private int[] dp;


    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.chats_list_view,parent,false);

        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {

        String nametxt=name[position];
        holder.name.setText(nametxt);

        int dptx=dp[position];
        holder.chatdp.setImageResource(dptx);

        String recenttxt=recentmsg[position];
        holder.recentmsg.setText(recenttxt);

        String timetxt=time[position];
        holder.time.setText(timetxt);

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder{
        CircleImageView chatdp;
        TextView recentmsg, time, name;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);

            chatdp=itemView.findViewById(R.id.personchatdpid);
            recentmsg=itemView.findViewById(R.id.chatrecentmessageid);
            name=itemView.findViewById(R.id.chatpersonnameid);
            time=itemView.findViewById(R.id.chattimeid);

        }
    }
}
