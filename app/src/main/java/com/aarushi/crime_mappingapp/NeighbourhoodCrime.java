package com.aarushi.crime_mappingapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aarushi.crime_mappingapp.API.NeighbourhoodCrimeAPI;
import com.aarushi.crime_mappingapp.Models.NeighborReport;
import com.aarushi.crime_mappingapp.Models.neghbourhood;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NeighbourhoodCrime extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker mGPSTracker;

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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.0f));



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://crime-mapping.herokuapp.com/api/")
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();
            NeighbourhoodCrimeAPI neighbourhoodCrimeAPI=retrofit.create(NeighbourhoodCrimeAPI.class);
            neighbourhoodCrimeAPI.getNeighbourCrimes(mLatitude+"",mLongitude+"").enqueue(new Callback<neghbourhood>() {
                @Override
                public void onResponse(Call<neghbourhood> call, Response<neghbourhood> response) {
                    Log.d("Retro","Success "+call.request().url());
                    Log.d("Retro","Body "+response.body());
                    Log.d("Retro","Is successfull "+response.isSuccessful());
                    Log.d("Retro","Message "+response.message());
                    Log.d("Retro","Code "+response.code());
                    Log.d("Retro","Body "+response.errorBody());
                    List<NeighborReport> neighbours= response.body().getNeighbours();

                    Log.d("Retro","Size "+neighbours.size());
                    for(int i=0;i<neighbours.size();i++){
                        NeighborReport neighborReport=neighbours.get(i);
                        LatLng crime = new LatLng(Double.parseDouble(neighborReport.getLatitude()),
                                Double.parseDouble(neighborReport.getLongitude()));
                        MarkerOptions options = new MarkerOptions()
                                .title("My Position")
                                .position(crime);
                        mMap.addMarker(options);

                    }


                }

                @Override
                public void onFailure(Call<neghbourhood> call , Throwable t) {

                    Log.d("Retro","Failure"+call.request().url());
                    Log.d("Retro","Message"+t.getMessage());
                    Log.d("Retro","To String"+t.toString());
                    Log.d("Retro","Localized message"+t.getLocalizedMessage());
                }
            });



        }else{
            mGPSTracker.showSettingsAlert();
        }
    }
}
