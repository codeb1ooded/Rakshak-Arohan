package com.aarushi.crime_mappingapp.Models;

/**
 * Created by arushiarora on 3/26/2018.
 */

public class NeighborReport {
    String time_crime;
    String longitude;
    String fir_location;
    String latitude;
    String complaint_by;
    String crime_description;
    String phone;
    String crime_type;
    String status;
    String date_crime;
    String complaint_time;

    public String getTime_crime() {
        return time_crime;
    }

    public void setTime_crime(String time_crime) {
        this.time_crime = time_crime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFir_location() {
        return fir_location;
    }

    public void setFir_location(String fir_location) {
        this.fir_location = fir_location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getComplaint_by() {
        return complaint_by;
    }

    public void setComplaint_by(String complaint_by) {
        this.complaint_by = complaint_by;
    }

    public String getCrime_description() {
        return crime_description;
    }

    public void setCrime_description(String crime_description) {
        this.crime_description = crime_description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_crime() {
        return date_crime;
    }

    public void setDate_crime(String date_crime) {
        this.date_crime = date_crime;
    }

    public String getComplaint_time() {
        return complaint_time;
    }

    public void setComplaint_time(String complaint_time) {
        this.complaint_time = complaint_time;
    }

    public NeighborReport(String time_crime, String longitude, String fir_location, String latitude, String complaint_by, String crime_description, String phone, String crime_type, String status, String date_crime, String complaint_time) {

        this.time_crime = time_crime;
        this.longitude = longitude;
        this.fir_location = fir_location;
        this.latitude = latitude;
        this.complaint_by = complaint_by;
        this.crime_description = crime_description;
        this.phone = phone;
        this.crime_type = crime_type;
        this.status = status;
        this.date_crime = date_crime;
        this.complaint_time = complaint_time;
    }
}
