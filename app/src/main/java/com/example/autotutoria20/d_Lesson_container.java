package com.example.autotutoria20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.Arrays;

public class d_Lesson_container extends AppCompatActivity implements
        f_0_lesson_pre_test.PreTestCompleteListener,
        f_1_lesson_text.OnNextButtonClickListener,
        f_1_lesson_text.TextLessonCompleteListener,
        f_3_lesson_post_test.PostTestCompleteListener
{

    public static boolean isPreTestComplete = false;
    public static boolean isPostTestComplete = false;

    private static final String TAG = "Module3Steps";
    public int pageNumber = 1; // 1 sya originally
    private GridLayout gridLayout;
    private AlertDialog dialog;
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
    public static Button nextButton;
    private Button backButton;
    static TextView txtSecondsRemaining;
    private ShapeableImageView currentButton;
    private f_2_lesson_video videoLesson;
    private L_lesson_sequence.StepType[] stepSequence;
    private static ViewPager viewPager;
    private L_lesson_handler pagerAdapter;
    private boolean isLessonFinished = false;
    private long backPressedTime; // Variable to track back press time
    private Toast backToast;
    private c_Lesson_feedback feedback;
    private x_bkt_algorithm bktAlgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_lesson_container);

        currentStep = 0;
        currentReturnStep = 0;
        returnStep = 0;
        numberOfSteps = 0;

        // e kasi ibang lesson na to :D
        isPreTestComplete = false;
        isPostTestComplete = false;

        txtSecondsRemaining = findViewById(R.id.seconds_remaining);
        gridLayout = findViewById(R.id.gridLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);  // Adjust the offscreen page limit

        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        learningMode = sharedPreferences.getString("learningMode", null);
        currentLesson = sharedPreferences.getString("currentLesson", null);
        currentModule = sharedPreferences.getString("currentModule", null);
        isCompleted = sharedPreferences.getBoolean("isCompleted", false);
//        isLessonPassed = sharedPreferences.getBoolean("isLessonPassed", false);

        String HEY = "EVO PLUS GOLD";

        Log.e(HEY, "Learning Mode: " + learningMode);
        Log.e(HEY, "Current Lesson: " + currentLesson);
        Log.e(HEY, "Current Module: " + currentModule);
        Log.e(HEY, "isComplete: " + isCompleted);

        int moduleIndex = Integer.parseInt(String.valueOf(currentModule.charAt(1)));
        int lessonIndex = Integer.parseInt(String.valueOf(currentLesson.charAt(7)));

        boolean isProgressiveMode = true;

        isProgressiveMode = learningMode.equalsIgnoreCase("Progressive Mode");

        boolean finalIsProgressiveMode = isProgressiveMode;

//        String collectionPath = learningMode;
//        String documentName = currentLesson;
//        String module = currentModule;

        bktAlgo = new x_bkt_algorithm();

        Log.e(TAG, "Learning Mode: " + learningMode);
        Log.e(TAG, "Lesson: " + currentLesson);
        Log.e(TAG, "Module: " + currentModule);

        // Get the Singleton instance
        bktAlgo = x_bkt_algorithm.getInstance(0.3, 0.2, 0.1, 0.4);

        bktAlgo.initializeBKTScores(learningMode, currentLesson, currentModule);

//        bktAlgo.initializeBKTScores(learningMode, currentLesson, currentModule, bktScores -> {
//                    if (bktScores != null) {
//
//                        Log.d("d_lesson_container", "et ba yon!:!: BKT Scores initialized: " + bktScores);
//
//                        double passingGrade = b_main_0_menu_categorize_user.passingGrade;
////                        lessonPassed = bktScores.get(0) > passingGrade;
//
//                        Log.d("TAG", "Lesson Complete? " + isCompleted);
////                        Log.e("TAG", "Score["+bktScores.get(0)+"] > PassingGrade["+passingGrade+"]");
//
//                        Log.d("TAG", "Lesson Passed? " + lessonPassed);
//
//                        // Reset only if di pa tapos yung lesson..
//                        // kelangan completed sya, pero dapat pasado din
//                        // Case 1: Completed, not Passed - RETAKE
//                        // Case 2: Completed, Passed - OK (do not reset)
//                        // Case 3: InComplete, not Passed - RETAKE
//                        // Case 4: InComplete, Passed - RETAKE (pwede kasi siyang passed if mataas ang Pre-Test)
//
//                        Log.e("TAG", "Let's Check Case...");
//                        Log.e("TAG", "Module: " + currentModule);
//                        Log.e("TAG", "Lesson: " + currentLesson);
//
//                        // Reset logic based on completion and passing status
//                        if (isCompleted) {
//                            if (!lessonPassed) {
//                                // Case 1: Completed, not Passed - RETAKE
//                                Log.e("TAG", "Case 1: Completed, not Passed - RETAKE");
//                                c_Lesson_feedback.resetResult();
//                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, finalIsProgressiveMode);
//                                showToast("You completed the lesson but did not pass, please retake the lesson");
//                            } else {
//                                Log.e("TAG", "Case 2: Completed, Passed - OK (do nothing)");
//                                // Case 2: Completed, Passed - OK (do nothing)
//                                showToast("You completed the lesson and passed! Great job!");
//                            }
//                        } else {
//                            // If the lesson is incomplete
//                            if (!lessonPassed) {
//                                Log.e("TAG", "Case 3: Incomplete, not Passed - RETAKE");
//                                // Case 3: Incomplete, not Passed - RETAKE
//                                c_Lesson_feedback.resetResult();
//                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, finalIsProgressiveMode);
//                                showToast("you completed the lesson but failed, please retake it.");
//                            } else {
//                                Log.e("TAG", "Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)");
//                                // Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)
//                                c_Lesson_feedback.resetResult();
//                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, finalIsProgressiveMode);
//                                showToast("You did not complete and failed the lesson, please retake it.");
//                            }
//                        }
//
//                    } else {
//                        Log.e("d_lesson_container", "Failed to retrieve BKT Scores");
//                    }
//                });



//        x_bkt_algorithm.getBKTScore(moduleIndex, lessonIndex, isProgressiveMode, new x_bkt_algorithm.ScoreCallback() {
//            @Override
//            public void onScoreRetrieved(float score) {
//                Log.d("TAG", "The BKT Score is: " + score);
//                // Handle the retrieved score here
//
//
//
//                Boolean lessonPassed = score < b_main_0_menu_categorize_user.passingGrade;
//
//                Log.d("TAG", "Lesson Complete? " + isCompleted);
//                Log.d("TAG", "Lesson Passed? " + lessonPassed);
//
//                if (!isCompleted
//                &&
//                score < b_main_0_menu_categorize_user.passingGrade
//                ) {
//                    c_Lesson_feedback.resetResult();
//
//                    x_bkt_algorithm.resetScore(
//                            moduleIndex,
//                            lessonIndex,
//                            finalIsProgressiveMode
//                    );
//                }
//            }
//        });

        Log.e("onCreate", "currentModule: " + currentModule + " | currentLesson: " + currentLesson);

        // Retrieve the lesson sequence
        stepSequence = L_lesson_sequence.getLessonSequences().get(currentModule + "_" + currentLesson);

        // Log the step sequence
        if (stepSequence != null) {
            Log.d(TAG, "Step sequence: " + Arrays.toString(stepSequence));
            numberOfSteps = stepSequence.length;

            // Count the number of TEXT lessons in the sequence
            numberOfTextLessons = L_lesson_sequence.countTextLessons(stepSequence);
            Log.d(TAG, "Number of TEXT lessons in " + currentModule + "_" + currentLesson + ": " + numberOfTextLessons);
        } else {
            Log.d(TAG, "Step sequence is null for key: " + currentModule + "_" + currentLesson);
            stepSequence = new L_lesson_sequence.StepType[0]; // Assign an empty array to avoid null
        }

        Log.d(TAG, "Number of steps: " + numberOfSteps);
        Log.d(TAG, "Learning Mode: " + learningMode);
        Log.d(TAG, "Current Lesson: " + currentLesson);
        Log.d(TAG, "Current Module: " + currentModule);
        Log.d(TAG, "isCompleted: " + isCompleted);

        Log.e("pagerAdapter", "LessonPagerAdapter(" + getSupportFragmentManager() + ", " + Arrays.toString(stepSequence) + ", " + currentModule + "_" + currentLesson + ");");

        // dapat kasi eto may nag c-call din dito somewhere, kasi pag ganto lagi talaga syang Page 1
        pagerAdapter = new L_lesson_handler(
                getSupportFragmentManager(),
                stepSequence,
                currentModule + "_" + currentLesson,
                learningMode,
                pageNumber);
        viewPager.setAdapter(pagerAdapter);


        // ANG WINO-WORK OUT MO IS BAKIT NAGLO-LOAD AGAD SI TEXT LESSON KAHIT NASA PRE-TEST PALANG
        // AND BAKIT DI NAG A-ADJUST LAYOUT PAG NASA VIDEO LESSON NA

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for this issue
            }

            @Override
            public void onPageSelected(int position) {

                Log.e(TAG, "Displayed fragment position: " + position);

                backButton.setVisibility(View.GONE);
                backButton.setEnabled(false);

                // Adjust the bottom margin of the ViewPager
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();

                // Default behavior of the 3 except video lesson..
                params.bottomMargin = 0;  // Adjust this value as needed
                viewPager.setLayoutParams(params);
                nextButton.setVisibility(View.GONE);

                // Get the current fragment
                Fragment currentFragment = getCurrentFragment();

                // Check if the last fragment was the video lesson and stop the video
                if (videoLesson != null && !(currentFragment instanceof f_2_lesson_video)) {
                    Log.d(TAG, "Stopping video playback as we're leaving the video lesson.");
                    videoLesson.stopVideoPlayback();
                }

                if (currentFragment instanceof f_0_lesson_pre_test) {

                    // ano gagawin by default? pwede namang wala, kasi wala naman talaga tong code na to nung una..
                    Log.e(TAG, "Pre Test");

                    backButton.setVisibility(View.GONE);
                    backButton.setEnabled(false);

                    // pero pano pag tapos na??
                    if (isPreTestComplete) {

                        f_0_lesson_pre_test.currentButton.setEnabled(true);
                        f_0_lesson_pre_test.currentButton.setVisibility(View.VISIBLE);

                        Log.e(TAG, "Pre Test, isPreTestComplete = true");

                        // disable tong next button??
                        nextButton.setVisibility(View.GONE);
                        nextButton.setEnabled(false);

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

                    // di pwedeng ganto, ma s-speed run nila yung buong lesson
//                    nextButton.setVisibility(View.VISIBLE);
//                    nextButton.setEnabled(true);

                    f_1_lesson_text textLessonFragment = (f_1_lesson_text) currentFragment;

                    if (viewPager.getCurrentItem() == 1) {
                        backButton.setVisibility(View.GONE);
                        backButton.setEnabled(false);
                    } else {
                        backButton.setVisibility(View.VISIBLE);
                        backButton.setEnabled(true);
                    }

                    // auto-next??
                    clickCenter(1);

                }
                if (currentFragment instanceof f_2_lesson_video) {

                    backButton.setVisibility(View.VISIBLE);
                    backButton.setEnabled(true);

                        // click in the middle one time to auto-play the video..
                        // sa onCreate nalang ng video class??
//                    simulateClicksInCenter();

                        videoLesson = (f_2_lesson_video) currentFragment;

                        params.bottomMargin = 225;  // Adjust this value as needed
                        viewPager.setLayoutParams(params);

//                  kung ilang seconds ang delay bago ipakita yung next button
                        int delayInSeconds = 5;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nextButton.setVisibility(View.VISIBLE);
                            }
                        }, delayInSeconds * 1000);

                }

                if (currentFragment instanceof f_3_lesson_post_test) {

                    // wala nang nextButton dito, last na to eh. abnormal kaba??
                    nextButton.setVisibility(View.GONE);
                    nextButton.setEnabled(false);

                    backButton.setVisibility(View.GONE);
                    backButton.setEnabled(false);

                    // I think magiging permanent na to??
                    nextButton.setText("Go to Post-Test");

                    // hide back button??
                    // or hayaan sila mag back, pero dapat mag decrement din yung sa next button
                    // kasi natatapos yung module eh.

//                    backButton.setVisibility(View.GONE);
//                    backButton.setEnabled(false);


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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });
        // nung naka-comment di na nawawala yung LAST Next button
        nextButton.setVisibility(View.GONE);
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
//        Log.e("Generate Hint", "Generate Hint");
//
//    }

    public static void resetModule(boolean isComplete, boolean isPassed) {


                        // Reset only if di pa tapos yung lesson..
                        // kelangan completed sya, pero dapat pasado din
                        // Case 1: Completed, not Passed - RETAKE
                        // Case 2: Completed, Passed - OK (do not reset)
                        // Case 3: InComplete, not Passed - RETAKE
                        // Case 4: InComplete, Passed - RETAKE (pwede kasi siyang passed if mataas ang Pre-Test)

                        Log.e("TAG", "Let's Check Case...");
                        Log.e("TAG", "Module: " + currentModule);
                        Log.e("TAG", "Lesson: " + currentLesson);

                        int moduleIndex = Integer.parseInt(String.valueOf(currentModule.charAt(1)));
                        int lessonIndex = Integer.parseInt(String.valueOf(currentLesson.charAt(7)));

                        boolean isProgressiveMode = learningMode.equalsIgnoreCase("Progressive Mode");

                        // Reset logic based on completion and passing status
                        if (isCompleted) {
                            if (!lessonPassed) {
                                // Case 1: Completed, not Passed - RETAKE
                                Log.e("TAG", "Case 1: Completed, not Passed - RETAKE");
                                c_Lesson_feedback.resetResult();
                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("You completed the lesson but did not pass, please retake the lesson");
                            } else {
                                Log.e("TAG", "Case 2: Completed, Passed - OK (do nothing)");
                                // Case 2: Completed, Passed - OK (do nothing)
//                                showToast("You completed the lesson and passed! Great job!");
                            }
                        } else {
                            // If the lesson is incomplete
                            if (!lessonPassed) {
                                Log.e("TAG", "Case 3: Incomplete, not Passed - RETAKE");
                                // Case 3: Incomplete, not Passed - RETAKE
                                c_Lesson_feedback.resetResult();
                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("you completed the lesson but failed, please retake it.");
                            } else {
                                Log.e("TAG", "Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)");
                                // Case 4: Incomplete, Passed - RETAKE (possible because of high Pre-Test)
                                c_Lesson_feedback.resetResult();
                                x_bkt_algorithm.resetScore(moduleIndex, lessonIndex, isProgressiveMode);
//                                showToast("You did not complete and failed the lesson, please retake it.");
                            }
                        }

    }

    private void onBackButtonClicked() {
        String TAG = "onBackButtonClicked";

        Log.e(TAG, "Hi! I pressed back, what should we do??");

        // get the current step
//        Log.e(TAG, "currentStep: " + currentStep);
        Log.e(TAG, "returnStep: " + returnStep);
//        Log.e(TAG, "store it to stepIndex...");

        if (currentStep == (numberOfSteps-1)) {
//            showToast("nasa last step kana");

            // currentStep should always be set to (numberOfSteps-1)
            returnStep = (numberOfSteps-2);
            Log.e(TAG, "last step kana... | returnStep: " + returnStep);
        }

        returnStep = Math.max(0, returnStep - 1);  // Avoid negative steps
        currentReturnStep = returnStep; // remove +1, since dun na sya i-increment

//        Log.e(TAG, "currentStep: " + currentStep);
//        Log.e(TAG, "currentReturnStep: " + currentReturnStep);
        Log.e(TAG, "returnStep: " + returnStep);

        Log.e(TAG, "viewPager.setCurrentItem(returnStep["+returnStep+"]);");
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
        }

        // if returnStep == (currentStep - 1),
        // nextButton lang..

        // if returnStep < (currentStep - 1),
        // dapat may kasamang return to current step

        // set the stepView designs here??
        for (int i = 0; i < numberOfSteps; i++) {
            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = (int) (10 * getResources().getDisplayMetrics().density);
            params.columnSpec = GridLayout.spec(i * 2, 1, 1f);
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

//            // Determine the background based on the step's position relative to the selected step
//            if (i < (currentStep)) {
////                Log.e(TAG, i + " < " + (currentStep) + ", so setting to completed (transparent) background");
//                stepView.setBackgroundResource(R.drawable.rounded_corners_completed);
//            } else if (i == (currentStep)) {
////                Log.e(TAG, i + " == " + (currentStep) + ", so setting to current step (highlighted) background");
//                stepView.setBackgroundResource(R.drawable.rounded_corners_current_step); // Highlight the current step
//            } else {
////                Log.e(TAG, i + " > " + (currentStep - 1) + ", so setting to transparent background");
//                stepView.setBackgroundResource(R.drawable.rounded_corners);
//            }

        } // end of for loop
    }

    private void populateGridLayout() {
        gridLayout.removeAllViews();

        for (int i = 0; i < numberOfSteps; i++) {

            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 1;
            params.height = (int) (8 * getResources().getDisplayMetrics().density);

//            if (i % 2 == 0) {
                params.columnSpec = GridLayout.spec(i * 2, 1, 3f);
//            } else {
//                // For odd indices, use weight for distribution
//                params.columnSpec = GridLayout.spec(i * 2, 0.5f);
//            }


            stepView.setLayoutParams(params);

            // Determine the background based on the step's position relative to the selected step
            if (i < (currentStep)) {
//                Log.e(TAG, i + " < " + (currentStep) + ", so setting to completed (transparent) background");
                stepView.setBackgroundResource(R.drawable.rounded_corners_completed);
            } else if (i == (currentStep)) {
//                Log.e(TAG, i + " == " + (currentStep) + ", so setting to current step (highlighted) background");
                stepView.setBackgroundResource(R.drawable.rounded_corners_current_step); // Highlight the current step
            } else {
//                Log.e(TAG, i + " > " + (currentStep - 1) + ", so setting to transparent background");
                stepView.setBackgroundResource(R.drawable.rounded_corners);
            }

            stepView.setTag(i);
            stepView.setClickable(true);  // Make the stepView clickable

            stepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stepIndex = (int) v.getTag();  // Get the step index from the tag
                    Log.d(TAG, "Step clicked: " + stepIndex);

                    // Only allow navigation to steps that are <= furthestStep
                    if (stepIndex <= furthestStep) {
                        viewPager.setCurrentItem(stepIndex);

                        Log.e(TAG, "let's call updateStepViewBackgrounds(" + stepIndex + ");");

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

        gridLayout.setColumnCount(numberOfSteps * 2 - 1);
    }

    private void updateProgressAndMoveToNextStep() {
        String TAG = "OKAY NEXT NA";

//        Log.e(TAG, "updateProgressAndMoveToNextStep()");

        Log.e(TAG, "currentStep: " + currentStep);
        Log.e(TAG, "currentReturnStep: " + currentReturnStep);
        Log.e(TAG, "returnStep: " + returnStep);

//        // Ensuring currentReturnStep is returnStep + 1
//        currentReturnStep = returnStep + 1;

        if (currentStep < numberOfSteps) {
            if (currentReturnStep < (currentStep)) {
                Log.e(TAG, "currentReturnStep[" + currentReturnStep + "] < currentStep[" + currentStep + "]");
                currentReturnStep++;
                Log.e(TAG, "currentReturnStep++; | currentReturnStep: " + currentReturnStep);
            } else if (currentReturnStep >= currentStep) {
                Log.e(TAG, "currentReturnStep[" + currentReturnStep + "] >= currentStep[" + currentStep + "]");
                currentReturnStep++;
                currentStep++;
                Log.e(TAG, "currentStep++; | currentStep: " + currentStep);
                Log.e(TAG, "currentReturnStep++; | currentReturnStep: " + currentReturnStep);
            }
            returnStep = currentStep; // Sync returnStep with new currentStep
        }

        Log.e(TAG, "updateProgressAndMoveToNextStep()");
        Log.e(TAG, "currentStep: " + currentStep);
        Log.e(TAG, "currentReturnStep: " + currentReturnStep);
        Log.e(TAG, "returnStep: " + returnStep);

        // Update furthestStep if currentStep moved forward
        furthestStep = Math.max(furthestStep, currentStep);

        if (!isCompleted) {
            updateCurrentModuleInDatabase();  // Update database with the new progress
        }

        populateGridLayout();  // Update the progress indicators

        if (isLessonFinished) {
            // Clear video preferences once the lesson is completed
            f_2_lesson_video.clearVideoPreferences(this);

            if (lessonPassed) {
                showPassedDialog(String.valueOf(currentModule.charAt(1)));
            } else {
                finish();
            }
        }
    }

    public static void startCountdown(final Context context, String mode) {
        Log.d("Countdown", "Starting countdown in mode: " + mode);

        new CountDownTimer(10000, 1000) { // 10 seconds countdown with 1-second interval
            boolean shouldFinish = true; // Flag to determine if onFinish should run

            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                Log.d("Countdown", "Seconds remaining: " + secondsRemaining);

                txtSecondsRemaining.setText(secondsRemaining + "s");

                if (mode.equals("Pre-Test")) {
                    if (!(f_0_lesson_pre_test.choicesGroup.getCheckedRadioButtonId() == -1)) {
                        Log.d("Countdown", "Pre-Test answer selected, skipping onFinish.");
                        shouldFinish = false; // Prevent onFinish from executing
                    }
                }

                if (mode.equals("Post-Test")) {
                    if (f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.EASY
                            || f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.MEDIUM) {
                        if (!(f_3_lesson_post_test.choicesGroup.getCheckedRadioButtonId() == -1)) {
                            Log.d("Countdown", "Post-Test answer selected (EASY/MEDIUM), skipping onFinish.");
                            shouldFinish = false; // Prevent onFinish from executing
                        }
                    }
                    if (f_3_lesson_post_test.getDifficulty() == e_Question.Difficulty.HARD) {
                        if (!(f_3_lesson_post_test.identificationAnswer.toString().isEmpty())) {
                            Log.d("Countdown", "Post-Test answer provided (HARD), skipping onFinish.");
                            shouldFinish = false; // Prevent onFinish from executing
                        }
                    }
                }
            }

            public void onFinish() {
                if (shouldFinish) { // Only show Toast if no answer was selected
                    txtSecondsRemaining.setText("");
                    Toast.makeText(context, "You haven't selected an answer yet", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Countdown", "onFinish called but answer was selected.");
                }
            }

        }.start();
    }

//    private void updateProgressAndMoveToNextStep() {
//        String TAG = "OKAY NEXT NA";
//
//        Log.e(TAG, "updateProgressAndMoveToNextStep()");
//
//        Log.e(TAG, "currentStep: " + currentStep);
//        Log.e(TAG, "currentReturnStep: " + currentReturnStep);
//        Log.e(TAG, "returnStep: " + returnStep);
//
//        // check para di na mag increment anymores :D
//        if (currentStep < numberOfSteps
////                && currentReturnStep == currentStep // para di muna sya mag next
//        ) {
//
//            if (currentReturnStep >= currentStep) {
//                Log.e(TAG, "currentReturnStep["+currentReturnStep+"] >= currentStep["+currentStep+"]");
////                currentReturnStep++;
//                currentStep++;
//                returnStep = currentStep;
//            } else if (currentReturnStep < currentStep) {
//                Log.e(TAG, "currentReturnStep["+currentReturnStep+"] < currentStep["+currentStep+"]");
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

        Log.e("SCALE", "SCALE | currentStep: " + currentStep);
        Log.e("SCALE", "SCALE | currentReturnStep: " + currentReturnStep);


        if (currentReturnStep < currentStep) {
            viewPager.setCurrentItem(currentReturnStep);
            Log.e("SCALE", "SCALE | currentReturnStep["+currentReturnStep+"] < currentStep["+currentStep+"]");
        }

        if (currentReturnStep == currentStep) {
            viewPager.setCurrentItem(currentStep);
            Log.e("SCALE", "SCALE | currentReturnStep["+currentReturnStep+"] == currentStep["+currentStep+"]");
        }

        // re-show nextButton
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem() != 0) {
                    nextButton.setVisibility(View.VISIBLE);
                    nextButton.setEnabled(true);
                }
            }
        }, 3000); // show after 3 seconds
    }

    public void onNextButtonClicked() {
        Log.e("onNextButtonClicked()", "currentStep: " + currentStep);
        Log.e("onNextButtonClicked()", "currentReturnStep: " + currentReturnStep);

        // Update the progress and move to the next step
        updateProgressAndMoveToNextStep();

        if (currentStep >= numberOfSteps) {
            // ahmmm wag ilagay dito yung finish??
                finish();
            Log.e("onNextBUttonClicked()", "tapos na dapat, pero dapat sa post-test to i-call");
//            showToast("tapos na dapat, pero dapat sa post-test to i-call");
        } else {

            // Use the updated helper method to get the current fragment
            Fragment currentFragment = getCurrentFragment();

            // Log the current fragment's class name to see what fragment was retrieved
            if (currentFragment != null) {
                Log.d("onNextButtonClicked()", "Current Fragment: " + currentFragment.getClass().getSimpleName());
            } else {
                Log.d("onNextButtonClicked()", "Current Fragment: null");
            }

            // yung sa mismong L_lesson_handler ang i-increment, kasi sya yung nag ha-handle..
            L_lesson_handler.pageNumber++;
            Log.e(TAG, "After increment | Page Number: " + pageNumber);

        }

        if (currentReturnStep < (currentStep)) {
            Log.e("onNextButtonClicked()", "onNextButtonClicked() | currentReturnStep["+currentReturnStep+"] < currentStep["+currentStep+"]");
            Log.e(TAG, "viewPager.setCurrentItem(["+currentReturnStep+"])");
            viewPager.setCurrentItem(currentReturnStep);
        } else if (currentReturnStep >= currentStep) {
            Log.e("onNextButtonClicked()", "onNextButtonClicked() | currentReturnStep["+currentReturnStep+"] >= currentStep["+currentStep+"]");
            Log.e(TAG, "viewPager.setCurrentItem(["+currentStep+"])");
            viewPager.setCurrentItem(currentStep);
        } else {
            Log.e("onNextButtonClicked()", "else statement {: ");
            Log.e("onNextButtonClicked()", "currentStep: " + currentStep);
            Log.e("onNextButtonClicked()", "currentReturnStep: " + currentReturnStep);
            Log.e(TAG, "viewPager.setCurrentItem(["+currentStep+"])");
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

        titleText.setText(testMode + " Result");
        messageText.setText("Your score for " + testMode + " is: " +
                // Pre-Test = (score-1);
                // Post-Test =
                (score-1) + "/" + questionCount);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
    public void onPreTestComplete(
//            boolean isCorrect,
//            double preTestScores,
            int score) {

        showScoreDialog("Pre-Test", score, f_0_lesson_pre_test.preTestQuestions);

        // Call feedback for pre-test
        c_Lesson_feedback.printResult("Pre-Test");

//        currentStep = 1;

        isPreTestComplete = true;
        onNextButtonClicked(); // Proceed to the next step if the test is passed

    }

    @Override
    public void onTextLessonComplete(boolean isDone, int delay) {

        String TEG = "onTextLessonComplete";

        Log.d(TEG, "isDone: " + isDone);

        Log.e(TEG, "pageNumber: " + pageNumber);
        Log.e(TEG, "numberOfTextLessons: " + numberOfTextLessons);

//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            // Disable the button initially
//            nextButton.setEnabled(false);
//            nextButton.setVisibility(View.GONE);
//        }, delay * 1000);

        // Enable and show the button after the delay
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                nextButton.setEnabled(true);
                nextButton.setVisibility(View.VISIBLE);
            }
        }, delay * 1000);  // Convert seconds to milliseconds

        if (isDone && pageNumber <= numberOfTextLessons) {
            // Check if there are any remaining text lessons
            int remainingTextLessons = L_lesson_sequence.getRemainingTextLessons(stepSequence, currentStep);

            Log.d("onTextLessonComplete()", remainingTextLessons + " > 0");
            if (remainingTextLessons > 0) {
//                pageNumber++; // Increment only if there are more text lessons
                Log.d("onTextLessonComplete()", "pageNumber++; | " + pageNumber );
            }
        } else {
            // Handle the case where the lesson is not done or no more text lessons
            Log.d("onTextLessonComplete", "No more text lessons or lesson not completed.");
        }
//        pageNumber++;
    }

    @Override
    public void onPostTestComplete(boolean isCorrect, int score, boolean isPassed) {
        Log.d("onPostTestComplete", "isCorrect: " + isCorrect);

//        Double new_pKnow = x_bkt_algorithm.getKnowledge();

        showScoreDialog("Pre-Test", score, f_3_lesson_post_test.postTestQuestions);

        lessonPassed = isPassed;

        isLessonFinished = true;
        updateProgressAndMoveToNextStep();

        c_Lesson_feedback.printResult("Post-Test");

        isPostTestComplete = true;

    }

    private void showPassedDialog(String lesson) {
        // Create a dialog using the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(d_Lesson_container.this);

        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View customDialogView = inflater.inflate(R.layout.c_lesson_passed_dialog, null);

        // Set the custom layout parameters for width and height
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Width: Match Parent
//                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 392, getResources().getDisplayMetrics()) // Height: 392dp
                ViewGroup.LayoutParams.WRAP_CONTENT // Height: Wrap Content

        );
        customDialogView.setLayoutParams(params);

        // Set the custom layout to the dialog builder
        builder.setView(customDialogView);

        TextView dialogMessage = customDialogView.findViewById(R.id.message);
        Button okButton = customDialogView.findViewById(R.id.okay_passed_button);

        dialogMessage.setText("You passed Lesson " + lesson + "!");

        TextView preTestMessage = customDialogView.findViewById(R.id.pre_test_score);
        preTestMessage.setText(
                "Pre-Test Score: "
                    + (c_Lesson_feedback.preTestCorrectAnswers-1)
                    + "/"
                    + (c_Lesson_feedback.preTestAttemptAnswers-1));

        TextView postTestMessage = customDialogView.findViewById(R.id.post_test_score);
        postTestMessage.setText(
                "Post-Test Score: "
                    + (c_Lesson_feedback.postTestCorrectAnswers-1)
                    + "/"
                    + (c_Lesson_feedback.postTestAttemptAnswers-1));

        // Show the dialog when OK button is clicked
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Optionally finish the activity
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false); // Prevent dismissing by tapping outside
        dialog.show();
    }

    private void updateCurrentModuleInDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        if (!userId.isEmpty()) {
            DocumentReference userRef = db.collection("users").document(userId)
                    .collection(learningMode).document(currentLesson);

            userRef.update(currentModule + ".Progress", currentStep)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Current step updated successfully in the database.");
                            } else {
                                Log.d(TAG, "Failed to update current step: " + task.getException().getMessage());
                                if (task.getException() instanceof FirebaseFirestoreException) {
                                    FirebaseFirestoreException firestoreException = (FirebaseFirestoreException) task.getException();
                                    if (firestoreException.getCode() == FirebaseFirestoreException.Code.NOT_FOUND) {
                                        Log.d(TAG, "The specified document does not exist.");
                                    }
                                }
                            }
                        }
                    });
        } else {
            Log.d(TAG, "User ID not found in SharedPreferences.");
        }
    }

    private void showExitConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_confirmation, null);
        builder.setView(dialogView);

        dialog = builder.create();

        Button btnYes = dialogView.findViewById(R.id.exit_module);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnCancel = dialogView.findViewById(R.id.cancel_exit_module);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public static void clickCenter(double delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the center coordinates of the screen
                int x = viewPager.getWidth() / 2;
                int y = viewPager.getHeight() / 2;

                simulateClick(x, y);
                Log.e("simulateClicksInCenter", "Click!");
            }
        }, (int) delay * 1000);  // Convert seconds to milliseconds
    }

    public static void simulateClicksInCenter() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get the center coordinates of the screen
                int x = viewPager.getWidth() / 2;
                int y = viewPager.getHeight() / 2;

//                // Number of clicks
//                int clickCount = 1;
//
//                for (int i=0;i<clickCount;i++) {
                    // Simulate click
                    simulateClick(x, y);
//                }
                Log.e("simulateClicksInCenter", "Click!");
            }
        }, 7000);  // Convert seconds to milliseconds
    }

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
