import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed plugins {
plugins{
    kotlin("multiplatform")
    alias(libs.plugins.androidLibrary)
}

kotlin {

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "chat"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                // implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.cogniheroid.framework.shared.core.chat"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
