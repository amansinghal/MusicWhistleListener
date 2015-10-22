package com.music.aman.musicg.Models;

import android.content.Context;
import android.text.Html;
import android.view.View;
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

    @Bind(R.id.item_ad_iv_ad_image)
    ImageView ivAdmage;
    @Bind(R.id.item_ad_delete)
    ImageView ivDelete;
    @Bind(R.id.item_ad_edit)
    ImageView ivEdit;
    @Bind(R.id.item_ad_click)
    TextView tvClick;
    @Bind(R.id.item_ad_url)
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
    public void bind(final Addvertisment addvertisment) {

        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemAction(view.getId(),addvertisment,view);
            }
        });

        ivEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemAction(view.getId(),addvertisment,view);
            }
        });


        Picasso.with(getContext()).load("http://whistleandfind.com/developer/add_pictures/" + addvertisment.getImage_url()).into(ivAdmage);
        tvClick.setText(Html.fromHtml("<b>Clicks:  </b>" + addvertisment.getClicks()));
        try {
            tvUrl.setText(Html.fromHtml("<b>Url:  </b>" + URLDecoder.decode(addvertisment.getUrl(), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
