package com.example.autotutoria20;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LessonPagerAdapter extends FragmentPagerAdapter {

    private final LessonSequence.StepType[] stepSequence;
    private final String currentLesson;

    public LessonPagerAdapter(@NonNull FragmentManager fm, LessonSequence.StepType[] stepSequence, String currentLesson) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.stepSequence = stepSequence;
        this.currentLesson = currentLesson;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        LessonSequence.StepType stepType = stepSequence[position];
        switch (stepType) {
            case PRE_TEST:
                return new f_pre_test();
            case POST_TEST:
                return new f_post_test();
            case VIDEO:
                String videoUrl = LessonSequence.getLessonVideoLinks().get(currentLesson);
                return f_video_lesson.newInstance(videoUrl);
            case TEXT:
                return new f_text_lesson();
            default:
                throw new IllegalStateException("Unexpected value: " + stepType);
        }
    }

    @Override
    public int getCount() {
        return stepSequence.length;
    }
}
