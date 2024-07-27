package com.example.autotutoria20;

import java.util.HashMap;
import java.util.Map;

public class LessonSequence {

    public static Map<String, StepType[]> getLessonSequences() {
        Map<String, StepType[]> lessonSequences = new HashMap<>();

        // Example lesson sequence for Module 1, Lesson 1
        lessonSequences.put("M1_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.VIDEO,
                StepType.TEXT,
                StepType.TEXT,
                StepType.POST_TEST
        });

        // Add more sequences for other lessons as needed

        return lessonSequences;
    }
}

