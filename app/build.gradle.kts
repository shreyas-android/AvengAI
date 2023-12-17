plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.cogniheroid.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cogniheroid.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 41
        versionName = "7.2.11"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    flavorDimensions += "version"

    productFlavors{

        create("convertor"){
            applicationIdSuffix = ".convertor"
            versionNameSuffix = "-convertor"
        }

        create("todo"){
            applicationIdSuffix = ".todo"
            versionNameSuffix = "-todo"
        }

        create("spinner"){
            applicationIdSuffix = ".spinner"
            versionNameSuffix = "-spinner"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.com.google.android.material.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)



    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)

    implementation(libs.kotlinCoroutines)

    implementation(libs.lifecycle.extensions)
   // implementation(libs.lifeCycleExtension)
    implementation(libs.lifeCycleRuntime)
    implementation(libs.lifeCycleProcess)

    implementation(project(":feature:convertor"))

}