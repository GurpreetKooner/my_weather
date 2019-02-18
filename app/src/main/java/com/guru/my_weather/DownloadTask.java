package com.guru.my_weather;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Vamshi on 5/14/2017.
 */

public class DownloadTask extends AsyncTask<String,Void,String> {

    String result;
    @Override
    protected String doInBackground(String... urls) {
        result = "";
        URL link;
        HttpURLConnection myconnection = null;

        try {
            link = new URL(urls[0]);
            myconnection = (HttpURLConnection)link.openConnection();
            InputStream in = myconnection.getInputStream();
            InputStreamReader myStreamReader = new InputStreamReader(in);
            int data = myStreamReader.read();
            while(data!= -1){
                char current = (char)data;
                result+= current;
                data = myStreamReader.read();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jasonObject = new JSONObject(result);
            JSONObject weatherData = new JSONObject(jasonObject.getString("main"));
            Double temperature = Double.parseDouble(weatherData.getString("temp"));
            int temperatureInteger = (int) (temperature - 273.15);

//            String temperature = weatherData.getString("temp");
            String placeName = jasonObject.getString("name");

            MainActivity.placeTextView.setText(placeName);
            MainActivity.temperatureTextView.setText(String.valueOf(temperatureInteger));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}