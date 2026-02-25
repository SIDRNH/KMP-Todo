package com.doodle.todo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    Firebase.initialize(
        context = null,
        options = FirebaseOptions(
            applicationId = BuildKonfig.FIREBASE_APP_ID,
            apiKey = BuildKonfig.FIREBASE_API_KEY,
            storageBucket = BuildKonfig.FIREBASE_STORAGE_BUCKET,
            projectId = BuildKonfig.FIREBASE_PROJECT_ID,
            authDomain = BuildKonfig.FIREBASE_AUTH_DOMAIN
        )
    )
    ComposeViewport {
        App()
    }
}