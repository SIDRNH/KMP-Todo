package com.doodle.todo.feature.auth.presentation.login

data class LoginScreenState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
) {
    val isFormValid: Boolean
        get() = !isLoading && emailError == null && passwordError == null
}
