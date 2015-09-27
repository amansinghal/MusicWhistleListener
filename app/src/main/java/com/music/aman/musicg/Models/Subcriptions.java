package com.music.aman.musicg.Models;

/**
 * Created by AmaN on 9/27/2015.
 */
public class Subcriptions {

    String facilitySubscription;
    String addSubscription;

    public String getFacilitySubscription() {
        return facilitySubscription;
    }

    public void setFacilitySubscription(String facilitySubscription) {
        this.facilitySubscription = facilitySubscription;
    }

    public String getAddSubscription() {
        return addSubscription;
    }

    public void setAddSubscription(String addSubscription) {
        this.addSubscription = addSubscription;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [facilitySubscription = "+facilitySubscription+", addSubscription = "+addSubscription+"]";
    }
}
