package com.demit.mehraan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Details extends AppCompatActivity {
    int next;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String userid = getIntent().getStringExtra("userid");
        String detail=getIntent().getStringExtra("detail");
        String taskstatus=getIntent().getStringExtra("taskstatus");
        String duedates=getIntent().getStringExtra("duedates");
        String taskid=getIntent().getStringExtra("taskid");
        String postername=getIntent().getStringExtra("postername");
        String price=getIntent().getStringExtra("price");
        String location=getIntent().getStringExtra("location");
        String userimage=getIntent().getStringExtra("userimage");
        String taskname=getIntent().getStringExtra("taskname");
        String taskdate=getIntent().getStringExtra("datetime");
        String direction=getIntent().getStringExtra("direction");
        this.next=getIntent().getIntExtra("next",0);
        this.a=getIntent().getIntExtra("a",0);

        if (next==1){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,postername,price,location,userimage,taskdate,userid)).commit();

        }

        else if(next==2){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new PostedTaskDetails(taskname,detail,taskstatus,duedates,taskid,postername,price,location,userimage,taskdate)).commit();

        }

        else if(next==3){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new MyAssignment(detail,taskstatus,duedates,taskid,postername,price,location,taskname,userimage,taskdate,direction)).commit();

        }

        else if(next==4){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new ChatLayout(getIntent().getStringExtra("id"),getIntent().getStringExtra("name"))).commit();

        }

        else if(next==5){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new Skills()).commit();

        }

        else if(next==6){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new ChangePassword("00")).commit();

        }

        else if(next==7){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new ContactUs()).commit();

        }

        else if(next==8){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new TermAndConditions()).commit();

        }

        else if(next==9){

            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new Profile()).commit();

        }else if(next==10){
            getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new More()).commit();
        }

       // getSupportFragmentManager().beginTransaction().replace(R.id.detailfragid,new TaskDetails(detail,taskstatus,duedates,taskid,postername,price)).commit();



    }
}
