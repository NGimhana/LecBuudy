package com.example.gimhana.lecbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NoteView extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private ImageView mImageView;
    private TextView description;
    private Button convertButton;

    //private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private String finalText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        mImageView = (ImageView) findViewById(R.id.image);
        convertButton = (Button) findViewById(R.id.convertToText);

        //mToolbarView = findViewById(R.id.toolbar);
        description = (TextView) findViewById(R.id.body);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        mImageView.setImageResource(R.mipmap.ic_launcher);
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.loadingViewer);

        Intent i = getIntent();
        Bundle bun = i.getExtras();

        String URL = bun.getString(NoteListAdapter.IMAGEURL);


        PicasoClient.downloadImage(getApplicationContext(), URL, mImageView);

        description.setText(URL);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setVisibility(View.VISIBLE);
                HandWritingRecognizer p = new HandWritingRecognizer(NoteView.this,mProgress);
                AsyncTask<Void, Void, JSONObject> result = p.execute();
                try {

                    JSONObject jsonObject = result.get();
                    JSONArray arr= new JSONArray(jsonObject);


                    ResponseReader rr = new ResponseReader( ResponseReader.convertStringToJSON(jsonObject.toString()));

                    System.out.println(rr.getFinalText());



                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }



}




