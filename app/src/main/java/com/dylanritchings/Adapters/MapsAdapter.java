package com.dylanritchings.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import com.dylanritchings.Activities.MapsActivity;
import com.dylanritchings.IOTools.DBSelect;
import com.dylanritchings.IOTools.ListenerTool;
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

public class MapsAdapter extends FragmentActivity {
    public static HashMap<Integer, Marker> singleHashMapMarker = new HashMap<>();
    public static HashMap<String, Integer> hashMapMarker = new HashMap<>();
    static Context mContext;
    GoogleMap mMap;
    CardView infoCard;
    final ArrayList<Spot> spots = new ArrayList<>();
    private HashMap<Integer, Spot> spotMap = new HashMap<Integer, Spot>();


    public MapsAdapter(Context mContext, GoogleMap mMap, CardView infoCard ) {
        this.mContext = mContext;
        this.mMap = mMap;
        this.infoCard = infoCard;
    }

    public void getSpots() {
        DBSelect dBSelect = DBSelect.getInstance();
        dBSelect.getSpots(spots, new ListenerTool.SomeCustomListener<String>() {

            @Override
            public void getResult(String result) {
                //final ArrayList<Spot> spots = new ArrayList<>();
                if (!result.isEmpty()) {
                    try {
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject ob = array.getJSONObject(i);
                            Spot spot = new Spot(Integer.parseInt(ob.getString("spotId"))
                                    , ob.getString("userId")
                                    , ob.getString("desc")
                                    , Float.parseFloat(ob.getString("lat"))
                                    , Float.parseFloat(ob.getString("lng"))
                                    , ob.getString("type")
                                    ,ob.getString("galleryId"));
                            spots.add(spot);

                        }
                        placeSpotMarkers(spots);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
    public static void getImages(String galleryId){
        DBSelect dBSelect = DBSelect.getInstance();
        final ArrayList imageIdList = new ArrayList();

        dBSelect.getImageIds(galleryId,mContext, new ListenerTool.SomeCustomListener<String>(){
            @Override
        public void getResult(String result) {
                Log.d("HELLO",result);
            //final ArrayList<Spot> spots = new ArrayList<>();
            if (!result.isEmpty()) {
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob = array.getJSONObject(i);
                        imageIdList.add(ob.getString("imageId"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        });
        Log.d("TEST", String.valueOf(imageIdList));
    }
    public void getRatings(String spotId)
    {
        DBSelect dBSelect = DBSelect.getInstance();
        dBSelect.getRatings(spotId,new ListenerTool.SomeCustomListener<String>() {

            @Override
            public void getResult(String result) {

                //final ArrayList<Spot> spots = new ArrayList<>();
                if (!result.isEmpty()) {
                    try {
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            //TODO: FINISH
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    private void placeSpotMarkers(ArrayList<Spot> spots) {
        for (Spot spot : spots) {

            float lat = spot.getLat();
            float lng = spot.getLng();
            int id = spot.getSpotId();

            spotMap.put(id,spot);
            String type = spot.getType();
            LatLng point = new LatLng(lat, lng);
            //Create new marker
            Log.d("MARKER", new MarkerOptions().position(point).toString());
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

            hashMapMarker.put(marker.getId(),id);
            //MapsActivity.hashMapMarker = hashMapMarker;

        }

        //MapsActivity.setMarkerListener(hashMapMarker);
    }


//    public void setMarkerListener(final HashMap hashMapMarker) {
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                fillInfoCard(hashMapMarker,marker);
//                return false;
//            }
//        });
//    }

    public void createNewSpotMarker(LatLng point){
        Marker originalMarker = singleHashMapMarker.get(-1);

        if (originalMarker != null){

            originalMarker.remove();

            singleHashMapMarker.remove(-1);
        }
        infoCard.setVisibility(View.GONE);
        //Create new marker
        Marker marker = mMap.addMarker(new MarkerOptions().position(point));
        singleHashMapMarker.put(-1, marker);
        MapsActivity.uploadSpotBtn.setVisibility(View.VISIBLE);

    }

    public HashMap<String,Object> getSpotInfo(Marker marker){
        //ArrayList<String> infoList = new ArrayList<String>()  ;
        HashMap<String,Object> infoList = new HashMap<String, Object>();
        String markerId = marker.getId();
        if(hashMapMarker.containsKey(markerId)){


            Integer spotId = hashMapMarker.get(markerId);

            //Remove placed marker
            Marker originalMarker = singleHashMapMarker.get(-1);
            if(originalMarker != null) {
                originalMarker.remove();
            }
            singleHashMapMarker.remove(-1);
            Spot spot = spotMap.get(spotId);
            //String desc = spot.getDesc();
            infoList = spot.getSpotMap();
            if (infoList.get("diff") == null || infoList.get("host") == null){
                getRatings(String.valueOf(spotId));

            }



            MapsActivity.uploadSpotBtn.setVisibility(View.GONE);
        }
        return infoList;
    }
    public static HashMap<String, Integer> getHashMapMarker(){
        return hashMapMarker;
    }
}

