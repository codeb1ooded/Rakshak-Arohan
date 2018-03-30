package com.aarushi.crime_mappingapp.Models.path;

import java.util.ArrayList;

/**
 * Created by megha on 27/03/18.
 */

public class Route {
    Bounds bounds;
    String copyrights;
    ArrayList<Leg> legs;
    Polyline overview_polyline;
    String summary;
    double score;
    //ArrayList<> warnings;
    //ArrayList<> waypoint_order;

    static class Polyline{
        String points;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public double getScore() {
        return score;
    }
}
