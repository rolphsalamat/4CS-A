package com.example.autotutoria20;

import static android.app.PendingIntent.getActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class b_main_0_menu extends AppCompatActivity {
    private boolean[] cardStates = {true, false, false}; // Example state array for 3 cards
    private static final int PICK_IMAGE_REQUEST = 1;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager viewPager;
    private CustomPagerAdapter pagerAdapter;
    private static TextView greetUserName;
    private static TextView greetUserCategory;
    private List<Fragment> progressiveFragmentList;
    private List<Fragment> freeUseFragmentList;
    private long backPressedTime;
    private Button increment_progress;
    private View module, description, uplifts;
    private ShapeableImageView profileImageView;
    private FrameLayout profileFrameLayout;
    private CustomLoadingDialog loadingDialog;
    private boolean isProgressiveMode = true;
    private TextView learningModeText;
    private ImageView learningModeIcon;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private String TAG = "Main Menu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hello", "word");
        setContentView(R.layout.b_main_0_menu);

        if (!n_Network.isNetworkAvailable(this)) {
            Toast.makeText(b_main_0_menu.this, "No Internet", Toast.LENGTH_SHORT).show();
        }

//        showLoadingDialog();

        // Instantiate x_bkt_algorithm
        x_bkt_algorithm algorithm = new x_bkt_algorithm();

        // Retrieve user category
        algorithm.retrieveUserCategory(new x_bkt_algorithm.FetchCategoryCallback() {
            @Override
            public void onCategoryFetched(String category) {
                // Handle the retrieved category here
                if (category != null) {
                    Log.d("MainActivity", "User Category: " + category);
                    // You can now use the category for further processing

                    b_main_0_menu_categorize_user.category = category;
                    b_main_0_menu_categorize_user.passingCategory(category);
                    x_bkt_algorithm.setBKTCategory(category);

                } else {
                    Log.d("MainActivity", "Failed to retrieve user category.");
                }
            }
        });

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Retrieve user session data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Log.e(TAG, "isLoggedIn: " + isLoggedIn);
//            FirebaseUser currentUser = mAuth.getCurrentUser();
            // Check if the tutorial is completed
            a_user_1_login_handler.checkTutorialCompletion(new a_user_1_login_handler.TutorialCompletionCallback() {
                @Override
                public void onTutorialChecked(boolean isComplete) {
                    Log.e(TAG, "checkTutorialCompletion(): " + isComplete);
                    if (isComplete) {
                        // The tutorial has been completed, proceed with fetching user data or whatever comes next
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            String userId = currentUser.getUid();
                            fetchUserData(userId);
                        }
                    } else {
                        // The tutorial has not been completed, show the tutorial
                        showTutorial();
                    }
                }
            });
        } else {
            // User is not logged in, redirect to login screen
            redirectToLogin();
        }



        Log.d("hello", "allyzza");

        learningModeText = findViewById(R.id.learning_mode_text);
        learningModeIcon = findViewById(R.id.learning_mode_icon);

        // Initialize the NavigationView and its header view
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        greetUserName = headerView.findViewById(R.id.user_firstName);
        greetUserCategory = headerView.findViewById(R.id.user_category);
        profileImageView = headerView.findViewById(R.id.user_profile_picture); // Initialize profileImageView here

        // Initialize ViewPager
        viewPager = findViewById(R.id.view_pager);

        if (progressiveFragmentList == null) {
            progressiveFragmentList = new ArrayList<>();
            progressiveFragmentList.add(new b_main_1_lesson_progressive());
            progressiveFragmentList.add(new b_main_3_description());
//            progressiveFragmentList.add(new b_main_4_uplifts());
        }

        if (freeUseFragmentList == null) {
            freeUseFragmentList = new ArrayList<>();
            freeUseFragmentList.add(new b_main_2_lesson_freeuse());
            freeUseFragmentList.add(new b_main_3_description());
//            freeUseFragmentList.add(new b_main_4_uplifts());
        }

        pagerAdapter = new CustomPagerAdapter(
                getSupportFragmentManager(), progressiveFragmentList);
        viewPager.setAdapter(pagerAdapter);

        // Button highlighter for Header
        module = findViewById(R.id.modulesSelected);
        description = findViewById(R.id.descriptionSelected);
//        uplifts = findViewById(R.id.upliftSelected);

        // OPEN NAVIGATION DRAWER
        Button toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
        toggleDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


//        TextView frequentlyAskedQuestions = findViewById(R.id.faq);
//        frequentlyAskedQuestions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("HELLO WORLD", "START FAQ");
//                startActivity(new Intent(b_main_0_menu.this, a_user_4_FAQ.class));
//                Log.e("HELLO WORLD", "FAQ | What happened!??!!");
//            }
//        });

        // NAVIGATION DRAWER BUTTONS
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Check if the ActionBar is not null before accessing it
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.profile) {
                    startActivity(new Intent(b_main_0_menu.this, b_main_0_menu_profile.class));
                } else if (id == R.id.settings) {
                    Log.e("NavigationView", "Let's open Settings");
                    Toast.makeText(b_main_0_menu.this, "Settings", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(b_main_0_menu.this, b_main_0_menu_settings.class));
                } else if (id == R.id.progressive_mode) {
                    showSwitchModeDialog("Progressive Mode");
                } else if (id == R.id.free_use_mode) {
                    showSwitchModeDialog("Free Use Mode");
                } else if (id == R.id.rate) {
                    showRateUsDialog();
                } else if (id == R.id.follow) {
                    openFacebookPage();
                } else if (id == R.id.faq) {
                    startActivity(new Intent(b_main_0_menu.this, a_user_4_FAQ.class));
                }else if (id == R.id.log_out) {
                    showLogoutDialog();
                }
                return false;
            }
        });

        // SHOW MODULE FRAGMENT
        Button modulesButton = findViewById(R.id.module_button);
        modulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                module.setBackgroundColor(Color.WHITE);
                description.setBackgroundColor(Color.TRANSPARENT);
//                uplifts.setBackgroundColor(Color.TRANSPARENT);
                viewPager.setCurrentItem(0);
            }
        });

        // SHOW DESCRIPTION FRAGMENT
        Button descriptionButton = findViewById(R.id.description_button);
        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                module.setBackgroundColor(Color.TRANSPARENT);
                description.setBackgroundColor(Color.WHITE);
//                uplifts.setBackgroundColor(Color.TRANSPARENT);
                viewPager.setCurrentItem(1);
            }
        });

//        // SHOW UPLIFTS FRAGMENT
//        Button upliftsButton = findViewById(R.id.uplifts_button);
//        upliftsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                module.setBackgroundColor(Color.TRANSPARENT);
//                description.setBackgroundColor(Color.TRANSPARENT);
//                uplifts.setBackgroundColor(Color.WHITE);
//                viewPager.setCurrentItem(2);
//            }
//        });

        // Add ViewPager OnPageChangeListener to update button highlights
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    int position,
                    float positionOffset,
                    int positionOffsetPixels
            )
            {
                // No action needed here
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        module.setBackgroundColor(Color.WHITE);
                        description.setBackgroundColor(Color.TRANSPARENT);
//                        uplifts.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case 1:
                        module.setBackgroundColor(Color.TRANSPARENT);
                        description.setBackgroundColor(Color.WHITE);
//                        uplifts.setBackgroundColor(Color.TRANSPARENT);
                        break;
//                    case 2:
//                        module.setBackgroundColor(Color.TRANSPARENT);
//                        description.setBackgroundColor(Color.TRANSPARENT);
//                        uplifts.setBackgroundColor(Color.WHITE);
//                        break;
                    default:
                        Log.e("onPageSelected(" + position +")", "Error!");
                }

//                if (position == 0) {
//                    description.setBackgroundColor(Color.TRANSPARENT);
//                    module.setBackgroundColor(Color.WHITE);
//                } else if (position == 1) {
//                    module.setBackgroundColor(Color.TRANSPARENT);
//                    description.setBackgroundColor(Color.WHITE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // No action needed here
            }
        });

        // Initialize profile image view and frame layout
        profileFrameLayout = headerView.findViewById(R.id.profile_frame_layout);

        profileFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

//    private void showLoadingDialog() {
//        loadingDialog = new CustomLoadingDialog();
//        loadingDialog.setCancelable(false); // Prevent closing the dialog
//        loadingDialog.show();
//    }
//
//    private void updateProgress(int progress) {
//        if (loadingDialog != null) {
//            loadingDialog.setProgress(progress);
//        }
//    }
//
//    private void hideLoadingDialog() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }



    private void showTutorial() {
        Log.d("b_main_0_menu", "Showing tutorial because loginAttempts is 0");
        Intent tutorialIntent = new Intent(this, b_main_0_menu_tutorial.class);
        startActivity(tutorialIntent);
        finish();
    }

//    private void moveToMainMenu(String firstName, String lastName, String email, String gender, int age, int loginAttempts) {
//        Intent intent = new Intent(this, b_main_0_menu.class);
//        intent.putExtra("firstName", firstName);
//        intent.putExtra("lastName", lastName);
//        intent.putExtra("email", email);
//        intent.putExtra("gender", gender);
//        intent.putExtra("age", age);
//        intent.putExtra("loginAttempts", loginAttempts);
//        startActivity(intent);
//        finish(); // Finish the login activity
//    }

    private void redirectToLogin() {
        Intent loginIntent = new Intent(this, a_user_1_login.class);
        startActivity(loginIntent);
        finish();
    }

//    private void redirectToLogin() {
//        Intent loginIntent = new Intent(this, a_user_1_login.class);
//        startActivity(loginIntent);
//        finish();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri sourceUri = data.getData();
            Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg"));

            // Start uCrop activity
            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(512, 512)
                    .start(this);
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                imageUri = resultUri;
                uploadImageToFirebase();  // Upload the cropped image
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, "Crop error: " + cropError, Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] data = baos.toByteArray();

                String userId = mAuth.getCurrentUser().getUid();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile_pictures/" + userId + ".jpg");

                Log.d("FirebaseStorage", "Uploading image to: " + storageReference.getPath());
                Log.d("FirebaseStorage", "Image URI: " + imageUri.toString());

                UploadTask uploadTask = storageReference.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                Log.d("FirebaseStorage", "Image uploaded successfully. URL: " + imageUrl);
                                saveImageUrlToFirestore(imageUrl);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseStorage", "Error uploading image", e);
                        Toast.makeText(b_main_0_menu.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("FirebaseStorage", "Error converting image to bitmap", e);
            }
        } else {
            Log.e("FirebaseStorage", "Image URI is null");
            Toast.makeText(b_main_0_menu.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveImageUrlToFirestore(String imageUrl) {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.update("profilePictureUrl", imageUrl, "hasCustomProfilePicture", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(b_main_0_menu.this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                        Picasso.get().load(imageUrl).into(profileImageView);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error updating Firestore", e);
                        Toast.makeText(b_main_0_menu.this, "Failed to update profile picture: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void showRateUsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_0_menu_rateus, null);
        builder.setView(dialogView);

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        Button submitButton = dialogView.findViewById(R.id.submit_button);

        AlertDialog dialog = builder.create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Toast.makeText(b_main_0_menu.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.b_main_0_menu_logout, null);
        builder.setView(dialogView);

        Button cancelButton = dialogView.findViewById(R.id.button_cancel);
        Button logoutButton = dialogView.findViewById(R.id.button_logout);

        AlertDialog dialog = builder.create();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Dismiss the dialog without action
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user session data
                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Clear all session data
                editor.apply();

                Toast.makeText(b_main_0_menu.this, "Logout Successful", Toast.LENGTH_SHORT).show();

                // Dismiss the dialog
                dialog.dismiss();

                // Redirect to login screen
                Intent loginIntent = new Intent(b_main_0_menu.this, a_user_1_login.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);

                // Finish the current activity
                finish();
            }
        });

        // Set the dialog window attributes
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                // Get the screen width
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int screenWidth = displayMetrics.widthPixels;

                // Calculate 90% of screen width
                int width = (int) (screenWidth * 1);

                // Set the fixed width and wrap content height for the dialog
                dialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

                // Set the dim amount for the background
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.dimAmount = 0.5f; // Change this value to adjust the dim level (0.0 to 1.0)
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }

        });

        dialog.show();
    }


    private void switchMode() {

        Log.d("switchMode()", "Switching mode...");

        if (isProgressiveMode) {
            learningModeText.setText("Progressive Mode");
            learningModeIcon.setImageResource(R.drawable.progressive_mode);
            pagerAdapter.setFragmentList(progressiveFragmentList);
            viewPager.setCurrentItem(progressiveFragmentList.indexOf(new b_main_1_lesson_progressive()));
        }
        else {
            learningModeText.setText("Free Use Mode");
            learningModeIcon.setImageResource(R.drawable.free_use_mode);
            pagerAdapter.setFragmentList(freeUseFragmentList);
            viewPager.setCurrentItem(freeUseFragmentList.indexOf(new b_main_2_lesson_freeuse()));
        }

        // Close the navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
//        pagerAdapter.notifyDataSetChanged();
        Log.d("switchMode()", "Mode switched.");

    }

    private void showSwitchModeDialog(final String mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the appropriate layout based on the mode
        View dialogView = null;
        if (mode.equals("Progressive Mode")) {
            dialogView = getLayoutInflater().inflate(R.layout.dialog_switch_to_progressive_mode, null);
        } else if (mode.equals("Free Use Mode")) {
            dialogView = getLayoutInflater().inflate(R.layout.dialog_switch_to_freeuse_mode, null);
        }

        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        // Set up the dialog buttons
        Button positiveButton = dialogView.findViewById(R.id.btn_positive);
        Button negativeButton = dialogView.findViewById(R.id.btn_negative);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mode.equals("Progressive Mode")) {
                    isProgressiveMode = true;
                }

                else if (mode.equals("Free Use Mode")) {
                    isProgressiveMode = false;
                }

                switchMode();
                dialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Adjust the dialog size
        dialog.getWindow().setLayout(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.95),  // width: 90% of the screen width
                ViewGroup.LayoutParams.WRAP_CONTENT  // height: wrap content
        );

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - backPressedTime < 2000) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            backPressedTime = currentTime;
        }
    }

    private void openFacebookPage() {
        String facebookUrl = "https://www.facebook.com/ucpncofficial";
        String facebookAppUrl = "fb://facewebmodal/f?href=" + facebookUrl;
        String facebookLiteUrl = "fb://lite/";

        // Try to open Facebook App
        try {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookAppUrl));
            facebookIntent.setPackage("com.facebook.katana"); // Facebook App package name
            startActivity(facebookIntent);
        } catch (Exception e) {
            // If Facebook app is not available, try Facebook Lite
            try {
                Intent facebookLiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookLiteUrl));
                facebookLiteIntent.setPackage("com.facebook.lite"); // Facebook Lite package name
                startActivity(facebookLiteIntent);
            } catch (Exception liteException) {
                // If Facebook Lite is not available, open in default browser
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                    startActivity(browserIntent);
                } catch (Exception browserException) {
                    // In case no browser is available, show error or handle fallback
                    Toast.makeText(this, "No application available to open Facebook", Toast.LENGTH_LONG).show();
                } // Catch no Browser Application
            } // Catch no Facebook Lite Application
        } // Catch no Facebook Application
    } // Method

    private void fetchUserData(String userId) {

        String TAG = "fetchUserData";

        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        // User's Personal Information
                        Boolean tutorial = document.getBoolean("Tutorial");
                        String firstName = document.getString("First Name");
                        String lastName = document.getString("Last Name");
                        String category = document.getString("User Category");
                        String email = document.getString("Email Address");
                        String gender = document.getString("Gender");
                        Long age = document.getLong("Age");

                        // Settings
                        Boolean updateNotif = document.getBoolean("App Update Notification");
                        Boolean newCourse = document.getBoolean("New Course Available Notification");
                        Boolean reminderNotif = document.getBoolean("Reminder Notification");

                        Log.e(TAG, "First Login: " + tutorial);
                        Log.e(TAG, "First Name: " + firstName);
                        Log.e(TAG, "Last Name: " + lastName);
                        Log.e(TAG, "Email Address: " + email);
                        Log.e(TAG, "Gender: " + gender);
                        Log.e(TAG, "Age: " + age);

                        Log.e(TAG, "App Update Notification: " + updateNotif);
                        Log.e(TAG, "New Course Available Notification: " + newCourse);
                        Log.e(TAG, "Reminder Notification: " + reminderNotif);

                        greetUserName.setText("Hello, " + firstName);
                        greetUserCategory.setText(category);

                        // Check if the user has a custom profile picture
                        Boolean hasCustomProfilePicture = document.getBoolean("hasCustomProfilePicture");
                        if (hasCustomProfilePicture != null && hasCustomProfilePicture) {
                            Log.e(TAG, "I have my own Profile Picture!");
                            String customProfilePictureUrl = document.getString("profilePictureUrl");
                            if (customProfilePictureUrl != null) {
                                Picasso.get().load(customProfilePictureUrl).into(profileImageView);
                            }
                        } else {
                            Log.e(TAG, "I aint got my own Profile Picture!");
                            // User does not have a custom profile picture yet
                            if (gender != null) {
                                if (gender.equalsIgnoreCase("male")) {
                                    // Apply male anonymous avatar
                                    profileImageView.setImageResource(R.drawable.default_male);
                                } else if (gender.equalsIgnoreCase("female")) {
                                    // Apply female anonymous avatar
                                    profileImageView.setImageResource(R.drawable.default_female);
                                } else {
                                    profileImageView.setImageResource(R.drawable.default_generic);
                                }
                            } else {
                                // Handle case where gender is null
                                Toast.makeText(b_main_0_menu.this, "Par wala kang Gender??", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Fetch lesson data
                        fetchLessonData(userId);

                    } else {
                        Toast.makeText(b_main_0_menu.this, "No user data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(b_main_0_menu.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void fetchLessonData(String userId) {
        // Replace with your actual Firestore collection and document paths
        db.collection("users").document(userId).collection("lessons")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, Map<String, Object>> progressiveModeData = new HashMap<>();
                            HashMap<String, Map<String, Object>> freeUseModeData = new HashMap<>();

                            for (DocumentSnapshot document : task.getResult()) {
                                String lessonName = document.getId();
                                Map<String, Object> lessonData = document.getData();

                                if (lessonData.containsKey("mode")) {
                                    String mode = (String) lessonData.get("mode");
                                    if ("Progressive Mode".equalsIgnoreCase(mode)) {
                                        progressiveModeData.put(lessonName, lessonData);
                                    } else if ("Free Use Mode".equalsIgnoreCase(mode)) {
                                        freeUseModeData.put(lessonName, lessonData);
                                    }
                                }
                            }

                            processLessonData(progressiveModeData, freeUseModeData);

                        } else {
                            Toast.makeText(b_main_0_menu.this, "Error fetching lesson data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void processLessonData(HashMap<String, Map<String, Object>> progressiveModeData, HashMap<String, Map<String, Object>> freeUseModeData) {
        Log.d("Main Menu", "Meron nako'ng data ng Lessons");

        // Log and process Progressive Mode data
        if (progressiveModeData != null) {
            List<String> sortedLessonNames = new ArrayList<>(progressiveModeData.keySet());
            Collections.sort(sortedLessonNames); // Sort lesson names alphabetically

            for (String lessonName : sortedLessonNames) {
                Map<String, Object> lessonData = progressiveModeData.get(lessonName);
                for (Map.Entry<String, Object> lessonEntry : lessonData.entrySet()) {
                    String moduleName = lessonEntry.getKey();
                    Object moduleValue = lessonEntry.getValue();
                    Log.d("LessonData", "hihi Progressive Mode: " + lessonName + ", Field: " + moduleName + ", Value: " + moduleValue);
                }
            }
        } else {
            Toast.makeText(this, "Wala par", Toast.LENGTH_SHORT).show();
            Log.d("No Progressive Mode", "Wala ako nakitang Progressive Mode par, sensya na you");
        }

        // Log and process Free Use Mode data
        if (freeUseModeData != null) {
            List<String> sortedLessonNames = new ArrayList<>(freeUseModeData.keySet());
            Collections.sort(sortedLessonNames); // Sort lesson names alphabetically

            for (String lessonName : sortedLessonNames) {
                Map<String, Object> lessonData = freeUseModeData.get(lessonName);
                for (Map.Entry<String, Object> lessonEntry : lessonData.entrySet()) {
                    String moduleName = lessonEntry.getKey();
                    Object moduleValue = lessonEntry.getValue();
                    Log.d("LessonData", "hihi Free Use Mode: " + lessonName + ", Field: " + moduleName + ", Value: " + moduleValue);
                }
            }
        } else {
            Toast.makeText(this, "Wala par", Toast.LENGTH_SHORT).show();
            Log.d("No Free Use Mode", "Wala ako nakitang Free Use Mode par, sensya na you");
        }

//        hideLoadingDialog();
    }
}