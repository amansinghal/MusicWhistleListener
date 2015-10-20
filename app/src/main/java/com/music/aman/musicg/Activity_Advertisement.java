package com.music.aman.musicg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.aman.musicg.Fragments.Frag_MyAds;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AmaN on 10/17/2015.
 */
public class Activity_Advertisement extends Activity {

    @Bind(R.id.back)
    ImageView ivBack;
    @Bind(R.id.add_ad)
    ImageView ivAddAd;
    @Bind(R.id.activity_ad_title)
    TextView tvTitle;
    public ProgressDialog progressDialog;
    public SharedPreferences preferences;

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,Activity_Advertisement.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        ButterKnife.bind(this);
        preferences = getSharedPreferences(MainActivity.URI_KEY, MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        getFragmentManager().beginTransaction().replace(R.id.ad_container,new Frag_MyAds()).commit();
    }

    public void showProgess(){
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    public void hideProgess(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    public void setTvTitle(String title){
        tvTitle.setText(title);
    }

}
