//package com.example.autotutoria20;
//
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//public class b_main_0_menu_change_profile_picture extends AppCompatActivity {
//
//
//    private FirebaseAuth mAuth;
//    private void uploadImageToFirebase(Uri imageUri) {
//
//        if (imageUri != null) {
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
//                byte[] data = baos.toByteArray();
//
//                String userId = mAuth.getCurrentUser().getUid();
//                StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile_pictures/" + userId + ".jpg");
//
//                Log.d("FirebaseStorage", "Uploading image to: " + storageReference.getPath());
//                Log.d("FirebaseStorage", "Image URI: " + imageUri.toString());
//
//                UploadTask uploadTask = storageReference.putBytes(data);
//                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String imageUrl = uri.toString();
//                                Log.d("FirebaseStorage", "Image uploaded successfully. URL: " + imageUrl);
//                                saveImageUrlToFirestore(imageUrl);
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("FirebaseStorage", "Error uploading image", e);
//                        Toast.makeText(b_main_0_menu_change_profile_picture.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e("FirebaseStorage", "Error converting image to bitmap", e);
//            }
//        } else {
//            Log.e("FirebaseStorage", "Image URI is null");
//            Toast.makeText(b_main_0_menu_change_profile_picture.this, "No image selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void saveImageUrlToFirestore(String imageUrl) {
//        String userId = mAuth.getCurrentUser().getUid();
//        DocumentReference userRef = db.collection("users").document(userId);
//
//        userRef.update("profilePictureUrl", imageUrl, "hasCustomProfilePicture", true)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(b_main_0_menu_change_profile_picture.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
//                        Picasso.get().load(imageUrl).into(profileImageView);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("Firestore", "Error updating Firestore", e);
//                        Toast.makeText(b_main_0_menu_change_profile_picture.this, "Failed to update profile picture: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//
//}
