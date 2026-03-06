package com.doodle.todo.feature.auth.presentation.register

sealed interface RegisterScreenAction {
    data class OnDisplayNameChanged(val displayName: String) : RegisterScreenAction
    data class OnEmailChanged(val email: String) : RegisterScreenAction
    data class OnPasswordChanged(val password: String) : RegisterScreenAction
    data object OnLoginClick : RegisterScreenAction
    data object OnRegisterClick : RegisterScreenAction
}