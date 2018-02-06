package com.example.gimhana.lecbuddy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by gimhana on 11/11/17.
 */

public class HandWritingRecognizer extends AsyncTask<Void, Void, JSONObject> {

    final static String subscriptionKey = "c350d11e7c2a4be78cf8e02a18b47a99";
    final static String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/recognizeText?handwriting=true";

    ProgressBar mProgress;
    Activity act;

    public HandWritingRecognizer(Activity x, ProgressBar y){
        mProgress = y;
        act = x;
    }


    @Override
    protected JSONObject doInBackground(Void... voids) {
        mProgress.setVisibility(View.VISIBLE);
        DefaultHttpClient textClient = new  DefaultHttpClient();
        DefaultHttpClient resultClient = new DefaultHttpClient();
        JSONObject jsonFinal =null;
        URI uri = null;
        try {
            uri = new URI(uriBase);

            HttpPost textRequest = new HttpPost(uri);
            // Request headers. Another valid content type is "application/octet-stream".
            textRequest.setHeader("Content-Type", "application/json");
            textRequest.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            String url = "{\"url\":\"https://firebasestorage.googleapis.com/v0/b/lecbuddy-v01.appspot.com/o/IMAG3772.jpg?alt=media&token=b0846e78-316e-4aac-ae64-6721c5064f52\"}";
            StringEntity requestEntity =new StringEntity(url);
            textRequest.setEntity(requestEntity);

            HttpResponse textResponse = textClient.execute(textRequest);

            // Check for success.
            if (textResponse.getStatusLine().getStatusCode() != 202)
            {
                // Format and display the JSON error message.
                HttpEntity entity = textResponse.getEntity();
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                Log.i("TAG","Error:\n");
                System.out.println(json.toString(2));
                //return null;
            }



            String operationLocation = null;
            // The 'Operation-Location' in the response contains the URI to retrieve the recognized text.
            Header[] responseHeaders = textResponse.getAllHeaders();
            for(Header header : responseHeaders) {
                if(header.getName().equals("Operation-Location"))
                {
                    // This string is the URI where you can get the text recognition operation result.
                    operationLocation = header.getValue();
                    break;
                }
            }

            Log.i("TAG","\nHandwritten text submitted. Waiting 10 seconds to retrieve the recognized text.\n");
            Thread.sleep(5000);

            // Execute the second REST API call and get the response.
            HttpGet resultRequest = new HttpGet(operationLocation);
            resultRequest.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            HttpResponse resultResponse = resultClient.execute(resultRequest);
            HttpEntity responseEntity = resultResponse.getEntity();



            if (responseEntity != null)
            {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(responseEntity);
                jsonFinal = new JSONObject(jsonString);
                Log.i("TAG","Text recognition result response: \n");
                Log.i("TAG",jsonFinal.toString(2));
            }

        } catch (URISyntaxException e) {
            Log.i("TAG","URI ERROR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonFinal;
    }

    @Override
    protected void onPreExecute() {
        mProgress.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        mProgress.setVisibility(View.INVISIBLE);
        super.onPostExecute(jsonObject);
    }
}
