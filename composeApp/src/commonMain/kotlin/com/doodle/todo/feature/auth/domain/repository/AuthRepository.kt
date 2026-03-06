package com.doodle.todo.feature.auth.domain.repository

import com.doodle.todo.feature.auth.domain.model.User
import com.doodle.todo.feature.auth.util.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun signIn(email: String, password: String): AuthResult
    suspend fun signUp(email: String, password: String): AuthResult
    suspend fun updateDisplayName(displayName: String)
    suspend fun signOut()
}