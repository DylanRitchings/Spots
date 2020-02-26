package com.dylanritchings.IOTools;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.dylanritchings.Adapters.MapsAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MediaDownload {
    static StorageReference mStorageRef;
    static Context context;
    static Uri uri;
    static String galleryId;


    public void getTwoImages(String galleryId, ArrayList imageIds, final Context context){
        for(int i = 0; i<2; i++){
            try{
                String imageId = imageIds.get(i).toString();
                //StorageReference storageRef = FirebaseStorage.getReference();
                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                final int finalI = i;
                mStorageRef.child(galleryId + "/Images/" + imageId + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        MapsAdapter.setTwoImages(uri,context, finalI);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("Error", exception.toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //MapsAdapter.setTwoImages(imageUris,context);
        //Spot spot = spotMap.get(spotId);
    }
    public void getAllImages(){
        //TODO: FINISH THIS BOY
    }
}
