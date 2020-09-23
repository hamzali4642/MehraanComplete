package com.demit.mehraan;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public class Profile extends Fragment implements BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate
,BSImagePicker.OnSelectImageCancelledListener {
    CircleImageView dp;
    TextView updatedp,username,password, phone, changepassword;
    EditText email;
    Button update;
    List<Uri> uriList=new ArrayList<>();
    String token;
    SharedPreferences sharedPreferences;
    ImageView backprofile;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        token=sharedPreferences.getString("token","");

        dp=view.findViewById(R.id.profileimageid);
        updatedp=view.findViewById(R.id.profiledpupdateid);
        username=view.findViewById(R.id.profilenaemid);
        password=view.findViewById(R.id.profilepassid);
        phone=view.findViewById(R.id.profilecontactid);
        changepassword=view.findViewById(R.id.profilechangepassid);
        email=view.findViewById(R.id.profileemailid);
        update=view.findViewById(R.id.updatebtnid);
        backprofile=view.findViewById(R.id.backprofileid);
        backprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(),Dashboard.class);
                intent.putExtra("back",4);
                v.getContext().startActivity(intent);

            }
        });


        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updatedp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BSImagePicker multiSelectionPicker = new BSImagePicker.Builder("com.yourdomain.yourpackage.fileprovider")
                        .isMultiSelect() //Set this if you want to use multi selection mode.
                        .setMinimumMultiSelectCount(1) //Default: 1.
                        .setMaximumMultiSelectCount(1) //Default: Integer.MAX_VALUE (i.e. User can select as many images as he/she wants)
                        .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                        .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                        .setMultiSelectDoneTextColor(R.color.colordone) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                        .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                        .disableOverSelectionMessage() //You can also decide not to show this over select message.
                        .build();
                multiSelectionPicker.show(getChildFragmentManager(), "picker");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().length()>0){
                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        try {
                            setUSerprofiledata();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        getUSerprofiledata();
        return view;
    }

    void getUSerprofiledata(){
        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,Constants.BaseUrl+"profile/userinfo",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                try {
                    Log.d("responseprofile",response.toString());
                    JSONObject jsonObject=response.getJSONObject("res");

                    String firstname,lastname,phonee,emaill,image;

                    firstname=jsonObject.getString("firstName");
                    lastname=jsonObject.getString("lastName");
                    phonee=jsonObject.getString("phone");
                    emaill=jsonObject.getString("email");
                    image=jsonObject.getString("profileImageUrl");

                    username.setText(firstname+" "+ lastname);
                    phone.setText(phonee);
                    email.setText(emaill);

                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("image",image);
                    editor.apply();
//                    if(dp != null)
                    Glide.with(getContext()).load(Constants.ImageUrl+image).into(dp);
                    Log.d("ImageUrllllll",Constants.ImageUrl+image);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    void setUSerprofiledata() throws JSONException {
        SweetAlertDialog loading=new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        loading.setCancelable(false);
        loading.setTitleText("Loading....");
        loading.show();

        JSONObject object=new JSONObject();
        object.put("Email",email.getText().toString());

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,Constants.BaseUrl+"profile/updateprofile",object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();

                getActivity().onBackPressed();
                Log.d("Response:",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                if(getActivity()!=null)
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
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

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
//        this.dp=ivImage;
        Glide.with(getContext()).load(imageUri).into(dp);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        this.uriList=uriList;
        try {
            requestUploadSurvey();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("KamalRaja",this.uriList.toString());
    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {

    }




    public interface WebServicesAPI {
        //  @FormUrlEncoded
        // @POST("register")
        // @FormUrlEncoded
        //@PartMap Map<String,RequestBody> params


        @Multipart
        //@Headers("Authentication: ")
        @POST("addprofileimage")
//     @POST("post")
            //  Call<Object> uploadSurvey(@Part("data") MyRequest request, @Part MultipartBody.Part propertyImagePart1);
        Call<Object> uploadSurvey(@Header("Authorization") String token, @Part MultipartBody.Part propertyImagePart1);



    }


    private void requestUploadSurvey () throws JSONException {
        File propertyImageFile = new File(getPath(getContext(),uriList.get(0)));
        RequestBody propertyImage = RequestBody.create(MediaType.parse("image/*"),
                propertyImageFile);
        MultipartBody.Part propertyImagePart1 = MultipartBody.Part.createFormData("DishImage",
                propertyImageFile.getName(),
                propertyImage);


        final WebServicesAPI webServicesAPI = ApiService2
                .getRetrofit()
                .create(WebServicesAPI.class);
        Call<Object> surveyResponse = null;
        Log.d("tag1","asa");
        surveyResponse = webServicesAPI.uploadSurvey("Bearer "+token,propertyImagePart1);
        surveyResponse.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                Log.d("TAG", "response 33: "+new Gson().toJson(response.body())+response.body() );

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.toString() );
                // Log error here since request failed
            }
        });

    }
    public static class ApiService2 {
        //private static String BASE_URL = "https://wrapandgo.conveyor.cloud/api/auth/";
        private  static  String BASE_URL = Constants.BaseUrl+"profile/";
        //       private static String BASE_URL = "https://httpbin.org/";
        static Retrofit getRetrofit() {


            return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
