package com.example.autotutoria20;

public class z_Lesson_steps
{
    public static final int[] lesson_1_steps = {4, 6, 6}; // Adjust these values accordingly
    public static final int lesson_1_complete = 16;
    public static final int[] lesson_2_steps = {4, 5, 6}; // Adjust these values accordingly
    public static final int lesson_2_complete = 15;
    public static final int[] lesson_3_steps = {3, 4, 5, 6}; // Adjust these values accordingly
    public static final int lesson_3_complete = 18;
    public static final int[] lesson_4_steps = {6, 7, 8}; // Adjust these values accordingly
    public static final int lesson_4_complete = 21;

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
        }
        return new int[0];
    }
}
