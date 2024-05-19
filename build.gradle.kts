// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.google.gms.google-services") version "4.3.15" apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifacts.applovin.com/android") }
        maven("https://cboost.jfrog.io/artifactory/chartboost-mediation/") {
            name = "Chartboost Mediation's Production Repo"
        }
        maven("https://cboost.jfrog.io/artifactory/chartboost-ads/") {
            name = "Chartboost's maven repo"
        }
    }
    dependencies {
        classpath("com.applovin.quality:AppLovinQualityServiceGradlePlugin:+")
    }
}
true // Needed to make the Suppress annotation work for the plugins block