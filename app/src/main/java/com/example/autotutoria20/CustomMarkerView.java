package com.example.autotutoria20;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkerView extends MarkerView {

    private final TextView textView;

    public CustomMarkerView(Context context, float userInput) {
        super(context, R.layout.custom_marker_view); // Custom layout for the marker
        textView = findViewById(R.id.score_text);
        textView.setText("→ " + (int) userInput); // Display user input value
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // Refresh marker content when highlighted
        textView.setText("→ " + (int) e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // Offset to center the arrow on the bar
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
