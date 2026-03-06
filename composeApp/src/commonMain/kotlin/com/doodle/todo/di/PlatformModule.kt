package com.doodle.todo.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.koin.dsl.module

val platformModule = module {
    single { Firebase.auth }
}