package com.example.autotutoria20;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ChartUtils {

    public static BarChart createGradientBarChart(Context context, BarChart barChart, float userInput) {
        // Configure the bar chart
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);

        // Set data
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, userInput)); // Single entry with user input value

        BarDataSet dataSet = new BarDataSet(entries, "User Input");
        dataSet.setGradientColor(Color.RED, Color.GREEN); // Gradient from red to green
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

        // Customize X and Y axes
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false); // No x-axis labels

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Add the arrow marker
        CustomMarkerView marker = new CustomMarkerView(context, userInput);
        barChart.setMarker(marker);

        barChart.invalidate(); // Refresh chart
        return barChart;
    }
}
