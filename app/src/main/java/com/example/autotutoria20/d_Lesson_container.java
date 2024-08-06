    package com.example.autotutoria20;
    
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
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
    
    public class d_Lesson_container extends AppCompatActivity {
    
        private static final String TAG = "Module3Steps";
        private GridLayout gridLayout;
        private AlertDialog dialog;
        private int currentStep = 0;
        private int numberOfSteps = 0;
        private FirebaseFirestore db;
        private String learningMode;
        private String currentLesson;
        private String currentModule;
        private LessonSequence.StepType[] stepSequence; // Use fully qualified name
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
    
            Log.e("onCreate", "currentModule: " + currentModule + " | currentLesson: " + currentLesson);
    
            // Retrieve the lesson sequence
            stepSequence = LessonSequence.getLessonSequences().get(currentModule + "_" + currentLesson);
    
            // Log the step sequence
            if (stepSequence != null) {
                Log.d(TAG, "Step sequence: " + Arrays.toString(stepSequence));
                numberOfSteps = stepSequence.length;
            } else {
                Log.d(TAG, "Step sequence is null for key: " + currentModule + "_" + currentLesson);
            }
    
            Log.d(TAG, "Number of steps: " + numberOfSteps);
            Log.d(TAG, "Learning Mode: " + learningMode);
            Log.d(TAG, "Current Lesson: " + currentLesson);
            Log.d(TAG, "Current Module: " + currentModule);
    
            pagerAdapter = new LessonPagerAdapter(getSupportFragmentManager(), stepSequence, currentModule + "_" + currentLesson);
            viewPager.setAdapter(pagerAdapter);
    
            populateGridLayout();
    
            Button nextButton = findViewById(R.id.nextButton);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNextButtonClicked();
                }
            });
    
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

        private void onNextButtonClicked() {
            if (currentStep == numberOfSteps - 1) {
                // This is the last step, check if it's the Post-Test Fragment
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + currentStep);
                if (currentFragment instanceof f_post_test) {
                    f_post_test postTestFragment = (f_post_test) currentFragment;
                    if (!postTestFragment.isPostTestCompleted()) {
                        // Show a message or prompt user to complete the post-test
                        Toast.makeText(this, "Please complete the post-test before proceeding.", Toast.LENGTH_SHORT).show();
                        return; // Do not proceed to the next step
                    }
                }
            }

            currentStep++;

            if (currentStep >= numberOfSteps) {
                updateCurrentModuleInDatabase();
                finish();
            } else {
                viewPager.setCurrentItem(currentStep);
                updateCurrentModuleInDatabase();
                populateGridLayout();
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
    }
