package com.demit.mehraan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ChangePassword extends Fragment {

    EditText newpassword, confirmnewpassword;
    Button change;
    ImageView close, back;
    String pass,num;
    Boolean check=false;
    EditText currentpass;
    public ChangePassword(String num){
        this.num=num;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);
        currentpass=view.findViewById(R.id.changepasswordcurrentid);
        close=view.findViewById(R.id.closechangepassbtnid);
        back=view.findViewById(R.id.backchangepassbtnid);
        newpassword=view.findViewById(R.id.changepassworid);
        confirmnewpassword=view.findViewById(R.id.changepassworid2);
        change=view.findViewById(R.id.changebtnid);

        if(num.equals("00")){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        num=sharedPreferences.getString("number","");
        }
   if (getActivity() instanceof MainActivity){

            check=false;
            currentpass.setVisibility(View.GONE);

        }
        else if(getActivity() instanceof Details){

            check=true;
            currentpass.setVisibility(View.GONE);
            close.setVisibility(View.GONE);

      }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pas,cpas="";
                pas=newpassword.getText().toString();
                cpas=confirmnewpassword.getText().toString();
                if(pas.length()<9 || cpas.equals("")){
                    Toast.makeText(getActivity(), "minimum password length is 9.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pas.equals(cpas)){
                    pass=pas;
                    pass=pas;
                    senddata();
                }else{
                    Toast.makeText(getActivity(), "Password didn't match", Toast.LENGTH_SHORT).show();
                }



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

                startActivity(new Intent(getActivity(),Details.class).putExtra("next",10));
                getActivity().finish();
//                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Mobile_Verification("fg")).commit();

            }
        });




        return view;
    }
    public void senddata(){
        try {

            SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
            loading.setCancelable(false);
            loading.setTitleText("Loading....");
            loading.show();

            String URL = "https://mehraan-oh3.conveyor.cloud/api/authentication/reset";
                URL=Constants.BaseUrl+"authentication/reset";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Phone",num );
            jsonBody.put("Password",pass );



            // jsonBody.put("Author", "BNK");
            final String requestBody = jsonBody.toString();

            //JSONArray songsArray = jsonBody.toJSONArray(jsonBody.names());
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //  Log.d("yewalaresponse",response.toString());

                    loading.dismiss();
                    //TODO: handle success
                    Toast.makeText(getContext(),"Password Successfully Changed",Toast.LENGTH_LONG).show();
                    if(check){

                        Intent intent=new Intent(getActivity(),BridgeActivity.class);
                        startActivity(intent);

                        return;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Signin()).commit();
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


