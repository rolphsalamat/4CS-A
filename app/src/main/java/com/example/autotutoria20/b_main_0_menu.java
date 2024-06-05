    package com.example.autotutoria20;

    import android.annotation.SuppressLint;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.Color;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.FrameLayout;
    import android.widget.RatingBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import android.content.pm.PackageManager;

    import androidx.appcompat.app.AppCompatActivity;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.ActionBar;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AlertDialog;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.fragment.app.Fragment;
    import androidx.viewpager.widget.ViewPager;

    import com.google.android.material.navigation.NavigationView;
    import com.google.android.material.imageview.ShapeableImageView;

    import java.util.ArrayList;
    import java.util.List;

    public class b_main_0_menu extends AppCompatActivity {
        private boolean[] cardStates = {true, false, false}; // Example state array for 3 cards
        private static final int PICK_IMAGE_REQUEST = 1;
        private DrawerLayout drawerLayout;
        private NavigationView navigationView;
        private ActionBarDrawerToggle drawerToggle;
        private ViewPager viewPager;
        private PagerAdapter pagerAdapter;
        private static TextView greetUserName;
        private List<Fragment> progressiveFragmentList;
        private List<Fragment> freeUseFragmentList;
        private List<Fragment> fragmentList;
        private long backPressedTime;
        private Button increment_progress;
        private View module, description;
        private ShapeableImageView profileImageView;
        private FrameLayout profileFrameLayout;
        private boolean isProgressiveMode = true;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.b_main_0_menu);

            // Initialize the NavigationView and its header view
            navigationView = findViewById(R.id.navigation_view);
            View headerView = navigationView.getHeaderView(0);
            greetUserName = headerView.findViewById(R.id.user_firstName);

            // Retrieve the keyname from the intent
            String firstName = getIntent().getStringExtra("firstName");
            greetUserName.setText("Hello, " + firstName); // change Greeting text

            // Initialize ViewPager
            viewPager = findViewById(R.id.view_pager);

            // Initialize fragment lists
            progressiveFragmentList = new ArrayList<>();
            freeUseFragmentList = new ArrayList<>();
            fragmentList = new ArrayList<>();

            // Add fragments to the lists
            progressiveFragmentList.add(new b_main_1_lesson_progressive());
            progressiveFragmentList.add(new b_main_3_description());

            freeUseFragmentList.add(new b_main_2_lesson_freeuse());
            freeUseFragmentList.add(new b_main_3_description());

            // Set up ViewPager with adapter
            pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
            viewPager.setAdapter(pagerAdapter);

            // Set initial fragment list based on mode
            switchMode();

            // Button highlighter for Header
            module = findViewById(R.id.modulesSelected);
            description = findViewById(R.id.descriptionSelected);

            // OPEN NAVIGATION DRAWER
            Button toggleDrawerButton = findViewById(R.id.toggle_drawer_button);
            toggleDrawerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            // NAVIGATION DRAWER BUTTONS
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.navigation_view);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
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
                    if (id == R.id.settings) {
                        Toast.makeText(b_main_0_menu.this, "Settings", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(b_main_0_menu.this, b_main_0_menu_settings.class));
                    } else if (id == R.id.progressive_mode) {
                        showSwitchModeDialog("Progressive Mode");
                    } else if (id == R.id.free_use_mode) {
                        showSwitchModeDialog("Free Use Mode");
                    } else if (id == R.id.rate) {
                        Toast.makeText(b_main_0_menu.this, "Rate Us", Toast.LENGTH_SHORT).show();
                        showRateUsDialog();
    //                    startActivity(new Intent(b_main_0_menu.this, b_main_0_menu_rateUs.class));
                    } else if (id == R.id.follow) {
                        openFacebookPage();
                    } else if (id == R.id.log_out) {
                        Toast.makeText(b_main_0_menu.this, "Logout", Toast.LENGTH_SHORT).show();
                        showLogoutDialog();
                    }
                    // Handle other options if necessary
                    return false;
                }
            });

            // SHOW MODULE FRAGMENT
            Button modulesButton = findViewById(R.id.module_button);
            modulesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    description.setBackgroundColor(Color.TRANSPARENT);
                    module.setBackgroundColor(Color.WHITE);
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
                    viewPager.setCurrentItem(1);
                }
            });

            // Add ViewPager OnPageChangeListener to update button highlights
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // No action needed here
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        description.setBackgroundColor(Color.TRANSPARENT);
                        module.setBackgroundColor(Color.WHITE);
                    } else if (position == 1) {
                        module.setBackgroundColor(Color.TRANSPARENT);
                        description.setBackgroundColor(Color.WHITE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // No action needed here
                }
            });

            // Initialize profile image view and frame layout
            View headerView1 = navigationView.getHeaderView(0);
            profileImageView = headerView1.findViewById(R.id.user_profile_picture);
            profileFrameLayout = headerView1.findViewById(R.id.profile_frame_layout);

            profileFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            });
        }

        // Method to show the Logout dialog with wrap content width and height
        private void showLogoutDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.b_main_0_menu_logout, null);
            builder.setView(dialogView);

            Button logoutButton = dialogView.findViewById(R.id.button_logout);

            AlertDialog dialog = builder.create();

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(b_main_0_menu.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.show();
        }

        // Method to show the Rate Us dialog
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

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                profileImageView.setImageURI(imageUri); // Set the selected image to the profile picture ImageView
            }
        }


        private void showSwitchModeDialog(final String mode) {
            AlertDialog.Builder builder = new AlertDialog.Builder(b_main_0_menu.this);
            builder.setMessage("Do you want to switch to " + mode + "?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Set Boolean for which mode is being used
                            if (mode.equals("Progressive Mode")) {
                                isProgressiveMode = true;
                            } else if (mode.equals("Free Use Mode")) {
                                isProgressiveMode = false;
                            }
                            switchMode();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }

        private void switchMode() {
            if (isProgressiveMode) {
                pagerAdapter.setFragmentList(progressiveFragmentList);
            } else {
                pagerAdapter.setFragmentList(freeUseFragmentList);
            }
            // Force the ViewPager to refresh
            viewPager.setAdapter(null);
            viewPager.setAdapter(pagerAdapter);
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
            String facebookUrl = "https://www.facebook.com/pncofficial";
            String facebookAppUrl = "fb://page/100070669176471"; // Assuming this is the page ID
            String facebookLiteUrl = "fb://lite/page/100070669176471"; // Assuming this is the page ID

            Intent intent = new Intent(Intent.ACTION_VIEW);

            // Try to open the Facebook app
            if (isAppInstalled("com.facebook.katana")) {
                intent.setData(Uri.parse(facebookAppUrl));
            }
            // Try to open Facebook Lite
            else if (isAppInstalled("com.facebook.lite")) {
                intent.setData(Uri.parse(facebookLiteUrl));
            }
            // Fallback to web browser
            else {
                intent.setData(Uri.parse(facebookUrl));
            }

            // Check if there's an app that can handle the intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No application can handle this request.", Toast.LENGTH_LONG).show();
            }
        }

        // Helper method to check if an app is installed
        private boolean isAppInstalled(String packageName) {
            PackageManager pm = getPackageManager();
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
    }
