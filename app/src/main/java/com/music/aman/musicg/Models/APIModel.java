package com.music.aman.musicg.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmaN on 9/26/2015.
 */
public class APIModel
{
    private String error;

    private String tag;

    private User user;

    private String success;

    private String replyMsg;

    private Subcriptions subcriptions;

    private String image_path;

    private ArrayList<Addvertisment> addvertisment;

    public String getReplyMsg() {
        return replyMsg;
    }

    public ArrayList<Addvertisment> getAddvertisment() {
        return addvertisment;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public String getTag ()
    {
        return tag;
    }

    public void setTag (String tag)
    {
        this.tag = tag;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    public Subcriptions getSubcriptions() {
        return subcriptions;
    }

    public void setSubcriptions(Subcriptions subcriptions) {
        this.subcriptions = subcriptions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", tag = "+tag+", user = "+user+", success = "+success+"]";
    }
}