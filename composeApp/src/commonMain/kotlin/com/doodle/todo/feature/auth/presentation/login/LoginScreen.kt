package com.doodle.todo.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
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
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChanged,
            label = { Text("Email") },
            singleLine = true,
            isError = emailError != null,
            supportingText = {
                emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChanged,
            label = { Text("Password") },
            singleLine = true,
            isError = passwordError != null,
            supportingText = {
                passwordError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Text(
                        if (passwordVisible) "Hide"
                        else "Show"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    if (isFormValid && !isLoading) onLoginClick()
                }
            )
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = onLoginClick,
            enabled = isFormValid && !isLoading,
            content = {
                if (isLoading) CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
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