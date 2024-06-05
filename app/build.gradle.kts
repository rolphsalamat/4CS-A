plugins {
    id("com.android.application")
    id("com.google.gms.google-services") version "4.4.2"
}

android {
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.example.autotutoria20"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    namespace = "com.example.autotutoria20"
}

dependencies {
    implementation("com.google.firebase:firebase-auth:21.0.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.activity:activity:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
//    implementation ("com.google.firebase:firebase-auth:21.0.1")
//    implementation ("com.facebook.android:facebook-android-sdk:[5,6)")
//    implementation ("androidx.browser:browser:1.0.0")
//    // replace all com.android.support libraries with their androidx equivalents
//    implementation ("androidx.core:core:1.5.0")
//    // Add other androidx dependencies as needed
}

// Add repositories block if necessary
//repositories {
//    google()
//    mavenCentral()
//}
