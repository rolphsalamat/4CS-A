package com.example.autotutoria20;

import java.util.HashMap;
import java.util.Map;

public class LessonSequence {

    // Enum for step types
    public enum StepType {
        PRE_TEST,
        TEXT,
        VIDEO,
        POST_TEST
    }

    // Method to get the number of steps for a given lesson and module
    public static int getNumberOfSteps(String lessonModule) {
        Map<String, StepType[]> lessonSequences = getLessonSequences();
        if (lessonSequences.containsKey(lessonModule)) {
            return lessonSequences.get(lessonModule).length;
        }
        return 0; // Return 0 if the lessonModule is not found
    }

    // Method to get the number of text lessons in the lessonSequence
    public static int countTextLessons(LessonSequence.StepType[] stepSequence) {
        int textLessonCount = 0;
        for (LessonSequence.StepType step : stepSequence) {
            if (step == StepType.TEXT) {
                textLessonCount++;
            }
        }
        return textLessonCount;
    }


    // Map to store lesson sequences
    public static Map<String, StepType[]> getLessonSequences() {
        Map<String, StepType[]> lessonSequences = new HashMap<>();

        /* =============== Module 1 =============== */
        lessonSequences.put("M1_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M2_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M4_Lesson 1", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 2 =============== */
        lessonSequences.put("M1_Lesson 2", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 3 =============== */
        lessonSequences.put("M1_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M2_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 3", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 4 =============== */
        lessonSequences.put("M1_Lesson 4", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M2_Lesson 4", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 4", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 5 =============== */
        lessonSequences.put("M1_Lesson 5", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M2_Lesson 5", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 5", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 6 =============== */
        lessonSequences.put("M1_Lesson 6", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M2_Lesson 6", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 6", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 7 =============== */
        lessonSequences.put("M1_Lesson 7", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });

        /* =============== Module 8 =============== */
        lessonSequences.put("M1_Lesson 8", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M2_Lesson 8", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });
        lessonSequences.put("M3_Lesson 8", new StepType[]{
                StepType.PRE_TEST,
                StepType.TEXT,
//                StepType.TEXT,
//                StepType.VIDEO,
                StepType.POST_TEST
        });

        return lessonSequences;
    }

    // Map to store YouTube links for each lesson
    public static Map<String, String> getLessonVideoLinks() {
        Map<String, String> lessonVideoLinks = new HashMap<>();

        // Add YouTube links for each lesson
        lessonVideoLinks.put("M1_Lesson 1", "https://www.youtube.com/watch?v=92O_Hc6Yz5M");
        lessonVideoLinks.put("M2_Lesson 1", "https://www.youtube.com/watch?v=noE3dkfamAU");
        lessonVideoLinks.put("M3_Lesson 1", "https://www.youtube.com/watch?v=Lw2aVmV6JyM");
        lessonVideoLinks.put("M4_Lesson 1", "https://www.youtube.com/watch?v=csCFyTz8WiE");

        lessonVideoLinks.put("M1_Lesson 2", "https://www.youtube.com/watch?v=PRDSdzRI3GQ");

        lessonVideoLinks.put("M1_Lesson 3", "https://www.youtube.com/watch?v=2uxZ6ksACGY");
        lessonVideoLinks.put("M2_Lesson 3", "https://www.youtube.com/watch?v=0qg1jb7HS4E");
        lessonVideoLinks.put("M3_Lesson 3", "");

        lessonVideoLinks.put("M1_Lesson 4", "https://www.youtube.com/watch?v=XyISYpKrNQw");
        lessonVideoLinks.put("M2_Lesson 4", "https://www.youtube.com/watch?v=IEMrT4SkqwY");
        lessonVideoLinks.put("M3_Lesson 4", "");

//        https://www.youtube.com/watch?v=OymdHfzCg0c
        lessonVideoLinks.put("M1_Lesson 5", "");
//        https://www.youtube.com/watch?v=6Mb11KYTmS0
        lessonVideoLinks.put("M2_Lesson 5", "");
//        https://www.youtube.com/watch?v=661W0SJl_5Q
        lessonVideoLinks.put("M3_Lesson 5", "");

//        https://www.youtube.com/watch?v=79cIoa6J3O8
        lessonVideoLinks.put("M1_Lesson 6", "");
//        https://www.youtube.com/watch?v=GGbYfum8vUM
        lessonVideoLinks.put("M2_Lesson 6", "");
        https://www.youtube.com/watch?v=dCVcWztylLs
        lessonVideoLinks.put("M3_Lesson 6", "");

        lessonVideoLinks.put("M1_Lesson 7", "");

        lessonVideoLinks.put("M1_Lesson 8", "");
        lessonVideoLinks.put("M2_Lesson 8", "");
        lessonVideoLinks.put("M3_Lesson 8", "");

        return lessonVideoLinks;
    }

    public static int getRemainingTextLessons(LessonSequence.StepType[] stepSequence, int currentStep) {
        int textLessonCount = 0;
        for (int i = currentStep + 1; i < stepSequence.length; i++) {
            if (stepSequence[i] == StepType.TEXT) {
                textLessonCount++;
            }
        }
        return textLessonCount;
    }

}
