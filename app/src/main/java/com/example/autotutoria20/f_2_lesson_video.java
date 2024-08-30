package com.example.autotutoria20;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class f_2_lesson_video extends Fragment {

    private static final String TAG = "f_2_lesson_video";
    private static final String ARG_VIDEO_URL = "video_url";
    private static final String SHARED_PREFS = "VideoPrefs";
    private static final String PREF_VIDEO_URL = "pref_video_url";

    private View youtubeButton;
    private View googleDriveButton;
    private WebView webView;
    private String videoUrl;

    public static f_2_lesson_video newInstance(String videoUrl) {
        Log.d(TAG, "newInstance called with videoUrl: " + videoUrl);
        f_2_lesson_video fragment = new f_2_lesson_video();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        return inflater.inflate(R.layout.f_2_lesson_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated called");

        webView = view.findViewById(R.id.webView);
        Log.d(TAG, "WebView initialized: " + (webView != null));

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        Log.d(TAG, "WebView settings configured");

        webView.setWebViewClient(new WebViewClient());
        Log.d(TAG, "WebViewClient set");

        // Disabled Google Drive pop out button
        googleDriveButton = view.findViewById(R.id.pop_out_button);
        googleDriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicking here is disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        // Disabled YouTube button
        youtubeButton = view.findViewById(R.id.youtube_button);
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicking here is disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        if (getArguments() != null) {
            videoUrl = getArguments().getString(ARG_VIDEO_URL);
            Log.d(TAG, "Video URL from arguments: " + videoUrl);
            if (videoUrl != null) {
                sharedPreferences.edit().putString(PREF_VIDEO_URL, videoUrl).apply();
                Log.d(TAG, "Video URL saved to SharedPreferences");
            }
        } else {
            videoUrl = sharedPreferences.getString(PREF_VIDEO_URL, null);
            Log.d(TAG, "Video URL retrieved from SharedPreferences: " + videoUrl);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        // Load the video when the fragment is resumed
        loadVideo(videoUrl);
    }

    private void loadVideo(String videoUrl) {
        Log.d(TAG, "loadVideo called with videoUrl: " + videoUrl);
        if (videoUrl != null && !videoUrl.isEmpty()) {
            String iframeHtml;
            if (videoUrl.contains("drive.google.com")) {
                iframeHtml = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\""
                        + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                Log.d(TAG, "Google Drive video link detected, iframe HTML generated");
            } else {
                iframeHtml = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                        + videoUrl + "?modestbranding=1&rel=0&showinfo=0\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                Log.d(TAG, "YouTube video link detected, iframe HTML generated");
            }
            webView.loadData(iframeHtml, "text/html", "utf-8");
            Log.d(TAG, "WebView loadData called with iframe HTML");
        } else {
            Log.d(TAG, "Video URL is null or empty, no video loaded");
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView called");
        if (webView != null) {
            Log.d(TAG, "WebView exists, stopping video playback and clearing resources");
            webView.loadUrl("about:blank");
            webView.clearHistory();
            webView.clearCache(true);
            webView.destroy();
            webView = null;
            Log.d(TAG, "WebView destroyed");
        }
        super.onDestroyView();
    }

    public void stopVideoPlayback() {
        Log.e(TAG, "stopVideoPlayback called");
        if (webView != null) {
            webView.loadUrl("about:blank");
            Log.d(TAG, "WebView loadUrl called with about:blank to stop video");
        } else {
            Log.d(TAG, "WebView is null, nothing to stop");
        }
    }

    public static void clearVideoPreferences(Context context) {
        Log.d(TAG, "clearVideoPreferences called");
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Log.d(TAG, "SharedPreferences cleared");
    }
}
