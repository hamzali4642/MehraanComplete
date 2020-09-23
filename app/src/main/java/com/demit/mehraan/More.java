package com.demit.mehraan;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.ACTIVITY_SERVICE;


public class More extends Fragment {
    TextView name, edit, skills, changepass,  contactus, tandc,logout;
    CircleImageView dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more, container, false);

        name=view.findViewById(R.id.accountnameid);
        edit=view.findViewById(R.id.editbtnid);
        skills=view.findViewById(R.id.skillsid);
        changepass=view.findViewById(R.id.changepassid);
        contactus=view.findViewById(R.id.contactusid);
        tandc=view.findViewById(R.id.tandcid);
        logout=view.findViewById(R.id.logoutid);
        dp=view.findViewById(R.id.accountdpid);


        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        String image= sharedpreferences.getString("image","");
        try {
            Glide.with(getContext()).load(Constants.ImageUrl+image).into(dp);
        }catch (Exception e){
            e.printStackTrace();
        }
        String token;
        token=sharedpreferences.getString("token","");
        try {
            name.setText(jwtverifier(token,"unique_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Details.class);
                intent.putExtra("next",9);
                startActivity(intent);

            }
        });

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Details.class);
                intent.putExtra("next",5);
                v.getContext().startActivity(intent);

            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Details.class);
                intent.putExtra("next",6);
                startActivity(intent);

            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Details.class);
                intent.putExtra("next",7);
                startActivity(intent);

            }
        });

        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Details.class);
                intent.putExtra("next",8);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog alert=new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE);
                alert.setTitleText("Do you really want to logout and exit?");
                alert.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        ((ActivityManager)getContext().getSystemService(ACTIVITY_SERVICE))
                                .clearApplicationUserData();
                    }
                });
                alert.setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                alert.setConfirmButtonBackgroundColor(getResources().getColor(R.color.colorPrimary));
                alert.setCancelButtonBackgroundColor(getResources().getColor(R.color.colorAccent));
                alert.show();
            }
        });
        return view;
    }

    public String jwtverifier(String token,String key) throws JSONException {
        String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        // Base64 base64Url = new Base64(true);

        String header = new String(Base64.decode(base64EncodedHeader,Base64.DEFAULT));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(Base64.decode(base64EncodedBody,Base64.DEFAULT));
        System.out.println("JWT Body : "+body);
        JSONObject object=new JSONObject(body);
        return  object.getString(key);
    }
}
