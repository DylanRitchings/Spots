package com.dylanritchings.IOTools;

import android.graphics.Bitmap;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageFetcher {
	private FirebaseStorage storage;
	private StorageReference storageRef;
	private StorageReference storageTypeRef;
	
	private void firebaseConnect(int spotId, String fileType) {
		FirebaseStorage storage = FirebaseStorage.getInstance("gs://spots-57c96.appspot.com");
		StorageReference storageRef = storage.getReference();
		StorageReference storageTypeRef = storageRef.child(spotId+"/"+fileType+"/1.jpg");
		
	}
	
	public void uploadPhoto(int spotId, Bitmap bitmap) {
		firebaseConnect(spotId, "images");
		
	}
	
	public void uploadVideo(int spotId) {
	
	}
	
	public void uploadMultiple(int spotId){
	
	}
}
