package com.dylanritchings.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.OpenableColumns;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.dylanritchings.Spots;
import com.dylanritchings.Utils.ModifiedSpinner;
import com.dylanritchings.spots.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class UploadSpotActivity extends FragmentActivity{
    private static int RESULT_LOAD_IMG = 1;

    /**
     *
     */
    public static Activity uploadSpotActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Spots appState = ((Spots)getApplicationContext());
        appState.setContext(this);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_spot);
        LatLng latLng = getIntent().getParcelableExtra("LAT_LNG");
        fillSpinner();
        setListeners();
        
        
        
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
    
    public void setListeners(){
        final Button uploadImageBtn = findViewById(R.id.uploadImageBtn);
        uploadImageBtn.setOnClickListener(new uploadImageOnClickListener());
    }
    
    
    public class uploadImageOnClickListener  implements View.OnClickListener {
        public uploadImageOnClickListener() {
        
        }
        
        @Override
        public void onClick(View view) {
            //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    
    
    
        }
        
    }
    
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        
        
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                
                //Get the file name
                Cursor returnCursor = getContentResolver().query(imageUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                TextView fileNameTextView = (TextView) this.findViewById(R.id.fileNameTextView);
                fileNameTextView.setText(returnCursor.getString(nameIndex));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                
            }
            
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    
    private void getData(){
	    //name = (EditText)findViewById(R.id.editText2);
    }
    private void fillSpinner(){
        String[] spinnerArray = new String[]{
                "Spot type",
                "Skatepark",
                "Stairs",
                "Handrail",
                "Box",
                "Bank",
                "Gap",
                "Other"
        };
    
        Spinner spotTypeSpinner = (Spinner) findViewById(R.id.spotTypeSpinner);
        //
        
        
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        spotTypeSpinner.setAdapter(
                new ModifiedSpinner(
                        adapter,
                        R.layout.spinner_disabled_selection,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spotTypeSpinner.setSelection(0, false);
        spotTypeSpinner.setAdapter(adapter);

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

