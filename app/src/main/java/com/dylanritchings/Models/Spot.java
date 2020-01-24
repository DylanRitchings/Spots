package com.dylanritchings.Models;

public class Spot {
    private int spotId;
    private String userId;
    private String desc;
    private Float lat;
    private Float lng;
    private String type;
    private Float diff;
    private Float host;

    public Spot(int spotId, String userId,String desc,Float lat,Float lng,String type,Float diff,Float host) {
        this.spotId = spotId;
        this.userId = userId;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.diff = diff;
        this.host = host;

    }
    public int getSpotId(){return spotId;}
    public String getUserId() {
        return userId;
    }

    public String getDesc(){
        return desc;
    }
    public Float getLat(){
        return lat;
    }
    public Float getLng(){
        return lng;
    }
    public String getType(){
        return type;
    }
    public Float getDiff(){
        return diff;
    }
    public Float getHost(){
        return host;
    }

    /**
     * TODO: Implement gallery
     * @return
     */
    public String getGalleryId(){
        return null;
    }
}
