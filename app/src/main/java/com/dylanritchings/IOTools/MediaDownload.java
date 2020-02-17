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


    public void getTwoImages(String galleryId, ArrayList imageIds){
        ArrayList imageStorageRefs = new ArrayList();
        for(int i = 0; i<2; i++){
            String imageId = imageIds.get(i).toString();
            Log.d("TEST",imageId);
            //StorageReference storageRef = FirebaseStorage.getReference();
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            mStorageRef.child(galleryId+"/Images/"+imageId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d("TEST","HELLLOOOOO");
                    //TODO not working
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Error",exception.toString());
            }
        });
            imageStorageRefs.add(mStorageRef);
            //TODO create function in maps adapter that converts to uri and sets it to imageview.
        }
        MapsAdapter.setImages(imageStorageRefs);
        //Spot spot = spotMap.get(spotId);
    }
}
