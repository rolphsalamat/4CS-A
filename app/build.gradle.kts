plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
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
    // Use the Firebase BoM to manage Firebase library versions
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))

    // Firebase dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-appcheck")

    // Google Play Services dependencies
    implementation("com.google.android.gms:play-services-auth:19.2.0")

    // AndroidX dependencies
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.activity:activity:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    // Material Design dependency
    implementation("com.google.android.material:material:1.5.0")

    // Picasso for image loading
    implementation("com.squareup.picasso:picasso:2.71828")

    // Testing dependencies
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
