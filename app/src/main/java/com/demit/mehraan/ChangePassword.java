package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class ChangePassword extends Fragment {

    EditText newpassword, confirmnewpassword, currentpass;
    Button change;
    ImageView close, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);

        close=view.findViewById(R.id.closechangepassbtnid);
        back=view.findViewById(R.id.backchangepassbtnid);
        newpassword=view.findViewById(R.id.changepassworid);
        confirmnewpassword=view.findViewById(R.id.changepassworid2);
        change=view.findViewById(R.id.changebtnid);
        currentpass=view.findViewById(R.id.changepasswordcurrentid);

        if (getActivity() instanceof MainActivity){

            currentpass.setVisibility(View.GONE);

        }
        else if(getActivity() instanceof Details){

            currentpass.setVisibility(View.VISIBLE);
            close.setVisibility(View.GONE);

        }

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification()).commit();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent= new Intent(getContext(),Dashboard.class);
                startActivity(intent);





            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getActivity() instanceof MainActivity){
                getFragmentManager().beginTransaction().replace(R.id.detailfragid,new Mobile_Verification()).commit();
                }

                if (getActivity() instanceof Details){

                    Intent intent= new Intent(getContext(),Dashboard.class);
                    intent.putExtra("back",4);
                    startActivity(intent);


                }

            }
        });





        return view;
    }
}
