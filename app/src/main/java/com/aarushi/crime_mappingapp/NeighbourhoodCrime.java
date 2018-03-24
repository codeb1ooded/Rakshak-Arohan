package com.aarushi.crime_mappingapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.API.NeighbourhoodCrimeAPI;
import com.aarushi.crime_mappingapp.Models.CrimeLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NeighbourhoodCrime extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GPSTracker mGPSTracker;

    private double mLatitude, mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbourhood_crime);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(28.7041, 77.1045);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Delhi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mGPSTracker = new GPSTracker(this);
        if(mGPSTracker.canGetLocation()){
            mLatitude = mGPSTracker.getLatitude();
            mLongitude = mGPSTracker.getLongitude();
            Toast.makeText(this, "Latitude="+mLatitude+"Longitude="+mLongitude, Toast.LENGTH_SHORT).show();

            LatLng latLng = new LatLng(mLatitude, mLongitude);
            MarkerOptions options = new MarkerOptions()
                    .title("My Position")
                    .position(latLng);
            googleMap.addMarker(options);



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://crime-mapping.herokuapp.com/api/")
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();
            NeighbourhoodCrimeAPI neighbourhoodCrimeAPI=retrofit.create(NeighbourhoodCrimeAPI.class);
            neighbourhoodCrimeAPI.getCrimeDetails(mLatitude+"",mLongitude+"").enqueue(new Callback<ArrayList<CrimeLocation>>() {
                @Override
                public void onResponse(Call<ArrayList<CrimeLocation>> call, Response<ArrayList<CrimeLocation>> response) {
                    // Log.d(TAG, "onResponse: ");



                }

                @Override
                public void onFailure(Call<ArrayList<CrimeLocation>> call, Throwable t) {

                }

            });
        }else{
            mGPSTracker.showSettingsAlert();
        }
    }
}
