package com.dylanritchings;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dylanritchings.Fragments.MapFragment;
import com.dylanritchings.spots.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//
//public class MapFiller {
//
//    public void check(MapFragment map, Context currentContext){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkUserLocationPermission();
//        }
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//
//        mapFragment.getMapAsync(currentContext);
//
//
//    }
//    /**
//     *
//     * @param googleMap
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        //Users current location permission Check
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//            //configureStartTrackingBtn();
//            return;
//        }
//
//    }
//
//    /**
//     *
//     */
//    protected synchronized void buildGoogleApiClient() {
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        googleApiClient.connect();
//    }
//
//    /**
//     *
//     * @param bundle
//     */
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(1100);
//        locationRequest.setFastestInterval(1100);
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//        }
//    }
//
//    /**
//     *
//     * @param i
//     */
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    /**
//     *
//     * @param location
//     */
//    @Override
//    public void onLocationChanged(Location location) {
//        if (currentUserLocationMarker != null) {
//            currentUserLocationMarker.remove();
//        }
//        double lat = location.getLatitude();
//        double lng = location.getLongitude();
//        LatLng latLng = new LatLng(lat, lng);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("user Current Location");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
//
//        currentUserLocationMarker = mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
//        if (googleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//
//        }
//
//    }
//
//    /**
//     *
//     * @return
//     */
//    public boolean checkUserLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    /**
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case Request_User_Location_Code:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        if (googleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        mMap.setMyLocationEnabled(true);
//                    }
//                } else {
//                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
//                }
//                return;
//        }
//    }
//
//    /**
//     *
//     * @param connectionResult
//     */
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//}
