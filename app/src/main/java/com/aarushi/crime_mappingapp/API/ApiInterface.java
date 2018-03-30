package com.aarushi.crime_mappingapp.API;

import com.aarushi.crime_mappingapp.Models.EmptyClass;
import com.aarushi.crime_mappingapp.Models.path.Path;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by megha on 10/03/17.
 */

public interface ApiInterface  {

    @Multipart
    @POST("api/upload_image")
    Call<ResponseBody> uploadImage(@Query("complaint_id") String id, @Part("description") String description, @PartMap RequestBody image);

    @GET("/api/safest_route/")
    Call<Path> getShortestPath(@Query("origin") String origin, @Query("destination") String destination);

    @POST("/api/upload/")
    Call<EmptyClass> upload(@Body JsonObject jsonObject);

}