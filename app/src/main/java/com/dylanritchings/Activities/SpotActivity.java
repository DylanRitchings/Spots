package com.dylanritchings.Activities;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.dylanritchings.ButtonListeners;
import com.dylanritchings.Spots;
import com.dylanritchings.spots.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * TODO: Change to fragment
 */
public class SpotActivity extends Activity {
    public static Activity spotActivity;
    HashMap<String, Object> spotInfo;
    Integer spotId;
    String type;
    Float lat;
    Float lng;
    Float difficulty;
    Float hostility;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
        spotActivity = this;
        Spots appState = ((Spots)getApplicationContext());
        appState.setContext(this);

        spotInfo  =(HashMap<String, Object>) getIntent().getSerializableExtra("SPOT_INFO");
        Log.d("TEST",spotInfo.toString());

        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ButtonListeners btnListeners = new ButtonListeners();
        final TextView closeSpotInfoTextView = findViewById(R.id.closeSpotInfoTextView);
        closeSpotInfoTextView.setOnClickListener(btnListeners.new CloseSpotInfoOnClicklistener());
    }

    protected void getData() throws IOException {
        spotId = Integer.parseInt( spotInfo.get("spotId").toString());
        type = spotInfo.get("type").toString();
        TextView spotTypeTextView = findViewById(R.id.spotTypeTextView2);
        spotTypeTextView.setText(type);


        lat = Float.valueOf(spotInfo.get("lat").toString());
        lng = Float.valueOf(spotInfo.get("lng").toString());
        String address = getAddress();
        TextView addressTextView = findViewById(R.id.addressTextView);
        addressTextView.setText(address);

        String galleryId = spotInfo.get("galleryId").toString();
    }

    protected String getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName();
        return address;
    }


    private void uploadMediaListener(){

    }
}
