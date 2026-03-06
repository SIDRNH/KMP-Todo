package com.doodle.todo.feature.auth.presentation.register

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

class RegisterScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterScreenState())
    val state: StateFlow<RegisterScreenState> = _state.asStateFlow()

    private val _event = Channel<RegisterScreenEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: RegisterScreenAction) {
        when(action) {
            is RegisterScreenAction.OnDisplayNameChanged -> {
                val error = AuthValidators.validateDisplayNameForRegister(action.displayName)
                _state.update { it.copy(displayName = action.displayName, displayNameError = error) }
            }
            is RegisterScreenAction.OnEmailChanged -> {
                val error = AuthValidators.validateEmail(action.email)
                _state.update { it.copy(email = action.email, emailError = error) }
            }
            RegisterScreenAction.OnLoginClick -> {
                viewModelScope.launch {
                    _event.send(RegisterScreenEvent.NavigateToLogin)
                }
            }
            is RegisterScreenAction.OnPasswordChanged -> {
                val error = AuthValidators.validatePasswordForRegister(action.password)
                _state.update { it.copy(password = action.password, passwordError = error) }
            }
            RegisterScreenAction.OnRegisterClick -> signUp()
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                when(val result = authRepository.signUp(_state.value.email, _state.value.password)) {
                    is AuthResult.Success -> {
                        try {
                            authRepository.updateDisplayName(_state.value.displayName)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        _event.send(RegisterScreenEvent.NavigateToHome)
                    }
                    is AuthResult.Error -> {
                        _event.send(RegisterScreenEvent.ShowSnackbar(result.toMessage()))
                    }
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}