package com.aarushi.crime_mappingapp.Models;

/**
 * Created by arushiarora on 3/29/2018.
 */

public class ReportedCrimes {
    String date_crime;
    String fir_location;
    String status;
    String complaint_time;
    String complaint_date;
    String phone;
    String crime_description;
    String longitude;
    String latitude;
    String crime_type;
    String time_crime;
    String complaint_by;

    public String getDate_crime() {
        return date_crime;
    }

    public void setDate_crime(String date_crime) {
        this.date_crime = date_crime;
    }

    public String getFir_location() {
        return fir_location;
    }

    public void setFir_location(String fir_location) {
        this.fir_location = fir_location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplaint_time() {
        return complaint_time;
    }

    public void setComplaint_time(String complaint_time) {
        this.complaint_time = complaint_time;
    }

    public String getComplaint_date() {
        return complaint_date;
    }

    public void setComplaint_date(String complaint_date) {
        this.complaint_date = complaint_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCrime_description() {
        return crime_description;
    }

    public void setCrime_description(String crime_description) {
        this.crime_description = crime_description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCrime_type() {
        return crime_type;
    }

    public void setCrime_type(String crime_type) {
        this.crime_type = crime_type;
    }

    public String getTime_crime() {
        return time_crime;
    }

    public void setTime_crime(String time_crime) {
        this.time_crime = time_crime;
    }

    public String getComplaint_by() {
        return complaint_by;
    }

    public void setComplaint_by(String complaint_by) {
        this.complaint_by = complaint_by;
    }

    public ReportedCrimes(String date_crime, String fir_location, String status, String complaint_time, String complaint_date, String phone, String crime_description, String longitude, String latitude, String crime_type, String time_crime, String complaint_by) {

        this.date_crime = date_crime;
        this.fir_location = fir_location;
        this.status = status;
        this.complaint_time = complaint_time;
        this.complaint_date = complaint_date;
        this.phone = phone;
        this.crime_description = crime_description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.crime_type = crime_type;
        this.time_crime = time_crime;
        this.complaint_by = complaint_by;
    }
}
