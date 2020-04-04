package com.dylanritchings.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.dylanritchings.ButtonListeners;
import com.dylanritchings.IOTools.*;
import com.dylanritchings.Spots;
import com.dylanritchings.Utils.ColorCheck;
import com.dylanritchings.spots.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

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
    String galleryId;
    static LinearLayout photoGallery;
    String userId;
    DBInsert insertData;


    /**
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

        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = currentFirebaseUser.getUid();
        insertData = new DBInsert(this.getApplication());

        ButtonListeners btnListeners = new ButtonListeners();
        final TextView closeSpotInfoTextView = findViewById(R.id.closeSpotInfoTextView);
        closeSpotInfoTextView.setOnClickListener(btnListeners.new CloseSpotInfoOnClicklistener());
        uploadMediaListener();
        galleryFiller();
        ratingListeners();
    }


    protected void ratingListeners(){
        RatingBar diffRating = (RatingBar) findViewById(R.id.difficultyRating);

        diffRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingInt =  Math.round(rating);
                insertData.setRating(userId,spotId.toString(), String.valueOf(ratingInt),"diff");
            }
        });
        RatingBar hostRating = (RatingBar) findViewById(R.id.hostilityRating);
        hostRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingInt = Math.round(rating);
                insertData.setRating(userId,spotId.toString(), String.valueOf(ratingInt),"host");
            }
        });
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

        galleryId = spotInfo.get("galleryId").toString();
        ColorCheck colorCheck = new ColorCheck();
        int color = colorCheck.getSpotColor(spotInfo.get("type").toString());



        TextView circle = (TextView) findViewById(R.id.spotTypeCircle2);
        circle.setBackgroundColor(color);
    }

    protected String getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        return address;
    }


    private void uploadMediaListener(){
        final Button uploadMediaBtn = (Button) findViewById(R.id.uploadMediaBtn);
        uploadMediaBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectFile();
            }
        });
    }
    private void selectFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
        //intent.setType("image/* video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (reqCode ==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            Uri uri = data.getData();
            Context context = getApplicationContext();
            String fileId = UUID.randomUUID().toString();
            MediaUpload.sendFile(galleryId,uri,context,fileId);

            //final InputStream imageStream = getContentResolver().openInputStream(uri);
//                img = BitmapFactory.decodeStream(imageStream);
//                //Get the file name
//                Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
//                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                returnCursor.moveToFirst();
//                fileNameTextView.setText(returnCursor.getString(nameIndex));

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    private void galleryFiller(){

        final ArrayList imageIdList = new ArrayList();
        photoGallery = (LinearLayout)findViewById(R.id.photoGalleryFill);
        DBSelect dBSelect = DBSelect.getInstance();
        final Context context = this;
        dBSelect.getImageIds(galleryId,context, new ListenerTool.SomeCustomListener<String>() {
            @Override
            public void getResult(String result) {
                //final ArrayList<Spot> spots = new ArrayList<>();
                if (!result.isEmpty()) {
                    try {
                        JSONArray array = new JSONArray(result);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject ob = array.getJSONObject(i);
                            imageIdList.add(ob.getString("imageId"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MediaDownload mediaDownload = new MediaDownload();
                    mediaDownload.getAllImages(galleryId, imageIdList, context);
                }
            }
        });
    }
    public void fillImageGallery(Uri uri){
        photoGallery.addView(insertPhoto(uri));
    }

    View insertPhoto(Uri uri){
        //Get width of device
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;


        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new LayoutParams(width, LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER);


        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new LayoutParams(width, LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageURI(uri);
        Glide.with(this)
                .load(uri)
                .into(imageView);
        layout.addView(imageView);
        return layout;
    }

//    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
//        Bitmap bm = null;
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(path, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        bm = BitmapFactory.decodeFile(path, options);
//
//        return bm;
//    }

    public int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
    }

//    public void setImage(Uri uri, Integer num){
//        ImageView spotImage;
//        if (num == 0){
//            spotImage = findViewById(R.id.spotImage1);
//        }
//        else{
//            spotImage = findViewById(R.id.spotImage2);
//        }
//        spotImage.setImageURI(uri);
//
//    }



}
