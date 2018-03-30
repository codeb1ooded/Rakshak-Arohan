package com.aarushi.crime_mappingapp.Models.path;

import java.util.ArrayList;

/**
 * Created by megha on 27/03/18.
 */

public class Leg {
    TextValuePair distance;
    TextValuePair duration;
    String end_address;
    LatitudeLongitude end_location;
    String start_address;
    LatitudeLongitude start_location;
    ArrayList<Step> steps;
    //ArrayList<> traffic_speed_entry;
    //ArrayList<> via_waypoint;


    public LatitudeLongitude getEndLocation() {
        return end_location;
    }

    public LatitudeLongitude getStartLocation() {
        return start_location;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
}
