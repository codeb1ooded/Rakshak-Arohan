package com.aarushi.crime_mappingapp.API;

import com.aarushi.crime_mappingapp.Models.neghbourhood;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arushiarora on 3/23/2018.
 */

public interface NeighbourhoodCrimeAPI {
    @GET("neighbourhood/")
    Call<neghbourhood> getNeighbourCrimes(@Query("latitude") String latitude,
                                          @Query("longitude") String longitude);
}
