package com.music.aman.musicg.Firebase;

import com.firebase.client.Firebase;
import com.music.aman.musicg.Utils;

/**
 * Created by kipl217 on 9/8/2015.
 */
public class FireBaseInstance  {

    public static final String FB_KEY_USER="Users";

    public static Firebase getInstance(String childNodeName){
        Firebase firebase = new Firebase(Utils.FireBase_URL+childNodeName);
        return firebase;
    };
}
