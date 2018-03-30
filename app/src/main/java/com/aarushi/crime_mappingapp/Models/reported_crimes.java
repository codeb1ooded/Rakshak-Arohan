package com.aarushi.crime_mappingapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arushiarora on 3/29/2018.
 */

public class reported_crimes {
    @SerializedName("reported_crimes")
    public List<ReportedCrimes> crimes;

    public List<ReportedCrimes> getCrimes(){
        return this.crimes;
    }

}
