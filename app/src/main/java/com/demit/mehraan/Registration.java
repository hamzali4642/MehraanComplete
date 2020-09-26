package com.demit.mehraan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demit.mehraan.ContextClass.JWTget;
import com.demit.mehraan.Model.RequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registration extends AppCompatActivity {

    Button confirm,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id;
                String name;
                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(Registration.this);
                String token = sharedpreferences.getString("token", "");
                JWTget jwTget = new JWTget(Registration.this);
                try {

                    id = Integer.parseInt(jwTget.jwtverifier(token, "Id"));
                    name = jwTget.jwtverifier(token, "unique_name");
                    RequestModel model = new RequestModel(name, id);
                    DatabaseReference reference = FirebaseDatabase .getInstance().getReference();
                    reference.child("Requests").child(id.toString()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registration.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                                SweetAlertDialog alertDialog = new SweetAlertDialog(Registration.this,SweetAlertDialog.SUCCESS_TYPE);
                                alertDialog.setCancelable(false);
                                alertDialog.setTitleText("Send your payment proof  via WhatsApp on +923244577540");
                                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                                alertDialog.show();

                            } else {
                                Toast.makeText(Registration.this, "Failed to submit request", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                } catch (Exception e) {

                }
            }
        });
    }
}