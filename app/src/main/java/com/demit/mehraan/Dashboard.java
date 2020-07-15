package com.demit.mehraan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView navigation;
    int back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.back=getIntent().getIntExtra("back",0);
        navigation=(BottomNavigationView)findViewById(R.id.navigationid);
        navigation.setOnNavigationItemSelectedListener(navListener);

        if(back==1){

            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney()).commit();

        }

        if(back==2){

            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new MyTask()).commit();

        }

        if(back==3){

            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new Messages()).commit();

        }

        if(back==4){

            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new More()).commit();

        }

        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney()).commit();

        }





    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;

            switch(menuItem.getItemId()){

                case R.id.earn_money:
                    selectedFragment=new EarnMoney();
                    break;
                case R.id.my_task:
                    selectedFragment=new MyTask();
                    break;
                case R.id.post_task:
                    selectedFragment=new PostTask();
                    break;
                case R.id.messages:
                    selectedFragment=new Messages();
                    break;
                case R.id.more:
                    selectedFragment=new More();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,selectedFragment).commit();

            return true;
        }
    };


}
