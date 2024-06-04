package com.example.autotutoria20;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class c_Lesson_freeuse_4 extends AppCompatActivity {

    private AlertDialog dialog; // Declare the dialog variable outside

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_freeuse_4);

        // Find all card views
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        CardView card3 = findViewById(R.id.card3);

        // Set click listeners for each card
        setCardClickListener(card1, 1);
        setCardClickListener(card2, 2);
        setCardClickListener(card3, 3);

        // Find exit button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    // Method to set click listener for each card
    private void setCardClickListener(CardView cardView, final int cardNumber) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Open Card " + cardNumber);
            }
        });
    }

    // Helper method to show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showExitConfirmationDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        // Find "Yes" button in custom layout
        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Yes" button click
                Toast.makeText(c_Lesson_freeuse_4.this, "Exiting module", Toast.LENGTH_SHORT).show();
                finish(); // Finish the activity
            }
        });

        // Find "No" button in custom layout
        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module_);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "No" button click
                Toast.makeText(c_Lesson_freeuse_4.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the dialog
        dialog = builder.create();
        dialog.show();
    }
}
