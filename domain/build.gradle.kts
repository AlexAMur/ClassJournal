plugins {
      alias(libs.plugins.android.library)
      alias(libs.plugins.kotlin.android)
      alias(libs.plugins.ksp)
      alias(libs.plugins.hilt)
}

android {
    namespace = "com.catshome.classjornal"
    compileSdk = 35
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
    }
    dependencies{
        implementation(libs.hilt)
        ksp(libs.hilt.compiler)
    }
}
