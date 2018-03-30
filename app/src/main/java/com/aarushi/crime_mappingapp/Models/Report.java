package com.aarushi.crime_mappingapp.Models;

/**
 * Created by arushiarora on 3/25/2018.
 */

public class Report {

    String name;
    String aadharcard;
    String phone;
    String crime_type;
    String latitude;
    String longitude;
    String crime_description;
    String date_crime;
    String time_crime;
    String complaint_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadharcard() {
        return aadharcard;
    }

    public void setAadharcard(String aadharcard) {
        this.aadharcard = aadharcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCrime_type() {
        return crime_type;
    }

    public void setCrime_type(String crime_type) {
        this.crime_type = crime_type;
    }

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

    public String getCrime_description() {
        return crime_description;
    }

    public void setCrime_description(String crime_description) {
        this.crime_description = crime_description;
    }

    public String getDate_crime() {
        return date_crime;
    }

    public void setDate_crime(String date_crime) {
        this.date_crime = date_crime;
    }

    public String getTime_crime() {
        return time_crime;
    }

    public void setTime_crime(String time_crime) {
        this.time_crime = time_crime;
    }

    public String getComplaint_time() {
        return complaint_time;
    }

    public void setComplaint_time(String complaint_time) {
        this.complaint_time = complaint_time;
    }

    public Report(String name, String aadharcard, String phone, String crime_type, String latitude, String longitude, String crime_description, String date_crime, String time_crime, String complaint_time) {

        this.name = name;
        this.aadharcard = aadharcard;
        this.phone = phone;
        this.crime_type = crime_type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.crime_description = crime_description;
        this.date_crime = date_crime;
        this.time_crime = time_crime;
        this.complaint_time = complaint_time;
    }
}
