package com.snowlarks.classbox.Helper;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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


/**
 * Created by Saswat on 12-08-2015.
 */
public class AsyncLoginTask extends AsyncTask<String,Void,Void> {

    private final String LOG_TAG = AsyncLoginTask.class.getSimpleName();

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    @Override
    protected Void doInBackground(String... params) {

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

            List<NameValuePair> send_params = new ArrayList<NameValuePair>();
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
            StringBuffer buffer = new StringBuffer();
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

            Log.d(LOG_TAG,reponse_str);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            reponse_str = null;
        }
        finally {
            if(urlConnection!=null) urlConnection.disconnect();

            if(reader !=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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

