package com.doodle.todo.feature.auth.presentation.login

sealed interface LoginScreenEvent {
    data object NavigateToRegister: LoginScreenEvent
    data object NavigateToHome: LoginScreenEvent
    data class ShowSnackbar(val message: String): LoginScreenEvent
}