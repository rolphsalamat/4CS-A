package com.example.autotutoria20;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class z_Lesson_steps
{
    public static final int total_module_count = 4; // Kung ilang cards sa main menu
    public static final int[] lesson_1_steps = {4, 6, 6}; // Kung ilang cards sa Lesson 1
    public static final int[] lesson_2_steps = {4, 5, 6}; // Kung ilang cards sa Lesson 2
    public static final int[] lesson_3_steps = {3, 4, 5, 6}; // Kung ilang cards sa Lesson 3
    public static final int[] lesson_4_steps = {6, 7, 8}; // Kung ilang cards sa Lesson 4

    // Private constructor to prevent instantiation
    private z_Lesson_steps() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static int[] getLessonSteps(int lessonNumber) {
        switch (lessonNumber) {
            case 1:
                return lesson_1_steps;
            case 2:
                return lesson_2_steps;
            case 3:
                return lesson_3_steps;
            case 4:
                return lesson_4_steps;
            default:
                return new int[0];
        }
    }

    public static int[] getAllLessonSteps() {
        List<Integer> allSteps = new ArrayList<>();
        for (int step : lesson_1_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_2_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_3_steps) {
            allSteps.add(step);
        }
        for (int step : lesson_4_steps) {
            allSteps.add(step);
        }
        int[] result = new int[allSteps.size()];
        for (int i = 0; i < allSteps.size(); i++) {
            result[i] = allSteps.get(i);
            Log.e("getAllLessonSteps", "Step Count: " + i);
        }
        return result;
    }
}
