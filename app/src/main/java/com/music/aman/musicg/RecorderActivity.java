package com.music.aman.musicg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lassana.recorder.AudioRecorder;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kipl217 on 9/2/2015.
 */
public class RecorderActivity extends Activity implements View.OnClickListener {

    public final static String PATH = Environment.getExternalStorageDirectory() + "/WhistleAndFind/";
    public final static String PREFIX = "WF_REC_";
    TextView tv_list_status, tv_record_status;
    ImageView iv_play_pause, iv_save;
    ListView lv_recordings;
    ArrayList<RecordModel> listRecords;
    boolean isRecording = false;
    File lastRecordedFile;
    AudioRecorder audioRecorder;

    public static Intent getIntent(Context context) {
        return new Intent(context, RecorderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        initUI();
        getRecordings();
    }

    private void getRecordings() {
        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Audio.Media.DISPLAY_NAME + " = ?", new String[]{PREFIX}, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
    }

    private void initUI() {
        tv_list_status = (TextView) findViewById(R.id.activity_record_tv_list_status);
        tv_record_status = (TextView) findViewById(R.id.activity_record_tv_record_status);
        iv_play_pause = (ImageView) findViewById(R.id.activity_record_iv_play_pause);
        iv_play_pause.setOnClickListener(this);
        iv_save = (ImageView) findViewById(R.id.activity_record_iv_save);
        iv_save.setOnClickListener(this);
        lv_recordings = (ListView) findViewById(R.id.activity_record_lv);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_record_iv_play_pause) {
            if (!isRecording) {
                lastRecordedFile = getPath();
                if (lastRecordedFile != null)
                    startRecording(lastRecordedFile);
                else
                    Toast.makeText(RecorderActivity.this,"Error while creating File Path",Toast.LENGTH_LONG).show();
            } else {
                stopRecording();
            }
        }
        if (v.getId() == R.id.activity_record_iv_save) {

        }
    }

    private void startRecording(File file) {
             final Context context = this;
            audioRecorder = AudioRecorder.build(context,PREFIX + System.currentTimeMillis());

        audioRecorder.start(new AudioRecorder.OnStartListener() {
            @Override
            public void onStarted() {
                tv_record_status.setText("Recording...");
            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
                tv_record_status.setText(e.getMessage());
            }
        });
    }

    private void stopRecording() {
        audioRecorder.pause(new AudioRecorder.OnPauseListener() {
            @Override
            public void onPaused(String activeRecordFileName) {
                tv_record_status.setText("Ready to record.");
            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
                tv_record_status.setText(e.getMessage());
            }
        });
    }

    private File getPath() {
        File file = new File(PATH);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        file = new File(PATH + PREFIX + System.currentTimeMillis() + ".3gpp");
        return file;
    }
}
