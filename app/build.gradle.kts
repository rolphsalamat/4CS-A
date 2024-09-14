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
        multiDexEnabled = true
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

    // for Text Lesson
    implementation ("com.codesgood:justifiedtextview:1.1.0")

    // Remove the deprecated YouTube Player API dependency
    // implementation("com.google.android.youtube:youtube-android-player-api:1.2.2")

    // MultiDex support
    implementation("com.android.support:multidex:1.0.3")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1") // For ProcessLifecycleOwner

    // Password Hashing
    implementation("org.mindrot:jbcrypt:0.4")

    // Theme
    implementation("com.google.android.material:material:1.8.0")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Notification
    implementation("androidx.work:work-runtime:2.7.1")

    // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-appcheck")
    implementation("com.google.firebase:firebase-appcheck-playintegrity")

    // Google Play Services dependencies
    implementation("com.google.android.gms:play-services-auth:20.0.0")

    // AndroidX dependencies
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.activity:activity:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Picasso for image loading
    implementation("com.squareup.picasso:picasso:2.71828")

    // Ucrop for image cropping
    implementation("com.github.yalantis:ucrop:2.2.6")

//    // Testing dependencies
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
