package com.doodle.todo.feature.auth.presentation.register

data class RegisterScreenState(
    val displayName: String = "",
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
) {
    val isFormValid: Boolean
        get() = !isLoading && emailError == null && passwordError == null && displayName.isNotBlank()
}
