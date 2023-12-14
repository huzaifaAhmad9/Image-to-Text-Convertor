plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.imagetotext"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.imagetotext"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // text recognition
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")

    // custom text-animation
    implementation ("com.daimajia.androidanimations:library:2.4@aar")

    // fancy toast
    implementation ("com.github.GrenderG:Toasty:1.5.2")

    // image slider
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
}