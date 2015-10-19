package com.music.aman.musicg.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.music.aman.musicg.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AmaN on 10/17/2015.
 */
public class Frag_MyAds extends Fragment
{
    @Bind(R.id.frag_ad_recycler)
    RecyclerView recyclerView;
    @Bind(R.id.frag_ad_text)
    TextView tvText;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_adds,container,false);
        ButterKnife.bind(getActivity(),view);
        return view;
    }
}
