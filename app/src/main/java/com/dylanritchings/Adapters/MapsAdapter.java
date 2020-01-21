package com.dylanritchings.Adapters;

import android.content.Context;
import android.util.Log;
import com.dylanritchings.IOTools.ListenerTool;
import com.dylanritchings.IOTools.NetworkManager;
import com.dylanritchings.Models.Spot;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsAdapter {
        private HashMap<Integer, Marker> hashMapMarker = new HashMap<>();
        Context mContext;
        GoogleMap mMap;
        final ArrayList<Spot> spots = new ArrayList<>();
        public MapsAdapter(Context mContext, GoogleMap mMap){
            this.mContext = mContext;
            this.mMap = mMap;
        }
    public void getSpots(){
            NetworkManager.getInstance().getSpots(spots, new ListenerTool.SomeCustomListener<String>() {

                @Override
                public void getResult(String result) {
                    //final ArrayList<Spot> spots = new ArrayList<>();

                    if (!result.isEmpty()) {
                        try {
                            JSONArray array = new JSONArray(result);

                            for (int i = 0; i < array.length(); i++) {
                                float diff = 4;
                                float host = 3;

                                JSONObject ob = array.getJSONObject(i);
                                Spot spot = new Spot(Integer.parseInt(ob.getString("spotId"))
                                        , ob.getString("userId")
                                        , ob.getString("desc")
                                        , Float.parseFloat(ob.getString("lat"))
                                        , Float.parseFloat(ob.getString("lng"))
                                        , ob.getString("type")
                                        , diff
                                        , host);
                                spots.add(spot);

                            }
                            placeSpotMarkers(spots);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
            Log.d("TEST", spots.toString());
        }
        private void placeSpotMarkers(ArrayList<Spot> spots){

            for (Spot spot : spots){
                float lat = spot.getLat();
                float lng = spot.getLng();
                int id = spot.getSpotId();
                String type = spot.getType();
                LatLng point = new LatLng(lat, lng);
                //Create new marker
                Marker marker = mMap.addMarker(new MarkerOptions().position(point));
                switch (type) {
                    case "Skatepark":
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        break;
                    case "Stairs":
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        break;
                    case "Handrail":
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        break;
                    case "Box":
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        break;
                    case "Gap":
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                        break;
                    default:
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        break;
                }
                hashMapMarker.put(id,marker);
            }
        }
    }

