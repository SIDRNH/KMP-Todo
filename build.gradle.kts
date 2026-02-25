plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    //BuildKonfig
    alias(libs.plugins.buildkonfig) apply false

    //Google Services
    alias(libs.plugins.google.services) apply false

    //Kotlin
    alias(libs.plugins.kotlin.serialization) apply false

    //KSP
    alias(libs.plugins.ksp) apply false

    //Room
    alias(libs.plugins.androidx.room) apply false
}