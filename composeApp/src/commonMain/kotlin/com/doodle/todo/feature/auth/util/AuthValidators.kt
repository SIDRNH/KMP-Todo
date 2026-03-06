package com.doodle.todo.feature.auth.util

object AuthValidators {
    private val EMAIL_REGEX = Regex("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")

    private val PASSWORD_REGEX = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$")

    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email is Required"
        if (!EMAIL_REGEX.matches(email)) return "Invalid Email"
        return null
    }

    fun validatePasswordForRegister(password: String): String? {
        if (password.isBlank()) return "Password is Required"
        if (!PASSWORD_REGEX.matches(password)) return "Password must be at least 8 characters and include letters, numbers, and a special character"
        return null
    }

    fun validatePasswordForLogin(password: String): String? {
        if (password.isBlank()) return "Password is Required"
        return null
    }

    fun validateDisplayNameForRegister(displayName: String): String? {
        if (displayName.isBlank()) return "Name is Required"
        return null
    }
}