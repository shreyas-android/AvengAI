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
        create("nutrichef") {
            storeFile = file(properties.getProperty("STRESS_BUSTER_RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("STRESS_BUSTER_RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("STRESS_BUSTER_RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("STRESS_BUSTER_RELEASE_KEY_PASSWORD")
        }

        create("equationinsight") {
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

        create("avengai") {
            applicationIdSuffix = ".ai"
            versionCode = 7
            versionName = "1.0.5"
            signingConfig = signingConfigs.getByName("cogniheroid")
        }

        create("imageai") {
            applicationIdSuffix = ".image.ai"
            versionCode = 1
            versionName = "1.0.0"
            signingConfig = signingConfigs.getByName("cogniheroid")
        }

        create("chat") {
            applicationIdSuffix = ".ai.chat"
            versionCode = 1
            versionName = "1.0.0"
            signingConfig = signingConfigs.getByName("cogniheroid")
        }

        create("nutrichef") {
            applicationId = "com.androidai.galaxy.stressbuster"
            versionCode = 34
            versionName = "5.2.3"
            signingConfig = signingConfigs.getByName("nutrichef")
        }

        create("equationinsight"){
            applicationId = "com.androidai.galaxy.ad"
            versionCode = 47
            versionName = "8.1.3"
            signingConfig = signingConfigs.getByName("equationinsight")
        }
        create("inspire"){
            applicationIdSuffix = ".ai.inspire"
            versionCode = 1
            versionName = "1.0.0"
            signingConfig = signingConfigs.getByName("cogniheroid")
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

    implementation(project(":feature:nlpai"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:imageai"))
    implementation(project(":feature:inspireai"))
    implementation(project(":utils"))

    implementation(libs.androidx.security.crypto)

    implementation(libs.compose.constraintlayout)
}