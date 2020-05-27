package com.demit.mehraan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView navigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        navigation=(BottomNavigationView)findViewById(R.id.navigationid);
        navigation.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney()).commit();

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
