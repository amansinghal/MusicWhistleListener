package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.music.aman.musicg.Firebase.FireBaseInstance;
import com.music.aman.musicg.Models.Users;
import com.music.aman.musicg.R;

/**
 * Created by kipl217 on 9/10/2015.
 */
public class Frag_Register extends Fragment implements View.OnClickListener {

    EditText et_email, et_pass;
    TextView tv_title;
    TextView tv_sign_in;
    View view;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        initUI();
        return view;
    }

    private void initUI() {
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_pass = (EditText) view.findViewById(R.id.et_password);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_sign_in = (TextView) view.findViewById(R.id.tv_sign_in);
        tv_sign_in.setOnClickListener(this);
        tv_title.setText("Enter email to check you account.");
        //et_pass.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sign_in) {
            checkEmail(et_email.getText().toString());
            //registerEmailPassword(et_email.getText().toString(),et_pass.getText().toString());
        }
    }

    private void checkEmail(String email) {
        dialog.show();
        Firebase firebase = FireBaseInstance.getInstance(FireBaseInstance.FB_KEY_USER);
        Query query = firebase.equalTo(email, "email");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dialog.hide();
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dialog.hide();
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dialog.hide();
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                dialog.hide();
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                dialog.hide();
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void registerEmailPassword(String email,String password){
        dialog.show();
        Firebase firebase = FireBaseInstance.getInstance(FireBaseInstance.FB_KEY_USER);
        Users users = new Users(email,false,password);
        firebase.setValue(users, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                dialog.hide();
            }
        });
    }
}
