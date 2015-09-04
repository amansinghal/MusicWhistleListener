package com.music.aman.musicg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kipl217 on 9/2/2015.
 */
public class RecorderActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public final static String PATH = Environment.getExternalStorageDirectory() + "/WhistleAndFind/";
    public final static String PREFIX = "WF_REC_";
    TextView tv_list_status, tv_record_status;
    ImageView iv_play_pause, iv_save;
    ListView lv_recordings;
    ArrayList<RecordModel> listRecords = new ArrayList<>();
    ArrayList<String> listRecordsString = new ArrayList<>();
    boolean isRecording = false;
    File lastRecordedFile;
    ArrayAdapter<String> arrayAdapter;
    MediaRecorder mediaRecorder = new MediaRecorder();
    SharedPreferences sharedPreferences;
    String selectedUri;

    public static Intent getIntent(Context context) {
        return new Intent(context, RecorderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(MainActivity.URI_KEY, MODE_PRIVATE);
        selectedUri = sharedPreferences.getString(MainActivity.ALARM_TONE_KEY,"");
        setContentView(R.layout.activity_recorder);
        mediaRecorder.release();
        initUI();
        getRecordings();
    }

    private void getRecordings() {
        listRecordsString.clear();
        listRecords.clear();
        arrayAdapter.notifyDataSetChanged();
        File[] files = new File(PATH).listFiles();
        if (files!=null)
        for (File fi : files) {
            setModel(fi);
        }
        if (listRecordsString.isEmpty()) {
            tv_list_status.setText("Oops! there is no recording.");
        } else {
            tv_list_status.setText("");
        }
        arrayAdapter.notifyDataSetChanged();
        //updateUriSelection(selectedUri);
    }

    private void updateUriSelection(String uri){
        int count = 0;
        for (int i=0;i<listRecords.size();i++){
            lv_recordings.setSelection(i);
            //View listItemView=lv_recordings.getChildAt(i- lv_recordings.getFirstVisiblePosition());
            if (uri.equals(listRecords.get(i).uri.toString())) {
                //lv_recordings.setSelection(i);
              /*  count++;
                listItemView.setBackgroundColor(Color.parseColor("#c94242"));
                ((TextView)listItemView.findViewById(android.R.id.text1)).setTextColor(Color.parseColor("#ffffff"));*/
            }else{
                /*listItemView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ((TextView)listItemView.findViewById(android.R.id.text1)).setTextColor(Color.parseColor("#000000"));*/
            }
        }

        arrayAdapter.notifyDataSetChanged();

        if (count == 0){
            sharedPreferences.edit().putString(MainActivity.ALARM_TONE_KEY, "").commit();
        }

    }

    private void setModel(File fi) {
        RecordModel recordModel = new RecordModel();
        listRecordsString.add(fi.getName());
        recordModel.name = fi.getName();
        recordModel.path = fi.getAbsolutePath();
        recordModel.uri = Uri.fromFile(fi);
        listRecords.add(recordModel);
    }

    private void initUI() {
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(android.R.color.transparent);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c94242")));
        getActionBar().setTitle("Recordings");
        tv_list_status = (TextView) findViewById(R.id.activity_record_tv_list_status);
        tv_record_status = (TextView) findViewById(R.id.activity_record_tv_record_status);
        iv_play_pause = (ImageView) findViewById(R.id.activity_record_iv_play_pause);
        iv_play_pause.setOnClickListener(this);
        iv_save = (ImageView) findViewById(R.id.activity_record_iv_save);
        iv_save.setOnClickListener(this);
        lv_recordings = (ListView) findViewById(R.id.activity_record_lv);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listRecordsString);
        lv_recordings.setAdapter(arrayAdapter);
        lv_recordings.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_record_iv_play_pause) {
            if (!isRecording)
                startRecording();
            else
                Toast.makeText(this,"Press on red highlighted icon to stop record.",Toast.LENGTH_LONG).show();
        }
        if (v.getId() == R.id.activity_record_iv_save) {
               if (isRecording)
                   stopRecording();
            else
                   Toast.makeText(this,"Press on red highlighted icon to start record.",Toast.LENGTH_LONG).show();
        }
    }


    private void startRecording() {

        mediaRecorder = getMediaRecorder();

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            tv_record_status.setText("Recording..");
            iv_play_pause.setBackgroundResource(R.drawable.bg_rectangle_black);
            iv_save.setBackgroundResource(R.drawable.bg_rectangle);
            isRecording = true;
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null && isRecording) {
            iv_play_pause.setBackgroundResource(R.drawable.bg_rectangle);
            iv_save.setBackgroundResource(R.drawable.bg_rectangle_black);
            isRecording = false;
            mediaRecorder.stop();
            mediaRecorder.release();
            tv_record_status.setText("Ready to record...");
            getRecordings();
        }
    }

    private MediaRecorder getMediaRecorder() {
        MediaRecorder myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        lastRecordedFile = getPath();
        myRecorder.setOutputFile(lastRecordedFile.getAbsolutePath());
        return myRecorder;
    }

    private File getPath() {
        File file = new File(PATH);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        String name = new SimpleDateFormat("dd_MM_yyyy_hh_mm_a").format(new Date(System.currentTimeMillis()));
        file = new File(PATH + PREFIX + name + ".mp3");
        return file;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        showDialogToChoose(position);
    }

    private void showDialogToChoose(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setSingleChoiceItems(new CharSequence[]{"Play", "Delete", "Set as tone"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_VIEW, listRecords.get(pos).uri);
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(RecorderActivity.this, "Install a default music player.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:
                        if (listRecords.get(pos).uri.toString().equals(selectedUri))
                            selectedUri="";
                        new File(listRecords.get(pos).path).delete();
                        getRecordings();
                        break;
                    case 2:
                        sharedPreferences.edit().putString(MainActivity.ALARM_TONE_KEY, listRecords.get(pos).uri.toString()).commit();
                        selectedUri = listRecords.get(pos).uri.toString();
                        getRecordings();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
