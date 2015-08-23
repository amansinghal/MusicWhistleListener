package com.music.aman.musicg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements View.OnClickListener {

    ImageView iv_back;
    TextView tv_whistle, tv_advertisement, tv_select_music_ringtone, tv_alarm, tv_ringtone, tv_music, tv_record;
    Intent runnerServiceIntent;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        Utils.overrideFonts(this, this.getWindow().getDecorView().findViewById(android.R.id.content));
        runnerServiceIntent = new Intent(this, RunnerService.class);
        if (!Utils.isMyServiceRunning(RunnerService.class, this))
            startService(runnerServiceIntent);
    }


    private void initUI() {
        iv_back = (ImageView) findViewById(R.id.back);
        iv_back.setOnClickListener(this);
        tv_advertisement = (TextView) findViewById(R.id.advertisment);
        tv_advertisement.setOnClickListener(this);
        tv_select_music_ringtone = (TextView) findViewById(R.id.select_music);
        //tv_select_music_ringtone.setOnClickListener(this);
        tv_whistle = (TextView) findViewById(R.id.whistle_listener);
        tv_whistle.setOnClickListener(this);
        tv_alarm = (TextView) findViewById(R.id.alarm_tone);
        tv_alarm.setOnClickListener(this);
        tv_ringtone = (TextView) findViewById(R.id.ring_tone);
        tv_ringtone.setOnClickListener(this);
        tv_record = (TextView) findViewById(R.id.record_tone);
        tv_record.setOnClickListener(this);
        tv_music = (TextView) findViewById(R.id.music_tone);
        tv_music.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.advertisment:
                futureAlert(view);
                break;
            case R.id.whistle_listener:
                if (Utils.isMyServiceRunning(RunnerService.class, MainActivity.this))
                    dialogForWhistle();
                else
                    dialogForWhistleOn();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.select_music:
                futureAlert(view);
                break;
            case R.id.music_tone:

                break;
            case R.id.ring_tone:

                break;
            case R.id.record_tone:
                recordAudio();
                break;
            case R.id.alarm_tone:

                break;
        }
    }

    private void recordAudio() {

    }

    private void dialogForWhistle() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Whistler");
        dialog.setMessage("The listener is already started,\n Do you want to off it?");
        dialog.setPositiveButton("Stop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (RunnerService.runnerIntent != null)
                    RunnerService.runnerIntent.stopSelf();
            }
        });
        dialog.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void dialogForWhistleOn() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Whistler");
        dialog.setMessage("The listener is not running,\n Do you want to start it?");
        dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startService(runnerServiceIntent);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    public void futureAlert(View view) {
        Toast.makeText(this, "For Paid user.", Toast.LENGTH_SHORT).show();
    }
}
