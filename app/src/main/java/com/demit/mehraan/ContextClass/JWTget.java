package com.demit.mehraan.ContextClass;

import android.content.Context;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class JWTget {

    Context context;

    public JWTget(Context context) {
        this.context = context;
    }

    public String jwtverifier(String token,String id) throws JSONException {
        String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        String header = new String(Base64.decode(base64EncodedHeader,Base64.DEFAULT));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(Base64.decode(base64EncodedBody,Base64.DEFAULT));
        System.out.println("JWT Body : "+body);
        JSONObject object=new JSONObject(body);
        String text;
        text= object.getString(id);

        return text;
    }
}
