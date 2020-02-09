package com.dylanritchings.IOTools;

import android.content.Context;
import android.net.Uri;
import com.google.firebase.storage.StorageReference;

public class MediaDownload {
    static StorageReference mStorageRef;
    static Context context;
    static Uri uri;
    static String galleryId;


    public void getTwoImages(String galleryId){
//        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Images");
//        Glide.with(this /* context */)
//                .load(mStorageRef)
//                .into(imageView);
    }
}
