package com.demit.mehraan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    Animation right, left;
    ImageView go, viral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        right= AnimationUtils.loadAnimation(this,R.anim.right_to_left);
        left= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        go=findViewById(R.id.goid);
        viral=findViewById(R.id.viralid);
        go.setAnimation(left);
        viral.setAnimation(right);

        new CountDownTimer(2000, 1000) { //40000 milli seconds is total time, 1000 milli seconds is time interval

            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {

                Intent intent=new Intent(SplashScreen.this, Signin.class);
                startActivity(intent);

            }
        }.start();

    }
}
