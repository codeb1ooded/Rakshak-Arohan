package com.aarushi.crime_mappingapp.safest_route;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.API.ApiClient;
import com.aarushi.crime_mappingapp.Constants;
import com.aarushi.crime_mappingapp.GPSTracker;
import com.aarushi.crime_mappingapp.Models.path.Leg;
import com.aarushi.crime_mappingapp.Models.path.Path;
import com.aarushi.crime_mappingapp.Models.path.Route;
import com.aarushi.crime_mappingapp.Models.path.Step;
import com.aarushi.crime_mappingapp.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SafestRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker mGPSTracker;

    private double mLatitude, mLongitude;
    private ProgressDialog progressDialog;
    private Marker marker;
    private ArrayList<Polyline> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safest_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("SafestRouteActivity", "Place: " + place.getName());
                if(marker != null)  marker.remove();
                MarkerOptions options = new MarkerOptions()
                        .title("My Position")
                        .position(place.getLatLng());
                marker = mMap.addMarker(options);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),8.0f));
                getDirection(mLatitude + "," + mLongitude,  place.getLatLng().latitude + "," + place.getLatLng().longitude);

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(SafestRouteActivity.this, "Unable to read. Try Again!", Toast.LENGTH_SHORT).show();
                Log.i("SafestRouteActivity", "An error occurred: " + status);
            }
        });
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

            if(mLatitude == 0){
                mLatitude = 22.7196;
                mLongitude = 75.8577;
            }

            LatLng latLng = new LatLng(mLatitude, mLongitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,8.0f));

        }else{
            mGPSTracker.showSettingsAlert();
        }
    }

    public void getDirection(String origin, String destination){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Finding the safest route");
        progressDialog.show();
        Call<Path> getDirections = ApiClient.getInterface().getShortestPath(origin, destination);
        getDirections.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    ArrayList<Route> routes = response.body().getRoutes();
                    if (routes.size() == 0){
                        Toast.makeText(SafestRouteActivity.this, "No route found!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Route max_safe_route = routes.get(0);
                        double max_score = 0;
                        for (Route r : routes) {
                            if (max_score < r.getScore()) {
                                max_safe_route = r;
                                max_score = r.getScore();
                            }
                        }
                        createRoute(max_safe_route);
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(SafestRouteActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                Toast.makeText(SafestRouteActivity.this, "Directions Not found" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createRoute(Route route){
        polylines = new ArrayList<>();
        if(polylines != null){
            for(Polyline line: polylines){
                line.remove();
            }
        }
        ArrayList<Step> steps = route.getLegs().get(0).getSteps();
        for(Step step: steps) {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(step.getStartLocation().getLat(), step.getStartLocation().getLng()),
                            new LatLng(step.getEndLocation().getLat(), step.getEndLocation().getLng()))
                    .width(5)
                    .color(Color.RED));
            polylines.add(line);
        }
    }

}
