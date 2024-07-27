package com.example.autotutoria20;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LessonPagerAdapter extends FragmentPagerAdapter {

    private final StepType[] stepSequence;

    public LessonPagerAdapter(@NonNull FragmentManager fm, StepType[] stepSequence) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.stepSequence = stepSequence;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        StepType stepType = stepSequence[position];
        switch (stepType) {
            case PRE_TEST:
                return new PreTestFragment();
            case POST_TEST:
                return new PostTestFragment();
            case VIDEO:
                return new VideoFragment();
            case TEXT:
                return new TextFragment();
            default:
                throw new IllegalStateException("Unexpected value: " + stepType);
        }
    }

    @Override
    public int getCount() {
        return stepSequence.length;
    }
}
