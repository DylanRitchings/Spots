package com.dylanritchings.Activities;


import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dylanritchings.IOTools.GetData;
import com.dylanritchings.Utils.AuthPreferences;
import com.dylanritchings.spots.R;

public class CheckLoginActivity extends AppCompatActivity {
    private AuthPreferences authPreferences;
    private AccountManager accountManager;
    @Override
/**
 *
 */
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        GetData.getInstance(this);
        Intent activityIntent;
        AccountManager am = AccountManager.get(this); // "this" references the current Context
        authPreferences = new AuthPreferences(this);


        // go straight to main if a token is stored
        if (authPreferences.getUser() != null && authPreferences.getToken() != null ) {
            activityIntent = new Intent(this, MapsActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }
}
