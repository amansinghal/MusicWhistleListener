package com.music.aman.musicg.Models;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.aman.musicg.R;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Created by gspl on 10/20/2015.
 */
public class AdListItemView extends BindableLayout<Addvertisment> {

    @Bind(R.id.item_ad_imageview)
    ImageView ivAdmage;
    @Bind(R.id.item_ad_iv_delete)
    ImageView ivDelete;
    @Bind(R.id.item_ad_tv_click)
    TextView tvClick;
    @Bind(R.id.item_ad_tv_url)
    TextView tvUrl;

    public AdListItemView(Context context) {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_ad;
    }

    @Override
    public void bind(Addvertisment addvertisment) {
        Picasso.with(getContext()).load("http://whistleandfind.com/developer/add_pictures/" + addvertisment.getImage_url()).into(ivAdmage);
        tvClick.setText(Html.fromHtml("<b>Clicks:  </b>" + addvertisment.getClicks()));
        try {
            tvUrl.setText(Html.fromHtml("<b>Url:  </b>" + URLDecoder.decode(addvertisment.getUrl(), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
