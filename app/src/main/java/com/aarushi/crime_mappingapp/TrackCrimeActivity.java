package com.aarushi.crime_mappingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aarushi.crime_mappingapp.API.TrackCrimeAPI;
import com.aarushi.crime_mappingapp.Adapters.CrimeReportingRecyclerViewAdapter;
import com.aarushi.crime_mappingapp.Models.ReportedCrimes;
import com.aarushi.crime_mappingapp.Models.reported_crimes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackCrimeActivity extends AppCompatActivity {
    String phone;
    RecyclerView recyclerView;
    CrimeReportingRecyclerViewAdapter crimeReportingRecyclerViewAdapter;

    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_crime);

        recyclerView=(RecyclerView)findViewById(R.id.rvPostsList);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        phone=sharedPref.getString("Number","");

        Log.d("Retro",phone);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://crime-mapping.herokuapp.com/api/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build();
        TrackCrimeAPI trackCrimeAPI=retrofit.create(TrackCrimeAPI.class);
        trackCrimeAPI.getReportedCrimes(phone).enqueue(new Callback<reported_crimes>() {

            @Override
            public void onResponse(Call<reported_crimes> call, Response<reported_crimes> response) {



                Log.d("Retro","Success "+call.request().url());
                Log.d("Retro","Body "+response.body());
                Log.d("Retro","Is successfull "+response.isSuccessful());
                Log.d("Retro","Message "+response.message());
                Log.d("Retro","Code "+response.code());
                Log.d("Retro","Body "+response.errorBody());
                List<ReportedCrimes> crimes= response.body().getCrimes();
                Log.d("Retro",crimes.size()+"");

                crimeReportingRecyclerViewAdapter=new CrimeReportingRecyclerViewAdapter(crimes,context);

                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(crimeReportingRecyclerViewAdapter);


            }


            @Override
            public void onFailure(Call<reported_crimes> call, Throwable t) {

                Log.d("Retro","Failure"+call.request().url());
            }
        });
    }
}
