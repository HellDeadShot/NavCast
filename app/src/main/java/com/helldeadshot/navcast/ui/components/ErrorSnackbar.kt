package com.helldeadshot.navcast.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackbar(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = "Dismiss"
        )
        onDismiss()
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier.padding(16.dp)
    ) { snackbarData ->
        Snackbar(
            action = {
                TextButton(onClick = { snackbarData.dismiss() }) {
                    Text("Dismiss")
                }
            }
        ) {
            Text(snackbarData.visuals.message)
        }
    }
}
