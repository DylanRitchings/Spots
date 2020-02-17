package com.dylanritchings.IOTools;

import android.content.Context;
import android.net.Uri;
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
            //StorageReference storageRef = FirebaseStorage.getReference();
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Images/"+imageId);
            imageStorageRefs.add(mStorageRef);
            //TODO create function in maps adapter that converts to uri and sets it to imageview.
        }
    }
}
