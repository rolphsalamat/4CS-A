package com.example.autotutoria20;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class f_video_lesson extends Fragment {

    private static final String ARG_VIDEO_URL = "video_url";
    private WebView webView;
    private String videoUrl;
    private View youtubeButton;
    private View googleDriveButton;

    public static f_video_lesson newInstance(String videoUrl) {
        f_video_lesson fragment = new f_video_lesson();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // disabled Google Drive pop out button
        googleDriveButton = view.findViewById(R.id.pop_out_button);
        googleDriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicking here is disabled.", Toast.LENGTH_SHORT).show();
            }
        });

        // disabled Youtube button
        youtubeButton = view.findViewById(R.id.youtube_button);
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicking here is disabled.", Toast.LENGTH_SHORT).show();
            }
        });

//        if (getArguments() != null) {
//            videoUrl = getArguments().getString(ARG_VIDEO_URL);
//            if (videoUrl != null && !videoUrl.isEmpty()) {
//                // Embed the self-hosted video using HTML5 player
//                String htmlVideo = "<html><body style='margin:0;padding:0;'><video width=\"100%\" height=\"100%\" controls>"
//                        + "<source src=\"" + videoUrl + "\" type=\"video/mp4\">"
//                        + "Your browser does not support the video tag."
//                        + "</video></body></html>";
//                webView.loadData(htmlVideo, "text/html", "utf-8");
//            }
//        }


        if (getArguments() != null) {
            videoUrl = getArguments().getString(ARG_VIDEO_URL);
            if (videoUrl != null && !videoUrl.isEmpty()) {
                String iframeHtml;
                if (videoUrl.contains("drive.google.com")) {
                    // Google Drive video link
                    iframeHtml = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\""
                            + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                } else {
                    // YouTube video ID
                    iframeHtml = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                            + videoUrl + "?modestbranding=1&rel=0&showinfo=0\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                }
                webView.loadData(iframeHtml, "text/html", "utf-8");
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.clearHistory();
            webView.clearCache(true);
            webView.destroy();
        }
        super.onDestroyView();
    }
}
