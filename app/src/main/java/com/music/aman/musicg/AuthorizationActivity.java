package com.music.aman.musicg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.music.aman.musicg.Fragments.Frag_After_Login;
import com.music.aman.musicg.Fragments.Frag_Register;
import com.music.aman.musicg.Models.APIInterface;
import com.music.aman.musicg.Models.APIModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by kipl217 on 9/10/2015.
 */
public class AuthorizationActivity extends Activity {

    private CallbackManager callbackManager;
    ProgressDialog progressDialog;
    String API = "http://whistleandfind.com/developer/index.php";
    private SharedPreferences sharedPreferences;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AuthorizationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(MainActivity.URI_KEY, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_authorization);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        if (!sharedPreferences.getBoolean(MainActivity.IS_USER_LOGGED_IN_KEY,false))
        getFragmentManager().beginTransaction().replace(R.id.container, new Frag_Register()).commit();
        else{
            progressDialog.show();
            String[] data;
            data = sharedPreferences.getString(MainActivity.USER_INFO_KEY,"").split(":");
            getReq("login", data[0], data[1], data.length>2?data[2]:"");
        }

        callbackManager = CallbackManager.Factory.create();
        // If using in a fragment
        // Other app specific specialization

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("FBlogin", "Success" + loginResult.getAccessToken());
                getFBInfo(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(AuthorizationActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFBInfo(AccessToken accessToken) {
        progressDialog.show();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("JSOn", "" + object);
                        try {
                            getReq("login", object.getString("id"), object.getString("name"), object.isNull("email")?"":object.getString("email"));
                        } catch (Exception e){
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getReq(String tag, final String id, String name, String email) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API).build();

        final APIInterface apiInterface = restAdapter.create(APIInterface.class);

        apiInterface.getUSerInfo(tag, id, name, email, new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                Log.e("APIModel", "" + apiModel);
                progressDialog.dismiss();
                if (apiModel.getSuccess().equals("1")){
                    Frag_After_Login fragAfterLogin = new Frag_After_Login();
                    Bundle bundle = new Bundle();
                    apiModel.getUser().setFb_id(id);
                    bundle.putSerializable("value",apiModel.getUser());
                    fragAfterLogin.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragAfterLogin).commit();
                }else{
                    Toast.makeText(AuthorizationActivity.this,"Error while logging please try later",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(AuthorizationActivity.this,"Error while logging please try later",Toast.LENGTH_LONG).show();
            }
        });

    }
}
