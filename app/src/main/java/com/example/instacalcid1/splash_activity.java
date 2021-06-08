package com.example.instacalcid1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class splash_activity extends AppCompatActivity {

    Animation top_anim,bottom_anim ;
    //ImageView splash_image;
    TextView splash_text,splash_image;
    Looper myLooper ;
    long  backpressedtime ;

    final  static  int SPLASH_SCREEN = 3000;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //animation
        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        splash_image = this.findViewById(R.id.splash_activity_image);
        splash_image.setAnimation(top_anim);

        splash_text = this.findViewById(R.id.splash_activity_text);
        splash_text.setAnimation(bottom_anim);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(splash_activity.this,login_activity.class);
                Pair[] pairs = new Pair[2];
                pairs[0]=new Pair<View,String>(splash_image,"image");
                        pairs[1]=new Pair<View,String>(splash_text,"text");
                ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(splash_activity.this,pairs);

                startActivity(intent,options.toBundle());
                finish();
            }
        },SPLASH_SCREEN);
    }

    @Override
    public void onBackPressed() {
    }

}