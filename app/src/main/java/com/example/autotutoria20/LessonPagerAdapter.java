package com.example.autotutoria20;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LessonPagerAdapter extends FragmentStatePagerAdapter {

    private final LessonSequence.StepType[] stepSequence;
    private final String currentLesson;
    private final String learningMode;

    public LessonPagerAdapter(@NonNull FragmentManager fm, LessonSequence.StepType[] stepSequence, String currentLesson, String learningMode) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        if (stepSequence == null) {
            throw new IllegalArgumentException("stepSequence cannot be null");
        }
        this.stepSequence = stepSequence;
        this.currentLesson = currentLesson;
        this.learningMode = learningMode;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        String TAG = "LessonPagerAdapter";

        if (position < 0 || position >= stepSequence.length) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        LessonSequence.StepType stepType = stepSequence[position];

        Log.e(TAG, "currentLesson: " + currentLesson);

        // Extract module and lesson from currentLesson
        String[] parts = currentLesson.split("_");
        if (parts.length != 2) {
            throw new IllegalStateException("currentLesson should be in the format 'Module_Lesson'");
        }
        String module = parts[0]; // e.g., "M1"
        String lesson = parts[1]; // e.g., "Lesson 1"

        Log.e(TAG, "f_pre_test.newInstance(" + module + ", " + lesson + ");");

        Log.e(TAG, "Step Type: " + stepType);
        Log.e(TAG, "Lesson: " + lesson);
        Log.e(TAG, "Module: " + module);

        switch (stepType) {
            case PRE_TEST:
                Log.e(TAG, "Pre Test");
                return f_pre_test.newInstance(module, lesson, learningMode);
            case POST_TEST:
                Log.e(TAG, "Post Test");
                return f_post_test.newInstance(module, lesson, learningMode);

                // ETONG VIDEO GUSTO LAGING MAY LAMAN YUNG LINK E PANO PAG WALA?!?! ANO MAGAGAWA KO!?!?!
            case VIDEO:
                Log.e(TAG, "Video Lesson");
                String videoUrl = LessonSequence.getLessonVideoLinks().get(currentLesson);
                Log.e(TAG, "videoUrl: " + videoUrl);
                if (videoUrl == null || videoUrl.isEmpty()) {
                    Log.e(TAG, "VIDEO URL IS NULL!");
                    throw new IllegalStateException("Video URL cannot be null or empty for lesson: " + currentLesson);
                } else {
                    Log.e(TAG, "VIDEO URL: " + videoUrl);
                    return f_video_lesson.newInstance(videoUrl);
                }
            case TEXT:
                Log.e(TAG, "Text Lesson");

                // lagyan ng int na accessible all around the world, para mailagay as 2nd parameter dito
                // kapag nailagay yon, made-detect na yugn page number
                // then sa text lesson class nalang sya i-increment
                // so pag nadetect ni d_lesson_container class na eto na yung page number nya, then ire-
                // relaunch nya si text lesson class, updated na yung page number base don sa value..

                // so dapat base dito sa logic na to, gagana na yung part na yon??
                return f_text_lesson.newInstance(currentLesson);
            default:
                throw new IllegalStateException("Unexpected value: " + stepType);
        }
    }

    @Override
    public int getCount() {
        return stepSequence == null ? 0 : stepSequence.length;
    }

}
