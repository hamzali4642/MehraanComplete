package com.demit.mehraan;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demit.mehraan.ContextClass.JWTget;
import com.demit.mehraan.Model.RequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.PeriodType;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.ACTIVITY_SERVICE;


public class More extends Fragment {
    TextView name, edit, skills, changepass,  contactus, tandc,logout,registration;
    CircleImageView dp;

    public  org.joda.time.Period period;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more, container, false);

        name=view.findViewById(R.id.accountnameid);
        edit=view.findViewById(R.id.editbtnid);
        skills=view.findViewById(R.id.skillsid);
        changepass=view.findViewById(R.id.changepassid);
        registration =view.findViewById(R.id.registration);
        contactus=view.findViewById(R.id.contactusid);
        tandc=view.findViewById(R.id.tandcid);
        logout=view.findViewById(R.id.logoutid);
        dp=view.findViewById(R.id.accountdpid);

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        reference = FirebaseDatabase.getInstance().getReference();


        String id;
        try {
            id = new JWTget(getContext()).jwtverifier(sharedpreferences.getString("token",""),"Id");

            reference.child("Users").child(id).child("date").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(!snapshot.exists()){
                        registration.setVisibility(View.VISIBLE);
                    }else{
                        String currentdate, adddate;
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date datee = new Date();
                        Log.d("Date", formatter.format(datee));

                        currentdate = formatter.format(datee);
                        adddate = snapshot.getValue().toString();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date cdate, adate;
                        try {
                            cdate = simpleDateFormat.parse(currentdate);
                            adate = simpleDateFormat.parse(adddate);
                            long cudate = cdate.getTime();
                            long addate = adate.getTime();
                            period = new org.joda.time.Period(addate, cudate, PeriodType.months());
                            Log.d("date", period.getMonths() + "");

                            Toast.makeText(getContext(), period.getMonths()+"", Toast.LENGTH_SHORT).show();
                            if(period.getMonths() >=1){
                                registration.setVisibility(View.VISIBLE);
                            }else {
                                registration.setVisibility(View.GONE);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }



        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog alertDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE);
                alertDialog.setTitleText("Do you want to Register?");
                alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        Integer id;
                        String name;
                        String token = sharedpreferences.getString("token","");
                        JWTget jwTget=new JWTget(getContext());
                        try {
                            id=Integer.parseInt( jwTget.jwtverifier(token,"Id"));
                             name =jwTget.jwtverifier(token,"unique_name");
                            RequestModel model =new RequestModel(name,id);
                            reference.child("Requests").child(id.toString()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Request Submitted", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(),"Failed to submit request",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                                            }
                });
                alertDialog.setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });


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
