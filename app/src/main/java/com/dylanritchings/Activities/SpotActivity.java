package com.dylanritchings.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.dylanritchings.ButtonListeners;
import com.dylanritchings.IOTools.UploadFile;
import com.dylanritchings.Spots;
import com.dylanritchings.spots.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
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
    String galleryId;


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

        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ButtonListeners btnListeners = new ButtonListeners();
        final TextView closeSpotInfoTextView = findViewById(R.id.closeSpotInfoTextView);
        closeSpotInfoTextView.setOnClickListener(btnListeners.new CloseSpotInfoOnClicklistener());
        uploadMediaListener();
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (reqCode ==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            try {
                Uri uri = data.getData();
                String path = uri.getPath();
                Context context = getApplicationContext();
                if (isImageFile(path)) {
                    UploadFile.uploadImage(galleryId,uri,context);

                }
                else if (isVideoFile(path)){
                    UploadFile.uploadVideo(galleryId,uri,context);
                }
                //img.setImageURI(imageUri);
               final InputStream imageStream = getContentResolver().openInputStream(uri);
//                img = BitmapFactory.decodeStream(imageStream);
//                //Get the file name
//                Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
//                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                returnCursor.moveToFirst();
//                fileNameTextView.setText(returnCursor.getString(nameIndex));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}
