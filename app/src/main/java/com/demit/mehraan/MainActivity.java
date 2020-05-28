package com.demit.mehraan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification()).commit();

        //alaa ka asnddfvb



    }

    public void Fun(){

        //Babar Azam

    }
}
