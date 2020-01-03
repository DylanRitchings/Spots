package com.dylanritchings.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dylanritchings.ButtonListeners;
import com.dylanritchings.Spots;
import com.dylanritchings.spots.R;

public class SpotActivity extends Activity {
    public static Activity spotActivity;

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

        ButtonListeners btnListeners = new ButtonListeners();
        final TextView closeSpotInfoTextView = findViewById(R.id.closeSpotInfoTextView);
        closeSpotInfoTextView.setOnClickListener(btnListeners.new CloseSpotInfoOnClicklistener());
    }
}
