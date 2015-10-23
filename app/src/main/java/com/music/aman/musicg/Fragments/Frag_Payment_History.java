package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.music.aman.musicg.MainActivity;
import com.music.aman.musicg.Models.APIModel;
import com.music.aman.musicg.Models.PayHisView;
import com.music.aman.musicg.Models.Payments;
import com.music.aman.musicg.R;
import com.music.aman.musicg.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by AmaN on 10/23/2015.
 */
public class Frag_Payment_History extends Fragment {

    View view;
    @Bind(R.id.frag_pay_his_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.text)
    TextView textView;
    SharedPreferences sharedPreferences;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment_history, container, false);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.URI_KEY, Context.MODE_PRIVATE);
        ButterKnife.bind(this, view);
        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");
        getHistories();
        return view;
    }

    private void getHistories() {
        dialog.show();
        Utils.getAdapterWebService().getPayments("getpayments", sharedPreferences.getString(MainActivity.USER_ID_KEY, ""), new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                dialog.dismiss();
                if (apiModel.getPaymentses().isEmpty()) {
                    textView.setText(apiModel.getReplyMsg());
                } else {
                    RecyclerMultiAdapter recyclerMultiAdapter = SmartAdapter.items(apiModel.getPaymentses()).map(Payments.class, PayHisView.class).recyclerAdapter();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(recyclerMultiAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Utils.showDialog(getActivity(), error.getMessage());
            }
        });
    }

    @OnClick(R.id.back)
    public void back(){
        getFragmentManager().popBackStack();
    }
}
