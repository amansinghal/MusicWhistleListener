package com.music.aman.musicg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;


public class Activity_Splash extends Activity {

    TextView tv_logo_text;
    ImageView iv_music1, iv_music2, iv_logo;
    ProgressBar progressBar;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
        //runAnimation(iv_music2);
        iv_music1.startAnimation(Utils.getRotationAnimation());
        iv_music2.startAnimation(Utils.getRotationReverseAnimation());
        //iv_logo.startAnimation(Utils.getRotationAnimation());
        progressBar.setMax(100);

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(i++);
                if (i==100){
                    startActivity(MainActivity.getIntent(Activity_Splash.this));
                    finish();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask,10,50);
        facebookHashKey();
    }

    private void facebookHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.music.aman.musicg", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashCode  = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                System.out.println("Print the hashKey for Facebook :"+hashCode);
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void runAnimation(View view){

        ValueAnimator vAnimator = ObjectAnimator.ofFloat(view, "translationY", 100f);
        vAnimator.setDuration(1000);
        //vAnimator.setRepeatMode(ValueAnimator.);
        //vAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ValueAnimator vAnimator2 = ObjectAnimator.ofFloat(view, "translationX", -100f);
        vAnimator2.setDuration(1000);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(vAnimator,vAnimator2);
        set.setStartDelay(0);
        set.start();

    }

    private void initUI() {
        tv_logo_text = (TextView) findViewById(R.id.activity_splash_logo_text);
        tv_logo_text.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-BoldItalic.ttf"));
        iv_music1 = (ImageView) findViewById(R.id.activity_splash_music1);
        iv_music2 = (ImageView) findViewById(R.id.activity_splash_music2);
        iv_logo = (ImageView) findViewById(R.id.activity_splash_logo);
        progressBar = (ProgressBar) findViewById(R.id.activity_splash_pb);
    }

}
