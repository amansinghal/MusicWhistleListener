package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.music.aman.musicg.Firebase.FireBaseInstance;
import com.music.aman.musicg.Models.Users;
import com.music.aman.musicg.R;

import java.util.Arrays;

/**
 * Created by kipl217 on 9/10/2015.
 */
public class Frag_Register extends Fragment implements View.OnClickListener {

    EditText et_email, et_pass;
    TextView tv_title;
    TextView tv_sign_in;
    View view;
    ProgressDialog dialog;
    private Button loginButton;
    private CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        initUI();
        initFB();
        return view;
    }

    public void initFB(){
        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    private void initUI() {

        //et_pass.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button){
            LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends"));
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppEventsLogger.deactivateApp(getActivity());
    }
}
