package com.music.aman.musicg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by AmaN on 8/22/2015.
 */
public class Activity_Dialog_Found extends Activity {
    Button found_btn;
    Ringtone r;
    AudioManager am;
    int currentVolume;
    Thread flashThread;
    boolean ShouldIGlow = true;
    private Camera cam;
    MediaPlayer mediaPlayer;
    String alramUri;
    public static boolean makeServiceDetectable = true;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, Activity_Dialog_Found.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_found);
        found_btn = (Button) findViewById(R.id.activity_dialog_found_btn);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
       alramUri= getSharedPreferences(MainActivity.URI_KEY, MODE_PRIVATE).getString(MainActivity.ALARM_TONE_KEY, "");
        if (!alramUri.isEmpty())
            notification = Uri.parse(alramUri);

        increaseTheVolume();

        r = RingtoneManager.getRingtone(getApplicationContext(), notification);

        found_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.setStreamVolume(AudioManager.STREAM_RING, currentVolume, 0);
                if (alramUri.contains("file:///")) {
                    mediaPlayer.stop();
                } else {
                    r.stop();
                }
                finish();
            }
        });

        if (alramUri.contains("file:///")) {
           mediaPlayer  = MediaPlayer.create(this,notification);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } else {
            r.play();
        }
        flashThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        NotifyWithFlash();
                    }
                }
        );
        if (getSharedPreferences(MainActivity.URI_KEY, MODE_PRIVATE).getBoolean(MainActivity.FLASH_KEY, true))
            flashThread.start();
    }

    private void increaseTheVolume() {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume = am.getStreamVolume(AudioManager.STREAM_RING);
        am.setStreamVolume(AudioManager.STREAM_RING, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        am.setStreamVolume(AudioManager.STREAM_RING, currentVolume, 0);
        if (alramUri.contains("file:///")) {
            mediaPlayer.stop();
        } else {
            r.stop();
        }
        ShouldIGlow = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               makeServiceDetectable = true;
            }
        },300);
    }

    public void NotifyWithFlash() {
        while (ShouldIGlow) {
            try{
            flashON();}catch (Exception e){

            }
            try {
                Thread.sleep(1000);
                try {
                    flashOFF();
                }catch (Exception e){
                    e.printStackTrace();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void flashON() {
        cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();
    }

    public void flashOFF() {
        cam.stopPreview();
        cam.release();
    }
}
