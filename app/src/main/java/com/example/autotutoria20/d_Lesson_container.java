package com.example.autotutoria20;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Space;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
    private int returnStep = 0;
    private int numberOfSteps = 0;
    private FirebaseFirestore db;
    private String learningMode;
    static String currentLesson;
    static String currentModule;
    private int furthestStep = 0; // Track the furthest step reached

    private Boolean isCompleted;
    public static Button nextButton;
    private Button backButton;
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
        returnStep = 0;
        numberOfSteps = 0;

        // Reset the pre-test and post-test counter??
        c_Lesson_feedback.resetResult();

        // e kasi ibang lesson na to :D
        isPreTestComplete = false;
        isPostTestComplete = false;

        gridLayout = findViewById(R.id.gridLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);  // Adjust the offscreen page limit

        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        learningMode = sharedPreferences.getString("learningMode", null);
        currentLesson = sharedPreferences.getString("currentLesson", null);
        currentModule = sharedPreferences.getString("currentModule", null);
        isCompleted = sharedPreferences.getBoolean("isCompleted", false);

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
                        builder.setTitle("Test Result");

                        String message = "ge, mag pre-test kalang hanggang magsawa ka," +
                                "pag nag sawa ka, edi tumigil ka, pake ko sayo? sino ka ba?" +
                                "tanginamo pala e, kupal abno burikot";

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
                else if (currentFragment instanceof f_1_lesson_text) {
                        f_1_lesson_text textLessonFragment = (f_1_lesson_text) currentFragment;

                        // auto-next??
                        clickCenter(1);

                } else if (currentFragment instanceof f_2_lesson_video) {

                        // click in the middle one time to auto-play the video..
                        // sa onCreate nalang ng video class??
//                    simulateClicksInCenter();

                        videoLesson = (f_2_lesson_video) currentFragment;

                        params.bottomMargin = 200;  // Adjust this value as needed
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
                else if (currentFragment instanceof f_3_lesson_post_test) {

                    // Create a dialog to show the user's score
                    AlertDialog.Builder builder = new AlertDialog.Builder(d_Lesson_container.this);
                    builder.setTitle("Pre Test");

                    String message = "GUMANA KA NAMAN PLEASE";

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

                    // pag nag return dito, and not yet finished yung post-test, dapat walang next Button
                    // else, if tapos na, pero mostly yon wala na siya sa lesson e noh hahah

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
        backButton = findViewById(R.id.backButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackButtonClicked(); }
        });

        // nung naka-comment di na nawawala yung LAST Next button
        nextButton.setVisibility(View.GONE);

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    private void onBackButtonClicked() {
        String TAG = "onBackButtonClicked";

        Log.e(TAG, "Hi! I pressed back, what should we do??");

        // get the current step
        Log.e(TAG, "currentStep: " + currentStep);
        Log.e(TAG, "returnStep: " + returnStep);
        Log.e(TAG, "store it to stepIndex...");

        returnStep--;

        // set current item of the viewPager
        viewPager.setCurrentItem(returnStep);

        if (viewPager.getCurrentItem() != 0) {
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
            else if (i == (returnStep) || i < currentStep) {
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
        String TAG = "updateProgressAndMoveToNextStep";

        Log.e(TAG, "updateProgressAndMoveToNextStep()");

        // check para di na mag increment anymores :D
        if (currentStep < numberOfSteps) {
            currentStep++;
            returnStep = currentStep;
        }

        if (currentStep > furthestStep) {
            furthestStep = currentStep;
        }

        if (!isCompleted) {
            updateCurrentModuleInDatabase();  // Update database with the new progress
        }

        populateGridLayout();  // Update the progress indicators

        if (isLessonFinished) {
            // Clear the video preferences once the lesson is completed
            f_2_lesson_video.clearVideoPreferences(this);
            finish();
        }
    }

    public static void onGoToCurrent() {

        viewPager.setCurrentItem(currentStep);
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

//        returnStep = currentStep;

        // Update the progress and move to the next step
        updateProgressAndMoveToNextStep();

        if (currentStep >= numberOfSteps) {
            // ahmmm wag ilagay dito yung finish??
                finish();
            Log.e("onNextBUttonClicked()", "tapos na dapat, pero dapat sa post-test to i-call");
            showToast("tapos na dapat, pero dapat sa post-test to i-call");
        } else {
            // Use the updated helper method to get the current fragment
            Fragment currentFragment = getCurrentFragment();

            // Log the current fragment's class name to see what fragment was retrieved
            if (currentFragment != null) {
                Log.d("onNextButtonClicked()", "Current Fragment: " + currentFragment.getClass().getSimpleName());
            } else {
                Log.d("onNextButtonClicked()", "Current Fragment: null");
            }

            Log.e(TAG, "Before increment | Page Number: " + pageNumber);
            // ETO LATEST TESTING
//            pageNumber++;

            // yung sa mismong L_lesson_handler ang i-increment, kasi sya yung nag ha-handle..
            L_lesson_handler.pageNumber++;
            Log.e(TAG, "After increment | Page Number: " + pageNumber);


            // Move to the next step
            viewPager.setCurrentItem(currentStep);
        }
    }



    private Fragment getCurrentFragment() {
        int position = viewPager.getCurrentItem();
        String fragmentTag = "android:switcher:" + R.id.viewPager + ":" + position;
        return getSupportFragmentManager().findFragmentByTag(fragmentTag);
    }


    @Override
    public void onPreTestComplete(boolean isCorrect, int score) {
        // Log the result
        Log.d("onPreTestComplete", "isCorrect: " + isCorrect);

        // Create a dialog to show the user's score
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Test Result");

        // Set the message based on whether the answer was correct or not
        String message = isCorrect ?
                "Congratulations! You passed the pre-test." : // Correct
                "Unfortunately, you did not pass the pre-test. Please try again."; // Incorrect

        String scoreMessage = " Your score for Pre-Test is: " + score;

        builder.setMessage(message + scoreMessage);

        builder.setCancelable(false);

        // Add a button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
//                onNextButtonClicked(); // Proceed to the next step if the test is passed
            }
        });


        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Call feedback for pre-test
        c_Lesson_feedback.printResult("Pre-Test");

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
    public void onPostTestComplete(boolean isCorrect, double score) {
        Log.d("onPostTestComplete", "isCorrect: " + isCorrect);

        isLessonFinished = true;
        updateProgressAndMoveToNextStep();

        c_Lesson_feedback.printResult("Post-Test");

        isPostTestComplete = true;

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

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
