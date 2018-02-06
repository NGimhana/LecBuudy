package com.example.gimhana.lecbuddy;

import android.content.Intent;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class openActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        ImageView logoView= (ImageView) findViewById(R.id.logo);
        logoView.setImageResource(R.mipmap.dietbuddy);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(openActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);



    }
}
