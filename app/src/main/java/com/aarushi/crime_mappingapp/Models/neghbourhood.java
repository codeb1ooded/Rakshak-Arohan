package com.aarushi.crime_mappingapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arushiarora on 3/26/2018.
 */

public class neghbourhood {
    @SerializedName("neghbourhood")
    public List<NeighborReport> neighbours;

    public List<NeighborReport> getNeighbours(){
        return neighbours;
    }
}
