package com.dylanritchings.Models;

import java.util.ArrayList;

public class SpotList {
    private ArrayList<Spot> spots;

    public SpotList(ArrayList<Spot> spots){
        this.spots = spots;
    }

    public ArrayList<Spot> getSpots(){
        return spots;
    }
}
