package com.example.autotutoria20;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;

public class d_Lesson_container extends AppCompatActivity implements f_pre_test.PreTestCompleteListener, f_text_lesson.OnNextButtonClickListener, f_post_test.PostTestCompleteListener {

    private static final String TAG = "Module3Steps";
    private GridLayout gridLayout;
    private AlertDialog dialog;
    private int currentStep = 0;
    private int numberOfSteps = 0;
    private FirebaseFirestore db;
    private String learningMode;
    private String currentLesson;
    private String currentModule;
    private Boolean isCompleted;
    private Button nextButton;
    private LessonSequence.StepType[] stepSequence;
    private ViewPager viewPager;
    private LessonPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_lesson_container);

        gridLayout = findViewById(R.id.gridLayout);
        viewPager = findViewById(R.id.viewPager);
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        learningMode = sharedPreferences.getString("learningMode", null);
        currentLesson = sharedPreferences.getString("currentLesson", null);
        currentModule = sharedPreferences.getString("currentModule", null);
        isCompleted = sharedPreferences.getBoolean("isCompleted", false);

        Log.e("onCreate", "currentModule: " + currentModule + " | currentLesson: " + currentLesson);

        // Retrieve the lesson sequence
        stepSequence = LessonSequence.getLessonSequences().get(currentModule + "_" + currentLesson);

        // Log the step sequence
        if (stepSequence != null) {
            Log.d(TAG, "Step sequence: " + Arrays.toString(stepSequence));
            numberOfSteps = stepSequence.length;
        } else {
            Log.d(TAG, "Step sequence is null for key: " + currentModule + "_" + currentLesson);
            stepSequence = new LessonSequence.StepType[0]; // Assign an empty array to avoid null
            Toast.makeText(this, "No steps found for this module and lesson.", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "Number of steps: " + numberOfSteps);
        Log.d(TAG, "Learning Mode: " + learningMode);
        Log.d(TAG, "Current Lesson: " + currentLesson);
        Log.d(TAG, "Current Module: " + currentModule);
        Log.d(TAG, "isCompleted: " + isCompleted);

        Log.e("pagerAdapter", "LessonPagerAdapter(" + getSupportFragmentManager() + ", " + stepSequence + ", " + currentModule + "_" + currentLesson + ");");
        pagerAdapter = new LessonPagerAdapter(getSupportFragmentManager(), stepSequence, currentModule + "_" + currentLesson, learningMode);
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

                // Handle automatic progress by calling onNextButtonClicked()
                if (position > currentStep) {
                    onNextButtonClicked();  // Automatically update progresss
                }

                // Get the current fragment directly from the ViewPager adapter
                Fragment currentFragment = (Fragment) pagerAdapter.instantiateItem(viewPager, position);

                // Handle logic based on the fragment type
                if (currentFragment instanceof f_pre_test) {
//                    f_pre_test.newInstance(currentModule, currentLesson, learningMode);
//                    showToast("Pre-test detected");
                } else if (currentFragment instanceof f_text_lesson) {
//                    showToast("Text lesson detected");
                } else if (currentFragment instanceof f_video_lesson) {
//                    showToast("Video lesson detected");

                    // Adjust the bottom margin of the ViewPager for video lessons
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
//                    params.topMargin = 800;
                    params.bottomMargin = 200;  // Adjust this value as needed
                    viewPager.setLayoutParams(params);

                    // Make the nextButton visible
                    nextButton.setVisibility(View.VISIBLE);
                } else if (currentFragment instanceof f_post_test) {
//                    showToast("Post-test detected");

                    // Adjust the bottom margin of the ViewPager for post-test
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
                    params.bottomMargin = 0;  // Reset margin
                    viewPager.setLayoutParams(params);

                    nextButton.setVisibility(View.GONE);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                // Not needed for this issue
            }
        });


        populateGridLayout();

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClicked();
            }
        });

        nextButton.setVisibility(View.GONE);

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }
        });
    }

    private void populateGridLayout() {
        gridLayout.removeAllViews();

        for (int i = 0; i < numberOfSteps; i++) {
            View stepView = new View(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = (int) (10 * getResources().getDisplayMetrics().density);
            params.columnSpec = GridLayout.spec(i * 2, 1, 1f);
            stepView.setLayoutParams(params);

            if (i < currentStep) {
                stepView.setBackgroundResource(R.drawable.rounded_corners_completed);
            } else if (i == currentStep) {
                stepView.setBackgroundResource(R.drawable.rounded_corners);
            } else {
                stepView.setBackgroundResource(R.drawable.rounded_corners);
            }

            stepView.setTag(i);
            stepView.setClickable(true);  // Make the stepView clickable

            stepView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stepIndex = (int) v.getTag();  // Get the step index from the tag
                    Log.d(TAG, "Step clicked: " + stepIndex);

                    // Only allow navigation to steps that are <= the currentStep
                    if (stepIndex <= currentStep) {
                        // Move to the clicked step
                        viewPager.setCurrentItem(stepIndex);
                        populateGridLayout();  // Update the grid layout to reflect the current step
                    } else {
                        // Optionally, show a message indicating that the user cannot access this step yet
                        Toast.makeText(d_Lesson_container.this, "You can't access this step yet!", Toast.LENGTH_SHORT).show();
                    }
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


//    public void onNextButtonClicked() {
//        String TAG = "onNextButtonClicked()";
//
//        Log.e(TAG, "NEXT!!!!");
//        Log.e(TAG, "currentStep: " + currentStep);
//
////        // Get the current fragment using FragmentManager
////        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
////
////        // PRE TEST
////        if (currentFragment instanceof f_pre_test) {
////            f_pre_test preTestFragment = (f_pre_test) currentFragment;
////            if (!preTestFragment.isCorrect) {
////                Toast.makeText(this, "Please complete the pre-test before proceeding.", Toast.LENGTH_SHORT).show();
////                return; // Do not proceed to the next step
////            }
////        }
////
////        // TEXT LESSON
////        if (currentFragment instanceof f_text_lesson) {
////            showToast("TEXT LESSON");
////        }
////
////        // VIDEO LESSON
////        if (currentFragment instanceof f_video_lesson) {
////            // Adjust the bottom margin of the ViewPager to make space for the nextButton
////            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
////            params.bottomMargin = 80;  // Adjust this value as needed (80dp equivalent in pixels)
////            viewPager.setLayoutParams(params);
////
////            Log.e(TAG, "params.BottomMargin = " + params.bottomMargin);
////
////            // Show a toast message indicating the button will be shown
////            showToast("VIDEO TO SHOW THE BUTTON");
////            Log.e("onNextButtonClicked", "VIDEO TO SHOW THE BUTTON");
////
////            // Make the nextButton visible
////            nextButton.setVisibility(View.VISIBLE);
////        }
////
////        // POST TEST
////        if (currentFragment instanceof f_post_test) {
////            f_post_test postTestFragment = (f_post_test) currentFragment;
////            if (!postTestFragment.isCorrect) {
////                Toast.makeText(this, "Please complete the post-test before proceeding.", Toast.LENGTH_SHORT).show();
////                return; // Do not proceed to the next step
////            }
////        }
//
//        // Move to the next step
//        currentStep++;
//
//        if (currentStep >= numberOfSteps) {
//            finish();
//        } else {
//            viewPager.setCurrentItem(currentStep);
//            populateGridLayout();
//        }
//
//        if (!isCompleted) {
//            updateCurrentModuleInDatabase();
//        }
//    }

    private void updateProgressAndMoveToNextStep() {
        currentStep++;

        if (!isCompleted) {
            updateCurrentModuleInDatabase();  // Update database with the new progress
        }

        populateGridLayout();  // Update the progress indicators
    }

    public void onNextButtonClicked() {
        Log.e("onNextButtonClicked()", "currentStep: " + currentStep);

        updateProgressAndMoveToNextStep();

        if (currentStep >= numberOfSteps) {
            finish();
        } else {
            viewPager.setCurrentItem(currentStep);
        }
    }

    @Override
    public void onPreTestComplete(boolean isCorrect) {
        Log.d("onPreTestComplete", "isCorrect: " + isCorrect);

        if (isCorrect) {
            onNextButtonClicked(); // Proceed to the next step if the test is passed
        } else {
            Toast.makeText(this, "Please complete the pre-test before proceeding.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPostTestComplete(boolean isCorrect) {
        Log.d("onPostTestComplete", "isCorrect: " + isCorrect);

        if (isCorrect) {
            onNextButtonClicked(); // Proceed to the next step if the test is passed
        } else {
            Toast.makeText(this, "Please complete the post-test before proceeding.", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateCurrentModuleInDatabase() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        if (!userId.isEmpty()) {
            DocumentReference userRef = db.collection("users").document(userId)
                    .collection(learningMode).document(currentLesson);

            userRef.update(currentModule, currentStep)
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
}
