package com.doodle.todo.feature.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val email: String?,
    val displayName: String?
)
