package com.music.aman.musicg.Models;

import java.io.Serializable;

/**
 * Created by AmaN on 10/23/2015.
 */
public class Payments implements Serializable
{
    private String amount;

    private String id;

    private String type;

    private String date_pay;

    private String pay_for;

    public String getPay_for() {
        return pay_for;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getDate_pay ()
    {
        return date_pay;
    }

    public void setDate_pay (String date_pay)
    {
        this.date_pay = date_pay;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", id = "+id+", type = "+type+", date_pay = "+date_pay+"]";
    }
}