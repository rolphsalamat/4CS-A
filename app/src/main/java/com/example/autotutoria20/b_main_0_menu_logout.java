package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class b_main_0_menu_logout extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_main_0_menu_logout);

        Button logoutButton = findViewById(R.id.button_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(b_main_0_menu_logout.this, "Logout", Toast.LENGTH_SHORT).show();
                // Here you can handle the rating submission logic (e.g., send it to a server or save locally)
                finish();
            }
        });
    }
}
