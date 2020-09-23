package com.demit.mehraan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class PinCode extends Fragment {
    TextView phonetext, codedigit1, codedigit2, codedigit3, codedigit4, pintime;
    Button resendPin;
    String number;
    ProgressDialog progressDialog;
    Boolean flag=true;
    String state;
    PinView pinView;
    SmsVerifyCatcher smsVerifyCatcher;
    String phnnumber="";
    int remtime=120000;
    public PinCode(String numb,String state) {
        this.number=numb;
        this.phnnumber=numb;
        this.state=state;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pin_code, container, false);
        resendPin=view.findViewById(R.id.resendpinid);
        pintime=view.findViewById(R.id.pin);
        pinView=view.findViewById(R.id.pinviewotp);
        phonetext=(TextView)view.findViewById(R.id.phonetextid);
        phonetext.setText(number);
        numbhejo();
        resendPin.setEnabled(false);
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setMessage("Sending Verification Code, please wait.");
        progressDialog.show();

        codedigit1=(TextView)view.findViewById(R.id.otpid1);
        codedigit2=(TextView)view.findViewById(R.id.otpid2);
        codedigit3=(TextView)view.findViewById(R.id.otpid3);
        codedigit4=(TextView)view.findViewById(R.id.otpid4);
//        codedigit1.addTextChangedListener(new GenericTextWatcher(codedigit1));
//        codedigit2.addTextChangedListener(new GenericTextWatcher(codedigit2));
//        codedigit3.addTextChangedListener(new GenericTextWatcher(codedigit3));
//        codedigit4.addTextChangedListener(new GenericTextWatcher(codedigit4));



        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String otp=pinView.getText().toString();
                if(otp.length()==4){
                    try {
                        sendotpnum(otp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        resendPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendPin.setEnabled(false);
                progressDialog.setMessage("Sending Verification Code, please wait.");
                progressDialog.show();
                number=phnnumber;
                numbhejo();
                remtime=120000;
                //  starttimer();

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // getFragmentManager().beginTransaction().replace(R.id.signinsignupfragid,new AskRole()).commit();
            }

        }, 120000);

        return view;
    }

    public void sendotpnum(String code) throws JSONException {
        //for debugin only

        // getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Signup(phnnumber)).commit();
//till now
        if (flag){
            this.flag=false;
            Log.d("behal",code);
            progressDialog.setMessage("Verifying OTP Code, please wait.");
            progressDialog.show();

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getActivity().getCurrentFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            final String username=phnnumber;
            final String otp=code;
            String URL1= "https://mehraan-oh3.conveyor.cloud/api/authentication/otpv";
            URL1=Constants.BaseUrl+"authentication/otpv";
            // URL1= "https://httpbin.org/post";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Phone", username);
            jsonBody.put("Code",otp);


            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL1, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("yewalaresponse","abc"+response.toString());
                    try {
                        String token= response.get("token").toString();
                        if(token.equals("OTPV")){
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),"OTP Verified",Toast.LENGTH_LONG).show();

                            if (state.equals("su"))
                                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new Signup(phnnumber)).commit();
                            if (state.equals("fg")){
                                getFragmentManager().beginTransaction().replace(R.id.signupfragid,new ChangePassword(phnnumber)).commit();
                            }
                            //take him to signup
                            // getFragmentManager().beginTransaction().replace(R.id.signinsignupfragid,new AskRole()).commit();

                        }
                        else if(token.equals("OTPN")){
                            Toast.makeText(getContext(),"OTP is incorrect",Toast.LENGTH_LONG).show();

                        }
                        else{
                            //show error message
                            Toast.makeText(getContext(),"Connection Error",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(),"Connection Error",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    // progressDialog.dismiss();


                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //TODO: handle failure
                }
            });




            Volley.newRequestQueue(getContext()).add(jsonRequest).setRetryPolicy((new DefaultRetryPolicy(0, -1, 0)));

        }}

    public String parseCode(String message) {
        Log.d("jaymenikla1",message);
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
    public void numbhejo()
    {
        String URL1= "https://httpbin.org/post";
        if(state.equals("su"))
    {        URL1="https://mehraan-oh3.conveyor.cloud/api/authentication/otp";
            URL1=Constants.BaseUrl+"authentication/otp";
    }    else if (state.equals("fg"))
        {    URL1="https://mehraan-oh3.conveyor.cloud/api/authentication/forgetpassword";
        URL1=Constants.BaseUrl+"authentication/forgetpassword";
        // URL1="https://entourer.conveyor.cloud/api/authentication/otp";
        // URL1= "https://httpbin.org/post";
        }
        StringRequest request1 = new StringRequest(Request.Method.POST,URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        starttimer();
                        resendPin.setEnabled(false);
                        // Showing response message coming from server.
                        Log.d("huahaiajpeli", ServerResponse);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        Toast.makeText(getContext(),"Connection Error",Toast.LENGTH_SHORT);
                        progressDialog.dismiss();
                        progressDialog.dismiss();
                        starttimer();
                        resendPin.setEnabled(true);
                        // Showing error message if something goes wrong.
                        Log.d("dataerrorjbl", volleyError.toString());
                        volleyError.printStackTrace();


                    }
                })
        {

            @Override
            public byte[] getBody() {
                char ch='"';
                String data=ch+phnnumber+ch;


                return data.getBytes();
            }
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }

        };
        request1.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));


        Volley.newRequestQueue(getContext()).add(request1);



    }



    public void starttimer(){
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {


                int minrem=remtime/60000;
                int secrem=((remtime)-(minrem*60000))/1000;
                if(secrem<10)
                    pintime.setText("0"+minrem+":"+"0"+secrem);
                else
                    pintime.setText("0"+minrem+":"+secrem);
                remtime=remtime-1000;
            }

            public void onFinish() {
                pintime.setText("00:00");
                resendPin.setEnabled(true);
            }

        }.start();
    }

//    public class GenericTextWatcher implements TextWatcher
//    {
//        private View view;
//        private GenericTextWatcher(View view)
//        {
//            this.view = view;
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            // TODO Auto-generated method stub
//            String text = editable.toString();
//            switch(view.getId())
//            {
//
//                case R.id.otpid1:
//                    if(text.length()==1)
//                        codedigit2.requestFocus();
//                    break;
//                case R.id.otpid2:
//                    if(text.length()==1)
//                        codedigit3.requestFocus();
//                    else if(text.length()==0)
//                        codedigit1.requestFocus();
//                    break;
//                case R.id.otpid3:
//                    if(text.length()==1)
//                        codedigit4.requestFocus();
//                    else if(text.length()==0)
//                        codedigit2.requestFocus();
//                    break;
//                case R.id.otpid4:
//                    if(text.length()==0) {
//                        codedigit3.requestFocus();
//
//                    }
//                    else{
//                        String code= codedigit1.getText().toString()+codedigit2.getText().toString()+codedigit3.getText().toString()+codedigit4.getText().toString();
//
//                        try {
//
//                            sendotpnum(code);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//            }
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//            // TODO Auto-generated method stub
//        }
//
//        @Override
//        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//            // TODO Auto-generated method stub
//        }
//    }

}
