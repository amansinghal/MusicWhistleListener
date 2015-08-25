package com.music.aman.musicg;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
