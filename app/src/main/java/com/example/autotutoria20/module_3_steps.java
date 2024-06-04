package com.example.autotutoria20;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class module_3_steps extends AppCompatActivity {

    private GridLayout gridLayout;
    private AlertDialog dialog; // Declare the dialog variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_3_steps);

        gridLayout = findViewById(R.id.gridLayout);

        // Initialize WebView
//        WebView webView = findViewById(R.id.webView);
//        webView.getSettings().setJavaScrip vtEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.youtube.com/watch?v=poa_QBvtIBA");

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(module_3_steps.this, "Next!", Toast.LENGTH_SHORT).show();
            }
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    private void showExitConfirmationDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        // Initialize the dialog
        dialog = builder.create();

        // Find "Yes" button in custom layout
        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Yes" button click
                finish(); // Finish the activity
            }
        });

        // Find "No" button in custom layout
        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module_);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Show the dialog
        dialog.show();
    }

}
