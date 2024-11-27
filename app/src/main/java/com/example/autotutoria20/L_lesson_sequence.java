//package com.example.autotutoria20;
//
//import android.util.Log;
//
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.Tasks;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class L_lesson_sequence {
//
//    // Enum for step types
////    public enum StepType {
////        PRE_TEST,
////        TEXT,
////        VIDEO,
////        POST_TEST
////    }
//
////    // Method to get the number of steps for a given lesson and module
////    public static int getNumberOfSteps(String lessonModule) {
////        Map<String, StepType[]> lessonSequences = getLessonSequences();
////        if (lessonSequences.containsKey(lessonModule)) {
////            return lessonSequences.get(lessonModule).length;
////        }
////        return 0; // Return 0 if the lessonModule is not found
////    }
//
//    // Method to get the number of text lessons in the lessonSequence
//    public static int countTextLessons(L_lesson_sequence.StepType[] stepSequence) {
//        int textLessonCount = 0;
//        for (L_lesson_sequence.StepType step : stepSequence) {
//            if (step == StepType.TEXT) {
//                textLessonCount++;
//            }
//        }
//        return textLessonCount;
//    }
//
//    // Retrieve step sequences from Firestore
////    public static void getLessonSteps(String module, String lesson) {
////        FirebaseFirestore db = FirebaseFirestore.getInstance();
////        String TAG = "getLessonSteps()";
////
////        // Path to the document
////        db.collection("Lessons Sequence").document(module)
////                .get()
////                // how do I go to the lesson?
////                // data structure:
////                // Lesson N [Array]
////                // ---> steps
////                .addOnSuccessListener(documentSnapshot -> {
////                    if (documentSnapshot.exists()) {
////
////                        // Get the "steps" array
////                        List<String> steps = (List<String>) documentSnapshot.get("steps");
////                        if (steps != null) {
////                            Log.e(TAG, "steps != null!: " + steps);
////                        } else {
////                            Log.e(TAG, ("Steps array is null"));
////                        }
////                    } else {
////                        Log.e(TAG, "Document does not exist");
////                    }
////                });
////    }
//
//
//    // How to make this dynamic?!?!??!
//    // Map to store lesson sequences
//    public static Map<String, StepType[]> getLessonSequences() {
//        Map<String, StepType[]> lessonSequences = new HashMap<>();
//
//        /* =============== Module 1 =============== */
//        lessonSequences.put("M1_Lesson 1", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
////                StepType.TEXT, // Page 3
////                StepType.TEXT, // Page 4
////                StepType.TEXT, // Page 5
////                StepType.TEXT, // Page 6
////                StepType.TEXT, // Page 7
////                StepType.TEXT, // Page 8
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M2_Lesson 1", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//
//        });
//
//        lessonSequences.put("M3_Lesson 1", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M4_Lesson 1", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        /* =============== Module 2 =============== */
//        lessonSequences.put("M1_Lesson 2", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        /* =============== Module 3 =============== */
//        lessonSequences.put("M1_Lesson 3", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M2_Lesson 3", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
////        lessonSequences.put("M3_Lesson 3", new StepType[]{
////                StepType.PRE_TEST,
////                StepType.TEXT,
//////                StepType.TEXT,
//////                StepType.VIDEO,
////                StepType.POST_TEST,
//////                StepType.RESULT
////        });
//
//        /* =============== Module 4 =============== */
//        lessonSequences.put("M1_Lesson 4", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
//                StepType.TEXT, // Page 5
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M2_Lesson 4", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
//                StepType.TEXT, // Page 5
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
////        lessonSequences.put("M3_Lesson 4", new StepType[]{
////                StepType.PRE_TEST,
////                StepType.TEXT,
//////                StepType.TEXT,
//////                StepType.VIDEO,
////                StepType.POST_TEST,
//////                StepType.RESULT
////        });
//
//        /* =============== Module 5 =============== */
//        lessonSequences.put("M1_Lesson 5", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M2_Lesson 5", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M3_Lesson 5", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        /* =============== Module 6 =============== */
//        lessonSequences.put("M1_Lesson 6", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M2_Lesson 6", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                  StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M3_Lesson 6", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                  StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        /* =============== Module 7 =============== */
//        lessonSequences.put("M1_Lesson 7", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
//                StepType.TEXT, // Page 5
//                  StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        /* =============== Module 8 =============== */
//        lessonSequences.put("M1_Lesson 8", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
////                StepType.TEXT,
//                  StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M2_Lesson 8", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
//                StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        lessonSequences.put("M3_Lesson 8", new StepType[]{
//                StepType.PRE_TEST,
//                StepType.TEXT, // Page 1
//                StepType.TEXT, // Page 2
//                StepType.TEXT, // Page 3
//                StepType.TEXT, // Page 4
//                StepType.TEXT, // Page 5
//                StepType.TEXT, // Page 6
//                  StepType.VIDEO,
//                StepType.POST_TEST
//        });
//
//        return lessonSequences;
//    }
//
//    public interface OnVideoLinkRetrievedCallback {
//        void onSuccess(String videoLink);
//        void onFailure(Exception e);
//    }
//
//
//    public static String getLessonVideoLinks2(String module, String lesson) {
//        String TAG = "getLessonVideoLinks2";
//        String videoLink = null;  // Default to null if no link is found
//
//        Log.d(TAG, "Module: " + module);
//        Log.d(TAG, "Lesson: " + lesson);
//
//        Log.i(TAG, "t_YoutubeLinkFromDatabase.youtubeLinksMap.get(" + module + "_" + lesson);
//        videoLink = t_YoutubeLinkFromDatabase.youtubeLinksMap.get(module + "_" + lesson);
//
//        return videoLink; // This will return the video link (or null if it wasn't found)
//    }
//
//
//
//
//
//    // Map to store YouTube links for each lesson
//    public static Map<String, String> getLessonVideoLinks() {
//
//
//        // Reference to Firestore document
////        DocumentReference userRef = db.collection("Youtube Links")
//
//        Map<String, String> lessonVideoLinks = new HashMap<>();
//
//        // Add YouTube links for each lesson
//        lessonVideoLinks.put("M1_Lesson 1", "92O_Hc6Yz5M");
//        lessonVideoLinks.put("M2_Lesson 1", "noE3dkfamAU");
//        lessonVideoLinks.put("M3_Lesson 1", "Lw2aVmV6JyM");
//        lessonVideoLinks.put("M4_Lesson 1", "csCFyTz8WiE");
//
//        lessonVideoLinks.put("M1_Lesson 2", "PRDSdzRI3GQ");
//
//        lessonVideoLinks.put("M1_Lesson 3", "2uxZ6ksACGY");
//        lessonVideoLinks.put("M2_Lesson 3", "0qg1jb7HS4E");
////        lessonVideoLinks.put("M3_Lesson 3", "");
//
//        lessonVideoLinks.put("M1_Lesson 4", "XyISYpKrNQw");
//        lessonVideoLinks.put("M2_Lesson 4", "IEMrT4SkqwY");
////        lessonVideoLinks.put("M3_Lesson 4", "");
//
//        lessonVideoLinks.put("M1_Lesson 5", "EYKJTsDwITk");
//        lessonVideoLinks.put("M2_Lesson 5", "0uu2Bc0fDUQ");
//        lessonVideoLinks.put("M3_Lesson 5", "8Zb8-ba8HOI");
//
//        lessonVideoLinks.put("M1_Lesson 6", "Dj0UzRJ6VkU");
//        lessonVideoLinks.put("M2_Lesson 6", "b7Xr0L4cFgc");
//        lessonVideoLinks.put("M3_Lesson 6", "zfFXEpd7Ow4");
//
//        lessonVideoLinks.put("M1_Lesson 7", "nny3UsS2zOI");
//
//        lessonVideoLinks.put("M1_Lesson 8", "L8rVEZdyBvc");
//        lessonVideoLinks.put("M2_Lesson 8", "nKiimePcNzs");
//        lessonVideoLinks.put("M3_Lesson 8", "ufaQ_S6IfbM");
//
//        return lessonVideoLinks;
//
//    }
//
//    public static int getRemainingTextLessons(L_lesson_sequence.StepType[] stepSequence, int currentStep) {
//        int textLessonCount = 0;
//        for (int i = currentStep + 1; i < stepSequence.length; i++) {
//            if (stepSequence[i] == StepType.TEXT) {
//                textLessonCount++;
//            }
//        }
//        return textLessonCount;
//    }
//
//}
