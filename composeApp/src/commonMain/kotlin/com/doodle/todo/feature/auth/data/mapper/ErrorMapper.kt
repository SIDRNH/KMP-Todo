package com.doodle.todo.feature.auth.data.mapper

import com.doodle.todo.feature.auth.util.AuthResult

fun AuthResult.Error.toMessage(): String = when(this) {
    AuthResult.Error.InvalidCredentials -> "Invalid email or password"
    AuthResult.Error.Network -> "Check your internet connection"
    AuthResult.Error.Unknown -> "Something went wrong"
    AuthResult.Error.UserAlreadyExists -> "Account already exists"
    AuthResult.Error.UserNotFound -> "User not Found"
    AuthResult.Error.WeakPassword -> "Password is too weak"
}