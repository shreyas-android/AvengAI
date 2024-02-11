import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.cogniheroid.android"
    compileSdk = 34

    val keystoreFile = project.rootProject.file("gradle.properties")
    val properties = org.jetbrains.kotlin.konan.properties.Properties()
    properties.load(keystoreFile.inputStream())

    defaultConfig {
        applicationId = "com.cogniheroid.android"
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"



        // Set API keys in BuildConfig
        buildConfigField(
            "String", "cogni_heroid_ai_api_key",
            properties.getProperty("COGNI_HEROID_AI_API_KEY"))
    }

    signingConfigs {
        create("stressbuster") {
            storeFile = file(properties.getProperty("STRESS_BUSTER_RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("STRESS_BUSTER_RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("STRESS_BUSTER_RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("STRESS_BUSTER_RELEASE_KEY_PASSWORD")
        }

        create("adgalaxy") {
            storeFile = file(properties.getProperty("ADGALAXY_RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("ADGALAXY_RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("ADGALAXY_RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("ADGALAXY_RELEASE_KEY_PASSWORD")
        }

        create("cogniheroid") {
            storeFile = file(properties.getProperty("COGNI_HEROID_AI_RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("COGNI_HEROID_AI_RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("COGNI_HEROID_AI_RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("COGNI_HEROID_AI_RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    productFlavors {

        create("convertor") {
            applicationIdSuffix = ".convertor"
            versionCode = 2
            versionName = "1.0.1"
            signingConfig = signingConfigs.getByName("cogniheroid")
        }

        create("avengai") {
            applicationIdSuffix = ".ai"
            versionCode = 6
            versionName = "1.0.4"
            signingConfig = signingConfigs.getByName("cogniheroid")
        }

        create("chat") {
            applicationIdSuffix = ".ai.chat"
            versionCode = 1
            versionName = "1.0.0"
            signingConfig = signingConfigs.getByName("cogniheroid")
        }

        create("stressbuster") {
            applicationId = "com.androidai.galaxy.stressbuster"
            versionCode = 29
            versionName = "5.0.3"
            signingConfig = signingConfigs.getByName("stressbuster")
        }

        create("adgalaxy"){
            applicationId = "com.androidai.galaxy.ad"
            versionCode = 43
            versionName = "8.0.1"
            signingConfig = signingConfigs.getByName("adgalaxy")
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

    implementation(libs.lifecycle.extensions) // implementation(libs.lifeCycleExtension)
    implementation(libs.lifeCycleRuntime)
    implementation(libs.lifeCycleProcess)

    implementation(project(":feature:convertor"))
    implementation(project(":feature:avengai"))
    implementation(project(":feature:chat"))

    implementation(libs.androidx.security.crypto)
}