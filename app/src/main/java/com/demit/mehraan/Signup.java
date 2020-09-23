package com.demit.mehraan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Signup extends Fragment {

    Spinner type;
    ImageView close, back;
    Button signup;
    EditText firstname, lastname, email, password,conpass;
    String fname,lname,emal,pass,cnfrmpass,typestr,num;


    public Signup(String num){
        this.num=num;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signup, container, false);

        type=(Spinner)view.findViewById(R.id.typespinnerid);
        String[] items = new String[]{"Earn Money","Post Task"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinneritems,items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

//aruba_butt_

        close=(ImageView)view.findViewById(R.id.closesignupbtnid);
        back=(ImageView)view.findViewById(R.id.backsignupbtnid);
        signup=(Button)view.findViewById(R.id.signupbtnid);
        firstname=(EditText) view.findViewById(R.id.firstnaemid);
        lastname=(EditText) view.findViewById(R.id.lastnameid);
        email=(EditText) view.findViewById(R.id.emailid);
        password=(EditText) view.findViewById(R.id.passwordid);
        conpass=view.findViewById(R.id.confirmpasswordid);



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

                Intent intent= new Intent(getContext(),Dashboard.class);
                startActivity(intent);
            }
        });

//

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname=firstname.getText().toString();
                lname=lastname.getText().toString();
                emal=email.getText().toString();
                pass=password.getText().toString();
                cnfrmpass=conpass.getText().toString();
                typestr=type.getSelectedItem().toString();
                if(pass.length()>8){
                    if (pass.equals(cnfrmpass)){
                        if(isValidEmail(emal)){

                            if (!fname.equals("")&&!lname.equals("")){
                                //   Toast.makeText(getContext(),"Mubarik Ho",Toast.LENGTH_LONG).show();
                                senddata();


                            }
                            else {
                                Toast.makeText(getContext(),"First Name and Last Name cannot be empty",Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            Toast.makeText(getContext(),"Invalid Email",Toast.LENGTH_LONG).show();
                        }

                    }
                    else{
                        Toast.makeText(getContext(),"Passwords do not match",Toast.LENGTH_LONG).show();
                    }

                }else
                {
                    Toast.makeText(getContext(),"Password length should be more than 8 characters",Toast.LENGTH_LONG).show();
                }


            }

        });




        return view;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public void senddata(){
        try {
            SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
            loading.setCancelable(false);
            loading.setTitleText("Loading....");
            loading.show();
            String URL = "https://mehraan-oh3.conveyor.cloud/api/authentication/signup";

            URL=Constants.BaseUrl+"authentication/signup";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("FirstName",fname );
            jsonBody.put("LastName",lname );
            jsonBody.put("Email",emal);
            jsonBody.put("Password",pass );
            jsonBody.put("Phone",num );
            jsonBody.put("Type", typestr );

            // jsonBody.put("Author", "BNK");
            final String requestBody = jsonBody.toString();

            //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    loading.dismiss();
                    Log.d("yewalaresponse",response.toString());

                    //TODO: handle success
                    Toast.makeText(getContext(),"Signed Up",Toast.LENGTH_LONG).show();

                    getFragmentManager().beginTransaction().replace(R.id.signupfragid, new Signin()).commit();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    loading.dismiss();
                    Log.d("yewalaresponse",error.toString());
                    //TODO: handle failure
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

}
