package com.example.autotutoria20;// Sample_module.java

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class module_4_steps extends AppCompatActivity {

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_4_steps);

        gridLayout = findViewById(R.id.gridLayout);

        int numberOfViews = getIntent().getIntExtra("numberOfViews", 0);

        addViewsToGridLayout(numberOfViews);

        // Initialize WebView
//        WebView webView = findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://www.youtube.com/watch?v=poa_QBvtIBA");

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(module_4_steps.this, "Next!", Toast.LENGTH_SHORT).show();
            }
        });

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addViewsToGridLayout(int numberOfViews) {
        for (int i = 0; i < numberOfViews; i++) {
            View view = new View(this);
            view.setBackgroundColor(Color.RED);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(i, 1f);
            params.rowSpec = GridLayout.spec(0);
            gridLayout.addView(view, params);
        }
    }
}
