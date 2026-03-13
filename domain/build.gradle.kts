import com.google.devtools.ksp.KspExperimental

plugins {
      alias(libs.plugins.android.library)
      alias(libs.plugins.kotlin.android)
      alias(libs.plugins.ksp)
      alias(libs.plugins.hilt)
    alias (libs.plugins.serialzation)


}

android {
    namespace = "com.catshome.classJournal"
    compileSdk = 36

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
        }
        dependencies {


        }
        ksp {
            @OptIn(KspExperimental::class)
            useKsp2 = true
        }
    }
}
dependencies {
    implementation(libs.identity.jvm)
    implementation(libs.hilt)
    implementation(libs.androidx.media3.common.ktx)
    ksp(libs.hilt.compiler)
    implementation(libs.serialisation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.datetime)
}
