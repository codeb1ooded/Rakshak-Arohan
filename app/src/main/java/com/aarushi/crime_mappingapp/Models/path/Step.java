package com.aarushi.crime_mappingapp.Models.path;

/**
 * Created by megha on 27/03/18.
 */

public class Step {
    TextValuePair distance;
    TextValuePair duration;
    String html_instructions;
    String maneuver;
    PolyLine polyline;
    LatitudeLongitude start_location;
    LatitudeLongitude end_location;
    String travel_mode;

    static class PolyLine{
        String points;
    }

    public LatitudeLongitude getStartLocation() {
        return start_location;
    }

    public LatitudeLongitude getEndLocation() {
        return end_location;
    }
}
