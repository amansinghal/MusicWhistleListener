package com.music.aman.musicg.Models;

import java.io.Serializable;

/**
 * Created by AmaN on 9/26/2015.
 */
public class User implements Serializable {
    private String uid;

    private String Price_facilitySubcription;

    private String Price_addSubcription;

    private String email;

    private String addSubcription;

    private String name;

    private String facilitySubcription;

    private String fb_id;

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getUid ()
    {
        return uid;
    }

    public void setUid (String uid)
    {
        this.uid = uid;
    }

    public String getPrice_facilitySubcription ()
    {
        return Price_facilitySubcription;
    }

    public void setPrice_facilitySubcription (String Price_facilitySubcription)
    {
        this.Price_facilitySubcription = Price_facilitySubcription;
    }

    public String getPrice_addSubcription ()
    {
        return Price_addSubcription;
    }

    public void setPrice_addSubcription (String Price_addSubcription)
    {
        this.Price_addSubcription = Price_addSubcription;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getAddSubcription ()
    {
        return addSubcription;
    }

    public void setAddSubcription (String addSubcription)
    {
        this.addSubcription = addSubcription;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getFacilitySubcription ()
    {
        return facilitySubcription;
    }

    public void setFacilitySubcription (String facilitySubcription)
    {
        this.facilitySubcription = facilitySubcription;
    }

    @Override
    public String toString()
    {
        return "[uid = "+uid+", Price_facilitySubcription = "+Price_facilitySubcription+", Price_addSubcription = "+Price_addSubcription+", email = "+email+", addSubcription = "+addSubcription+", name = "+name+", facilitySubcription = "+facilitySubcription+"]";
    }
}
