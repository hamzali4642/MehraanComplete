package com.demit.mehraan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;


public class Mobile_Verification extends Fragment {
    public Mobile_Verification(String state){
        this.state=state;
    }

    Button verifynumber;
    TextView heading;
    TextView cellno;
    SmsVerifyCatcher smsVerifyCatcher;
    String number,state;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mobile__verification, container, false);
        cellno=view.findViewById(R.id.phonenumberid);

        heading=view.findViewById(R.id.heading);
        if (state.equals("fg")){
            heading.setText(R.string.ForgetPassword);
        }
        if (state.equals("su"))
        {
            heading.setText(R.string.signup);
        }

        verifynumber=(Button)view.findViewById(R.id.verifynumberid);
        verifynumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=cellno.getText().toString();
                String numb="+92"+ number.substring(1);
                if(numb.length()==13){

                    getFragmentManager().beginTransaction().replace(R.id.signupfragid,new PinCode(numb,state)).addToBackStack(null).commit();

                }
                else{
                    Toast.makeText(getContext(),"Please Enter 11 Digits Number",Toast.LENGTH_LONG).show();
                }


            }
        });



        return view;
    }


}
