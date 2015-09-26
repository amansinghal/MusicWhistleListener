package com.music.aman.musicg.Models;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by AmaN on 9/26/2015.
 */
public  interface APIInterface {
    @FormUrlEncoded
    @POST("/index.php")
    public void getUSerInfo(@Field("tag")String tag,@Field("fb_id")String fb_id,@Field("name")String name,@Field("email")String email, Callback<APIModel> apiModelCallback);
}