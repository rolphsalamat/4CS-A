package com.example.autotutoria20;

import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a password using BCrypt
    public static String hashPassword(String plainTextPassword) {
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        Log.e("hashPassword", "original password: " + plainTextPassword);
        Log.e("hashPassword", "hashed password: " + hashedPassword);
        return hashedPassword;
    }

    // Check if a plaintext password matches a hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        Log.e("checkPassword", "original password: " + plainTextPassword);
        Log.e("checkPassword", "checking against hash: " + hashedPassword);
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}

