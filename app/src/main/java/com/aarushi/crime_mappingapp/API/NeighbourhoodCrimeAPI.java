package com.aarushi.crime_mappingapp.API;

import com.aarushi.crime_mappingapp.Models.CrimeDetails;
import com.aarushi.crime_mappingapp.Models.CrimeLocation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arushiarora on 3/23/2018.
 */

public interface NeighbourhoodCrimeAPI {
    @GET("neighbourhood")
    Call<ArrayList<CrimeLocation>> getCrimeDetails(@Query("latitude") String latitude, @Query("longitude") String longitude);
}
