package com.example.instacalcid1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class singlepostactivity extends AppCompatActivity {

    String postid,userid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepostactivity);

        postid = getIntent().getStringExtra("postid");
        userid= getIntent().getStringExtra("userid");
        Toast.makeText(getApplicationContext(),userid,Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.singlepostactivity_framelayout,new notificationactivity_postfragment(postid,userid)).commit();
    }
}