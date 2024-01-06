import org.jetbrains.kotlin.konan.properties.Properties

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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = org.jetbrains.kotlin.konan.properties.Properties()
        properties.load(keystoreFile.inputStream())

        // Set API keys in BuildConfig
        buildConfigField("String", "cogni_heroid_ai_api_key", "${properties.getProperty("COGNI_HEROID_AI_API_KEY")}")

      //  buildConfigField("String", "cogni_heroid_ai_api_key", properties.getProperty("COGNI_HEROID_AI_API_KEY"))
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
            versionCode = 1
            versionName = "1.0.0"
        }

        create("avengai"){
            applicationIdSuffix = ".ai"
            versionCode = 2
            versionName = "1.0.1"
        }

        create("chat"){
            applicationIdSuffix = ".chat"
            versionCode = 1
            versionName = "1.0.0"
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
    implementation(project(":feature:avengai"))
    implementation(project(":feature:chat"))
}