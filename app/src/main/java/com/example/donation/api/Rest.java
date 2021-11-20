package com.example.donation.api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {
    private static HttpURLConnection httpCon = null;
    private static URL url;

    private static final String hostURL = "https://donateappuet.herokuapp.com/";
    private static final String LocalHostURL = "http://192.168.1.5:3000";

    public static void setup(String request) {
        try {
            url = new URL(hostURL + request);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setUseCaches(false);
            httpCon.setReadTimeout(15 * 1000); //15 seconds to timeout
            httpCon.setRequestProperty("Content-Type", "application/json" );
            httpCon.setRequestProperty("Accept", "application/json");
        } catch (Exception e) {
            Log.v("donate", "REST SETUP ERROR" + e.getMessage());
        }
    }

    //GET
    public static String get(String url) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = null;

        try {
            setup(url);
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);
            httpCon.connect();

            Log.v("donate", "GET REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            //read the output from the server
            reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            reader.close();
            Log.v("donate", "JSON GET REQUEST : " + stringBuilder.toString());

        } catch (Exception e) {
            Log.v("donate","GET REQUEST ERROR" + e.getMessage());
        }

        return stringBuilder.toString();
    }

    //POST
    public static String post(String url, String json) {
        OutputStreamWriter writer = null;
        StringBuilder stringBuilder = null;

        try {
            setup(url);
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.connect();

            Log.v("donate", "POST REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            // Read the output from server
            writer = new OutputStreamWriter(httpCon.getOutputStream());
            writer.write(json);
            writer.close();

            stringBuilder = new StringBuilder();
            int HttpResult = httpCon.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                Log.v("donate", "JSON POST RESPONSE : " + stringBuilder.toString());
            }
        } catch (Exception e) {
            Log.v("donate", "POST ERROR MESSAGE: " + e.getMessage());
        }

        return stringBuilder.toString();
    }

    //DELETE
    public static String delete(String url) {
        String response = null;

        try {
            setup(url);
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();

            Log.v("donate", "DELETE REQUEST is : " + httpCon.getRequestMethod() + " " + httpCon.getURL());

            response = httpCon.getResponseMessage();
            Log.v("donate", "JSON DELETE RESPONSE : " + response);

        } catch (Exception e) {
            Log.v("donate","DELETE REQUEST ERROR" + e.getMessage());
        }

        return response;
    }

    //PUT
    public static String put(String url, String json) {
        String result = "";

        return result;
    }
}