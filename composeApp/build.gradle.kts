import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    //BuildKonfig
    alias(libs.plugins.buildkonfig)

    //Kotlin
    alias(libs.plugins.kotlin.serialization)

    //Google Services
    alias(libs.plugins.google.services)

    //KSP
    alias(libs.plugins.ksp)

    //Room
    alias(libs.plugins.androidx.room)
}

buildkonfig {
    packageName = "com.doodle.todo"

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    fun getLocalProperty(key: String): String {
        return localProperties.getProperty(key) ?: ""
    }

    defaultConfigs {
        buildConfigField(Type.STRING, "FIREBASE_API_KEY", "\"${getLocalProperty("FIREBASE_API_KEY")}\"")
        buildConfigField(Type.STRING, "FIREBASE_APP_ID", "\"${getLocalProperty("FIREBASE_APP_ID")}\"")
        buildConfigField(Type.STRING, "FIREBASE_PROJECT_ID", "\"${getLocalProperty("FIREBASE_PROJECT_ID")}\"")
        buildConfigField(Type.STRING, "FIREBASE_STORAGE_BUCKET", "\"${getLocalProperty("FIREBASE_STORAGE_BUCKET")}\"")
        buildConfigField(Type.STRING, "FIREBASE_AUTH_DOMAIN", "\"${getLocalProperty("FIREBASE_AUTH_DOMAIN")}\"")
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    js(IR) {
        browser {
            commonWebpackConfig { outputFileName = "composeApp.js" }
        }
        binaries.executable()
    }

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser {
//            commonWebpackConfig { outputFileName = "composeApp.js" }
//        }
//        binaries.executable()
//    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            //Coroutines
            implementation(libs.kotlinx.coroutines.play.services)

            //Koin
            implementation(libs.koin.android)

            //Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))

            //Room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
        }
        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.androidx.testExt.junit)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.espresso.core)
            implementation(libs.androidx.testExt.junit)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //Coroutines
            implementation(libs.kotlinx.coroutines.core)

            //Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            //Firebase - GitLive
            implementation(libs.firebase.common)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)

            //Navigation
            implementation(libs.navigation3.ui)
            implementation(libs.lifecycle.viewmodel.navigation3)

            //Kotlin
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.doodle.todo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.doodle.todo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

//Room Schema Directory
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    debugImplementation(libs.compose.uiTooling)

    //Room-KSP
    add("kspAndroid", libs.androidx.room.compiler)
}

