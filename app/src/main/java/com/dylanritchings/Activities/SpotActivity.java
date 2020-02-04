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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO: Change to fragment
 */
public class SpotActivity extends Activity {
    public static Activity spotActivity;
    ArrayList spotInfo;
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

        spotInfo  = (ArrayList) getIntent().getSerializableExtra("SPOT_INFO");
        Log.d("TEST",spotInfo.toString());

        try {
            fillData();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ButtonListeners btnListeners = new ButtonListeners();
        final TextView closeSpotInfoTextView = findViewById(R.id.closeSpotInfoTextView);
        closeSpotInfoTextView.setOnClickListener(btnListeners.new CloseSpotInfoOnClicklistener());
    }

    protected void fillData() throws IOException {
        spotId = Integer.parseInt( spotInfo.get(0).toString());
        type = spotInfo.get(1).toString();
        TextView spotTypeTextView = findViewById(R.id.spotTypeTextView2);
        spotTypeTextView.setText(type);


        lat = Float.valueOf(spotInfo.get(2).toString());
        lng = Float.valueOf(spotInfo.get(3).toString());
        String address = getAddress();
        TextView addressTextView = findViewById(R.id.addressTextView);
        addressTextView.setText(address);
    }

    protected String getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        Log.d("TEST", String.valueOf(lat));
        Log.d("TEST", String.valueOf(lng));
        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName();
        return address;
    }

}
