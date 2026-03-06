package com.doodle.todo.di

import com.doodle.todo.feature.auth.data.reposiotry.AuthRepositoryImpl
import com.doodle.todo.feature.auth.domain.repository.AuthRepository
import com.doodle.todo.feature.auth.presentation.login.LoginScreenViewModel
import com.doodle.todo.feature.auth.presentation.register.RegisterScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::RegisterScreenViewModel)
}