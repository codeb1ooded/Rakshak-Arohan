package com.aarushi.crime_mappingapp.safest_route;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.API.ApiClient;
import com.aarushi.crime_mappingapp.Constants;
import com.aarushi.crime_mappingapp.GPSTracker;
import com.aarushi.crime_mappingapp.Models.path.Leg;
import com.aarushi.crime_mappingapp.Models.path.Path;
import com.aarushi.crime_mappingapp.Models.path.Route;
import com.aarushi.crime_mappingapp.Models.path.Step;
import com.aarushi.crime_mappingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SafestRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker mGPSTracker;

    private double mLatitude, mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safest_route);
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
        mGPSTracker = new GPSTracker(this);
        if(mGPSTracker.canGetLocation()){
            mLatitude = mGPSTracker.getLatitude();
            mLongitude = mGPSTracker.getLongitude();

            LatLng latLng = new LatLng(mLatitude, mLongitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            MarkerOptions options = new MarkerOptions()
                    .title("My Position")
                    .position(latLng);
            googleMap.addMarker(options);
            getDirection(mLatitude + "," + mLongitude, "faridabad");


        }else{
            mGPSTracker.showSettingsAlert();
        }
    }

    public void getDirection(String origin, String destination){
        Call<Path> getDirections = ApiClient.getInterface().getShortestPath(origin, destination, Constants.API_KEY, true);
        getDirections.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if (response.isSuccessful()) {
                    createRoute(response.body().getRoutes().get(1));
                } else {

                }
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                Toast.makeText(SafestRouteActivity.this, "Directions Not found" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createRoute(Route route){
        ArrayList<Step> steps = route.getLegs().get(0).getSteps();
        for(Step step: steps) {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(step.getStartLocation().getLat(), step.getStartLocation().getLng()),
                            new LatLng(step.getEndLocation().getLat(), step.getEndLocation().getLng()))
                    .width(5)
                    .color(Color.RED));
        }
    }

}
