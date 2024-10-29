package com.example.autotutoria20;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class a_user_2_signup extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private boolean isPasswordVisible = false;
    private boolean isRetypePasswordVisibile = false;
    private Map<String, Object> userData;
    static CheckBox termsAndConditions;
    TextView firstNameEditText;
    TextView lastNameEditText;
    TextView emailEditText;
    TextView usernameEditText;
    TextView passwordEditText;
    TextView retypePaswordEditText;
    DatePicker datePicker;
    RadioGroup genderRadioGroup;

    // Must validate 5
    int validCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_user_2_signup);

        // Initialize EditText and other views
        firstNameEditText = findViewById(R.id.txtFirstName);
        lastNameEditText = findViewById(R.id.txtLastName);
        emailEditText = findViewById(R.id.txtEmail);
        usernameEditText = findViewById(R.id.txtUsername);
        passwordEditText = findViewById(R.id.txtPassword);
        retypePaswordEditText = findViewById(R.id.txtRetypePassword);
        genderRadioGroup = findViewById(R.id.rdoGender);

        datePicker = findViewById(R.id.datePicker);

        // Set the maximum date to today
        Calendar calendar = Calendar.getInstance();
        datePicker.setMaxDate(calendar.getTimeInMillis());

        ImageView pnc = findViewById(R.id.pnc_signup);
        pnc.setOnClickListener(v -> {
            Log.d(TAG, "PNC in Signup Class Clicked");
            openFacebookPage();
        });


        Log.d(TAG, "Views initialized");

        // Find the Button view
        Button signupButton = findViewById(R.id.btnSignup);

        ;
        EditText passwordText = findViewById(R.id.txtPassword);

        Button exitSignup = findViewById(R.id.exit_signup);
        exitSignup.setOnClickListener(v -> {
            Log.d(TAG, "Exit button clicked");
            finish();
        });

        ImageButton showHidePasswordButton = findViewById(R.id.btnShowPassword);
        showHidePasswordButton.setOnClickListener(v -> {
            Log.d(TAG, "Show/Hide password button clicked");
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
        });


        ImageButton showHideRePasswordButton = findViewById(R.id.btnShowRetypePassword);
        showHideRePasswordButton.setOnClickListener(v -> {
            Log.d(TAG, "Show/Hide password button clicked");
            // Toggle password visibility
            isRetypePasswordVisibile = !isRetypePasswordVisibile;

            // Change the password visibility in the EditText
            if (isRetypePasswordVisibile) {
                // Show password
                retypePaswordEditText.setTransformationMethod(null); // Set null to show the password
                showHideRePasswordButton.setBackgroundResource(R.drawable.hide_password); // Change icon to hide password
            } else {
                // Hide password
                retypePaswordEditText.setTransformationMethod(new PasswordTransformationMethod()); // Use PasswordTransformationMethod to hide the password
                showHideRePasswordButton.setBackgroundResource(R.drawable.show_password); // Change icon to show password
            }
        });

        signupButton.setOnClickListener(v -> {
            Log.d(TAG, "Signup button clicked");

            // Reset validCounter
            validCounter = 0;

            // Retrieve the values from the EditText views
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = retypePaswordEditText.getText().toString();
            String gender = "";

            Log.d(TAG, "Retrieved input values: firstName=" + firstName + ", lastName=" + lastName +
                    ", email=" + email + ", username=" + username + ", password=******");

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

            Log.d(TAG, "Gender selected: " + gender);

            // Calculate the actual age
            int age = calculateAge(datePicker);
            Log.d(TAG, "Calculated age: " + age);

            // PROGRESSIVE MODE and FREE USE MODE module progress data
            Map<String, Map<String, Object>> moduleProgressData = new HashMap<>();

            // Arrays to hold the lesson titles and modes
            String[] modes = {"Progressive Mode", "Free Use Mode"};
            String[] lessons = {
                    "Lesson 1",
                    "Lesson 2",
                    "Lesson 3",
                    "Lesson 4",
                    "Lesson 5",
                    "Lesson 6",
                    "Lesson 7",
                    "Lesson 8"
            };

            // Loop through each mode and lesson to populate the moduleProgressData map
            for (String mode : modes) {
                for (int i = 0; i < lessons.length; i++) {
                    String key = mode + " - " + lessons[i];
                    int stepLength = getLessonStepsLength(i + 1); // Method to get the lesson steps length
                    moduleProgressData.put(key, createModuleProgress(stepLength));
                }
            }

            Log.d(TAG, "Module progress data populated");

            // Hash the password before storing it
            String hashedPassword = a_user_3_password_encryption.hashPassword(password);

            // Create a map to store user data
            userData = new HashMap<>();
            userData.put("Tutorial", false);
            userData.put("First Name", firstName);
            userData.put("Last Name", lastName);
            userData.put("Email Address", email);
            userData.put("Username", username);
            userData.put("Password", hashedPassword); // Save the hashed password
            userData.put("Gender", gender);
            userData.put("Profile Picture", defaultProfilePicture);
            userData.put("Age", age);
            userData.put("App Update Notification", true);
            userData.put("New Course Available Notification", true);
            userData.put("Reminder Notification", true);

            Log.d(TAG, "User data populated");

            String tanginamo = "awdawd";

            // Initialize Firebase Authentication
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            // Validate inputs
            validCounter += validateName(firstName, "first");
            validCounter += validateName(lastName, "last");
            validCounter += validatePassword(password);
            validCounter += validateRetypePassword(password, confirmPassword);

            if (termsAndConditions.isChecked())
                validCounter++;
            else
                Toast.makeText(a_user_2_signup.this,
                "Please Agree to Terms and Conditions",
                Toast.LENGTH_SHORT).show();

            // Now handle asynchronous validations for email and username
            validateEmail(email, () -> {
                validateUsername(username, isValid -> {
                    if (isValid)
                        validCounter++;

                    Log.d(TAG, "Final validCounter: " + validCounter);

//                    if (validCounter == 6) { // eto na kapag may CONFIRM PASSWORD na
                        if (validCounter == 7) { // eto na kapag may Terms and Conditions na
                        Log.d(TAG, "All validations passed. Proceeding with signup");

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(createUserTask -> {
                                    if (createUserTask.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            // Send verification email
                                            user.sendEmailVerification()
                                                    .addOnCompleteListener(verificationTask -> {
                                                        if (verificationTask.isSuccessful()) {
                                                            // Notify user that a verification email was sent
                                                            Toast.makeText(getApplicationContext(),"Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                                            // Save user data to Firestore
                                                            String userId = user.getUid();
                                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                            db.collection("users").document(userId).set(userData)
                                                                    .addOnSuccessListener(aVoid -> {
                                                                        Log.d(TAG, "User details saved to Firestore");
                                                                        Toast.makeText(getApplicationContext(),
                                                                                "User details saved to Firestore",
                                                                                Toast.LENGTH_SHORT).show();
                                                                        saveUserModuleProgress(db, userId, moduleProgressData);
                                                                        // Clear fields and prevent further action until email is verified
                                                                        clearAllFields();
                                                                    })
                                                                    .addOnFailureListener(e -> {
                                                                        Log.e(TAG, "Error saving user details to Firestore", e);
                                                                        Toast.makeText(getApplicationContext(),
                                                                                "Error saving user details to Firestore",
                                                                                Toast.LENGTH_SHORT).show();
                                                                    });
                                                            finish();
                                                        } else {
                                                            Log.e(TAG, "Failed to send verification email", verificationTask.getException());
                                                            Toast.makeText(getApplicationContext(),
                                                                    "Failed to send verification email.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", createUserTask.getException());
                                        if (createUserTask.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(getApplicationContext(), "The email is already taken.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Authentication failed: " + createUserTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

//                        FirebaseFirestore db = FirebaseFirestore.getInstance();
//                        Map<String, Object> usernameData = new HashMap<>();
//                        Map<String, Object> emailData = new HashMap<>();
//                        usernameData.put("Username", username);
//                        emailData.put("Email", email);


//                        db.collection("usernames").document(username).set(usernameData)
//                                .addOnSuccessListener(aVoid -> Log.d(TAG, "Username saved to usernames collection"))
//                                .addOnFailureListener(e -> Log.e(TAG, "Error saving username", e));

                    } else {
                        Log.d(TAG, "Validation failed. ValidCounter: " + validCounter);
                        validCounter = 0;
                    }
                });
            });
        });

        // Find the login LinearLayout
        LinearLayout loginLinearLayout = findViewById(R.id.btnLogin);

        // Set onClickListener for the login LinearLayout
        loginLinearLayout.setOnClickListener(v -> {
            Log.d(TAG, "Login button clicked");
            Intent intent = new Intent(a_user_2_signup.this, a_user_1_login.class);
            startActivity(intent);
            finish();
        });

        termsAndConditions = findViewById(R.id.terms_and_conditions);
        termsAndConditions.setOnClickListener(v -> {
            termsAndConditions.setChecked(false);
            startActivity(new Intent(
                    a_user_2_signup.this,
                    a_user_5_terms_and_conditions.class));
        });


    }

    private int validateName(String name, String field) {
        if (name.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your " + field + " name", Toast.LENGTH_SHORT).show();
            Log.e("validateName", "Please enter your " + field + " name");
            return 0;
        } else if (!name.matches("[a-zA-Z\\s]+")) {  // Allows letters and spaces
            Toast.makeText(a_user_2_signup.this, field + " name can only contain letters and spaces", Toast.LENGTH_SHORT).show();
            Log.e("validateName", field + " name can only contain letters and spaces");
            return 0;
        }
        Log.e("validateName", field + " name is valid");
        return 1;
    }

    private void validateEmail(String email, Runnable onComplete) {
        if (email.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            Log.e("validateEmail", "Please enter your email");
            onComplete.run();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(a_user_2_signup.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            Log.e("validateEmail", "Please enter a valid email address");
            onComplete.run();
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<String> signInMethods = task.getResult().getSignInMethods();
                            if (signInMethods != null && !signInMethods.isEmpty()) {
                                Toast.makeText(a_user_2_signup.this, "The email is already taken.", Toast.LENGTH_SHORT).show();
                                Log.e("validateEmail", "The email is already taken.");
                            } else {
                                Log.e("validateEmail", email + " is valid");
                                validCounter++;  // Increment the counter only if the email is not taken
                            }
                        } else {
                            Log.e("SignupActivity", "Error fetching sign-in methods for email", task.getException());
                            Toast.makeText(a_user_2_signup.this, "Error checking email address. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        onComplete.run();  // Continue after the validation
                    });
        }
    }

    public interface UsernameValidationCallback {
        void onValidationComplete(boolean isValid);
    }

    private void validateUsername(String username, UsernameValidationCallback callback) {
        if (username.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your username", Toast.LENGTH_SHORT).show();
            Log.e("validateUsername", "Please enter your username.");
            callback.onValidationComplete(false);
        } else if (!username.matches("[a-zA-Z0-9_]+")) {
            Toast.makeText(a_user_2_signup.this, "Username can only contain letters, numbers, and underscores", Toast.LENGTH_SHORT).show();
            Log.e("validateUsername", "Username can only contain letters, numbers, and underscores");
            callback.onValidationComplete(false);
        } else if (username.length() < 3) {
            Toast.makeText(a_user_2_signup.this, "Username must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            Log.e("validateUsername", "Username must be at least 3 characters long");
            callback.onValidationComplete(false);
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Log.d("validateUsername", "Starting Firestore query for username: " + username);

            db.collection("usernames")
                    .whereEqualTo("Username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Log.e("validateUsername", username + " is a valid username");
                                callback.onValidationComplete(true);  // Username is valid since it's not taken
                            } else {
                                Toast.makeText(a_user_2_signup.this, "The username is already taken.", Toast.LENGTH_SHORT).show();
                                Log.e("validateUsername", "The username is already taken.");
                                callback.onValidationComplete(false);  // Username is taken
                            }
                        } else {
                            Log.e("validateUsername", "Firestore query failed: " + task.getException().getMessage());
                            Toast.makeText(a_user_2_signup.this, "Error checking username. Please try again.", Toast.LENGTH_SHORT).show();
                            callback.onValidationComplete(false);
                        }
                    });

        }
    }

    private int validatePassword(String password) {
        if (password.isEmpty()) {
            Toast.makeText(a_user_2_signup.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            Log.e("validatePassword", "Please enter your password.");
            return 0;
        } else if (password.length() < 8) {
            Toast.makeText(a_user_2_signup.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
            Log.e("validatePassword", "Password must be at least 8 characters long");
            return 0;
        } else if (!password.matches(".*[a-z].*")) {
            Toast.makeText(a_user_2_signup.this, "Password must contain at least one lowercase letter", Toast.LENGTH_SHORT).show();
            Log.e("validatePassword", "Password must contain at least one lowercase letter");
            return 0;
        } else if (!password.matches(".*[A-Z].*")) {
            Toast.makeText(a_user_2_signup.this, "Password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
            Log.e("validatePassword", "Password must contain at least one uppercase letter");
            return 0;
        }
        Log.e("validatePassword", password + " is a valid password");
        return 1;
    }

    private int validateRetypePassword(String password, String retypePassword) {
        if (!Objects.equals(retypePassword, password)) {
            Toast.makeText(a_user_2_signup.this, "Password does not match.", Toast.LENGTH_SHORT).show();
            Log.e("validateRetypePassword", "Password does not match.");
            return 0;
        } else {
            Log.e("validateRetypePassword", "Confirm Password match.");
            return 1;
        }
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

    // Modified code base sa new structure..
    private Map<String, Object> createModuleProgress(int moduleCount) {
        Map<String, Object> moduleProgress = new HashMap<>();

        for (int i = 1; i <= moduleCount; i++) {
            Map<String, Object> moduleData = new HashMap<>();
            moduleData.put("BKT Score", 0);
            moduleData.put("Post-Test Score", 0);
            moduleData.put("Pre-Test Score", 0);
            moduleData.put("Progress", 0);

            moduleProgress.put("M" + i, moduleData);
        }

        return moduleProgress;
    }


    // Original Code
//    private Map<String, Object> createModuleProgress(int moduleCount) {
//        Map<String, Object> moduleProgress = new HashMap<>();
//        for (int i = 1; i <= moduleCount; i++) {
//            moduleProgress.put("M" + i, 0);
//        }
//        return moduleProgress;
//    }

    private void saveUserModuleProgress(FirebaseFirestore db, String userId, Map<String, Map<String, Object>> moduleProgressData) {
        for (Map.Entry<String, Map<String, Object>> entry : moduleProgressData.entrySet()) {
            String collectionPath = entry.getKey().contains("Progressive") ? "Progressive Mode" : "Free Use Mode";
            String documentName = entry.getKey().substring(entry.getKey().lastIndexOf('-') + 2).trim();

            // Get the module progress data
            Map<String, Object> progressData = entry.getValue();

//            // Initialize BKT Scores based on the lesson number
//            int lessonNumber = getLessonIndexFromDocumentName(documentName) + 1;
//            Map<String, Double> bktScores = initializeBKTScores(lessonNumber);
//            progressData.put("BKT Scores", bktScores);

            // Save the module progress to Firestore
            db.collection("users").document(userId).collection(collectionPath)
                    .document(documentName)
                    .set(progressData)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Module progress and BKT scores saved successfully: " + collectionPath + " | " + documentName))
                    .addOnFailureListener(e -> Log.e(TAG, "Error adding module progress document: " + documentName, e));
        }
    }

    private int getModuleIndexFromKey(String key) {
        // Assuming the key is like "Progressive Mode - Lesson 1"
        String mode = key.split(" - ")[0].trim();
        switch (mode) {
            case "Progressive Mode":
                return 0;
            case "Free Use Mode":
                return 1;
            default:
                return -1; // Invalid key
        }
    }

    private int getLessonIndexFromDocumentName(String documentName) {
        // Extract the lesson number from the document name, assuming it's in the format "Lesson X"
        return Integer.parseInt(documentName.replaceAll("[^0-9]", "")) - 1;
    }

//    bakit may kasamang 0 sa BKT Scores, dapat 1 to 4 lang sya

    private Map<String, Double> initializeBKTScores(int lessonNumber) {
        String EYYY = "initializeBKTScores()";
        Log.e(EYYY, "lessonNumber: " + lessonNumber);

        // Get the lesson steps length
        int lessonStepCount = getLessonStepsLength(lessonNumber);

        // Initialize the BKT scores map
        Map<String, Double> bktScores = new HashMap<>();

        // Populate the BKT scores map based on the number of steps in the lesson
        for (int i = 0; i < lessonStepCount; i++) {
            bktScores.put(String.valueOf(i), 0.0); // Use the step number as the key
        }

        return bktScores;
    }

    private void clearAllFields() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        emailEditText.setText("");
        usernameEditText.setText("");
        passwordEditText.setText("");
//        retypePasswordEditText.setText("");
        datePicker.updateDate(2000, 0, 1);
        genderRadioGroup.clearCheck();
    }

    private int getLessonStepsLength(int lessonNumber) {
        switch (lessonNumber) {
            case 1:
                return z_Lesson_steps.lesson_1_steps.length;
            case 2:
                return z_Lesson_steps.lesson_2_steps.length;
            case 3:
                return z_Lesson_steps.lesson_3_steps.length;
            case 4:
                return z_Lesson_steps.lesson_4_steps.length;
            case 5:
                return z_Lesson_steps.lesson_5_steps.length;
            case 6:
                return z_Lesson_steps.lesson_6_steps.length;
            case 7:
                return z_Lesson_steps.lesson_7_steps.length;
            case 8:
                return z_Lesson_steps.lesson_8_steps.length;
            default:
                throw new IllegalArgumentException("Invalid lesson number: " + lessonNumber);
        }
    }

    private void showUserDetails() {
        Log.e(TAG, "User Details:");

        // Iterate over the userData map and log each key-value pair
        for (Map.Entry<String, Object> entry : userData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Log.e(TAG, key + ": " + value.toString());
        }
    }

    private void openFacebookPage() {
        String facebookUrl = "https://www.facebook.com/ucpncofficial";
        String facebookAppUrl = "fb://facewebmodal/f?href=" + facebookUrl;
        String facebookLiteUrl = "fb://lite/";

        // Try to open Facebook App
        try {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookAppUrl));
            facebookIntent.setPackage("com.facebook.katana"); // Facebook App package name
            startActivity(facebookIntent);
        } catch (Exception e) {
            // If Facebook app is not available, try Facebook Lite
            try {
                Intent facebookLiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookLiteUrl));
                facebookLiteIntent.setPackage("com.facebook.lite"); // Facebook Lite package name
                startActivity(facebookLiteIntent);
            } catch (Exception liteException) {
                // If Facebook Lite is not available, open in default browser
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                    startActivity(browserIntent);
                } catch (Exception browserException) {
                    // In case no browser is available, show error or handle fallback
                    Toast.makeText(this, "No application available to open Facebook", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
