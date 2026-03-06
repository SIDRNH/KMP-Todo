package com.doodle.todo.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doodle.todo.feature.auth.data.mapper.toMessage
import com.doodle.todo.feature.auth.domain.repository.AuthRepository
import com.doodle.todo.feature.auth.util.AuthResult
import com.doodle.todo.feature.auth.util.AuthValidators
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel (
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    private val _event = Channel<LoginScreenEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: LoginScreenAction) {
        when(action) {
            is LoginScreenAction.OnEmailChanged -> {
                val error = AuthValidators.validateEmail(action.email)
                _state.update { it.copy(email = action.email, emailError = error) }
            }
            LoginScreenAction.OnLoginClick -> {
                signIn()
            }
            is LoginScreenAction.OnPasswordChanged -> {
                val error = AuthValidators.validatePasswordForLogin(action.password)
                _state.update { it.copy(password = action.password, passwordError = error) }
            }

            LoginScreenAction.OnRegisterClick -> {
                viewModelScope.launch {
                    _event.send(LoginScreenEvent.NavigateToRegister)
                }
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                when(val result = authRepository.signIn(_state.value.email, _state.value.password)) {
                    is AuthResult.Success -> {
                        _event.send(LoginScreenEvent.NavigateToHome)
                    }
                    is AuthResult.Error -> {
                        _event.send(LoginScreenEvent.ShowSnackbar(result.toMessage()))
                    }
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}