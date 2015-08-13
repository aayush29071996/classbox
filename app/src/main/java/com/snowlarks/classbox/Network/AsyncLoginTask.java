package com.snowlarks.classbox.Network;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.snowlarks.classbox.LoginActivity;
import com.snowlarks.classbox.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class AsyncLoginTask extends AsyncTask<String,Void,List<NameValuePair>> {

    private final String LOG_TAG = AsyncLoginTask.class.getSimpleName();

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    private LoginActivity activity;

    public AsyncLoginTask(LoginActivity activity){
        this.activity = activity;
    }
    @Override
    protected List<NameValuePair> doInBackground(String... params) {

        final String BASE_URL = "http://192.168.1.4:8080";
        final String QUERY_ACTION = params[0];

        String reponse_str=null;
        try {
            Uri buildUri = Uri.parse(BASE_URL).buildUpon().appendPath(QUERY_ACTION).build();
            URL login_url = new URL( buildUri.toString());

            urlConnection = (HttpURLConnection) login_url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            List<NameValuePair> send_params = new ArrayList<>();
            send_params.add(new BasicNameValuePair("email",params[1]));
            send_params.add(new BasicNameValuePair("password",params[2]));

            //Perform Write Operations
            OutputStream os = urlConnection.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(getQuery(send_params));
            writer.flush();
            writer.close();
            os.close();

            //Now send the post request to server
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if(in == null){
                reponse_str = null;
            }

            reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine())!=null){
                buffer.append(line+"\n");
            }
            if(buffer.length()==0) reponse_str = null;
            reponse_str = buffer.toString();


        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
            reponse_str = null;
        }
        finally {
            if(urlConnection!=null) urlConnection.disconnect();

            if(reader !=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.toString());
                }
            }

            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.toString());
                }
            }
        }
        //TODO: Handle null response properly
        if (reponse_str==null) return null;
        try {
            return Extra.parseJSON(new JSONObject(reponse_str));
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<NameValuePair> list) {
        super.onPostExecute(list);

        //TODO: Handle null list properly
        if(list==null) return;

        boolean success = false;
        boolean signin = false;
        String response = "";
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equalsIgnoreCase("success")) success = Boolean.parseBoolean(list.get(i).getValue());
            if(list.get(i).getName().equalsIgnoreCase("category")){
                String category = list.get(i).getValue();
                if(category.equalsIgnoreCase("signin")) signin = true;
            }
            if(list.get(i).getName().equalsIgnoreCase("response")) response = list.get(i).getValue();
        }

        if(success && !signin){
            //TODO: Shift fragment to login
            Toast.makeText(activity,response,Toast.LENGTH_SHORT).show();
        }
        if(!success && !signin){
            //TODO: Clear password feilds and show message not as Toast
            Toast.makeText(activity,response,Toast.LENGTH_SHORT).show();
        }
        //Check if it was a success Signin
        if(success && signin){
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }
        if(!success && signin){
            //TODO: Clear password field and Print appropriate message
            Toast.makeText(activity,response,Toast.LENGTH_SHORT).show();
        }
        Log.d(LOG_TAG,list.toString());

    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}

