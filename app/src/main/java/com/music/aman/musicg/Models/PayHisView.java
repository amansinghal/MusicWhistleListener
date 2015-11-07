package com.music.aman.musicg.Models;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.music.aman.musicg.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Created by AmaN on 10/23/2015.
 */
public class PayHisView extends BindableLayout<Payments> {

    @Bind(R.id.item_pay_his_tv)
    TextView textView;

    public PayHisView(Context context) {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_pay_his;
    }

    @Override
    public void bind(Payments payments) {
        String fromat = "<b>Pay for:</b> "+payments.getPay_for()+"<br>" +
                "<b>Date: </b>"+payments.getDate_pay()+"<br>" +
                "<b>Pay via: </b>"+payments.getType()+"<br>"+
                "<b>&#163;</b>"+payments.getAmount();
        textView.setText(Html.fromHtml(fromat));
    }
}
