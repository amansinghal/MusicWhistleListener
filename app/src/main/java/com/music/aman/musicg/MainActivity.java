package com.music.aman.musicg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements View.OnClickListener {

    public static String URI_KEY = "uri_key", ALARM_TONE_KEY = "alarm_tone_key", FLASH_KEY = "flash_key",IS_USER_LOGGED_IN_KEY="IS_USER_LOGGED_IN_KEY";
    ImageView iv_back;
    TextView tv_whistle, tv_advertisement, tv_select_music_ringtone, tv_alarm, tv_ringtone, tv_music, tv_record;
    Intent runnerServiceIntent;
    String currentRingtone;
    SharedPreferences sharedPreferences;
    ToggleButton toggleButton;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(URI_KEY, MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        initUI();
        Utils.overrideFonts(this, this.getWindow().getDecorView().findViewById(android.R.id.content));
        runnerServiceIntent = new Intent(this, RunnerService.class);
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
        toggleButton = (ToggleButton) findViewById(R.id.toggleBtn);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferences.edit().putBoolean(FLASH_KEY, b).commit();
            }
        });
    }

    private void getAlarmUri() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone");
        currentRingtone = sharedPreferences.getString(MainActivity.ALARM_TONE_KEY, "");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtone.isEmpty() ? null : Uri.parse(currentRingtone));
        this.startActivityForResult(intent, 5);
    }

    private void getMusicUri() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select music"), 6);
    }

    private void getRingtoneUri() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
        currentRingtone = sharedPreferences.getString(MainActivity.ALARM_TONE_KEY, "");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtone.isEmpty() ? null : Uri.parse(currentRingtone));
        this.startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 5) {
            final Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                //RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, uri);
                sharedPreferences.edit().putString(ALARM_TONE_KEY, uri.toString()).commit();
            } else {
                sharedPreferences.edit().putString(ALARM_TONE_KEY, "").commit();
            }

        }
        if (resultCode == RESULT_OK && requestCode == 6) {
            final Uri uri = data.getData();
            if (uri != null) {
                //RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, uri);
                sharedPreferences.edit().putString(ALARM_TONE_KEY, uri.toString()).commit();
            } else {
                sharedPreferences.edit().putString(ALARM_TONE_KEY, "").commit();
            }
        }
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
                //futureAlert(view);
                break;
            case R.id.music_tone:
                checkAuthorization();
                //getMusicUri();
                break;
            case R.id.ring_tone:
                getRingtoneUri();
                break;
            case R.id.record_tone:
                recordAudio();
                break;
            case R.id.alarm_tone:
                getAlarmUri();
                break;
        }
    }

    private void checkAuthorization(){
        if (sharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY,false)){

        }else{
            startActivity(AuthorizationActivity.getIntent(this));
        }
    }

    private void recordAudio() {
        stopService(runnerServiceIntent);
        Utils.isMyServiceRunning(RunnerService.class, this);
        startActivity(RecorderActivity.getIntent(this));
    }

    @Override
    protected void onResume() {
        if (!Utils.isMyServiceRunning(RunnerService.class, this))
            startService(runnerServiceIntent);
        super.onResume();
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
