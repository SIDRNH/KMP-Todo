package com.doodle.todo.feature.auth.presentation.register

data class RegisterScreenState(
    val displayName: String = "",
    val displayNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading: Boolean = false,
) {
    val isFormValid: Boolean
        get() = !isLoading &&
                displayName.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                displayNameError == null &&
                emailError == null &&
                passwordError == null
}
