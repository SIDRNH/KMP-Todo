package com.doodle.todo.feature.auth.util

import com.doodle.todo.feature.auth.domain.model.User

sealed interface AuthResult {
    data class Success(val user: User) : AuthResult

    sealed interface Error: AuthResult {
        data object InvalidCredentials : Error
        data object UserNotFound : Error
        data object UserAlreadyExists : Error
        data object WeakPassword : Error
        data object Network : Error
        data object Unknown : Error
    }
}