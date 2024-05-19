@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.gms.google-services")
    id("applovin-quality-service")

}

android {
    namespace = "com.jackson.android"
    compileSdk = 34

    val keystoreFile = project.rootProject.file("gradle.properties")
    val properties = org.jetbrains.kotlin.konan.properties.Properties()
    properties.load(keystoreFile.inputStream())

    defaultConfig {
        applicationId = "com.jackson.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("jackson") {
            storeFile = file(properties.getProperty("JACK_SON_AI_RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("JACK_SON_AI_RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("JACK_SON_AI_RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("JACK_SON_AI_RELEASE_KEY_PASSWORD")
        }

        create("cogniheroid") {
            storeFile = file(properties.getProperty("COGNI_HEROID_AI_RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("COGNI_HEROID_AI_RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("COGNI_HEROID_AI_RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("COGNI_HEROID_AI_RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            /*proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )*/
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
        create("convertor") {
            applicationId = "com.cogniheroid.android.convertor"
            versionCode = 6
            versionName = "1.3.1"
            signingConfig = signingConfigs.getByName("cogniheroid")

            buildConfigField(
                "String", "google_ad_publish_key",
                properties.getProperty("VERSA_CONVERTOR_AD_MOB_PUB_ID"))

            buildConfigField(
                "String", "versa_convertor_applovin_sdk_key",
                properties.getProperty("VERSA_CONVERTOR_APPLOVIN_SDK_KEY"))

            buildConfigField(
                "String", "versa_convertor_helium_app_id",
                properties.getProperty("VERSA_CONVERTOR_HELIUM_APP_ID"))

            buildConfigField(
                "String", "versa_convertor_helium_app_signature",
                properties.getProperty("VERSA_CONVERTOR_HELIUM_APP_SIGNATURE"))

            buildConfigField(
                "String", "versa_convertor_start_io_app_id",
                properties.getProperty("VERSA_CONVERTOR_START_IO_APP_ID"))

            buildConfigField(
                "String", "versa_convertor_notix_app_id",
                properties.getProperty("VERSA_CONVERTOR_NOTIX_APP_ID"))

            buildConfigField(
                "String", "versa_convertor_notix_app_token",
                properties.getProperty("VERSA_CONVERTOR_NOTIX_APP_TOKEN"))
        }

        create("devicesize"){
            applicationIdSuffix = ".device.size"
            versionCode = 1
            versionName = "1.0.0"
            signingConfig = signingConfigs.getByName("jackson")
        }
    }
}

applovin {
    apiKey = "HUoRFWBKOshiHTwwuxpW6gIeBlOUpabipflVi7np_slUZTI4sIpP91L1LQxctcx6PpBcNdNd0WwtFSPdT8ad7W"
}


dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
   // implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(project(":feature:convertor"))

    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)

    implementation(libs.kotlinCoroutines)

    implementation(libs.lifecycle.extensions)
    implementation(libs.lifeCycleRuntime)
    implementation(libs.lifeCycleProcess)

    implementation("androidx.glance:glance-material3:1.0.0")
    implementation("androidx.glance:glance-appwidget:1.0.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database")
}