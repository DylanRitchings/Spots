package com.dylanritchings;

import android.app.Activity;
import android.view.View;
import com.dylanritchings.Activities.SpotActivity;

public class ButtonListeners extends Activity {
// --Commented out by Inspection START (4/23/2020 4:24 PM):
//// --Commented out by Inspection START (4/23/2020 4:24 PM):
////    /**
////     * TODO: Move these to other class
////     */
////
////    //MapActivity
////    public class MoreInfoOnClickListener  implements View.OnClickListener {
////
////        @Override
////        public void onClick(View view) {
////
////            Context context = Spots.getContext();
////            Intent spotInformationIntent = new Intent(context, SpotActivity.class);
// --Commented out by Inspection STOP (4/23/2020 4:24 PM)
//            context.startActivity(spotInformationIntent);
//
//
//        }
//
//    }
// --Commented out by Inspection STOP (4/23/2020 4:24 PM)
    //SpotActivity
    public static class CloseSpotInfoOnClicklistener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            SpotActivity.spotActivity.finish();
        }
    }

}
