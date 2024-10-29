package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class a_user_5_terms_and_conditions extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_user_5_terms_and_conditions);


        Button agree = findViewById(R.id.agree_button);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a_user_2_signup.termsAndConditions.setChecked(true);
                finish();
            }
        });

    }
}
