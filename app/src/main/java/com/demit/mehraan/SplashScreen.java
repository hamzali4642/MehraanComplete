package com.demit.mehraan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    Animation right, left,fade,fade2;
    ImageView logo, credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fade= AnimationUtils.loadAnimation(this,R.anim.fade_scale_animation);
        fade2= AnimationUtils.loadAnimation(this,R.anim.bottom_up);
        right= AnimationUtils.loadAnimation(this,R.anim.right_to_left);
        left= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        logo=findViewById(R.id.logoid);
        credit=findViewById(R.id.creditid);
        logo.setAnimation(fade);
//        logo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.rotation));
        credit.setAnimation(fade2);


        new CountDownTimer(2000, 1000) { //40000 milli seconds is total time, 1000 milli seconds is time interval

            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {

                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                String token=sharedPreferences.getString("token","");
                if(token.equals("")){
                    Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(intent);
                }
                finish();
            }
        }.start();

    }
}
