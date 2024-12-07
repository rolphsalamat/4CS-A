package com.example.autotutoria20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class d_Lesson_container extends AppCompatActivity implements
        f_0_lesson_pre_test.PreTestCompleteListener,
        f_1_lesson_text.OnNextButtonClickListener,
        f_1_lesson_text.TextLessonCompleteListener,
        f_3_lesson_post_test.PostTestCompleteListener
{

    private int moduleIndex;
    private int lessonIndex;
    private Boolean isProgressiveMode;
    private Runnable delayedAction;
    private static Boolean isResumed = false;
    public static boolean isPreTestComplete = false;
    public static boolean isPostTestComplete = false;
    private Handler handler = new Handler();
    private Runnable showNextButtonRunnable;
    private static final String TAG = "Module3Steps";
    public int pageNumber = 1; // 1 sya originally
    private GridLayout gridLayout;
    private AlertDialog dialog;
    private AlertDialog exitDialog;
    private int numberOfTextLessons = 0; // Declare here
    static int currentStep = 0;
    static int currentReturnStep = 0;
    private int returnStep = 0;
    private int numberOfSteps = 0;
    private FirebaseFirestore db;
    public static String learningMode;
    public static String currentLesson;
    public static String currentModule;
    public static boolean lessonPassed;
    public static boolean isPassed;
    private int furthestStep = 0; // Track the furthest step reached

    public static Boolean isCompleted;
//    private Boolean isLessonPassed;
    private Button skipVideoButton;
    public static Button nextButton;
    private Button backButton;
    static TextView txtSecondsRemaining;
    private ShapeableImageView currentButton;
//    private f_2_lesson_video videoLesson;
    private StepType[] stepSequence;
    private static ViewPager viewPager;
    private L_lesson_handler pagerAdapter;
    private boolean isLessonFinished = false;
    private long backPressedTime; // Variable to track back press time
    private Toast backToast;
    private c_Lesson_feedback feedback;
    private LinearLayout tokenLayout;
    private x_bkt_algorithm bktAlgo;
//    public static long token;
    public static int d_lesson_container_token;
    public static TextView tokenCount;

    SharedPreferences sharedPreferences;
    String userId;

    // Enum for step types
    public enum StepType {
        PRE_TEST,
        TEXT,
        VIDEO,
        POST_TEST
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_lesson_container);

        currentStep = 0;
        currentReturnStep = 0;
        returnStep = 0;
        numberOfSteps = 0;
        isResumed = false;

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        learningMode = sharedPreferences.getString("learningMode", null);
        currentLesson = sharedPreferences.getString("currentLesson", null);
        currentModule = sharedPreferences.getString("currentModule", null);
        isCompleted = sharedPreferences.getBoolean("isCompleted", false);
//        isLessonPassed = sharedPreferences.getBoolean("isLessonPassed", false);

        String HEY = "EVO PLUS GOLD";

        Log.i(HEY, "GENAMIE | Learning Mode: " + learningMode); // Progressive Mode : Free Use Mode
        Log.i(HEY, "GENAMIE | Current Lesson: " + currentLesson); // Lesson 1 -> Lesson 8
        Log.i(HEY, "GENAMIE | Current Module: " + currentModule); // M1 -> M4
        Log.i(HEY, "GENAMIE | isComplete: " + isCompleted); // true : false

        if (isCompleted) {
            // show Dialog "You already finished this lesson, do you want to reset your score and retake this lesson?"
            showResetLessonDialog();
        }

        String formattedModule = "Module " + currentModule.charAt(1);

        stepSequence = getStepsAsStepTypeArray(formattedModule, currentLesson);
        Log.i(HEY, "GENAMIE | stepSequence: " + stepSequence);

        // Log the step sequence
        if (stepSequence != null) {
            numberOfSteps = stepSequence.length;
            Log.i(HEY, "GENAMIE | numberOfSteps: " + numberOfSteps);
        } else {
            Log.e(TAG, "Step sequence is null for key: " + currentModule + "_" + currentLesson);
        }

        Log.i("HEY", "GENAMIE | stepSequence.length: " + stepSequence.length + " | numberOfSteps: " + numberOfSteps);
        Log.i("HEY", "GENAMIE | Path: users/" + userId + "/" + learningMode + "/" + currentLesson + "/" + currentModule + ".Progress");

        db.collection("users")
                .document(userId)
                .collection(learningMode) // Progressive Mode or Free Use Mode
                .document(currentLesson) // e.g., Lesson 1
                .get()
                .addOnCompleteListener(documentSnapshot -> {
                    if (documentSnapshot.isComplete()) {
                        Log.i("HEY", "GENAMIE | Document fetch complete.");
                        Map<String, Object> lessonData = documentSnapshot.getResult().getData();
                        if (lessonData != null) {
                            Log.i("HEY", "GENAMIE | Lesson Data: " + lessonData.toString());

                            // Get the module data
                            Map<String, Object> moduleData = (Map<String, Object>) lessonData.get(currentModule);
                            if (moduleData != null) {
                                Log.i("HEY", "GENAMIE | Module data found for key: " + currentModule);

                                // Check for Progress key
                                if (moduleData.containsKey("Progress")) {
                                    Object progress = moduleData.get("Progress");
                                    if (progress != null) {
                                        Log.i("HEY", "GENAMIE | Progress found: " + progress.toString());
                                        int currentStep = Integer.parseInt(progress.toString());

                                        if (currentStep == 0 || currentStep == numberOfSteps)
                                            Log.i("HEY", "GENAMIE | Lesson is either finished or not yet progressed.");
                                            // proceed from the beginning
                                        else
                                            showResumeDialog(currentStep);

                                    } else {
                                        Log.i("HEY", "GENAMIE | Progress is null.");
                                    }
                                } else {
                                    Log.i("HEY", "GENAMIE | Progress key does not exist in module data.");
                                }
                            } else {
                                Log.i("HEY", "GENAMIE | Module data is null for key: " + currentModule);
                            }
                        } else {
                            Log.i("HEY", "GENAMIE | Lesson data is null.");
                        }
                    } else {
                        Log.i("HEY", "GENAMIE | Lesson Document Not Found.");
                    }
                })
                .addOnFailureListener(e -> Log.e("HEY", "GENAMIE | Error Retrieving Data", e));

        Log.i("HEY", "GENAMIE | Done na yung query sa database");
        tokenLayout = findViewById(R.id.tokenLayout);
        tokenCount = findViewById(R.id.token);
        tokenCount.setText("" + b_main_0_menu.token);

        d_lesson_container_token = (int) b_main_0_menu.token;

        tokenLayout.setVisibility(View.GONE);
        tokenLayout.setEnabled(false);

        isPreTestComplete = false;
        isPostTestComplete = false;

        c_Lesson_feedback.resetResult();

        gridLayout = findViewById(R.id.gridLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);  // Originally 1

        isProgressiveMode = learningMode.equalsIgnoreCase("Progressive Mode");

        boolean finalIsProgressiveMode = isProgressiveMode;
        String learningMode = isProgressiveMode ? "Progressive Mode" : "Free Use Mode";

        bktAlgo = new x_bkt_algorithm();

        // Get the Singleton instance
        bktAlgo = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);

        bktAlgo.initializeBKTScores(learningMode, currentLesson, currentModule);

        pagerAdapter = new L_lesson_handler(
                getSupportFragmentManager(),
                stepSequence,
                currentModule + "_" + currentLesson,
                learningMode,
                pageNumber);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for this issue
            }

            @Override
            public void onPageSelected(int position) {

//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            // Log.(TAG, "Displayed fragment position: " + position);

                backButton.setVisibility(View.GONE);
                backButton.setEnabled(false);

                // Adjust the bottom margin of the ViewPager
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();

                // Default behavior of the 3 except video lesson..
                params.bottomMargin = 0;  // Adjust this value as needed

                viewPager.setLayoutParams(params);

                nextButton.setVisibility(View.GONE);
                nextButton.setEnabled(false);

//                txtSecondsRemaining.setVisibility(View.GONE);
//                txtSecondsRemaining.setEnabled(false);

                // Get the current fragment
                Fragment currentFragment = getCurrentFragment();

//                // Check if the last fragment was the video lesson and stop the video
//                if (videoLesson != null && !(currentFragment instanceof f_2_lesson_video)) {
//                // Log.(TAG, "Stopping video playback as we're leaving the video lesson.");
//                    videoLesson.stopVideoPlayback();
//                }

                // Check if the last fragment was the video lesson and stop the video
                if (f_2_lesson_video.webView != null && !(currentFragment instanceof f_2_lesson_video)) {
                    // Log.(TAG, "Stopping video playback as we're leaving the video lesson.");
                    f_2_lesson_video.stopVideoPlayback();
                }

                tokenLayout.setVisibility(View.GONE);
                tokenLayout.setEnabled(false);

                if (currentFragment instanceof f_0_lesson_pre_test) {

                    // ano gagawin by default? pwede namang wala, kasi wala naman talaga tong code na to nung una..
                    // Log.(TAG, "Pre Test");

//                    txtSecondsRemaining.setVisibility(View.VISIBLE);
//                    txtSecondsRemaining.setEnabled(true);

                    backButton.setVisibility(View.GONE);
                    backButton.setEnabled(false);

                    // disable tong next button??
                    nextButton.setVisibility(View.GONE);
                    nextButton.setEnabled(false);
                    nextButton.setText("HUH?!?!");

                    // pero pano pag tapos na??
                    if (isPreTestComplete) {

                        f_0_lesson_pre_test.currentButton.setEnabled(true);
                        f_0_lesson_pre_test.currentButton.setVisibility(View.VISIBLE);

                        nextButton.setText("Lesson Text | Next");

                        // Create a dialog to show the user's score
                        AlertDialog.Builder builder = new AlertDialog.Builder(d_Lesson_container.this);
                        builder.setTitle("Pre-Test Complete!");

                        String message = "You may answer the pre-test and your score will not be affected," +
                                " feel free to return to answer all pre-test questions ^_^";

                        builder.setMessage(message);

                        // Add a button to dismiss the dialog
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Close the dialog
                            }
                        });

                        builder.setCancelable(false);

                        // Create and show the dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        // or simply disable the whole pre-test??
                        // how??
                        // ewan ko tanginamo
                    }

                }

                if (currentFragment instanceof f_1_lesson_text) {

                    // Log.(TAG, "Text Lesson");

//                    // Execute additional code after a delay of 600 milliseconds
//                    handler.postDelayed(() -> {
//
//                        // disable if want to show contentTextViews  one by one
//                        nextButton.setEnabled(true);
//                        nextButton.setVisibility(View.VISIBLE);
//
//                    }, 3000);

                    if (viewPager.getCurrentItem() == 1) {
                        backButton.setVisibility(View.GONE);
                        backButton.setEnabled(false);
                    } else {
                        backButton.setVisibility(View.VISIBLE);
                        backButton.setEnabled(true);
                    }

                    // auto-next??
//                    clickCenter(1);

                }

                if (currentFragment instanceof f_2_lesson_video) {

                    handler.removeCallbacksAndMessages(null);

                    params.bottomMargin = 225;  // Adjust this value as needed
                    viewPager.setLayoutParams(params);

                    Handler handler = new Handler(Looper.getMainLooper());
                    delayedAction = new Runnable() {
                        @Override
                        public void run() {
                            // Show and enable the button after 2500ms
                            nextButton.setVisibility(View.VISIBLE);
                            nextButton.setEnabled(true);
                        }
                    }; // Post the delay
                    handler.postDelayed(delayedAction, 3000); // Delay in milliseconds

                    nextButton.setVisibility(View.GONE);
                    nextButton.setEnabled(false);

                    backButton.setVisibility(View.VISIBLE);
                    backButton.setEnabled(true);

                }

                if (currentFragment instanceof f_3_lesson_post_test) {

//                    // Cancel the Runnable
//                    handler.removeCallbacks(delayedAction);

                    handler.removeCallbacksAndMessages(null);

                    // just in case lang, and also anti error
                    if (f_2_lesson_video.webView != null)
                        f_2_lesson_video.stopVideoPlayback();

                    nextButton.setVisibility(View.GONE);
                    nextButton.setEnabled(false);

//                    // Later, when you need to revert back
//                    if (originalOrientation == Configuration.ORIENTATION_PORTRAIT) {
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    } else if (originalOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    } else {
//                        // Default to portrait if the orientation isn't clear
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    }

                    Log.i(TAG, "Post-Test");
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    // Cancel any pending tasks
                    handler.removeCallbacks(showNextButtonRunnable);

                    if (f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.HARD) {
                        tokenLayout.setVisibility(View.VISIBLE);
                        tokenLayout.setEnabled(true);
                    }

                    backButton.setVisibility(View.GONE);
                    backButton.setEnabled(false);

                    nextButton.setVisibility(View.GONE);
                    nextButton.setEnabled(false);

                }

                // else case eto sa if currentFragment ay video lesson
//                else {
//                    videoLesson = null; // Clear the videoLesson reference when not on a video lesson
//                }

                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed for this issue
            }
        });


        populateGridLayout();

        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButtonLayout);

//        // Initialize and handle the new button
//        skipVideoButton = findViewById(R.id.skip_video_tutorial);
//        skipVideoButton.setOnClickListener(v -> {
//            Log.i(TAG, "Skip Video Button clicked!");
//
//            // Set currentStep to the index of the POST_TEST fragment in stepSequence
//            for (int i = 0; i < stepSequence.length; i++) {
//                if (stepSequence[i] == StepType.POST_TEST) {
//                    currentStep = i; // Set the step to POST_TEST
//                    break;
//                }
//            }
//
//            populateGridLayout();
//
//            // Update the ViewPager to navigate to POST_TEST
//            viewPager.setCurrentItem(currentStep);
//        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });

        // nung naka-comment di na nawawala yung LAST Next button
        nextButton.setVisibility(View.GONE);
        nextButton.setEnabled(false);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackButtonClicked(); }
        });
        backButton.setVisibility(View.GONE);



        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    private void showResetLessonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(d_Lesson_container.this);
        builder.setTitle("Lesson Completed")
                .setMessage("You already finished this lesson. Do you want to reset your score and retake this lesson?")
                .setPositiveButton("Reset and Retake", (dialog, which) -> {
                    // Logic to reset the score and retake the lesson
                    resetLessonProgress();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetLessonProgress() {
        // Placeholder for resetting logic
        int moduleIndex = Integer.parseInt(String.valueOf(d_Lesson_container.currentModule.charAt(1)));
        int lessonIndex = Integer.parseInt(String.valueOf(d_Lesson_container.currentLesson.charAt(7)));
        Boolean isProgressiveMode = "Progressive Mode".equals(learningMode);
        x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
        Log.d("ResetLesson", "Lesson progress has been reset.");
        isCompleted = false;
        // Add your database update logic or other reset functionality here
    }


    private void showResumeDialog(int databaseCurrentStep) {
        new AlertDialog.Builder(d_Lesson_container.this)
                .setTitle("Resume Progress")
                .setMessage("You have saved progress at step " + databaseCurrentStep + ". Do you want to resume?")
                .setPositiveButton("Resume", (dialog, which) -> {
                    // Resume from the saved progress
                    Log.i("HEY", "GENAMIE | Resuming from step: " + databaseCurrentStep);
                    currentStep = databaseCurrentStep;
                    furthestStep = databaseCurrentStep;
                    currentReturnStep = databaseCurrentStep;
                    L_lesson_handler.pageNumber = currentStep;
                    viewPager.setCurrentItem(currentStep);
                    populateGridLayout(); // to set to currentStep
                    isResumed = true;
                })
                .setNegativeButton("Start Over", (dialog, which) -> {
                    // Restart progress
                    Log.i("HEY", "GENAMIE | Starting Over.");
                    currentStep = 0;
                })
                .show();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Handle landscape-specific adjustments
            Log.d("Orientation", "Switched to landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Handle portrait-specific adjustments
            Log.d("Orientation", "Switched to portrait");
        }
    }

//    public static void showDialog(String title, String message) {
//
////      Create a dialog to show the user's score
//        AlertDialog.Builder builder = new AlertDialog.Builder(d_Lesson_container.this);
//        builder.setTitle(title);
//
//        builder.setMessage(message);
//
//        // Add a button to dismiss the dialog
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss(); // Close the dialog
//            }
//        });
//
//        builder.setCancelable(false);
//
//        // Create and show the dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    // Log.("Generate Hint", "Generate Hint");
//
//    }

    // Method to retrieve the steps as StepType[] array
    public StepType[] getStepsAsStepTypeArray(String module, String lesson) {

        Log.i(TAG, "Coco Martin | Original Module: " + module);
        Log.i(TAG, "Coco Martin | Original Lesson: " + lesson);

        Log.i(TAG, "Coco Martin | Original Module.chartAt(7): " + module.charAt(7)); // Lesson N
        Log.i(TAG, "Coco Martin | Original Lesson.chartAt(7): " + lesson.charAt(7)); // Module N

        String newModule = "Module " + lesson.charAt(7);
        String newLesson = "Lesson " + module.charAt(7);

        Log.i(TAG, "Coco Martin | newModule: " + newModule);
        Log.i(TAG, "Coco Martin | newLesson: " + newLesson);

        Log.i(TAG, "Checking for newModule in lessonStepsMap: " + newModule);
        Log.i(TAG, "Checking for newLesson in module map: " + newLesson);


        Log.d(TAG, "Coco Martin | lessonStepsMap: " + t_LessonSequenceFromDatabase.lessonStepsMap);

        Log.i(TAG, "Coco Martin | lessonStepsMap.containsKey(" + newModule + ");");
        if (t_LessonSequenceFromDatabase.lessonStepsMap.containsKey(newModule)) {
            Map<String, String> lessonsMap = t_LessonSequenceFromDatabase.lessonStepsMap.get(newModule);

            if (lessonsMap == null) {
                Log.e(TAG, "lessonsMap is null for key: " + newLesson);
                return null; // Early exit to prevent further null pointer issues.
            }

            if (lessonsMap != null && lessonsMap.containsKey(newLesson)) {
                // Retrieve the steps as a string
                String stepsAsString = lessonsMap.get(newLesson);

                if (stepsAsString != null) {

                    Log.i(TAG, "Coco Martin | Module : " + module);
                    Log.i(TAG, "Coco Martin | Lesson : " + lesson);
                    Log.i(TAG, "Coco Martin | newModule: " + newModule);
                    Log.i(TAG, "Coco Martin | newLesson: " + newLesson);
                    Log.i(TAG, "Coco Martin | stepsAsString: " + stepsAsString);

                    // Split the string into individual steps
                    String[] steps = stepsAsString.split(", ");

                    // Convert the string steps to StepType enums
                    stepSequence = new StepType[steps.length];
                    for (int i = 0; i < steps.length; i++) {
                        try {
                            // Sanitize the step names to match the StepType enum values
                            String sanitizedStep = steps[i]

                                    /*
                                    1. Pre-Test
                                    ---> PRE_TEST
                                    !!! replace("-","_")

                                    2. Text Lesson
                                    ---> TEXT
                                    !!! replace(" Lesson", "")

                                    3. Video Lesson
                                    ---> VIDEO
                                    !!! replace(" Lesson", "")

                                    4. Post-Test
                                    ---> POST_TEST

                                     */
                                    .replace("-", "_") // Pre-Test && Post-Test
                                    .replace(" Lesson", "") // Text Lesson && Video Lesson
                                    .toUpperCase();

                        // Log.(TAG, "sanitizedStep: " + sanitizedStep);

                            // Now, try to convert the sanitized string to the corresponding StepType enum
                            stepSequence[i] = StepType.valueOf(sanitizedStep);

                            // Log the matched step
                        // Log.(TAG, "Step[" + i + "]: " + stepSequence[i]);

                        } catch (IllegalArgumentException e) {
                        // Log.("RetrieveSteps", "Invalid step type: " + steps[i]);
                        }

                    }
                }
            } else {
            // Log.("RetrieveSteps", "Lesson not found in module: " + lesson);
            }
        } else {
        // Log.("RetrieveSteps", "Module not found: " + module);
        }

        return stepSequence;
    }

    public static void resetModule(boolean isComplete, boolean isPassed) {


                        // Reset only if di pa tapos yung lesson..
                        // kelangan completed sya, pero dapat pasado din
                        // Case 1: Completed, not Passed - RETAKE
                        // Case 2: Completed, Passed - OK (do not reset)
                        // Case 3: InComplete, not Passed - RETAKE
                        // Case 4: InComplete, Passed - RETAKE (pwede kasi siyang passed if mataas ang Pre-Test)

                    // Log.("TAG", "Let's Check Case...");
                    // Log.("TAG", "Module: " + currentModule);
                    // Log.("TAG", "Lesson: " + currentLesson);

                        int moduleIndex = Integer.parseInt(String.valueOf(currentModule.charAt(1)));
                        int lessonIndex = Integer.parseInt(String.valueOf(currentLesson.charAt(7)));

                        boolean isProgressiveMode = learningMode.equalsIgnoreCase("Progressive Mode");

                        // Reset logic based on completion and passing status
                        if (isCompleted) {
                            if (!lessonPassed && !isResumed) {
                                // Case 1: Completed, not Passed - RETAKE
                            // Log.("TAG", "Case 1: Completed, not Passed - RETAKE");
                                c_Lesson_feedback.resetResult();
//                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("You completed the lesson but did not pass, please retake the lesson");
                            } else if (lessonPassed && !isResumed){
                            // Log.("TAG", "Case 2: Completed, Passed - OK (do nothing)");
                                // Case 2: Completed, Passed - OK (do nothing)
//                                showToast("You completed the lesson and passed! Great job!");
                            }
                        } else {
                            // If the lesson is incomplete
                            if (!lessonPassed && !isResumed) {
                            // Log.("TAG", "Case 3: Incomplete, not Passed - RETAKE");
                                // Case 3: Incomplete, not Passed - RETAKE
                                c_Lesson_feedback.resetResult();
//                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("you completed the lesson but failed, please retake it.");
                            } else if (lessonPassed && !isResumed){
                            // Log.("TAG", "Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)");
                                // Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)
                                c_Lesson_feedback.resetResult();
//                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("You did not complete and failed the lesson, please retake it.");
                            }
                        }

    }

    private void onBackButtonClicked() {
        String TAG = "onBackButtonClicked";

    // Log.(TAG, "Hi! I pressed back, what should we do??");

        // get the current step
//    // Log.(TAG, "currentStep: " + currentStep);
    // Log.(TAG, "returnStep: " + returnStep);
//    // Log.(TAG, "store it to stepIndex...");

        if (currentStep == (numberOfSteps-1)) {
//            showToast("nasa last step kana");

            // currentStep should always be set to (numberOfSteps-1)
            returnStep = (numberOfSteps-2);
        // Log.(TAG, "last step kana... | returnStep: " + returnStep);
        }

        returnStep = Math.max(0, returnStep - 1);  // Avoid negative steps
        currentReturnStep = returnStep; // remove +1, since dun na sya i-increment

//    // Log.(TAG, "currentStep: " + currentStep);
//    // Log.(TAG, "currentReturnStep: " + currentReturnStep);
    // Log.(TAG, "returnStep: " + returnStep);

    // Log.(TAG, "viewPager.setCurrentItem(returnStep["+returnStep+"]);");
        viewPager.setCurrentItem(returnStep);

        // Original Code
//        returnStep--;
//        currentReturnStep = returnStep+1;
//
//        // set current item of the viewPager
//        viewPager.setCurrentItem(returnStep);

        if (viewPager.getCurrentItem() != 0
                &&
            viewPager.getCurrentItem() != numberOfSteps) {

                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
            // Log.(TAG, "onBackButtonClick() | show NextButton");
        }

    // Log.(TAG, "viewPager: " + viewPager.getAdapter());
    // Log.(TAG, "viewPager.getCurrentItem(): " + viewPager.getCurrentItem());
    // Log.(TAG, "numberOfSteps: " + numberOfSteps);

        // if returnStep == (currentStep - 1),
        // nextButton lang..

        // if returnStep < (currentStep - 1),
        // dapat may kasamang return to current step

        // set the stepView designs here??
        for (int i = 0; i < numberOfSteps; i++) {

            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            params.width = (int) (8 * getResources().getDisplayMetrics().density); // Proportional width
            params.height = (int) (8 * getResources().getDisplayMetrics().density); // Height consistent with width
            params.columnSpec = GridLayout.spec(i * 2); // Simplify weight handling


            // Original Code
//            params.width = 0;
//            params.height = (int) (10 * getResources().getDisplayMetrics().density);
//            params.columnSpec = GridLayout.spec(i * 2, 1, 3f);
            stepView.setLayoutParams(params);

            // Determine the background based on the step's position relative to the selected step
            if (i < (returnStep)) {
                stepView.setBackgroundResource(R.drawable.rounded_corners_completed);
            }
            else if (i == (returnStep) || i < currentReturnStep) {
                stepView.setBackgroundResource(R.drawable.rounded_corners_current_step); // Highlight the current step
            }
            else {
                stepView.setBackgroundResource(R.drawable.rounded_corners);
            }

        } // end of for loop
    }

    private void populateGridLayout() {
        gridLayout.removeAllViews();

        for (int i = 0; i < numberOfSteps; i++) {

            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            // Original Code : mukang piyaot
//            params.width = 1;
            params.width = (int) (8 * getResources().getDisplayMetrics().density);
            params.height = (int) (8 * getResources().getDisplayMetrics().density);

            params.columnSpec = GridLayout.spec(i * 2, 1, 3f);


            stepView.setLayoutParams(params);

            // Determine the background based on the step's position relative to the selected step
            if (i < (currentStep)) {
//            // Log.(TAG, i + " < " + (currentStep) + ", so setting to completed (transparent) background");
                stepView.setBackgroundResource(R.drawable.rounded_corners_completed);
            } else if (i == (currentStep)) {
//            // Log.(TAG, i + " == " + (currentStep) + ", so setting to current step (highlighted) background");
                stepView.setBackgroundResource(R.drawable.rounded_corners_current_step); // Highlight the current step
            } else {
//            // Log.(TAG, i + " > " + (currentStep - 1) + ", so setting to transparent background");
                stepView.setBackgroundResource(R.drawable.rounded_corners);
            }

            stepView.setTag(i);
            stepView.setClickable(true);  // Make the stepView clickable

            if (currentStep >= (numberOfSteps-1))
                stepView.setClickable(false);

            stepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stepIndex = (int) v.getTag();  // Get the step index from the tag
                // Log.(TAG, "Step clicked: " + stepIndex);

                    // Only allow navigation to steps that are <= furthestStep
                    if (stepIndex <= furthestStep) {
                        viewPager.setCurrentItem(stepIndex);

                    // Log.(TAG, "let's call updateStepViewBackgrounds(" + stepIndex + ");");

                        for (int i = 0; i < numberOfSteps; i++) {
                            View stepView = gridLayout.getChildAt(i * 2); // Get the step view at index i (multiply by 2 because of spaces)

                            if (i < stepIndex) {
                                stepView.setBackgroundResource(R.drawable.rounded_corners_completed);
                            }

                            if (i == stepIndex) {
                                stepView.setBackgroundResource(R.drawable.rounded_corners_current_step);
                            }
                        }

                    } else {
                        Toast.makeText(d_Lesson_container.this, "You can't access this step yet!", Toast.LENGTH_SHORT).show();
                    }

                    currentStep = stepIndex;

                    // re-show nextButton
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (viewPager.getCurrentItem() != 0) {
                                nextButton.setVisibility(View.VISIBLE);
                            // Log.(TAG, "populateGridLayout | show NextButton");
                                nextButton.setEnabled(true);
                            }
                        }
                    }, 3000); // show after 3 seconds
                }
            });


            gridLayout.addView(stepView);

            if (i < numberOfSteps - 1) {
                Space space = new Space(this);
                GridLayout.LayoutParams spaceParams = new GridLayout.LayoutParams();
                spaceParams.width = (int) (20 * getResources().getDisplayMetrics().density);
                spaceParams.height = (int) (10 * getResources().getDisplayMetrics().density);
                space.setLayoutParams(spaceParams);
                gridLayout.addView(space);
            }
        }

        gridLayout.setColumnCount((numberOfSteps+1) * 2 - 1);
    }

    private void updateProgressAndMoveToNextStep() {

        String TAG = "updateProgressAndMoveToNextStep()";

        Log.i(TAG, "CATH | currentStep: " + currentStep);
        Log.i(TAG, "CATH | numberOfSteps: " + numberOfSteps);
        Log.i(TAG, "CATH | furthestStep: " + furthestStep);
        Log.i(TAG, "CATH | ");

        if (currentStep < numberOfSteps) {

            if (currentReturnStep < (currentStep)) {
                currentReturnStep++;
            } else {
                currentReturnStep++;
                currentStep++;
            }

            returnStep = currentStep; // Sync returnStep with new currentStep
        }

        // Update furthestStep if currentStep moved forward
        furthestStep = Math.max(furthestStep, currentStep);

        Log.i(TAG, "CATH | isCompleted: " + isCompleted);

        if (!isCompleted)
            updateCurrentModuleInDatabase();  // Update database with the new progress

        Log.i(TAG, "CATH | let's call populateGridLayout();");
        populateGridLayout();  // Update the progress indicators

        if (isLessonFinished) {

            // Clear video preferences once the lesson is completed
            f_2_lesson_video.clearVideoPreferences(this);
            Log.i("CATH", "CATH | isLessonFinished: " + isLessonFinished);

            if (lessonPassed) {
                showPassedDialog(String.valueOf(currentModule.charAt(1)));
                Log.i("CATH", "CATH | showPassedDialog();");
            } else {
                Log.i("CATH", "CATH | finish();");
                finish();
            }

            x_bkt_algorithm.resultMap.clear();
            c_Lesson_feedback.resetResult();

        }
    }

    public static void startCountdown(final Context context, String mode, Boolean isTestFinished) {

    // Log.("Countdown", "Starting countdown in mode: " + mode);
    // Log.("Countdown", "isTestFinished: " + isTestFinished);

        int milliseconds = 10000; // 10 seconds countdown
        final CountDownTimer countDownTimer = new CountDownTimer(milliseconds, 1000) { // 1-second interval
            boolean timerActive = true; // Tracks whether the timer is active

            @Override
            public void onTick(long millisUntilFinished) {

                if (isTestFinished) {
                // Log.("Countdown", "Test is finished, stopping countdown.");
                    timerActive = false; // Prevent onFinish from executing
//                    txtSecondsRemaining.setText(""); // Clear text
                    cancel(); // Stop the countdown
                // Log.("Countdown", mode + " Mode | Countdown cancelled due to test completion.");
                    return;
                }

                int secondsRemaining = (int) (millisUntilFinished / 1000);
            // Log.("Countdown", "Seconds remaining: " + secondsRemaining);
//                txtSecondsRemaining.setText(secondsRemaining + "s");

                // Check for answer selection based on mode
                if (mode.equals("Pre-Test") && f_0_lesson_pre_test.choicesGroup.getCheckedRadioButtonId() != -1) {
                // Log.("Countdown", "Pre-Test answer selected, stopping countdown.");
                    timerActive = false; // Prevent onFinish from executing
//                    txtSecondsRemaining.setText(""); // Clear text
                    cancel(); // Stop the countdown
                // Log.("Countdown", "Pre-Test | Countdown Cancelled.");
                    return;
                }

                if (mode.equals("Post-Test")) {
                    if (f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.EASY ||
                            f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.MEDIUM) {
                        if (f_3_lesson_post_test.choicesGroup.getCheckedRadioButtonId() != -1) {
                        // Log.("Countdown", "Post-Test answer selected (EASY/MEDIUM), stopping countdown.");
                            timerActive = false; // Prevent onFinish from executing
//                            txtSecondsRemaining.setText(""); // Clear text
                            cancel(); // Stop the countdown
                        // Log.("Countdown", "Post-Test | Countdown Cancelled.");
                            return;
                        }
                    }

                    if (f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.HARD &&
                            !f_3_lesson_post_test.identificationAnswer.toString().isEmpty()) {
                    // Log.("Countdown", "Post-Test answer provided (HARD), stopping countdown.");
                        timerActive = false; // Prevent onFinish from executing
//                        txtSecondsRemaining.setText(""); // Clear text
                        cancel(); // Stop the countdown
                    // Log.("Countdown", "Post-Test | Countdown Cancelled.");
                        return;
                    }
                }
            }

            @Override
            public void onFinish() {
                if (timerActive) {
                // Log.("Countdown", "countdown complete.");
                // Log.("Countdown", "isTestFinished: " + isTestFinished);
                    if (isTestFinished) {
//                        txtSecondsRemaining.setText("");
                    // Log.("Countdown", "You haven't selected an answer yet");
                        Toast.makeText(context, "You haven't selected an answer yet", Toast.LENGTH_SHORT).show();
                    }
                } else {
                // Log.("Countdown", "onFinish called but the timer was canceled.");
                }
            }
        };

        // Start the countdown if the test is not finished
        if (!isTestFinished) {
            countDownTimer.start();
        }
    }



//    private void updateProgressAndMoveToNextStep() {
//        String TAG = "OKAY NEXT NA";
//
//    // Log.(TAG, "updateProgressAndMoveToNextStep()");
//
//    // Log.(TAG, "currentStep: " + currentStep);
//    // Log.(TAG, "currentReturnStep: " + currentReturnStep);
//    // Log.(TAG, "returnStep: " + returnStep);
//
//        // check para di na mag increment anymores :D
//        if (currentStep < numberOfSteps
////                && currentReturnStep == currentStep // para di muna sya mag next
//        ) {
//
//            if (currentReturnStep >= currentStep) {
//            // Log.(TAG, "currentReturnStep["+currentReturnStep+"] >= currentStep["+currentStep+"]");
////                currentReturnStep++;
//                currentStep++;
//                returnStep = currentStep;
//            } else if (currentReturnStep < currentStep) {
//            // Log.(TAG, "currentReturnStep["+currentReturnStep+"] < currentStep["+currentStep+"]");
//                currentReturnStep++;
//            }
//
//        }
//
//        // Update furthest step
//        // Optimized version
//        furthestStep = Math.max(furthestStep, currentStep);
//
////        if (currentStep > furthestStep) {
////            furthestStep = currentStep;
////        }
//
//        if (!isCompleted) {
//            updateCurrentModuleInDatabase();  // Update database with the new progress
//        }
//
//        populateGridLayout();  // Update the progress indicators
//
//        if (isLessonFinished) {
//            // Clear the video preferences once the lesson is completed
//            f_2_lesson_video.clearVideoPreferences(this);
//
////            showToast("TAPOS NA");
//
//            if (lessonPassed)
//                showPassedDialog(String.valueOf(currentModule.charAt(1)));
//            else {
//                finish();
//            }
//
//        }
//    }

    public static void onGoToCurrent() {

    // Log.("SCALE", "SCALE | currentStep: " + currentStep);
    // Log.("SCALE", "SCALE | currentReturnStep: " + currentReturnStep);


        if (currentReturnStep < currentStep) {
            viewPager.setCurrentItem(currentReturnStep);
        // Log.("SCALE", "SCALE | currentReturnStep["+currentReturnStep+"] < currentStep["+currentStep+"]");
        }

        if (currentReturnStep == currentStep) {
            viewPager.setCurrentItem(currentStep);
        // Log.("SCALE", "SCALE | currentReturnStep["+currentReturnStep+"] == currentStep["+currentStep+"]");
        }

        // re-show nextButton
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem() != 0) {
                    nextButton.setVisibility(View.VISIBLE);
                // Log.(TAG, "onGoToCurrent() | show NextButton");
                    nextButton.setEnabled(true);
                }
            }
        }, 3000); // show after 3 seconds
    }

    public void onNextButtonClicked() {
    // Log.("onNextButtonClicked()", "currentStep: " + currentStep);
    // Log.("onNextButtonClicked()", "currentReturnStep: " + currentReturnStep);

        // nasa ilalim ng method to kanina
        L_lesson_handler.pageNumber++;

        // Update the progress and move to the next step
        updateProgressAndMoveToNextStep();

        Log.i("HEY", "CATH | currentStep: " + currentStep);
        Log.i("HEY", "CATH | numberOfSteps: " + numberOfSteps);

        if (currentStep >= numberOfSteps) {
            // ahmmm wag ilagay dito yung finish??
                finish();
        // Log.("onNextBUttonClicked()", "tapos na dapat, pero dapat sa post-test to i-call");
//            showToast("tapos na dapat, pero dapat sa post-test to i-call");
        } else {

            // Use the updated helper method to get the current fragment
            Fragment currentFragment = getCurrentFragment();

            // Log the current fragment's class name to see what fragment was retrieved
            if (currentFragment != null) {
            // Log.("onNextButtonClicked()", "Current Fragment: " + currentFragment.getClass().getSimpleName());
            } else {
            // Log.("onNextButtonClicked()", "Current Fragment: null");
            }

            // yung sa mismong L_lesson_handler ang i-increment, kasi sya yung nag ha-handle..
        // Log.(TAG, "After increment | Page Number: " + pageNumber);

        }

        if (currentReturnStep < (currentStep)) {
        // Log.("onNextButtonClicked()", "onNextButtonClicked() | currentReturnStep["+currentReturnStep+"] < currentStep["+currentStep+"]");
        // Log.(TAG, "viewPager.setCurrentItem(["+currentReturnStep+"])");
            viewPager.setCurrentItem(currentReturnStep);
        } else if (currentReturnStep >= currentStep) {
        // Log.("onNextButtonClicked()", "onNextButtonClicked() | currentReturnStep["+currentReturnStep+"] >= currentStep["+currentStep+"]");
        // Log.(TAG, "viewPager.setCurrentItem(["+currentStep+"])");
            viewPager.setCurrentItem(currentStep);
        } else {
        // Log.("onNextButtonClicked()", "else statement {: ");
        // Log.("onNextButtonClicked()", "currentStep: " + currentStep);
        // Log.("onNextButtonClicked()", "currentReturnStep: " + currentReturnStep);
        // Log.(TAG, "viewPager.setCurrentItem(["+currentStep+"])");
            viewPager.setCurrentItem(currentStep);
        }

    }

    public void showScoreDialog(String testMode, int score, int questionCount) {

        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.c_lesson_feedback_score, null);
        builder.setView(dialogView);

        // Find views in the dialog layout
        TextView titleText = dialogView.findViewById(R.id.title);
        TextView messageText = dialogView.findViewById(R.id.message);
        Button okayButton = dialogView.findViewById(R.id.okay);

        // Create the dialog
        AlertDialog dialog = builder.create();

        if (testMode.equals("Post-Test"))
            questionCount += 1;

        titleText.setText(testMode + " Result");
        messageText.setText("Your score for " + testMode + " is:\n" +
                score + " / " + questionCount);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (testMode.equals("Pre-Test"))
                    onNextButtonClicked();
            }
        });

        dialog.show(); // Show the dialog
    }

    public Fragment getCurrentFragment() {
        int position = viewPager.getCurrentItem();
        String fragmentTag = "android:switcher:" + R.id.viewPager + ":" + position;
        return getSupportFragmentManager().findFragmentByTag(fragmentTag);
    }


    @Override
    public void onPreTestComplete(int score) {

        String category = b_main_0_menu_categorize_user.category;
        double bktScore = x_bkt_algorithm.getKnowledge();

        isLessonFinished = false;

//        x_bkt_algorithm.updateTestBKTScore();

        Log.i(TAG, "onPreTestComplete System Intervention");
        Log.i(TAG, "HDMI | Category: " + category);
        Log.i(TAG, "HDMI | bktScore: " + bktScore);
        Log.i(TAG, "HDMI | let's change category...");
//        t_SystemInterventionCategory.changeCategory(category, bktScore);

        showScoreDialog("Pre-Test", score, f_0_lesson_pre_test.preTestQuestions);

        // Call feedback for pre-test
        c_Lesson_feedback.printResult("Pre-Test");

        isPreTestComplete = true;

//        Log.i("HEY" , "ICHI | Pre-Test is Done!");
//        updateProgressAndMoveToNextStep();

        Log.i("HEY", "ICHI | updateProgressAndMoveToNextStep() done!");
    }

    @Override
    public void onTextLessonComplete(boolean isDone, int delay) {

        String TEG = "onTextLessonComplete";

    // Log.(TEG, "isDone: " + isDone);

    // Log.(TEG, "pageNumber: " + pageNumber);
    // Log.(TEG, "numberOfTextLessons: " + numberOfTextLessons);

//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            // Disable the button initially
//            nextButton.setEnabled(false);
//            nextButton.setVisibility(View.GONE);
//        }, delay * 1000);

        // Enable and show the button after the delay
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                nextButton.setEnabled(true);
//                nextButton.setVisibility(View.VISIBLE);
//            // Log.(TAG, "onTextLessonComplete | show NextButton");
//            }
//        }, delay * 1000);  // Convert seconds to milliseconds

//        if (isDone && pageNumber <= numberOfTextLessons) {
//            // Check if there are any remaining text lessons
//            int remainingTextLessons = L_lesson_sequence.getRemainingTextLessons(stepSequence, currentStep);
//
//        // Log.("onTextLessonComplete()", remainingTextLessons + " > 0");
//            if (remainingTextLessons > 0) {
////                pageNumber++; // Increment only if there are more text lessons
//            // Log.("onTextLessonComplete()", "pageNumber++; | " + pageNumber );
//            }
//        } else {
//            // Handle the case where the lesson is not done or no more text lessons
//        // Log.("onTextLessonComplete", "No more text lessons or lesson not completed.");
//        }
//        pageNumber++;
    }

    @Override
    public void onPostTestComplete(
            int score, boolean isPassed) {

        t_SystemInterventionCategory.calculateGrowth
                (f_3_lesson_post_test.preTestScore, f_3_lesson_post_test.postTestScore,
                c_Lesson_feedback.postTestQuestions, f_3_lesson_post_test.difficultyLevel);

        x_bkt_algorithm.updateLearningStatus
                (isProgressiveMode, moduleIndex, lessonIndex,
                c_Lesson_feedback.preTestCorrectAnswers, c_Lesson_feedback.postTestCorrectAnswers,
                c_Lesson_feedback.postTestQuestions);

        lessonPassed = isPassed;
        isLessonFinished = true;

        updateProgressAndMoveToNextStep();

        // Log lang to
        c_Lesson_feedback.printResult("Post-Test");

        isPostTestComplete = true;

    }

    private void showPassedDialog(String lesson) {

        // Create a dialog using the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(d_Lesson_container.this);

        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View customDialogView = inflater.inflate(R.layout.c_lesson_passed_dialog_2, null);

        // Set the custom layout to the dialog builder
        builder.setView(customDialogView);

        // Find and update TextViews
        TextView dialogMessage = customDialogView.findViewById(R.id.message);
        dialogMessage.setText("You passed Lesson " + lesson + "!");

        int preTestCorrectAnswers = 0;

        moduleIndex = Integer.parseInt(String.valueOf(d_Lesson_container.currentModule.charAt(1)));
        lessonIndex = Integer.parseInt(String.valueOf(d_Lesson_container.currentLesson.charAt(7)));
        isProgressiveMode = "Progressive Mode".equals(learningMode);

        if (c_Lesson_feedback.preTestCorrectAnswers == 0 && isResumed) {
            preTestCorrectAnswers = x_bkt_algorithm.retrievePreTestScore(isProgressiveMode, moduleIndex, lessonIndex);
        } else {
            preTestCorrectAnswers = c_Lesson_feedback.preTestCorrectAnswers;
        }

        TextView preTestMessage = customDialogView.findViewById(R.id.pre_test_score);
        preTestMessage.setText(
                "Pre-Test Score: "
                        + (preTestCorrectAnswers)
                        + "/"
//                        + (c_Lesson_feedback.preTestAttemptAnswers - 1)
                        + (f_0_lesson_pre_test.preTestQuestions)
        );

        TextView postTestMessage = customDialogView.findViewById(R.id.post_test_score);
        postTestMessage.setText(
                "Post-Test Score: "
                        + (c_Lesson_feedback.postTestCorrectAnswers)
                        + "/"
//                        + (c_Lesson_feedback.postTestAttemptAnswers - 1)
                        + (c_Lesson_feedback.postTestQuestions));

        TextView nlgFeedback = customDialogView.findViewById(R.id.nlg_feedback);

        t_SystemInterventionCategory NLG = new t_SystemInterventionCategory();

        String learningRate = null;

        if (NLG.NLG_Score > 0.7)
            learningRate = "a strong improvement";
        else if (NLG.NLG_Score >= 0.3 && NLG.NLG_Score <= 0.7)
            learningRate = "a moderate improvement";
        else
            learningRate = "a minimal improvement";

        String feedback = "Your learning gain for this lesson is classified as" +
                NLG.NLG_Feedback + ". This reflects " + learningRate + " of " +
                (NLG.NLG_Score*100) + " in your understanding.";

        nlgFeedback.setText(feedback);

        TextView receivedMessage = customDialogView.findViewById(R.id.token);

        // Handle button click
        Button okButton = customDialogView.findViewById(R.id.okay_passed_button);
        okButton.setOnClickListener(v -> {
            // Add token to pag true
            f_3_lesson_post_test_generateHint.takeToken(true, 10);
            finish();
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        // Dynamically set dialog width and height
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialog.getWindow().
                    setBackgroundDrawableResource
                            (android.R.color.transparent);
        }

        receivedMessage.setText("You received: 10");
    }


    private void updateCurrentModuleInDatabase() {
//        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
//        String userId = sharedPreferences.getString("userId", "");

        Log.i("Katalon", "Katalon | userId: " + userId);
        Log.i("Katalon", "Katalon | Collection: " + learningMode);
        Log.i("Katalon", "Katalon | Document: " + currentLesson);
        Log.i("Katalon", "Katalon | userRef.update(" + currentModule + ".Progress: " + currentStep);

        if (!userId.isEmpty()) {
            Log.i("Katalon", "Katalon | user.Id is not empty!");
            Log.i("Katalon", "Katalon | path: \"users\"/"+userId+"/"+learningMode+"/"+currentLesson+"/"+currentModule+".Progress["+currentStep+"]");
            Log.i("Katalon", "Katalon | ");
            DocumentReference userRef = db.collection("users").document(userId)
                    .collection(learningMode).document(currentLesson);

            userRef.update(currentModule + ".Progress", currentStep)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("Katalon", "Katalon | task.isSuccessful()");
                             Log.i(TAG, "Current step updated successfully in the database.");
                            } else {
                             Log.i(TAG, "Failed to update current step: " + task.getException().getMessage());
                                if (task.getException() instanceof FirebaseFirestoreException) {
                                    FirebaseFirestoreException firestoreException = (FirebaseFirestoreException) task.getException();
                                    if (firestoreException.getCode() == FirebaseFirestoreException.Code.NOT_FOUND) {
                                     Log.i(TAG, "The specified document does not exist.");
                                    }
                                }
                            }
                        }
                    });

            Log.i("Katalon", "Katalon | done na mag query.");

        } else {
        // Log.(TAG, "User ID not found in SharedPreferences.");
        }
    }

    private void showExitConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        exitDialog = builder.create();

        Button btnYes = dialogView.findViewById(R.id.button_exit_lesson);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnCancel = dialogView.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });

        exitDialog.show();
    }


    public static void clickCenter(double delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the center coordinates of the screen
                int x = viewPager.getWidth() / 2;
                int y = viewPager.getHeight() / 2;

                simulateClick(x, y);
            // Log.("simulateClicksInCenter", "Click!");
            }
        }, (int) delay * 1000);  // Convert seconds to milliseconds
    }

//    public static void simulateClicksInCenter() {
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Get the center coordinates of the screen
//                int x = viewPager.getWidth() / 2;
//                int y = viewPager.getHeight() / 2;
//
////                // Number of clicks
////                int clickCount = 1;
////
////                for (int i=0;i<clickCount;i++) {
//                    // Simulate click
//                    simulateClick(x, y);
////                }
//            // Log.("simulateClicksInCenter", "Click!");
//            }
//        }, 7000);  // Convert seconds to milliseconds
//    }

    public static  void simulateClick(int x, int y) {
        long downTime = System.currentTimeMillis();
        long eventTime = System.currentTimeMillis() + 10; // Delay between down and up events

        // Simulate touch down event
        MotionEvent motionEventDown = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
        viewPager.dispatchTouchEvent(motionEventDown);

        // Simulate touch up event
        MotionEvent motionEventUp = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, 0);
        viewPager.dispatchTouchEvent(motionEventUp);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            if (backToast != null) {
                backToast.cancel();
            }
            showExitConfirmationDialog(); // Show the exit dialog on second back press
        } else {
            backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis(); // Update the back press time
    }
}
