package com.aarushi.crime_mappingapp.API;

import com.aarushi.crime_mappingapp.Models.Report;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arushiarora on 3/25/2018.
 */

public interface ReportFilingAPI {
    @GET("reportcrime/")
    Call<ResponseBody> fileReport(@Query("crimetype") String crimetype,
                                  @Query("latitude") String latitude,
                                  @Query("longitude") String longitude,
                                  @Query("crime_description") String crime_description,
                                  @Query("complaint_by") String complaint_by,
                                  @Query("date_crime") String date_crime,
                                  @Query("time_crime") String time_crime,
                                  @Query("fir_location") String fir_location,
                                  @Query("complaint_time") String complaint_time,
                                  @Query("phone") String phone,
                                  @Query("status") String status);
}
