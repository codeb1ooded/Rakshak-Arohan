package com.aarushi.crime_mappingapp.API;

import com.aarushi.crime_mappingapp.Models.path.Path;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by megha on 10/03/17.
 */

public interface ApiInterface  {

    @GET("/api/safest_route/")
    Call<Path> getShortestPath(@Query("origin") String origin, @Query("destination") String destination);

}