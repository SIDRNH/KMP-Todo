package com.doodle.todo.feature.auth.presentation.login

sealed interface LoginScreenAction {
    data class OnEmailChanged(val email: String) : LoginScreenAction
    data class OnPasswordChanged(val password: String) : LoginScreenAction
    data object OnLoginClick : LoginScreenAction
    data object OnRegisterClick : LoginScreenAction
}