package com.example.autotutoria20;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NonScrollableViewPager extends ViewPager {

    public NonScrollableViewPager(Context context) {
        super(context);
    }

    public NonScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Return false to consume the touch event and prevent scrolling
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Prevent child views from receiving touch events
        return false;
    }
}
