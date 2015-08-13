package com.snowlarks.classbox.Network;

import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Extra {

    public static boolean comparePassword(EditText p, EditText cp){
        String password = p.getText().toString();
        String confirm_password = cp.getText().toString();
        password = password.trim();
        confirm_password = confirm_password.trim();

        return (password.equals(confirm_password)) && !password.isEmpty();

    }

    public static List<NameValuePair> parseJSON(JSONObject json) throws JSONException {

        List<NameValuePair> pairs = new ArrayList<>();

        Iterator<String> keys = json.keys();
        while(keys.hasNext()){
            String key = keys.next();
            String val = null;
            try{
                JSONObject value = json.getJSONObject(key);
                pairs.addAll(parseJSON(value));
            }catch(Exception e){
                val = json.getString(key);
            }

            if(val != null){
                pairs.add(new BasicNameValuePair(key,val));
            }
        }
        return pairs;
    }


}
