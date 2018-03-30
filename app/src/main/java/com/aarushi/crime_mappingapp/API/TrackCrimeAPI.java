package com.aarushi.crime_mappingapp.API;

import com.aarushi.crime_mappingapp.Models.reported_crimes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arushiarora on 3/29/2018.
 */

public interface TrackCrimeAPI {
    @GET("reported_crimes/")
    Call<reported_crimes> getReportedCrimes(@Query("phone") String phone);
}
