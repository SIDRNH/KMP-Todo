package com.doodle.todo.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    onEvent: suspend (T) -> Unit
) {
    LaunchedEffect(flow) {
        flow.collect { event ->
            onEvent(event)
        }
    }
}