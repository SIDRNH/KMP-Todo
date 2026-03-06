package com.doodle.todo.navigaton

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.doodle.todo.feature.auth.presentation.login.LoginScreen
import com.doodle.todo.feature.auth.presentation.login.LoginScreenEvent
import com.doodle.todo.feature.auth.presentation.login.LoginScreenViewModel
import com.doodle.todo.feature.auth.presentation.register.RegisterScreen
import com.doodle.todo.feature.auth.presentation.register.RegisterScreenEvent
import com.doodle.todo.feature.auth.presentation.register.RegisterScreenViewModel
import com.doodle.todo.util.ObserveAsEvents
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(NavigationRoute.SignIn::class, NavigationRoute.SignIn.serializer())
                    subclass(NavigationRoute.SignUp::class, NavigationRoute.SignUp.serializer())
                }
            }
        },
        NavigationRoute.SignIn
    )
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = { key ->
            when(key) {
                is NavigationRoute.SignIn -> {
                    NavEntry(key) {
                        val viewModel = koinViewModel<LoginScreenViewModel>()
                        val state = viewModel.state.collectAsState()

                        ObserveAsEvents(viewModel.event) {
                            when(it) {
                                LoginScreenEvent.NavigateToHome -> snackbarHostState.showSnackbar(message = "To Be Implemented")
                                LoginScreenEvent.NavigateToRegister -> {
                                    backStack.add(NavigationRoute.SignUp)
                                }
                                is LoginScreenEvent.ShowSnackbar -> snackbarHostState.showSnackbar(message = it.message)
                            }
                        }

                        LoginScreen(
                            state = state.value,
                            onAction = viewModel::onAction
                        )
                    }
                }
                is NavigationRoute.SignUp -> {
                    NavEntry(key) {
                        val viewModel = koinViewModel<RegisterScreenViewModel>()
                        val state = viewModel.state.collectAsState()

                        ObserveAsEvents(viewModel.event) {
                            when(it) {
                                RegisterScreenEvent.NavigateToHome -> snackbarHostState.showSnackbar(message = "To Be Implemented")
                                RegisterScreenEvent.NavigateToLogin -> {
                                    backStack.removeLast()
                                }
                                is RegisterScreenEvent.ShowSnackbar -> snackbarHostState.showSnackbar(message = it.message)
                            }
                        }

                        RegisterScreen(
                            state = state.value,
                            onAction = viewModel::onAction
                        )
                    }
                }
                else -> error("Unknown Route: $key")
            }
        }
    )
}