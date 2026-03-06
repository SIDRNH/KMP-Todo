package com.doodle.todo.feature.auth.presentation.register

sealed interface RegisterScreenEvent {
    data object NavigateToLogin: RegisterScreenEvent
    data object NavigateToHome: RegisterScreenEvent
    data class ShowSnackbar(val message: String): RegisterScreenEvent
}