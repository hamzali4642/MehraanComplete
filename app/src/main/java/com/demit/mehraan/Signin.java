package com.demit.mehraan;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Signin extends Fragment {


    TextView signup,forgetPass;
    Button login;
    String phonenum;
    EditText phone, password;

    DatabaseReference reference ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin, container, false);

        login=(Button)view.findViewById(R.id.loginbtnid);
        phone=(EditText) view.findViewById(R.id.loginphoneid);
        password=(EditText) view.findViewById(R.id.loginpasswordid);
        signup=(TextView)view.findViewById(R.id.loginsignupid);
        forgetPass=(TextView)view.findViewById(R.id.loginforgetpassid);

        reference= FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification("su")).addToBackStack(null).commit();


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText().toString().length()<11 && password.getText().toString().length()<9){
                    Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }else{
                    login.setEnabled(false);
                    senddata();
                }




            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification("fg")).addToBackStack(null).commit();

            }
        });



        return view;
    }
    public void senddata(){


        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        phonenum=phone.getText().toString();
        phonenum="+92"+ phonenum.substring(1);
        String password=this.password.getText().toString();
        try {

            String URL = Constants.BaseUrl+"authentication/login";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("phone",phonenum );
            jsonBody.put("Password",password);


            // jsonBody.put("Author", "BNK");
//            final String requestBody = jsonBody.toString();

            //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loading.dismiss();
                    Log.d("yewalaresponse",response.toString());
                    try {
                        login.setEnabled(true);
                        getToken(response.get("token").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        login.setEnabled(true);
                        Log.d("ErrorException",e.toString());
                    }

                    //TODO: handle success
                    Toast.makeText(getContext(),"Logged In",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),"Invalid Login",Toast.LENGTH_LONG).show();

                    loading.dismiss();
                    login.setEnabled(true);
                    Log.d("ErrorException2",error.toString());
                    error.printStackTrace();
                }
            });

            jsonRequest.setRetryPolicy(
                    new DefaultRetryPolicy(
                            500000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    ));
            Volley.newRequestQueue(getContext()).add(jsonRequest);

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getToken(String token){


        try {
            jwtverifier(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void jwtverifier(String token) throws JSONException {
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

        String token1 = FirebaseInstanceId.getInstance().getToken();
        String name=object.getString("unique_name");;
        String id=object.getString("Id");

        Map<String,String> params=new HashMap<>();
        params.put("id",id);
        params.put("name",name);
        params.put("token",token1);

        reference.child("Users").child(id).child("id").setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child("Users").child(id).child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        reference.child("Users").child(id).child("token").setValue(token1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                reference.child("Users").child(id).child("block").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString("token" ,  token);
                                            editor.putString("number",phonenum);
                                            editor.apply();

                                            Intent intent= new Intent(getContext(),Dashboard.class);
                                            startActivity(intent);
                                            getActivity().finish();

                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

    }
}
