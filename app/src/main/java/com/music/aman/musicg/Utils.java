package com.music.aman.musicg;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.music.aman.musicg.Models.APIInterface;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by AmaN on 8/22/2015.
 */
public class Utils {

    public static String FireBase_URL = "https://luminous-torch-931.firebaseio.com/";

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
        Log.e(serviceClass.getSimpleName(), "Not Found");
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

    public static APIInterface getAdapterWebService(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(5, TimeUnit.MINUTES);
        mOkHttpClient.setReadTimeout(5, TimeUnit.MINUTES);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AuthorizationActivity.API).setClient(new OkClient(mOkHttpClient)).build();
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return  restAdapter.create(APIInterface.class);
    }

    public static void showDialog(Context context, String message, String positiveText, DialogInterface.OnClickListener posClick, String negativeText, DialogInterface.OnClickListener negClick) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(positiveText, posClick);
        builder1.setNegativeButton(negativeText, negClick);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void showDialogWithListSelection(Context context, String title, CharSequence[] array, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setSingleChoiceItems(array, 0, listener);
        dialog.show();
    }

}
