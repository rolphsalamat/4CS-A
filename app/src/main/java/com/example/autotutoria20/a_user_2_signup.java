package com.example.autotutoria20;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class a_user_2_signup extends AppCompatActivity {

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
            String TAG = "SignupActivity";
            @Override
            public void onClick(View v) {
                // Retrieve the values from the EditText views
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String gender = "";

                // Retrieve the selected gender from the RadioGroup
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.rdoFemale) {
                    gender = "Female";
                } else if (selectedId == R.id.rdoMale) {
                    gender = "Male";
                } else if (selectedId == R.id.rdoRatherNoySay) {
                    gender = "Rather not say";
                }

                // Calculate the actual age
                Calendar currentDate = Calendar.getInstance();
                int currentYear = currentDate.get(Calendar.YEAR);
                int currentMonth = currentDate.get(Calendar.MONTH) + 1; // Month is 0-based
                int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is 0-based
                int year = datePicker.getYear();
                int age;
                if (currentMonth > month || (currentMonth == month && currentDay >= day)) {
                    age = currentYear - year;
                } else {
                    age = currentYear - year - 1;
                }

                // PROGRESSIVE MODE

                // Define module progress data for Progressive Mode - Lesson 1
                Map<String, Object> progressiveModuleProgress_Lesson1 = new HashMap<>();
                progressiveModuleProgress_Lesson1.put("M1", 0);
                progressiveModuleProgress_Lesson1.put("M2", 0);
                progressiveModuleProgress_Lesson1.put("M3", 0);

                // Define module progress data for Progressive Mode - Lesson 2
                Map<String, Object> progressiveModuleProgress_Lesson2 = new HashMap<>();
                progressiveModuleProgress_Lesson2.put("M1", 0);
                progressiveModuleProgress_Lesson2.put("M2", 0);
                progressiveModuleProgress_Lesson2.put("M3", 0);

                // Define module progress data for Progressive Mode - Lesson 3
                Map<String, Object> progressiveModuleProgress_Lesson3 = new HashMap<>();
                progressiveModuleProgress_Lesson3.put("M1", 0);
                progressiveModuleProgress_Lesson3.put("M2", 0);
                progressiveModuleProgress_Lesson3.put("M3", 0);
                progressiveModuleProgress_Lesson3.put("M4", 0);

                // Define module progress data for Progressive Mode - Lesson 4
                Map<String, Object> progressiveModuleProgress_Lesson4 = new HashMap<>();
                progressiveModuleProgress_Lesson4.put("M1", 0);
                progressiveModuleProgress_Lesson4.put("M2", 0);
                progressiveModuleProgress_Lesson4.put("M3", 0);

                // FREE USE MODE

                // Define module progress data for Free Use Mode - Lesson 1
                Map<String, Object> freeUseModuleProgress_Lesson1 = new HashMap<>();
                freeUseModuleProgress_Lesson1.put("M1", 0);
                freeUseModuleProgress_Lesson1.put("M2", 0);
                freeUseModuleProgress_Lesson1.put("M3", 0);

                // Define module progress data for Free Use Mode - Lesson 2
                Map<String, Object> freeUseModuleProgress_Lesson2 = new HashMap<>();
                freeUseModuleProgress_Lesson2.put("M1", 0);
                freeUseModuleProgress_Lesson2.put("M2", 0);
                freeUseModuleProgress_Lesson2.put("M3", 0);

                // Define module progress data for Free Use Mode - Lesson 3
                Map<String, Object> freeUseModuleProgress_Lesson3 = new HashMap<>();
                freeUseModuleProgress_Lesson3.put("M1", 0);
                freeUseModuleProgress_Lesson3.put("M2", 0);
                freeUseModuleProgress_Lesson3.put("M3", 0);
                freeUseModuleProgress_Lesson3.put("M4", 0);

                // Define module progress data for Free Use Mode - Lesson 4
                Map<String, Object> freeUseModuleProgress_Lesson4 = new HashMap<>();
                freeUseModuleProgress_Lesson4.put("M1", 0);
                freeUseModuleProgress_Lesson4.put("M2", 0);
                freeUseModuleProgress_Lesson4.put("M3", 0);

                // Create a map to store user data
                Map<String, Object> userData = new HashMap<>();
                userData.put("First name", firstName);
                userData.put("Last name", lastName);
                userData.put("Email Address", email);
                userData.put("Password", password);
                userData.put("Gender", gender);
                userData.put("Age", age);

                // Initialize Firebase Authentication
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                // Sign up the user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign up success, proceed to save user details to Firestore
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid(); // Retrieve the user ID

                                // Add the user data to Firestore
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("users").document(userId).set(userData)
                                        .addOnSuccessListener(aVoid -> {
                                            // Display success message
                                            Toast.makeText(getApplicationContext(), "User details saved to Firestore", Toast.LENGTH_SHORT).show();

                                            // Add Progressive Mode - Lesson 1 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Progressive Mode")
                                                    .document("Lesson 1")
                                                    .set(progressiveModuleProgress_Lesson1)
                                                    .addOnSuccessListener(aVoid1 -> {
                                                        // Module 1 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Progressive Mode - Lesson 1 document", e);
                                                    });

                                            // Add Progressive Mode - Lesson 2 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Progressive Mode")
                                                    .document("Lesson 2")
                                                    .set(progressiveModuleProgress_Lesson2)
                                                    .addOnSuccessListener(aVoid2 -> {
                                                        // Module 2 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Progressive Mode - Lesson 2 document", e);
                                                    });

                                            // Add Progressive Mode - Lesson 3 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Progressive Mode")
                                                    .document("Lesson 3")
                                                    .set(progressiveModuleProgress_Lesson3)
                                                    .addOnSuccessListener(aVoid3 -> {
                                                        // Module 3 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Progressive Mode - Lesson 3 document", e);
                                                    });

                                            // Add Progressive Mode - Lesson 4 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Progressive Mode")
                                                    .document("Lesson 4")
                                                    .set(progressiveModuleProgress_Lesson4)
                                                    .addOnSuccessListener(aVoid4 -> {
                                                        // Module 4 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Progressive Mode - Lesson 4 document", e);
                                                    });

                                            // Add Free Use Mode - Lesson 1 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Free Use Mode")
                                                    .document("Lesson 1")
                                                    .set(freeUseModuleProgress_Lesson1)
                                                    .addOnSuccessListener(aVoid1 -> {
                                                        // Module 1 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Free Use Mode - Lesson 1 document", e);
                                                    });

                                            // Add Free Use Mode - Lesson 2 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Free Use Mode")
                                                    .document("Lesson 2")
                                                    .set(freeUseModuleProgress_Lesson2)
                                                    .addOnSuccessListener(aVoid2 -> {
                                                        // Module 2 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Free Use Mode - Lesson 2 document", e);
                                                    });

                                            // Add Free Use Mode - Lesson 3 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Free Use Mode")
                                                    .document("Lesson 3")
                                                    .set(freeUseModuleProgress_Lesson3)
                                                    .addOnSuccessListener(aVoid3 -> {
                                                        // Module 3 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Free Use Mode - Lesson 3 document", e);
                                                    });

                                            // Add Free Use Mode - Lesson 4 module progress data to Firestore
                                            db.collection("users").document(userId).collection("Free Use Mode")
                                                    .document("Lesson 4")
                                                    .set(freeUseModuleProgress_Lesson4)
                                                    .addOnSuccessListener(aVoid4 -> {
                                                        // Module 1 progress saved successfully
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e(TAG, "Error adding Free Use Mode - Lesson 4 document", e);
                                                    });

                                            finish();

                                        })
                                        .addOnFailureListener(e -> {
                                            // Display error message
                                            Toast.makeText(getApplicationContext(), "Error saving user details to Firestore", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "Error saving user details", e);
                                            clearAllFields();
                                        });
                            } else {
                                // If sign up fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Find the login TextView
        TextView loginTextView = findViewById(R.id.btnLogin);
        // Set onClickListener for the login TextView
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the login page (LoginActivity)
                Intent intent = new Intent(a_user_2_signup.this, a_user_1_login.class);
                startActivity(intent);
                // Finish the current activity (SignupActivity) to prevent going back to it
                finish();
            }
        });
    }

    private void clearAllFields() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        datePicker.updateDate(2000, 0, 1); // Reset to a default date
        genderRadioGroup.clearCheck();
    }
}
