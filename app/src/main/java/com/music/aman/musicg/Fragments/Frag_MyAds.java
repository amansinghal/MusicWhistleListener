package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
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
import com.music.aman.musicg.Activity_Paypal;
import com.music.aman.musicg.AuthorizationActivity;
import com.music.aman.musicg.MainActivity;
import com.music.aman.musicg.Models.APIInterface;
import com.music.aman.musicg.Models.APIModel;
import com.music.aman.musicg.Models.AdListItemView;
import com.music.aman.musicg.Models.Addvertisment;
import com.music.aman.musicg.R;
import com.music.aman.musicg.Utils;

import java.util.ResourceBundle;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.janmuller.android.simplecropimage.Util;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AmaN on 10/17/2015.
 */
public class Frag_MyAds extends Fragment implements ViewEventListener {
    @Bind(R.id.frag_ad_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.frag_ad_text)
    TextView tvText;
    Activity_Advertisement advertisement;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_adds, container, false);
        advertisement = (Activity_Advertisement) getActivity();
        advertisement.setTvTitle("My Ads");
        ButterKnife.bind(this, view);
        ((Activity_Advertisement) getActivity()).ivAddAd.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        getAdds();
        super.onResume();
    }

    private void getAdds() {
        advertisement.showProgess();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(AuthorizationActivity.API).setLogLevel(RestAdapter.LogLevel.FULL).build();

        final APIInterface apiInterface = restAdapter.create(APIInterface.class);

        apiInterface.getMyAdds("myaddvertisment", advertisement.preferences.getString(MainActivity.USER_ID_KEY, ""), new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                System.out.println(apiModel);
                advertisement.hideProgess();
                if (apiModel.getAddvertisment().isEmpty()) {
                    tvText.setText(apiModel.getReplyMsg());
                    tvText.setVisibility(View.VISIBLE);
                } else {
                    tvText.setVisibility(View.GONE);
                }

                RecyclerMultiAdapter recyclerMultiAdapter = SmartAdapter.items(apiModel.getAddvertisment()).map(Addvertisment.class, AdListItemView.class).listener(Frag_MyAds.this).recyclerAdapter();
                recyclerView.setAdapter(recyclerMultiAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                advertisement.hideProgess();
                Toast.makeText(advertisement, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onViewEvent(int i, final Object o, int i1, View view) {

        if (view.getId() == R.id.item_ad_payment) {
            startActivity(Activity_Paypal.getIntent(getActivity(), R.id.advertisment, "Ad Subscription", ((Addvertisment)o).getId()));
        }

        if (view.getId() == R.id.item_ad_delete) {
            Utils.showDialog(getActivity(), "Are you sure?  you want to delete.", "Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    advertisement.showProgess();
                    Utils.getAdapterWebService().deleteAdds("deleteAd", ((Addvertisment) o).getId(), new Callback<APIModel>() {
                        @Override
                        public void success(APIModel apiModel, Response response) {
                            advertisement.hideProgess();
                            if (apiModel.getSuccess().equalsIgnoreCase("1"))
                                getAdds();
                            else
                                Toast.makeText(getActivity(), apiModel.getReplyMsg(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            advertisement.hideProgess();
                            error.printStackTrace();
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

        if (view.getId() == R.id.item_ad_edit) {
            Frag_Add_Ad frag_add_ad = new Frag_Add_Ad();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ad", (Addvertisment) o);
            frag_add_ad.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.ad_container, frag_add_ad).addToBackStack(null).commit();
        }

    }
}
