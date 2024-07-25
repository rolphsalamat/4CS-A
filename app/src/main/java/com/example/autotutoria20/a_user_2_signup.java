package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class a_user_2_signup extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private boolean isPasswordVisible = false;
    TextView firstNameEditText;
    TextView lastNameEditText;
    TextView emailEditText;
    TextView passwordEditText;
    DatePicker datePicker;
    RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_user_2_signup);

        // Initialize EditText and other views
        firstNameEditText = findViewById(R.id.txtFirstName);
        lastNameEditText = findViewById(R.id.txtLastName);
        emailEditText = findViewById(R.id.txtEmail);
        passwordEditText = findViewById(R.id.txtPassword);
        datePicker = findViewById(R.id.datePicker);
        genderRadioGroup = findViewById(R.id.rdoGender);

        // Find the Button view
        Button signupButton = findViewById(R.id.btnSignup);

        ImageButton showHidePasswordButton = findViewById(R.id.btnShowPassword);
        EditText passwordText = findViewById(R.id.txtPassword);

        showHidePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                isPasswordVisible = !isPasswordVisible;

                // Change the password visibility in the EditText
                if (isPasswordVisible) {
                    // Show password
                    passwordText.setTransformationMethod(null); // Set null to show the password
                    showHidePasswordButton.setBackgroundResource(R.drawable.hide_password); // Change icon to hide password
                } else {
                    // Hide password
                    passwordText.setTransformationMethod(new PasswordTransformationMethod()); // Use PasswordTransformationMethod to hide the password
                    showHidePasswordButton.setBackgroundResource(R.drawable.show_password); // Change icon to show password
                }
            }
        });

        // Set onClickListener for the sign-up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values from the EditText views
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String gender = "";

                // Must validate 4
                int validCounter = 0;

                // Validate inputs
                validCounter += validateName(firstName, "first");
                validCounter += validateName(lastName, "last");
                validCounter += validateEmail(email);
                validCounter += validatePassword(password);

                // Retrieve the selected gender from the RadioGroup
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                String defaultProfilePicture = "";

                if (selectedId == R.id.rdoFemale) {
                    gender = "Female";
                    defaultProfilePicture = "default_female.png";
                } else if (selectedId == R.id.rdoMale) {
                    gender = "Male";
                    defaultProfilePicture = "default_male.png";
                } else if (selectedId == R.id.rdoRatherNoySay) {
                    gender = "Rather not say";
                    defaultProfilePicture = "default_generic.png";
                }

                // Calculate the actual age
                int age = calculateAge(datePicker);

                // PROGRESSIVE MODE and FREE USE MODE module progress data
                Map<String, Map<String, Object>> moduleProgressData = new HashMap<>();
                moduleProgressData.put("Progressive Mode - Lesson 1", createModuleProgress(z_Lesson_steps.lesson_1_steps.length));
                moduleProgressData.put("Progressive Mode - Lesson 2", createModuleProgress(z_Lesson_steps.lesson_2_steps.length));
                moduleProgressData.put("Progressive Mode - Lesson 3", createModuleProgress(z_Lesson_steps.lesson_3_steps.length));
                moduleProgressData.put("Progressive Mode - Lesson 4", createModuleProgress(z_Lesson_steps.lesson_4_steps.length));
                moduleProgressData.put("Free Use Mode - Lesson 1", createModuleProgress(z_Lesson_steps.lesson_1_steps.length));
                moduleProgressData.put("Free Use Mode - Lesson 2", createModuleProgress(z_Lesson_steps.lesson_2_steps.length));
                moduleProgressData.put("Free Use Mode - Lesson 3", createModuleProgress(z_Lesson_steps.lesson_3_steps.length));
                moduleProgressData.put("Free Use Mode - Lesson 4", createModuleProgress(z_Lesson_steps.lesson_4_steps.length));

                // Create a map to store user data
                Map<String, Object> userData = new HashMap<>();
                userData.put("First Name", firstName);
                userData.put("Last Name", lastName);
                userData.put("Email Address", email);
                userData.put("Password", password);
                userData.put("Gender", gender);
                userData.put("Profile Picture", defaultProfilePicture);
                userData.put("Age", age);
                userData.put("App Update Notification", true);
                userData.put("New Course Available Notification", true);
                userData.put("Reminder Notification", true);

                // Initialize Firebase Authentication
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                if (validCounter == 4) {
                    // Check if the email is already in use
                    mAuth.fetchSignInMethodsForEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    List<String> signInMethods = task.getResult().getSignInMethods();
                                    if (signInMethods != null && !signInMethods.isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "The email is already taken.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        mAuth.createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(createUserTask -> {
                                                    if (createUserTask.isSuccessful()) {
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        String userId = user.getUid();
                                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                        db.collection("users").document(userId).set(userData)
                                                                .addOnSuccessListener(aVoid -> {
                                                                    Toast.makeText(getApplicationContext(), "User details saved to Firestore", Toast.LENGTH_SHORT).show();
                                                                    saveUserModuleProgress(db, userId, moduleProgressData);
                                                                    finish();
                                                                })
                                                                .addOnFailureListener(e -> {
                                                                    Toast.makeText(getApplicationContext(), "Error saving user details to Firestore", Toast.LENGTH_SHORT).show();
                                                                    Log.e(TAG, "Error saving user details", e);
                                                                    clearAllFields();
                                                                });
                                                    } else {
                                                        Log.w(TAG, "createUserWithEmail:failure", createUserTask.getException());
                                                        if (createUserTask.getException() instanceof FirebaseAuthUserCollisionException) {
                                                            Toast.makeText(getApplicationContext(), "The email is already taken.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Authentication failed: " + createUserTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Log.e(TAG, "Error fetching sign-in methods for email", task.getException());
                                    Toast.makeText(getApplicationContext(), "Error checking email address. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Find the login LinearLayout
        LinearLayout loginLinearLayout = findViewById(R.id.btnLogin);

        // Set onClickListener for the login LinearLayout
        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(a_user_2_signup.this, a_user_1_login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private int validateName(String name, String field) {
        if (name.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your " + field + " name", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (!name.matches("[a-zA-Z]+")) {
            Toast.makeText(a_user_2_signup.this, field + " name can only contain letters", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    private int validateEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(a_user_2_signup.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    private int validatePassword(String password) {
        if (password.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (password.length() < 8) {
            Toast.makeText(a_user_2_signup.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (!password.matches(".*[a-z].*")) {
            Toast.makeText(a_user_2_signup.this, "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (!password.matches(".*[A-Z].*")) {
            Toast.makeText(a_user_2_signup.this, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 1;
    }

    private int calculateAge(DatePicker datePicker) {
        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        int age;
        if (currentMonth > month || (currentMonth == month && currentDay >= day)) {
            age = currentYear - year;
        } else {
            age = currentYear - year - 1;
        }
        return age;
    }

    private Map<String, Object> createModuleProgress(int moduleCount) {
        Map<String, Object> moduleProgress = new HashMap<>();
        for (int i = 1; i <= moduleCount; i++) {
            moduleProgress.put("M" + i, 0);
        }
        return moduleProgress;
    }

    private void saveUserModuleProgress(FirebaseFirestore db, String userId, Map<String, Map<String, Object>> moduleProgressData) {
        for (Map.Entry<String, Map<String, Object>> entry : moduleProgressData.entrySet()) {
            String collectionPath = entry.getKey().contains("Progressive") ? "Progressive Mode" : "Free Use Mode";
            String documentName = entry.getKey().substring(entry.getKey().lastIndexOf('-') + 2).trim();
            db.collection("users").document(userId).collection(collectionPath)
                    .document(documentName)
                    .set(entry.getValue())
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Module progress saved successfully: " + documentName);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding module progress document: " + documentName, e);
                    });
        }
    }

    private void clearAllFields() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        datePicker.updateDate(2000, 0, 1);
        genderRadioGroup.clearCheck();
    }
}
