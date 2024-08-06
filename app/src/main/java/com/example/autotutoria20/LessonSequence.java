package com.example.autotutoria20;

import java.util.HashMap;
import java.util.Map;

public class LessonSequence {

    // Enum for step types
    public enum StepType {
        PRE_TEST,
        VIDEO,
        TEXT,
        POST_TEST
    }

    // Map to store lesson sequences
    public static Map<String, StepType[]> getLessonSequences() {
        Map<String, StepType[]> lessonSequences = new HashMap<>();

        /* ===== Module 1 ===== */
        lessonSequences.put("M1_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M1_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M1_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M1_Lesson 4", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 2 ===== */
        lessonSequences.put("M2_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 3 ===== */
        lessonSequences.put("M3_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 4 ===== */
        lessonSequences.put("M4_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M4_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M4_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 5 ===== */
        lessonSequences.put("M5_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M5_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M5_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 6 ===== */
        lessonSequences.put("M6_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M6_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M6_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 7 ===== */
        lessonSequences.put("M7_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* ===== Module 8 ===== */
        lessonSequences.put("M8_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M8_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M8_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        return lessonSequences;
    }

    // Map to store YouTube links for each lesson
    public static Map<String, String> getLessonVideoLinks() {
        Map<String, String> lessonVideoLinks = new HashMap<>();

        // Add YouTube links for each lesson
        lessonVideoLinks.put("M1_Lesson 1", "https://www.youtube.com/watch?v=06kbuUQlvbw");
        lessonVideoLinks.put("M1_Lesson 2", "");
        lessonVideoLinks.put("M1_Lesson 3", "");
        lessonVideoLinks.put("M1_Lesson 4", "");

        lessonVideoLinks.put("M2_Lesson 1", "");

        lessonVideoLinks.put("M3_Lesson 1", "");
        lessonVideoLinks.put("M3_Lesson 2", "");
        lessonVideoLinks.put("M3_Lesson 3", "");

        lessonVideoLinks.put("M4_Lesson 1", "");
        lessonVideoLinks.put("M4_Lesson 2", "");
        lessonVideoLinks.put("M4_Lesson 3", "");

        lessonVideoLinks.put("M5_Lesson 1", "https://www.youtube.com/watch?v=OymdHfzCg0c");
        lessonVideoLinks.put("M5_Lesson 2", "https://www.youtube.com/watch?v=6Mb11KYTmS0");
        lessonVideoLinks.put("M5_Lesson 3", "https://www.youtube.com/watch?v=661W0SJl_5Q");

        lessonVideoLinks.put("M6_Lesson 1", "https://www.youtube.com/watch?v=79cIoa6J3O8");
        lessonVideoLinks.put("M6_Lesson 2", "https://www.youtube.com/watch?v=GGbYfum8vUM");
        lessonVideoLinks.put("M6_Lesson 3", "https://www.youtube.com/watch?v=dCVcWztylLs");

        lessonVideoLinks.put("M7_Lesson 1", "");

        lessonVideoLinks.put("M8_Lesson 1", "");
        lessonVideoLinks.put("M8_Lesson 2", "");
        lessonVideoLinks.put("M8_Lesson 3", "");

        return lessonVideoLinks;
    }
}
