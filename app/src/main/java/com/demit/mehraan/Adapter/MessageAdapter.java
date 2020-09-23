package com.demit.mehraan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.demit.mehraan.ChatLayout;
import com.demit.mehraan.ChatListAdapter;
import com.demit.mehraan.Dashboard;
import com.demit.mehraan.Details;
import com.demit.mehraan.Model.MessageModel;
import com.demit.mehraan.R;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH> {

    List<MessageModel> list;

    Context context;
    public MessageAdapter(List<MessageModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.chats_list_view,parent,false);

        context=parent.getContext();
        return new MessageAdapter.MessageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageVH holder, int position) {


//        if(list.get(position).getName() !=null)

        holder.name.setText(list.get(position).getName());
        holder.lastmessage.setText(list.get(position).getLastmsg());
        holder.date.setText(list.get(position).getLastmsgtime());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Details.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("next",4);
                context.startActivity(intent);
//                Fragment selectedFragment=new ChatLayout(list.get(position).getId(),list.get(position).getName());
//                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,selectedFragment).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MessageVH extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        ImageView dp;
        TextView lastmessage,date,name;
        public MessageVH(@NonNull View itemView) {
            super(itemView);


            layout=itemView.findViewById(R.id.chatid);
            dp=itemView.findViewById(R.id.personchatdpid);
            name=itemView.findViewById(R.id.chatpersonnameid);
            lastmessage=itemView.findViewById(R.id.chatrecentmessageid);
            date=itemView.findViewById(R.id.chattimeid);

        }
    }
}
