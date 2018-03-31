package com.aarushi.crime_mappingapp.Models;

import java.io.Serializable;

/**
 * Created by megha on 31/03/18.
 */

public class ImageModel implements Serializable {
    private String pathName;
    private String timestampTime;
    private String timestampDate;
    private String latitude;
    private String longitude;

    public ImageModel(String pathName, String timestampTime, String timestampDate, String latitude, String longitude) {
        this.pathName = pathName;
        this.timestampTime = timestampTime;
        this.timestampDate = timestampDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPathName() {
        return pathName;
    }

    public String getTimestampTime() {
        return timestampTime;
    }

    public String getTimestampDate() {
        return timestampDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
