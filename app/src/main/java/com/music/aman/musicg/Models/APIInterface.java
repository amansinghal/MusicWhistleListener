package com.music.aman.musicg.Models;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by AmaN on 9/26/2015.
 */
public  interface APIInterface {
    @FormUrlEncoded
    @POST("/index.php")
    public void getUSerInfo(@Field("tag")String tag,@Field("fb_id")String fb_id,@Field("name")String name,@Field("email")String email, Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void getUSerSubscriptionInfo(@Field("tag")String tag,@Field("user_id")String user_id,Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void getUSerSubscriptionUpdate(@Field("tag")String tag,@Field("user_id")String user_id,@Field("price")String price,@Field("type")String type,Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void addSubscriptionUpdate(@Field("tag")String tag,@Field("user_id")String user_id,@Field("price")String price,@Field("type")String type,@Field("ad_id")String ad_id,@Field("period")String period,Callback<APIModel> apiModelCallback);

    @Multipart
    @POST("/upload.php")
    public void upload(@Part("fileToUpload")TypedFile file,@Part("ad_id")String ad_id,@Part("uid")String uid,@Part("url")String url,Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void getMyAdds(@Field("tag")String tag,@Field("uid")String user_id,Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void addClicks(@Field("tag")String tag,@Field("ad_id")String ad_id,Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void getPayments(@Field("tag")String tag,@Field("uid")String user_id,Callback<APIModel> apiModelCallback);

    @FormUrlEncoded
    @POST("/index.php")
    public void deleteAdds(@Field("tag")String tag,@Field("ad_id")String ad_id,Callback<APIModel> apiModelCallback);
}
