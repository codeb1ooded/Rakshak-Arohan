package com.aarushi.crime_mappingapp.Models;

/**
 * Created by arushiarora on 3/23/2018.
 */

public class CrimeLocation {
    String latitude;
    String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public CrimeLocation(String latitude, String longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
