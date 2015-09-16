package com.music.aman.musicg;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by kipl217 on 9/8/2015.
 */
public class ApplicationContext extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
