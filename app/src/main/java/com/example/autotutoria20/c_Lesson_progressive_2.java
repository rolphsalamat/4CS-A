package com.example.autotutoria20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class c_Lesson_progressive_2 extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean[] cardCompletionStatus = {false, false, false}; // Track completion status of each card

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_lesson_progressive_1);

        FrameLayout card1 = findViewById(R.id.card1);
        FrameLayout card2 = findViewById(R.id.card2);
        FrameLayout card3 = findViewById(R.id.card3);

        setCardClickListener(card1, 1);
        setCardClickListener(card2, 2);
        setCardClickListener(card3, 3);

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    private void navigateToModule(int cardNumber) {
        if (isPreviousCardCompleted(cardNumber)) {
            switch (cardNumber) {
                case 1:
                    navigateToSampleModule3Steps();
                    break;
                case 2:
                    navigateToSampleModule3Steps(); // Assuming the same module for card 2
                    break;
                case 3:
                    navigateToSampleModule4Steps();
                    break;
                default:
                    break;
            }
        } else {
            showCustomDialog();
        }
    }

    private void navigateToSampleModule3Steps() {
        Intent intent = new Intent(c_Lesson_progressive_2.this, module_3_steps.class);
        startActivity(intent);
    }

    private void navigateToSampleModule4Steps() {
        Intent intent = new Intent(c_Lesson_progressive_2.this, module_4_steps.class);
        startActivity(intent);
    }

    private void setCardClickListener(FrameLayout frame, final int cardNumber) {
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToModule(cardNumber);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c_Lesson_progressive_2.this, "Exiting module", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module_);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c_Lesson_progressive_2.this, "Cancel Exit", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private boolean isPreviousCardCompleted(int cardNumber) {
        // Assuming card numbers start from 1
        if (cardNumber <= 1) {
            // If it's the first card, consider it completed
            return true;
        } else {
            // Check the completion status of the previous card
            return cardCompletionStatus[cardNumber - 2];
        }
    }

    // Method to show the custom dialog
    private void showCustomDialog() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_complete_previous_lesson, null);
        builder.setView(dialogView);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Find the "Okay" button in    the custom layout
        Button exitButton = dialogView.findViewById(R.id.okay_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the button is clicked
                dialog.dismiss();
            }
        });

        // Set the dialog window attributes
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                // Convert dp to pixels
                int width = (int) (350 * getResources().getDisplayMetrics().density);
                int height = (int) (350 * getResources().getDisplayMetrics().density);

                // Set the fixed size for the dialog
                dialog.getWindow().setLayout(width, height);
            }
        });

        // Show the dialog
        dialog.show();
    }
}
