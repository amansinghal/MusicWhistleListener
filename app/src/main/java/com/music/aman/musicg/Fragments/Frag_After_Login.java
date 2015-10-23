package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.music.aman.musicg.CircularImageview;
import com.music.aman.musicg.MainActivity;
import com.music.aman.musicg.Models.User;
import com.music.aman.musicg.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

/**
 * Created by kipl217 on 9/10/2015.
 */
public class Frag_After_Login extends Fragment implements View.OnClickListener {

    TextView tv_user_name;
    TextView tv_facility,tv_addvertisment;
    View view;
    CircularImageview profile_image;
    ProgressDialog dialog;
    private Button logOutButton,paymentHistory;
    private CallbackManager callbackManager;
    User user;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_after_login, container, false);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.URI_KEY, Context.MODE_PRIVATE);
        user = (User) getArguments().getSerializable("value");
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        initUI();
        return view;
    }

    private void initUI() {
        paymentHistory = (Button)view.findViewById(R.id.payment_history);
        paymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().add(R.id.container, new Frag_Payment_History()).addToBackStack(null).commit();
            }
        });
        tv_addvertisment = (TextView)view.findViewById(R.id.subscription_text);
        tv_facility = (TextView)view.findViewById(R.id.facility_text);
        tv_user_name = (TextView)view.findViewById(R.id.user_name);
        profile_image = (CircularImageview)view.findViewById(R.id.profile_image);
        logOutButton = (Button)view.findViewById(R.id.sign_out);
        logOutButton.setOnClickListener(this);
        Picasso.with(getActivity()).load("https://graph.facebook.com/" + user.getFb_id() + "/picture?type=large").resize(512,512).centerCrop().into(profile_image);
        tv_addvertisment.setText(user.getAddSubcription().equals("0")?"Not subscribed.":(user.getAddSubcription().contains("+")?user.getAddSubcription().replace("+", ""):user.getAddSubcription())+" Days Left");
        tv_facility.setText(user.getFacilitySubcription().equals("0")?"Not subscribed.":(user.getFacilitySubcription().contains("+")?user.getFacilitySubcription().replace("+", ""):user.getFacilitySubcription())+" Days Left");
        tv_user_name.setText(user.getName());
        sharedPreferences.edit().putBoolean(MainActivity.IS_USER_LOGGED_IN_KEY,true).putString(MainActivity.USER_INFO_KEY,user.getFb_id()+":"+user.getName()+":"+user.getEmail()).putString(MainActivity.USER_ID_KEY,user.getUid()).commit();
        //et_pass.setVisibility(View.GONE);
        ((ImageView)view.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_out){
            sharedPreferences.edit().putBoolean(MainActivity.IS_USER_LOGGED_IN_KEY,false).putString(MainActivity.USER_INFO_KEY,"").commit();
            LoginManager.getInstance().logOut();
            getActivity().finish();
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
