package com.music.aman.musicg;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmaN on 8/22/2015.
 */
public class Utils {

    public static Animation getRotationAnimation() {
        RotateAnimation anim = new RotateAnimation(0f, 25f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setDuration(500);
        return anim;
    }

    public static Animation getRotationReverseAnimation() {
        RotateAnimation anim = new RotateAnimation(25f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setDuration(500);
        return anim;
    }

    public static Animation getTranslateAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 20.0f,
                0.0f, 0.0f);
        animation.setDuration(300);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);
        return animation;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e(serviceClass.getSimpleName(), "Found");
                return true;
            }
        }
        return false;
    }

    public static boolean isRunning(Context ctx) {
        ArrayList<String> runningactivities = new ArrayList<String>();
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (int i1 = 0; i1 < services.size(); i1++) {
            runningactivities.add(0, services.get(i1).topActivity.toString());
        }
        if (runningactivities.contains("ComponentInfo{com.music.aman.musicg/com.music.aman.musicg.Activity_Dialog_Found}") == true) {
            return true;
        }
        return false;
    }

    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView || v instanceof Button) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));
            }
        } catch (Exception e) {
        }
    }
}
