package com.example.autotutoria20;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class L_lesson_handler extends FragmentPagerAdapter {

    private final d_Lesson_container.StepType[] stepSequence;
    private final String currentLesson;
    private final String learningMode;
    static int pageNumber;

    public L_lesson_handler(@NonNull FragmentManager fm, d_Lesson_container.StepType[] stepSequence, String currentLesson, String learningMode, int pageNumber) {
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

        d_Lesson_container.StepType stepType = stepSequence[position];
        String[] parts = currentLesson.split("_");

        String module = parts[0]; // M1 | M2 | M3 | M4
        String lesson = parts[1]; // Lesson 1 | Lesson 2

        Log.i(TAG, "Module: " + module);
        Log.i(TAG, "Lesson: " + lesson);

        switch (stepType) {
            case PRE_TEST:
                return f_0_lesson_pre_test.newInstance(module, lesson, learningMode);
            case POST_TEST:
                return f_3_lesson_post_test.newInstance(module, lesson, learningMode);
            case VIDEO:
                Log.i(TAG, " getLessonVideoLinks2(" + module + ", " + lesson + ");");
                String videoUrl = t_YoutubeLinkFromDatabase.getLessonVideoLinks2(module, lesson);
                Log.d(TAG, "BOSS YUNG LINK MO AY: " + videoUrl);

                return f_2_lesson_video.newInstance(videoUrl);
            case TEXT:
//                Log.e(TAG, "Loading text lesson for key: " + currentLesson);
//                Log.e(TAG, "ill go to text lesson.newInstance(" + currentLesson + ", " + pageNumber + ");");
                return f_1_lesson_text.newInstance(currentLesson, pageNumber);
            default:
                throw new IllegalStateException("Unexpected step type: " + stepType);
        }
    }

    @Override
    public int getCount() {
        if (stepSequence.length == 0)
            return 0;
        return stepSequence.length;
    }
}
