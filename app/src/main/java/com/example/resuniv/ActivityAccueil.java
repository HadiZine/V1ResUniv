package com.example.resuniv;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActivityAccueil extends AppCompatActivity {
    private FirebaseAuth MyAuth;
    ImageView logo;
    TextView mTxt;

    public static String txt="5000";
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        MyAuth = FirebaseAuth.getInstance();
        //Animation topanim, buttanim;

        //topanim = AnimationUtils.loadAnimation(this, R.anim.top_animate);
       // buttanim = AnimationUtils.loadAnimation(this, R.anim.button);


        logo = findViewById(R.id.imageView2);
        mTxt = findViewById(R.id.textView);
        //logo.animate().;
        //mTxt.animate().getStartDelay();
        //logo.setAnimation(topanim);
       //mTxt.setAnimation(buttanim);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.top_wave);
        logo.setAnimation(animation1);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(

                logo,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f)
        );

        objectAnimator.setDuration(500);

        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        objectAnimator.setRepeatCount(ValueAnimator.REVERSE);


        objectAnimator.start();
        animatTex("Resto Univ");












    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTxt.setText(charSequence.subSequence(0,index++));
            if(index<= charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public  void  animatTex(CharSequence cs){

        charSequence = cs;
        index =0;
        mTxt.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);

    }



}