package com.example.autotutoria20;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LessonPagerAdapter extends FragmentPagerAdapter {

    private final LessonSequence.StepType[] stepSequence;
    private final String currentLesson;
    private final String learningMode;
    private final int pageNumber;

    public LessonPagerAdapter(@NonNull FragmentManager fm, LessonSequence.StepType[] stepSequence, String currentLesson, String learningMode, int pageNumber) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.stepSequence = stepSequence;
        this.currentLesson = currentLesson;
        this.learningMode = learningMode;
        this.pageNumber = pageNumber;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        String TAG = "LessonPagerAdapter";

        LessonSequence.StepType stepType = stepSequence[position];
        String[] parts = currentLesson.split("_");

        Log.e(TAG, "currentLesson: " + currentLesson);
        String module = parts[0];
        String lesson = parts[1];


        switch (stepType) {
            case PRE_TEST:
                return f_pre_test.newInstance(module, lesson, learningMode);
            case POST_TEST:
                return f_post_test.newInstance(module, lesson, learningMode);
            case VIDEO:
                String videoUrl = LessonSequence.getLessonVideoLinks().get(currentLesson);
                return f_video_lesson.newInstance(videoUrl);
            case TEXT:
                Log.e(TAG, "Loading text lesson for key: " + currentLesson);
                Log.e(TAG, "ill go to text lesson.newInstance(" + currentLesson + ", " + pageNumber + ");");
                return f_text_lesson.newInstance(currentLesson, pageNumber);
            default:
                throw new IllegalStateException("Unexpected step type: " + stepType);
        }
    }

    @Override
    public int getCount() {
        return stepSequence.length;
    }
}
