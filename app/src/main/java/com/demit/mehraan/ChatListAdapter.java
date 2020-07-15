package com.demit.mehraan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.UserInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    private Context context;
    private  String[] recentmsg, time;
    private List<UserInfo> name;
    private int[] dp;

    public ChatListAdapter( Context context, String[] recentmsg, String[] time, List<UserInfo> name, int[] dp) {
        this.context = context;
        this.recentmsg = recentmsg;
        this.time = time;
        this.name = name;
        this.dp = dp;
    }




    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.chats_list_view,parent,false);

        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {

        UserInfo nametxt=name.get(position);
        holder.name.setText(nametxt.getDisplayName());

        int dptx=dp[position];
        holder.chatdp.setImageResource(dptx);

        String recenttxt=recentmsg[position];
        holder.recentmsg.setText(recenttxt);

        String timetxt=time[position];
        holder.time.setText(timetxt);

        holder.chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(),Details.class);
                intent.putExtra("next",4);
                v.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout chat;
        CircleImageView chatdp;
        TextView recentmsg, time, name;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);

            chat=itemView.findViewById(R.id.chatid);
            chatdp=itemView.findViewById(R.id.personchatdpid);
            recentmsg=itemView.findViewById(R.id.chatrecentmessageid);
            name=itemView.findViewById(R.id.chatpersonnameid);
            time=itemView.findViewById(R.id.chattimeid);

        }
    }
}
