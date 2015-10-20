package com.music.aman.musicg.Models;

import java.io.Serializable;
import java.io.SerializablePermission;

/**
 * Created by AmaN on 10/17/2015.
 */
public class Addvertisment implements Serializable
{
    private String id;

    private String image_url;

    private String user_id;

    private String url;

    private String clicks;

    public String getClicks() {
        return clicks;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getImage_url ()
    {
        return image_url;
    }

    public void setImage_url (String image_url)
    {
        this.image_url = image_url;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", image_url = "+image_url+", user_id = "+user_id+", url = "+url+"]";
    }
}
