package com.music.aman.musicg;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.splunk.mint.Mint;


/**
 * Created by kipl217 on 9/8/2015.
 */
public class ApplicationContext extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Mint.initAndStartSession(this, "39bb0ea7");
        FacebookSdk.sdkInitialize(this);
    }

}
