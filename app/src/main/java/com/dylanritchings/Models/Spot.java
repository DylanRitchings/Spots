package com.dylanritchings.Models;

import java.util.HashMap;

public class Spot {
    private int spotId;
    private String userId;
    private String desc;
    private Float lat;
    private Float lng;
    private String type;
    private Float diff;
    private Float host;
    private String galleryId;

    public Spot(int spotId, String userId,String desc,Float lat,Float lng,String type,String galleryId) {
        this.spotId = spotId;
        this.userId = userId;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.galleryId = galleryId;

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

    public void setDiff(Float diff){
        this.diff = diff;
    }
    public void setHost(Float host){
        this.host = host;
    }

    public String getGalleryId(){
        return galleryId;
    }

    public HashMap<String,Object> getSpotMap(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("spotId",getSpotId());
        map.put("userId",getUserId());
        map.put("desc",getDesc());
        map.put("lat",getLat());
        map.put("lng",getLng());
        map.put("type",getType());
        map.put("diff",getDiff());
        map.put("galleryId",getGalleryId());
        return map;

    }
}
