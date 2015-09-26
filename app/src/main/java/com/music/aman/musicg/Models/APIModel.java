package com.music.aman.musicg.Models;

/**
 * Created by AmaN on 9/26/2015.
 */
public class APIModel
{
    private String error;

    private String tag;

    private User user;

    private String success;

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

    @Override
    public String toString()
    {
        return "ClassPojo [error = "+error+", tag = "+tag+", user = "+user+", success = "+success+"]";
    }
}