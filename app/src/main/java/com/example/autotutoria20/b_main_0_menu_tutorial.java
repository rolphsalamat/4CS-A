package com.example.autotutoria20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class b_main_0_menu_tutorial extends AppCompatActivity {

    private static WebView webView;
    private static final String TAG = "TutorialActivity";
    private View youtubeButton;
    private View googleDriveButton;
    private FirebaseAuth mAuth;
    private Map<String, Object> userData;

    // Known publicly available YouTube video
    String videoUrl = "NF765WOy5Vk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_tutorial);

        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "onCreate: Tutorial Activity started");

        // Set up the WebView to display the video
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // disabled Google Drive pop out button
        googleDriveButton = findViewById(R.id.pop_out_button);
        googleDriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(b_main_0_menu_tutorial.this, "clicking here is disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        // disabled Youtube button
        youtubeButton = findViewById(R.id.youtube_button);
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(b_main_0_menu_tutorial.this, "clicking here is disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "onCreate: WebView initialized");

        // Load the video
        loadVideo(videoUrl);

        // Button to finish the tutorial
        Button finishTutorialButton = findViewById(R.id.btnFinishTutorial);
        finishTutorialButton.setOnClickListener(v -> {
            Log.d(TAG, "Finish button clicked, marking tutorial as completed");
            markTutorialAsCompleted();

            // since first log in sya, ask user about category..
            Intent mainMenuIntent = new Intent(b_main_0_menu_tutorial.this, b_main_0_menu_categorize_user.class);
            startActivity(mainMenuIntent);
            Log.d(TAG, "Redirecting to categorization");

            finish();

        });
    }

    private void loadVideo(String videoUrl) {
        Log.d(TAG, "Loading video with URL: " + videoUrl);
        String iframeHtml;
        if (videoUrl.contains("drive.google.com")) {
            Log.d(TAG, "Detected Google Drive video link");
            // Google Drive video link
            iframeHtml = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\""
                    + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        } else {
            Log.d(TAG, "Detected YouTube video ID");
            // YouTube video ID
            iframeHtml = "<html><body style='margin:0;padding:0;'>" +
                    "<div style='width: 100%; height: 100%; overflow: hidden; position: relative;'>" +
                    "<iframe style='width: 250%; height: 100%; margin-left: -75%;' " +
                    "src=\"https://www.youtube.com/embed/" + videoUrl + "?modestbranding=1&rel=0&showinfo=0\" " +
                    "frameborder=\"0\" allowfullscreen></iframe>" +
                    "</div></body></html>";

        }
        webView.loadData(iframeHtml, "text/html", "utf-8");

        // Handle errors in WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: WebView finished loading");
                if (view.getTitle().contains("Video unavailable")) {
                    Log.e(TAG, "Video unavailable in WebView");
                    Toast.makeText(b_main_0_menu_tutorial.this, "Video cannot be played. Opening in YouTube.", Toast.LENGTH_LONG).show();
                    openInYouTube(videoUrl);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "onReceivedError: WebView error " + description);
                Toast.makeText(b_main_0_menu_tutorial.this, "Error loading video. Opening in YouTube.", Toast.LENGTH_LONG).show();
                openInYouTube(videoUrl);
            }
        });

        Log.d(TAG, "Video loaded in WebView");

        simulateClicksInCenter();

    }

    public static void simulateClicksInCenter() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the center coordinates of the screen
                int x = webView.getWidth() / 2;
                int y = webView.getHeight() / 2;

                simulateClick(x, y);
//                }
                Log.e("simulateClicksInCenter", "Click!");
            }
        }, 1000);  // Convert seconds to milliseconds
    }

    public static  void simulateClick(int x, int y) {
        long downTime = System.currentTimeMillis();
        long eventTime = System.currentTimeMillis() + 10; // Delay between down and up events

        // Simulate touch down event
        MotionEvent motionEventDown = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
        webView.dispatchTouchEvent(motionEventDown);

        // Simulate touch up event
        MotionEvent motionEventUp = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
        webView.dispatchTouchEvent(motionEventUp);
    }

    private void openInYouTube(String videoUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoUrl));
        startActivity(intent);
        finish();
    }

    private void markTutorialAsCompleted() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance(); // Ensure FirebaseAuth is initialized
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Update Firestore
            db.collection("users").document(userId)
                    .update("Tutorial", true)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Tutorial", "Tutorial completion updated in Firestore");

                        finish();

                    })
                    .addOnFailureListener(e -> Log.e("Tutorial", "Error updating tutorial completion", e));
        } else {
            Log.e("Tutorial", "No authenticated user found");
            // Handle the case where the user is not authenticated, e.g., redirect to login
            redirectToLogin();
        }
    }

    private void redirectToLogin() {
        Intent loginIntent = new Intent(this, a_user_1_login.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: Cleaning up WebView resources");
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.clearHistory();
            webView.clearCache(true);
            webView.destroy();
        }
        super.onDestroy();
        Log.d(TAG, "onDestroy: Tutorial Activity destroyed");
    }
}
