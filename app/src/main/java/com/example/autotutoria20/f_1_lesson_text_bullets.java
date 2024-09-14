package com.example.autotutoria20;

import android.util.Log;

public class f_1_lesson_text_bullets {

    // bat ko nga ba pinarameter yung pageNumber di ko rin alam :D
    public static int[][] getIndentation(String key, int pageNumber) {
        String TAG = "getIndentation(String key)";

        Log.e(TAG, "Key: " + key + ", Page Number: " + pageNumber);

        // Check if key is long enough
        if (key.length() < 11) {
            Log.e(TAG, "Invalid key length, key must be at least 11 characters long.");
            return new int[0][0]; // Return empty array to avoid IndexOutOfBoundsException
        }

        // Convert char to int for module (charAt(1))
        int module = Character.getNumericValue(key.charAt(1));
        int lessonType = Character.getNumericValue(key.charAt(10));

        // Validate lessonType
        if (lessonType == -1) {
            Log.e(TAG, "Invalid lesson type.");
            return new int[0][0];
        }

        Log.e(TAG, "Module: " + module + ", Lesson Type: " + lessonType);

        switch (lessonType) {
            case 1:
                switch (module) {
                    case 1: return module1_1;
                    case 2: return module1_2;
                    case 3: return module1_3;
                    case 4: return module1_4;
                    default:
                        Log.e(TAG, "Invalid module number: " + module);
                        return new int[0][0];
                }
            case 2:
                if (module == 1) return module2_1;
                Log.e(TAG, "Invalid module number for lesson type 2: " + module);
                break;
            case 3:
                switch (module) {
                    case 1: return module3_1;
                    case 2: return module3_2;
                    default:
                        Log.e(TAG, "Invalid module number for lesson type 3: " + module);
                        return new int[0][0];
                }
            case 4:
                switch (module) {
                    case 1: return module4_1;
                    case 2: return module4_2;
                    default:
                        Log.e(TAG, "Invalid module number for lesson type 4: " + module);
                        return new int[0][0];
                }
            case 5:
                switch (module) {
                    case 1: return module5_1;
                    case 2: return module5_2;
                    case 3: return module5_3;
                    default:
                        Log.e(TAG, "Invalid module number for lesson type 5: " + module);
                        return new int[0][0];
                }
            case 6:
                switch (module) {
                    case 1: return module6_1;
                    case 2: return module6_2;
                    case 3: return module6_3;
                    default:
                        Log.e(TAG, "Invalid module number for lesson type 6: " + module);
                        return new int[0][0];
                }
            case 7:
                if (module == 1) return module7_1;
                Log.e(TAG, "Invalid module number for lesson type 7: " + module);
                break;
            case 8:
                switch (module) {
                    case 1: return module8_1;
                    case 2: return module8_2;
                    case 3: return module8_3;
                    default:
                        Log.e(TAG, "Invalid module number for lesson type 8: " + module);
                        return new int[0][0];
                }
            default:
                Log.e(TAG, "Invalid lesson type: " + lessonType);
                return new int[0][0];
        }

        Log.e(TAG, "No valid array found for the given key and page number.");
        return new int[0][0];
    }


    static int[][] module1_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {1, 1, 2, 2, 2, 0, 0, 0},
        /* Page 3 */ {1, 1, 1, 2, 0, 0, 0, 0},
        /* Page 4 */ {1, 1, 0, 0, 0, 0, 0, 0},
        /* Page 5 */ {1, 1, 0, 0, 0, 0, 0, 0},
        /* Page 6 */ {1, 2, 1, 2, 1, 2, 0, 0},
        /* Page 7 */ {1, 1, 0, 0, 0, 0, 0, 0},
        /* Page 8 */ {1, 1, 1, 2, 0, 0, 0, 0}
    };

    static int[][] module1_2 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 1, 0, 1, 0, 1, 0, 0},
        /* Page 3 */ {0, 1, 0, 1, 0, 1, 0, 0}
    };

    static int[][] module1_3 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 1, 1, 1, 0, 1, 1, 1, 0}
    };
    static int[][] module1_4 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 1, 1, 1, 0, 0, 0, 0}
    };
    static int[][] module2_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 0, 1, 0, 0, 0}
    };
    static int[][] module3_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 1, 1, 0, 0, 0}
    };
    static int[][] module3_2 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 1, 1, 0, 0, 0}
    };
    static int[][] module4_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 2, 2, 0, 0, 0},
        /* Page 4 */ {0, 0, 1, 1, 0, 0, 0, 0},
        /* Page 5 */ {0, 0, 1, 1, 0, 0, 0, 0}
    };
    static int[][] module4_2 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 1, 1, 1, 1, 0, 0},
        /* Page 3 */ {0, 0, 1, 1, 1, 1, 2, 2, 2, 0},
        /* Page 4 */ {0, 0, 1, 1, 1, 1, 2, 2, 2, 0},
        /* Page 5 */ {0, 0, 1, 1, 1, 1, 2, 2, 0}
    };
    static int[][] module5_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 1, 2, 2, 0, 0},
        /* Page 3 */ {0, 1, 2, 2, 2, 0}
    };
    static int[][] module5_2 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 1, 1, 1, 1, 0}
    };
    static int[][] module5_3 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
        /* Page 3 */ {0, 0, 1, 1, 1, 1, 1, 1, 1}
    };
    static int[][] module6_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 0, 1, 0, 1, 1, 0},
        /* Page 4 */ {0, 0, 0, 1, 0, 1, 1, 0},
    };
    static int[][] module6_2 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 2, 0, 2, 0, 0}
    };
    static int[][] module6_3 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 1, 1, 1, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 2, 1, 2, 1, 2, 1, 2}
    };
    static int[][] module7_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 1, 1, 0, 1, 1, 1},
        /* Page 4 */ {0, 0, 1, 1, 1, 0, 0, 0},
        /* Page 5 */ {0, 0, 0, 1, 1, 1, 0, 1, 1}
    };
    static int[][] module8_1 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 2, 2, 2, 2, 2, 0},
        /* Page 4 */ {0, 1, 2, 2, 2, 0}
    };
    static int[][] module8_2 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 0, 1, 1, 2, 2, 2, 0, 2, 2, 2, 2},
        /* Page 4 */ {0, 1, 1, 0, 0, 0, 0, 0}
    };
    static int[][] module8_3 = {
        /* Page 1 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 2 */ {0, 0, 1, 0, 0, 0, 0, 0},
        /* Page 3 */ {0, 1, 2, 2, 2, 2, 0, 0},
        /* Page 4 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 5 */ {0, 0, 0, 0, 0, 0, 0, 0},
        /* Page 6 */ {0, 0, 1, 1, 1, 1, 0, 0}
    };
}
