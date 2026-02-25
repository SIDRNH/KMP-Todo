package com.doodle.todo.feature.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(64.dp))
        Content(
            displayName = state.displayName,
            onDisplayNameChanged = { onAction(RegisterScreenAction.OnDisplayNameChanged(it)) },
            email = state.email,
            onEmailChanged = { onAction(RegisterScreenAction.OnEmailChanged(it)) },
            password = state.password,
            onPasswordChanged = { onAction(RegisterScreenAction.OnPasswordChanged(it)) },
            isLoading = state.isLoading,
            isFormValid = state.isFormValid,
            onRegisterClick = { onAction(RegisterScreenAction.OnRegisterClick) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { onAction(RegisterScreenAction.OnLoginClick) },
            content = { Text(text = "Already a User? Wanna Login?") }
        )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    displayName: String,
    onDisplayNameChanged: (String) -> Unit,
    email: String,
    emailError: String? = null,
    onEmailChanged: (String) -> Unit,
    password: String,
    passwordError: String? = null,
    onPasswordChanged: (String) -> Unit,
    isLoading: Boolean,
    isFormValid: Boolean,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = displayName,
            onValueChange = onDisplayNameChanged,
            label = { Text("Name") },
            placeholder = { Text("Name") },
        )
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
            isError = passwordError != null,
            supportingText = {
                passwordError?.let { Text(text = it) }
            }
        )
        Button(
            onClick = onRegisterClick,
            enabled = isFormValid,
            content = {
                if (isLoading) CircularProgressIndicator()
                else Text(text = "Sign Up")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    MaterialTheme {
        Content(
            displayName = "John Doe",
            onDisplayNameChanged = {},
            email = "email@example.com",
            onEmailChanged = {},
            password = "password",
            onPasswordChanged = {},
            isLoading = false,
            onRegisterClick = {},
            isFormValid = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            state = RegisterScreenState(
                displayName = "John Doe",
                email = "email@example.com",
                password = "password"
            ),
            onAction = {}
        )
    }
}