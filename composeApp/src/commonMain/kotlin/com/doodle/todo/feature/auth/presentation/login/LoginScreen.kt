package com.doodle.todo.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(64.dp))
        Content(
            email = state.email,
            emailError = state.emailError,
            onEmailChanged = { onAction(LoginScreenAction.OnEmailChanged(it)) },
            password = state.password,
            passwordError = state.passwordError,
            onPasswordChanged = { onAction(LoginScreenAction.OnPasswordChanged(it)) },
            isLoading = state.isLoading,
            isFormValid = state.isFormValid,
            onLoginClick = { onAction(LoginScreenAction.OnLoginClick) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { onAction(LoginScreenAction.OnRegisterClick) },
            content = { Text(text = "New User? Wanna Register?") }
        )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String? = null,
    password: String,
    passwordError: String? = null,
    onPasswordChanged: (String) -> Unit,
    isLoading: Boolean,
    isFormValid: Boolean,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            placeholder = { Text("Email") },
            isError = emailError != null,
            supportingText = {
                emailError?.let { Text(text = it) }
            }
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text("Password") },
            placeholder = { Text("Password") },
            isError = passwordError != null
        )
        Button(
            onClick = onLoginClick,
            enabled = isFormValid,
            content = {
                if (isLoading) CircularProgressIndicator()
                else Text(text = "Sign In")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    MaterialTheme {
        Content(
            email = "email@example.com",
            onEmailChanged = {},
            password = "password",
            onPasswordChanged = {},
            isLoading = false,
            onLoginClick = {},
            isFormValid = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            state = LoginScreenState(
                email = "email@example.com",
                password = "password"
            ),
            onAction = {}
        )
    }
}