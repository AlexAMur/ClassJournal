import com.google.devtools.ksp.KspExperimental
import org.gradle.kotlin.dsl.androidTest
import org.gradle.kotlin.dsl.kotlinOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias (libs.plugins.serialzation)

}

android {
    namespace = "com.catshome.classJournal"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.catshome.classJournal"
        minSdk = 29
        targetSdk = 35
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
    ksp{
        @OptIn(KspExperimental::class)
        useKsp2 = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_23
        }
    }


    buildFeatures {
        compose = true
    }

}
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.material)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.material)
    implementation(libs.material)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)




    implementation(libs.androidx.navigation.compose)
    implementation (libs.hilt.navigation.compose)
    implementation(libs.hilt)
    implementation(libs.room.runtime)

    ksp(libs.hilt.compiler)
    ksp(libs.room)

    implementation(project(":domain"))
    implementation(project(":data"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
