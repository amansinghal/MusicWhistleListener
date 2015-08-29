package com.music.aman.musicg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
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
        String alramUri=getSharedPreferences(MainActivity.URI_KEY,MODE_PRIVATE).getString(MainActivity.ALARM_TONE_KEY,"");
        if (!alramUri.isEmpty())
            notification = Uri.parse(alramUri);

        increaseTheVolume();
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        found_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.setStreamVolume(AudioManager.STREAM_RING, currentVolume, 0);
                r.stop();
                finish();
            }
        });
        r.play();
    }

    private void increaseTheVolume() {
        am =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume=am.getStreamVolume(AudioManager.STREAM_RING);
        am.setStreamVolume(AudioManager.STREAM_RING, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        am.setStreamVolume(AudioManager.STREAM_RING, currentVolume, 0);
        r.stop();
    }
}
