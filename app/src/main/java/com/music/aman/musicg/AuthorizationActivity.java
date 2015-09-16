package com.music.aman.musicg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.music.aman.musicg.Fragments.Frag_Register;

/**
 * Created by kipl217 on 9/10/2015.
 */
public class AuthorizationActivity extends Activity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context,AuthorizationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        getFragmentManager().beginTransaction().replace(R.id.container,new Frag_Register()).commit();
    }
}
