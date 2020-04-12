package com.dylanritchings.Models;

import java.util.ArrayList;
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
    private Float overall;
    private String galleryId;
    private ArrayList imageRefs;
    private ArrayList videoRefs;

    public Spot(int spotId, String userId,String desc,Float lat,Float lng,String type,String galleryId) {
        //,Float diff, Float host, Float overall
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
    public void setOverall(Float overall){
        this.overall= overall;
    }

    public String getGalleryId(){
        return galleryId;
    }

    public void setImageRefs(ArrayList imageRef){
        this.imageRefs = imageRef;
    }
    public void setVideoRefs(ArrayList videoRef){
        this.videoRefs = videoRef;
    }
    public ArrayList getImageRef(){
        return imageRefs;
    }
    public ArrayList getVideoRef(){
        return videoRefs;
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
        map.put("host",getHost());
        map.put("galleryId",getGalleryId());
        map.put("imageIds",getImageRef());
        map.put("videoIds",getVideoRef());
        map.put("overall",getOverall());
        return map;

    }

    public Float getOverall() {
        return overall;
    }
}
