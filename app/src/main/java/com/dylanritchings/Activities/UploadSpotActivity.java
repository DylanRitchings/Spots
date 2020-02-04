package com.dylanritchings.Activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.dylanritchings.IOTools.InsertData;
import com.dylanritchings.Spots;
import com.dylanritchings.Utils.ModifiedSpinner;
import com.dylanritchings.spots.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;


public class UploadSpotActivity extends FragmentActivity{
    private static int RESULT_LOAD_IMG = 1;
    Bitmap img;
    StorageReference mStorageRef;
    public Uri imageUri;
    String galleryId;

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
        galleryId = UUID.randomUUID().toString();


        FillSpinner();
        SetListeners();
        SetLatLng();
        mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Images");
    }
    public void SetListeners(){
        final Button uploadImageBtn = findViewById(R.id.uploadImageBtn);
        uploadImageBtn.setOnClickListener(new uploadImageOnClickListener());
        ratingBars();
        configureUploadSpotBtn();

    }
    private void ratingBars(){
        final TextView difficultyTxt = (TextView) findViewById(R.id.difficultyTxt);
        final RatingBar difficultyRating = (RatingBar) findViewById(R.id.difficultyRating2);

        difficultyRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {

                difficultyTxt.setText(Float.toString(difficultyRating.getRating()));
            }
        });

        final RatingBar hostilityRating = (RatingBar) findViewById(R.id.hostilityRating2);
        final TextView hostilityTxt = (TextView) findViewById(R.id.hostilityTxt);
        hostilityRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {
                hostilityTxt.setText(Float.toString(hostilityRating.getRating()));
            }
        });
    }

    private void SetLatLng(){
        LatLng latLng = getIntent().getParcelableExtra("LAT_LNG");
        EditText latTxt = (EditText) findViewById(R.id.latTxt);
        latTxt.setText(String.valueOf(latLng.latitude));
        EditText lngTxt = (EditText) findViewById(R.id.lngTxt);
        lngTxt.setText(String.valueOf(latLng.longitude));
    }



    private void configureUploadSpotBtn(){
        Button uploadSpotBtn = (Button) findViewById(R.id.completeUploadBtn);

        //Click

        uploadSpotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadSpot();
                uploadImage();
                finish();
            }
        });
    }
    private void uploadImage(){
        final StorageReference ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imageUri));

        UploadTask uploadTask = ref.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }


    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadSpot(){
        EditText descTxt = (EditText) findViewById(R.id.descTxt);
        String desc = descTxt.getText().toString();
        Spinner typeTxt = (Spinner) findViewById(R.id.spotTypeSpinner);
        String type = typeTxt.getSelectedItem().toString();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userId = currentFirebaseUser.getUid();
        TextView imageTextView = (TextView) findViewById(R.id.imageTextView);
        TextView errorTxt = (TextView) findViewById(R.id.errorTxt);
        if (type == "Spot type"){
            errorTxt.setText("Please select the type of spot.");
        }
        else if(imageTextView.getText() == "No file uploaded"){
            errorTxt.setText("Please upload an image.");
        }
        else{
            InsertData insertData = new InsertData(this.getApplication());
            LatLng latLng = getIntent().getParcelableExtra("LAT_LNG");
            String lat = String.valueOf(latLng.latitude);
            String lng = String.valueOf(latLng.longitude);
            TextView difficultyTxt = (TextView) findViewById(R.id.difficultyTxt);

            String difficulty = difficultyTxt.getText().toString();
            TextView hostilityTxt = (TextView) findViewById(R.id.hostilityTxt);
            String hostility = hostilityTxt.getText().toString();
            insertData.UploadSpot(userId,desc,lat,lng,type,difficulty,hostility,galleryId);
        }

    }


    public class uploadImageOnClickListener  implements View.OnClickListener {
        public uploadImageOnClickListener() {
        
        }
        
        @Override
        public void onClick(View view) {
            //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
//            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
////            photoPickerIntent.setType("image/*");
////            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
////            ImageView imageView = (ImageView) findViewById(R.id.imageView);
////            imageView.setImageBitmap(BitmapFactory.decodeFile(photoPickerIntent));
             selectFile();
        }
        
    }

    private void selectFile(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent,1);

    }


    
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        
        
        if (reqCode ==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            try {
                imageUri = data.getData();
                Log.d("test",imageUri.toString());
                //img.setImageURI(imageUri);
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                img = BitmapFactory.decodeStream(imageStream);
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
    private void FillSpinner(){
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

