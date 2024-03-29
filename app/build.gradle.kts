plugins {
    id("com.android.application")
}

android {
    namespace = "algonquin.cst2335.android_group_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "algonquin.cst2335.android_group_project"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Volley for network requests
    implementation ("com.android.volley:volley:1.2.1")

// Room for database operations
    implementation ("androidx.room:room-runtime:2.3.0")
    annotationProcessor ("androidx.room:room-compiler:2.3.0") // For Java


// Lifecycle components for LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

// RecyclerView for displaying lists
    implementation ("androidx.recyclerview:recyclerview:1.2.1")



}