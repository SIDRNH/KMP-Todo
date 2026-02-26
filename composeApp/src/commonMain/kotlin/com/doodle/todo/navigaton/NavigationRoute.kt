package com.doodle.todo.navigaton

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationRoute: NavKey {
    @Serializable
    data object SignIn: NavigationRoute, NavKey

    @Serializable
    data object SignUp: NavigationRoute, NavKey
}