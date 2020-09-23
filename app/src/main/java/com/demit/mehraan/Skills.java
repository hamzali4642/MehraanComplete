package com.demit.mehraan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Skills extends Fragment {

    ImageView backskills;
    RecyclerView skilllist;
    Button addskill;
    AutoCompleteTextView autoCompleteTextView;

    String token;
    SharedPreferences sharedPreferences;
    ArrayList<String> nameskill=new ArrayList<>();
    ArrayList<String> nameskilladded=new ArrayList<>();
    ArrayList<Integer> id=new ArrayList<>();
    ArrayList<Integer> idadded=new ArrayList<>();
    ArrayList<Integer> newidadded=new ArrayList<>();
    SkillsAdapter skillsAdapter;
    Button save;
    Integer counter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_skills, container, false);
        backskills=view.findViewById(R.id.backskillsid);
        autoCompleteTextView=view.findViewById(R.id.skillslistid);
        skilllist=view.findViewById(R.id.skilllistid);
        save=view.findViewById(R.id.saveskillbtn);
        addskill=view.findViewById(R.id.addskillbtnid);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        token=sharedPreferences.getString("token","");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,nameskill);
        autoCompleteTextView.setAdapter(arrayAdapter);

        skilllist.setLayoutManager(new LinearLayoutManager(getActivity()));
        skillsAdapter=new SkillsAdapter(nameskilladded,idadded);
        skilllist.setAdapter(skillsAdapter);

        addskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=autoCompleteTextView.getText().toString();
                int index=checkname(text);
                if(index != -1){
                    if(checkavailible(id.get(index)))
                    {nameskilladded.add(nameskill.get(index));
                    idadded.add(id.get(index));
                    newidadded.add(id.get(index));
                    skillsAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                    }
                }else{
                    Toast.makeText(getActivity(), "Invalid Skill", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),Dashboard.class);
                intent.putExtra("back",4);
                startActivity(intent);

            }
        });
        getallskills();
        getyourskills();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newidadded.size()>0){
                    setyourskills();
                }else{
                    Toast.makeText(getActivity(), "nothing to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    void getallskills(){

        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,Constants.BaseUrl+"basic/Getallskills",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    loading.dismiss();
                    JSONArray array=response.getJSONArray("res");
                    for(int i =0;i<array.length();i++){

                        nameskill.add(array.getJSONObject(i).getString("skillName"));
                        id.add(array.getJSONObject(i).getInt("skillId"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.d("Error",error.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }

    void setyourskills()  {

        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

            JSONArray array=new JSONArray();
            for(int i=0;i<newidadded.size();i++){
                JSONObject obj=new JSONObject();

                try {
                    obj.put("SkillId",newidadded.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(obj);
            }

            Log.d("setskillid",array.toString());


        JsonArrayRequest jsonObjectRequest=new JsonArrayRequest(Request.Method.POST,Constants.BaseUrl+"profile/AddUserSkills",array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                newidadded.clear();
                Toast.makeText(getActivity(), newidadded.size()+"", Toast.LENGTH_SHORT).show();
//                Log.d("Response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.d("Error",error.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }
    void getyourskills() {

        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,Constants.BaseUrl+"profile/Userskills",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    loading.dismiss();
                    Log.d("Response",response.toString());
                    JSONArray array=response.getJSONArray("res");
                    for(int i =0;i<array.length();i++){

                        nameskilladded.add(array.getJSONObject(i).getString("skillName"));
                        idadded.add(array.getJSONObject(i).getInt("skillId"));


                        skillsAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Log.d("Error",error.toString());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }
    public int checkname(String text){
        for(int i=0;i<nameskill.size();i++){
            if(text.equals(nameskill.get(i))){
                return i;
            }
        }
        return -1;
    }

    public  boolean checkavailible(Integer id){

        for(int i = 0 ;i<idadded.size();i++){
            if(idadded.get(i) == id){
                return false;
            }
        }
        return true;
    }
}
