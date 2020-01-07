package com.dylanritchings.Activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.dylanritchings.Spots;
import com.dylanritchings.spots.R;


public class UploadSpotActivity extends FragmentActivity{


    /**
     *
     */
    public static Activity uploadSpotActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Spots appState = ((Spots)getApplicationContext());
        appState.setContext(this);

        //map
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_spot);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkUserLocationPermission();
//        }
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.addressMapView);

       // mapFragment.getMapAsync(this);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_upload_spot);
        //uploadSpotActivity = this;
        //Spots appState = ((Spots)getApplicationContext());
        //appState.setContext(this);

        //createMap( savedInstanceState);

    }







//
//    private OnClickListener onClickListener = new OnClickListener() {
//
//        @Override
//        public void onClick(final View v) {
//            switch(v.getId()){
//                case R.id.button1:
//                    //DO something
//                    break;
//                case R.id.button2:
//                    //DO something
//                    break;
//            }
//
//        }
//    };
}

