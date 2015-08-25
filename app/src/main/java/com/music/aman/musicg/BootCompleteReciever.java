package com.music.aman.musicg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kipl217 on 8/25/2015.
 */

public class BootCompleteReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, RunnerService.class);
        context.startService(startServiceIntent);
    }
}
