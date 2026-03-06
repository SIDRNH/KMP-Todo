package com.doodle.todo.feature.auth.data.reposiotry

import com.doodle.todo.feature.auth.data.mapper.toUser
import com.doodle.todo.feature.auth.domain.model.User
import com.doodle.todo.feature.auth.domain.repository.AuthRepository
import com.doodle.todo.feature.auth.util.AuthResult
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override val currentUser: Flow<User?> = firebaseAuth.authStateChanged.map { firebaseUser -> firebaseUser?.toUser() }.distinctUntilChanged()

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email.trim(), password)
            val user = result.user?.toUser() ?: return AuthResult.Error.Unknown
            AuthResult.Success(user)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            mapSignInException(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email.trim(), password)
            val user = result.user?.toUser() ?: return AuthResult.Error.Unknown
            AuthResult.Success(user)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            mapSignUpException(e)
        }
    }

    override suspend fun updateDisplayName(displayName: String) {
        val user = firebaseAuth.currentUser ?: throw IllegalStateException("User not logged in")
        user.updateProfile(displayName = displayName.trim())
    }

    override suspend fun signOut() = firebaseAuth.signOut()

    private fun mapSignInException(e: Throwable): AuthResult.Error {
        return when (e) {
            is FirebaseAuthInvalidCredentialsException -> AuthResult.Error.InvalidCredentials
            is FirebaseAuthInvalidUserException -> AuthResult.Error.UserNotFound
            else -> mapCommonExceptions(e)
        }
    }

    private fun mapSignUpException(e: Throwable): AuthResult.Error {
        return when (e) {
            is FirebaseAuthUserCollisionException -> AuthResult.Error.UserAlreadyExists
            is FirebaseAuthInvalidCredentialsException -> AuthResult.Error.WeakPassword
            else -> mapCommonExceptions(e)
        }
    }

    private fun mapCommonExceptions(e: Throwable): AuthResult.Error {
        val message = e.message?.lowercase() ?: return AuthResult.Error.Unknown
        return when {
            "network" in message -> AuthResult.Error.Network
            else -> AuthResult.Error.Unknown
        }
    }
}