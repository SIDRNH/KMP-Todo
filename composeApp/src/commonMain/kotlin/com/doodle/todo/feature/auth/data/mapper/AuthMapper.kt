package com.doodle.todo.feature.auth.data.mapper

import com.doodle.todo.feature.auth.domain.model.User
import dev.gitlive.firebase.auth.FirebaseUser

fun FirebaseUser.toUser(): User {
    return User(
        uid = this.uid,
        email = this.email,
        displayName = this.displayName
    )
}