package com.example.randomanimegenerator.feature_profile.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SignOutDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onSignOut: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = closeDialog,
            text = {
                Text(
                    text = "Are you sure you want to sign out?",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    closeDialog()
                    onSignOut()
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = closeDialog) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}