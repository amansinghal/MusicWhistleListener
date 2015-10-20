package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.music.aman.musicg.Activity_Advertisement;
import com.music.aman.musicg.AuthorizationActivity;
import com.music.aman.musicg.MainActivity;
import com.music.aman.musicg.Models.APIInterface;
import com.music.aman.musicg.Models.APIModel;
import com.music.aman.musicg.Models.AdListItemView;
import com.music.aman.musicg.Models.Addvertisment;
import com.music.aman.musicg.R;

import java.util.ResourceBundle;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AmaN on 10/17/2015.
 */
public class Frag_MyAds extends Fragment
{
    @Bind(R.id.frag_ad_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.frag_ad_text)
    TextView tvText;
    Activity_Advertisement advertisement;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_adds,container,false);
        advertisement = (Activity_Advertisement)getActivity();
        advertisement.setTvTitle("My Ads");
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAdds();
        return view;
    }

    private void getAdds(){
        advertisement.showProgess();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AuthorizationActivity.API).setLogLevel(RestAdapter.LogLevel.FULL).build();

        final APIInterface apiInterface = restAdapter.create(APIInterface.class);

        apiInterface.getMyAdds("myaddvertisment",advertisement.preferences.getString(MainActivity.USER_ID_KEY, ""), new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                System.out.println(apiModel);
                advertisement.hideProgess();
                if (apiModel.getAddvertisment().isEmpty()){
                    tvText.setText(apiModel.getReplyMsg());
                }else{
                    tvText.setVisibility(View.GONE);
                    RecyclerMultiAdapter recyclerMultiAdapter = SmartAdapter.items(apiModel.getAddvertisment()).map(Addvertisment.class, AdListItemView.class).recyclerAdapter();
                    recyclerView.setAdapter(recyclerMultiAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                advertisement.hideProgess();
                Toast.makeText(advertisement,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
