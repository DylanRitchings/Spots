package com.dylanritchings.spots;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dylanritchings.spots.Activities.SpotActivity;

public class ButtonListeners extends Activity {


    //MapActivity
    public class MoreInfoOnClickListener  implements View.OnClickListener {
        public void MoreInfoOnClickListener() {

        }

        @Override
        public void onClick(View view) {

            Context context = MyApp.getContext();
            Intent spotInformationIntent = new Intent(context, SpotActivity.class);
            context.startActivity(spotInformationIntent);


        }

    }
    //SpotActivity
    public class CloseSpotInfoOnClicklistener implements View.OnClickListener{
        public void CloseSpotinfoOnClickListner(){

        }
        @Override
        public void onClick(View view){
            SpotActivity.spotActivity.finish();
        }
    }

}
