package com.dylanritchings.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.dylanritchings.Adapters.MapsAdapter;
import com.dylanritchings.Models.Spot;
import com.dylanritchings.Spots;
import com.dylanritchings.Utils.ColorCheck;
import com.dylanritchings.spots.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        //GoogleApiClient.ConnectionCallbacks,
        // GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //Map variables
    private GoogleMap mMap;
// --Commented out by Inspection START (4/23/2020 4:24 PM):
//    //private GoogleApiClient googleApiClient;
// --Commented out by Inspection START (4/23/2020 4:24 PM):
////    private GoogleSignInClient signInClient;
//// --Commented out by Inspection STOP (4/23/2020 4:24 PM)
// --Commented out by Inspection STOP (4/23/2020 4:24 PM)
    // --Commented out by Inspection (4/23/2020 4:24 PM):private LocationListener locationListener;
    private static final String TAG = "MapsActivity";
    // --Commented out by Inspection (4/23/2020 4:24 PM):private Marker currentUserLocationMarker;
    private int Request_User_Location_Code = 99;
    //public static HashMap<S// --Commented out by Inspection (4/23/2020 4:24 PM):tring, Integer> hashMapMarker = new HashMap<>();
    FusedLocationProviderClient fusedLocationClient;
    public static Activity MapsActivity;
    public ArrayList<Spot> spots;
    public static Button uploadSpotBtn;
    public CardView infoCard;
    private HashMap<String,Object> spotInfo;
    private Location currentLocation;
    TextView tvDistanceDuration;
    //TextView spotTypeTextView = (TextView) findViewById(R.id.spotTypeTextView);
    MapsAdapter mapsAdapter;

    /**
     * TODO: Current issues with whole application:
     * - When image is uploaded to firebase there is no check to see if the database IDs have been input and vice versa.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        infoCard = findViewById(R.id.infoCard);
        infoCard.setVisibility(View.GONE);
        //mapsAdapter = new MapsAdapter(this,mMap,infoCard);
        Spots appState = ((Spots) getApplicationContext());
        appState.setContext(this);
        tvDistanceDuration = (TextView) findViewById(R.id.driveTimeTextView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Objects.requireNonNull(mapFragment).getMapAsync(this);




    }
    
    /**
     *
     */
    private void setListeners() {
        //TODO all listener to this activity
    //uploadSpotBtn
        uploadSpotBtn = (Button) findViewById(R.id.uploadSpotBtn);
        uploadSpotBtn.setOnClickListener(new uploadSpotOnClickListener());

    }


    private void placeSpotMarkers(){

        mapsAdapter.getSpots();
        setMarkerListener();
    }


    /**
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        boolean mapReady = true;
        mMap = googleMap;
        mapsAdapter = new MapsAdapter(this,mMap,infoCard);
        setListeners();

        //Users current location permission Check
        if (ContextCompat. checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            getLocation();
            createMapClickListener();

            placeSpotMarkers();


        }


    }

    /**
     *
     */
//    protected synchronized void buildGoogleApiClient() {
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();

    // googleApiClient.connect();
//    }
    public void getLocation() {
        //MapsAdapter mapsAdapter;
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                currentLocation = location;
                                setLocationMarker(location);
                            }

                        }
                    });
        }


    }

//
//    public void createLocationListener() {
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // notTODO: Consider calling
//            //    Activity#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for Activity#requestPermissions for more details.
//            return;
//        }
//
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        final LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                double longitude = location.getLongitude();
//                double latitude = location.getLatitude();
//            }
//        };
//
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, (android.location.LocationListener) locationListener);
//
//
//    }
    /**
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng point = new LatLng(lat, lng);
        mapsAdapter.createNewSpotMarker(point);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
//        if (googleApiClient != null) {
//            //LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//            LocationServices.getFusedLocationProviderClient(this);
//
//        }

    }
    /**
     *
     * @param location
     */
    public void setLocationMarker(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng point = new LatLng(lat, lng);
        mapsAdapter.createNewSpotMarker(point);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        //mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
    }


    /**
     *
     */
    public void checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
        } else {
        }
    }

    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request_User_Location_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    /**
     * Creates a click listener on the map
     */
    public void createMapClickListener(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mapsAdapter.createNewSpotMarker(point);
            }
        });
    }

//    public void createNewSpotMarkerOld(LatLng point){
//        Marker originalMarker = hashMapMarker.get(-1);
//        if (originalMarker != null){
//            originalMarker.remove();
//            hashMapMarker.remove(-1);
//        }
//
//        //Create new marker
//        Marker marker = mMap.addMarker(new MarkerOptions().position(point));
//        hashMapMarker.put(-1,marker);
//    }
    /**
     *
     * TODO: Maybe move this to another class
     */
    public static class uploadSpotOnClickListener  implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            HashMap<Integer, Marker> singleHashMapMarker = MapsAdapter.singleHashMapMarker;
            Marker marker = singleHashMapMarker.get(-1);
            LatLng latLng = Objects.requireNonNull(marker).getPosition();
            Context context = Spots.getContext();
            Intent uploadSpotIntent = new Intent(context, UploadSpotActivity.class);
            uploadSpotIntent.putExtra("LAT_LNG", latLng);
            context.startActivity(uploadSpotIntent);

            

        }

    }

    public void openSpotActivity(HashMap<String, Object> spotInfo){
        Intent intent = new Intent(MapsActivity.this, SpotActivity.class);
        RatingBar diffRatingBar = findViewById(R.id.smallDifficultyRating);
        RatingBar hostRatingBar = findViewById(R.id.smallHostilityRating);
        spotInfo.put("diff",diffRatingBar.getRating());
        spotInfo.put("host",hostRatingBar.getRating());
        intent.putExtra("SPOT_INFO", spotInfo);
        startActivity(intent);
    }

    private void setMoreInfoButtonListener(final HashMap<String, Object> spotInfo){
        final Button moreInfoBtn = findViewById(R.id.moreInfoBtn);
        moreInfoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSpotActivity(spotInfo);
            }
        });

    }
    public void getSpotInfo(Marker marker){
        //hashMapMarker = mapsAdapter.hashMapMarker;
        spotInfo = mapsAdapter.getSpotInfo(marker);
        setMoreInfoButtonListener(spotInfo);
        //String type =  spotInfo.get(0);
        //spotTypeTextView.setText(spotInfo.get(0));
        //infoCard.setVisibility(View.VISIBLE);
    }

    public void setImages(HashMap<String, Object> spotInfo){
        ImageView spotImage1 = (ImageView) findViewById(R.id.spotImage1);
        ImageView spotImage2 = (ImageView) findViewById(R.id.spotImage2);
        spotImage1.setImageDrawable(getResources().getDrawable(R.drawable.blank));
        spotImage2.setImageDrawable(getResources().getDrawable(R.drawable.blank));
        mapsAdapter.getImages(Objects.requireNonNull(spotInfo.get("galleryId")).toString());
    }
    public void fillSpotInfoText(){
        TextView spotTypeTextView = (TextView) findViewById(R.id.spotTypeTextView);
        String spotId = Objects.requireNonNull(spotInfo.get("spotId")).toString();
        spotTypeTextView.setText(Objects.requireNonNull(spotInfo.get("type")).toString());
        ColorCheck colorCheck = new ColorCheck();
        int color = colorCheck.getSpotColor(Objects.requireNonNull(spotInfo.get("type")).toString());
//        Drawable unwrappedDrawable = AppCompatResources.getDrawable(Spots.getContext(), R.drawable.circle).mutate();
//        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//        DrawableCompat.setTint(wrappedDrawable, Color.alpha(color));



        TextView circle = (TextView) findViewById(R.id.spotTypeCircle);
        circle.setBackgroundColor(color);
        //circle.setHighlightColor(color);

//        String diff = spotInfo.get(2).toString();
//        String host = spotInfo.get(3).toString();
        //mapsAdapter.getRatings(spotId);


//        Float lat = (Float) Float.parseFloat(spotInfo.get(1).toString());
//        Float lng = (Float) Float.parseFloat(spotInfo.get(2).toString());
//        getShortestTime(lat,lng);
// --Commented out by Inspection START (4/23/2020 4:24 PM):
        infoCard.setVisibility(View.VISIBLE);
//    }
//
//    private void getShortestTime(Float lat, Float lng){
//        LatLng origin = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
//        LatLng dest = new LatLng(lat, lng);
//
//        Object[] dataTransfer = new Object[2];
//        // Getting URL to the Google Directions API
//        String url = getDirectionsUrl(origin, dest);
//        dataTransfer[0] = mMap;
//        dataTransfer[1] = url;
//        DownloadTask downloadTask = new DownloadTask();
//
//        // Start downloading json data from Google Directions API
// --Commented out by Inspection STOP (4/23/2020 4:24 PM)
//        downloadTask.execute(dataTransfer);
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service

        return "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
    }
    public void setMarkerListener() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onSpotInfoOpen(marker);

                return false;
            }
        });
    }

    private void onSpotInfoOpen(Marker marker){
        HashMap<String, Integer> hashMapMarker = MapsAdapter.getHashMapMarker();
        String markerId = marker.getId();
        if(hashMapMarker.containsKey(markerId)) {

            getSpotInfo(marker);

            fillSpotInfoText();
            setImages(spotInfo);
            //RatingAdapter ratingAdapter = RatingAdapter.getInstance();

            String spotId = Objects.requireNonNull(spotInfo.get("spotId")).toString();
            mapsAdapter.getRatingsArray(spotId);

            //RatingBar overallRatingBar = findViewByID(R.id.overallRating)
//            int count = 0;
//            int maxTries = 5;
//            float diffRating = 0;
//            float hostRating = 0;
//            while(true) {
//                try {
//                    break;
//                } catch (Exception e){
//                    if (++count == maxTries) throw e;
//                }
//            }
//            diffRatingBar.setRating(diffRating);
//            hostRatingBar.setRating(hostRating);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        try{
            mMap.clear();
            onMapReady(mMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}



