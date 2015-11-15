package com.music.aman.musicg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by AmaN on 8/22/2015.
 */
public class RunnerService extends Service implements OnSignalsDetectedListener {

    private DetectorThread detectorThread;
    private RecorderThread recorderThread;
    private boolean isDetected=false;
    public static RunnerService runnerIntent= null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnerIntent=this;
        recorderThread=new RecorderThread();
        recorderThread.start();
        detectorThread=new DetectorThread(recorderThread);
        detectorThread.setOnSignalsDetectedListener(this);
        detectorThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(),"onDestroy");
        stopThread();
    }

    private synchronized void stopThread(){
        if (recorderThread  != null){
            recorderThread.stopRecording();
            recorderThread.interrupt();
        }
        if (detectorThread!=null){
            detectorThread.interrupt();
        }
    }

    @Override
    public void onWhistleDetected() {
        //Toast.makeText(getBaseContext(),"Detected",Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), "whistleRing");
        if (!Utils.isRunning(this) && Activity_Dialog_Found.makeServiceDetectable) {
            Activity_Dialog_Found.makeServiceDetectable = false;
            startActivity(Activity_Dialog_Found.getIntent(this));
        }
    }
}
