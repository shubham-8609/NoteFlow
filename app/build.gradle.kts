plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("kotlin-kapt") // ✅ Add this line
}

android {
    namespace = "com.codeleg.noteflow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.codeleg.noteflow"
        minSdk = 30
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // ✅ ROOM dependencies
    implementation("androidx.room:room-runtime:2.8.1")
    implementation("androidx.room:room-ktx:2.8.1")
    kapt("androidx.room:room-compiler:2.8.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")

    // (Optional but recommended)
    // For using LiveData with ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.4")

    // (Optional)
    // LiveData builder for Coroutines
    implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.9.4")
    implementation("androidx.fragment:fragment-ktx:1.8.9")


    // (Optional) For saving and restoring UI state
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.9.4")
    // SSP AND SDP
    implementation("com.intuit.ssp:ssp-android:1.1.1")
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
