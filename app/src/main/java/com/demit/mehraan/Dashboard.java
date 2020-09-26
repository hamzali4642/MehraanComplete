package com.demit.mehraan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.demit.mehraan.ContextClass.JWTget;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView navigation;
    int back;

    SweetAlertDialog alertDialog;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.back=getIntent().getIntExtra("back",0);
        navigation=(BottomNavigationView)findViewById(R.id.navigationid);
        navigation.setOnNavigationItemSelectedListener(navListener);



        if(back==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(0,0,0.0,0.0,0)).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,new EarnMoney(0,0,0.0,0.0,0)).commit();

        }





    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;

            switch(menuItem.getItemId()){

                case R.id.earn_money:
                    selectedFragment=new EarnMoney(0,0,0.0,0.0,0);
                    i=0;
                    break;
                case R.id.my_task:
                    selectedFragment=new MyTask();
                    i=1;
                    break;
                case R.id.post_task:
                    selectedFragment=new PostTask();
                    i=1;
                    break;
                case R.id.messages:
                    selectedFragment=new Messages();
                    i=1;
                    break;
                case R.id.more:
                    selectedFragment=new More();
                    i=1;
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,selectedFragment).commit();

            return true;
        }
    };

    @SuppressLint("ResourceType")
    @Override
    public void onBackPressed() {
        if(i==0){
            super.onBackPressed();
        }else{
            Fragment selectedFragment=null;
            navigation.getMenu().getItem(0).setChecked(true);
            selectedFragment=new EarnMoney(0,0,0.0,0.0,0);
            getSupportFragmentManager().beginTransaction().replace(R.id.dashfragid,selectedFragment).commit();
            i=0;
            navigation.setSelectedItemId(0);
        }

    }
}
