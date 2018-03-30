package com.aarushi.crime_mappingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aarushi.crime_mappingapp.API.ReportFilingAPI;
import com.aarushi.crime_mappingapp.Models.CrimeLocation;
import com.aarushi.crime_mappingapp.Models.Report;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportFileActivity extends AppCompatActivity {
    EditText _name,_aadharcard,_phone,_crimetype,_latitude,_longitude,_crime_description,_date_crime,_time_crime,_complaint_time,
    _complaint_date,_complaint_by,_location,_fir_location,_status;
    Button btn_submit;
    String name,aadharcard,phone,crimetype,latitude,longitude,crime_description,date_crime,time_crime,complaint_time,
    complaint_date,complaint_by,location,fir_location,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_file);
        _name=(EditText)findViewById(R.id.name1);
        _aadharcard=(EditText)findViewById(R.id.aadhaar);
        _phone=(EditText)findViewById(R.id.phone);
        _crimetype=(EditText)findViewById(R.id.crimeType);
        _latitude=(EditText)findViewById(R.id.latCrime);
        _longitude=(EditText)findViewById(R.id.longCrime);
        _crime_description=(EditText)findViewById(R.id.describeCrime);
        _date_crime=(EditText)findViewById(R.id.dateCrime);
        _time_crime=(EditText)findViewById(R.id.timeCrime);
        _complaint_time=(EditText)findViewById(R.id.complaintTime);
        _complaint_date=(EditText)findViewById(R.id.complaintDate);
        _location=(EditText)findViewById(R.id.location);
        _complaint_by=(EditText)findViewById(R.id.complaintBy);
        _fir_location=(EditText)findViewById(R.id.firLocation);
        _status=(EditText)findViewById(R.id.status);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=_name.getText().toString();
                aadharcard=_aadharcard.getText().toString();
                phone=_phone.getText().toString();
                crimetype=_crimetype.getText().toString();
                latitude=_latitude.getText().toString();
                longitude=_longitude.getText().toString();
                crime_description=_crime_description.getText().toString();
                date_crime=_date_crime.getText().toString();
                time_crime=_time_crime.getText().toString();
                complaint_time=_complaint_time.getText().toString();
                complaint_date=_complaint_date.getText().toString();
                location=_location.getText().toString();
                complaint_by=_complaint_by.getText().toString();
                fir_location=_fir_location.getText().toString();
                status=_status.getText().toString();

                Report report=new Report(name,aadharcard,phone,crimetype,latitude,longitude,crime_description,date_crime,
                        time_crime,complaint_time);




                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://crime-mapping.herokuapp.com/api/")
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        )
                        .build();

                Log.d("Retro",retrofit.toString());

                ReportFilingAPI reportfilingAPI=retrofit.create(ReportFilingAPI.class);

                Log.d("Retro",reportfilingAPI.toString());


                ReportFilingAPI reportFilingAPI=retrofit.create(ReportFilingAPI.class);
                reportfilingAPI.fileReport(name,
                        aadharcard,
                        phone,
                        crimetype,
                        latitude,
                        longitude,
                        crime_description,
                        date_crime,
                        time_crime,
                        complaint_time,
                        complaint_date,
                        location

                       ).enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("Retro","Success"+call.request().url());
                        Log.d("Retro","Body"+response.body());
                        Log.d("Retro","IS successfull"+response.isSuccessful());
                        Log.d("Retro","Message"+response.message());
                        Log.d("Retro","Code"+response.code());
                        Log.d("Retro","Body"+response.errorBody());
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.d("Retro","Failure"+call.request().url());
                    }
                });
            }
        });
    }
}
