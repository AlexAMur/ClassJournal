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
        implementation(libs.serialisation)
       // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    }
}
dependencies {
    implementation(libs.identity.jvm)
}
