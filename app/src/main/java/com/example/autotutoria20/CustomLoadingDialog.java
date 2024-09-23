package com.example.autotutoria20;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.autotutoria20.R;

public class CustomLoadingDialog extends Dialog {

    private ProgressBar loadingProgressBar;

    public CustomLoadingDialog(Context context) {
        super(context, R.style.FullScreenDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        // Ensure the window attributes are set to match the parent
        Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        // Initialize the progress bar
        loadingProgressBar = findViewById(R.id.progressBar_loading);
    }

    public void setProgress(int progress) {
        if (loadingProgressBar != null) {
            loadingProgressBar.setProgress(progress);
        }
    }

    public ProgressBar getLoadingProgressBar() {
        return loadingProgressBar;
    }
}

