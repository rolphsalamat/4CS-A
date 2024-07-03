package com.example.autotutoria20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Lesson_4 extends AppCompatActivity {

    private TextView lessonTopic;
    private Button exitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_4);

        // Find all card views
        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        CardView card3 = findViewById(R.id.card3);

        // Set click listeners for each card
        setCardClickListener(card1, 1);
        setCardClickListener(card2, 2);
        setCardClickListener(card3, 3);

        lessonTopic = findViewById(R.id.topic);

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog to confirm exit
                AlertDialog.Builder builder = new AlertDialog.Builder(Lesson_4.this);
                builder.setTitle("Exit module?");
                builder.setMessage("Are you sure you want to exit " + lessonTopic.getText() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Lesson_4.this, "Exiting module", Toast.LENGTH_SHORT).show();
                        finish(); // Finish the activity
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog if "No" is clicked
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    // Method to set click listener for each card
    private void setCardClickListener(CardView cardView, final int cardNumber) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Module " + cardNumber + " clicked");
                startActivity(new Intent(Lesson_4.this, d_Lesson_container.class));
            }
        });
    }

    // Helper method to show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
