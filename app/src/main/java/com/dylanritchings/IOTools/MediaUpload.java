package com.dylanritchings.IOTools;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import static android.widget.Toast.LENGTH_LONG;

public class MediaUpload {
    static StorageReference mStorageRef;
    static Context context;
    static Uri uri;
    static String galleryId;
    static String fileName;

//    public static void uploadImage(String galleryId, Uri imageUri, Context contextInp){
//        context = contextInp;
//        uri = imageUri;
//        mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Images");
//        uploadFile();
//    }
//
//    public static void uploadVideo(String galleryId, Uri videoUri, Context contextInp){
//        context = contextInp;
//        uri = videoUri;
//        mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Video");
//        uploadFile();
//    }
    public static void sendFile(String galleryIdInp, Uri uriInp, Context contextInp,String fileNameInp){
        galleryId = galleryIdInp;
        uri = uriInp;
        context = contextInp;
        fileName = fileNameInp;
        setStorageRef();
    }

    private static void setStorageRef(){
        String path = uri.getPath();
        if (isImageFile(Objects.requireNonNull(path))) {
            mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Images");
            DBInsert.setGalleryId(galleryId,fileName,"img", context);

        }
        else if (isVideoFile(path)){
            mStorageRef = FirebaseStorage.getInstance().getReference(galleryId+"/Video");
            DBInsert.setGalleryId(galleryId,fileName,"vid", context);

        }
        uploadFile();
    }

    private static void uploadFile(){
        final StorageReference ref = mStorageRef.child(fileName + ".png");
        UploadTask uploadTask = ref.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {

                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                }  // Handle failures
                // ...

            }
        });
        Toast toast = Toast.makeText(context,"File uploaded successfully",LENGTH_LONG);
        toast.show();
    }
// --Commented out by Inspection START (4/23/2020 4:24 PM):
//    private static String getExtension(Uri uri){
//        ContentResolver contentResolver = context.getContentResolver();
//        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//    }
// --Commented out by Inspection STOP (4/23/2020 4:24 PM)


    private static boolean isImageFile(String path) {
//        String mimeType = URLConnection.guessContentTypeFromName(path);
//        return mimeType != null && mimeType.startsWith("image");
        return (path.contains("image"));
    }

    private static boolean isVideoFile(String path) {
//        String mimeType = URLConnection.guessContentTypeFromName(path);
//        return mimeType != null && mimeType.startsWith("video");
        return (path.contains("video"));
    }
}
