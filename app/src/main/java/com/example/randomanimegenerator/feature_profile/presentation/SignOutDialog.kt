package com.example.randomanimegenerator.feature_profile.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.randomanimegenerator.R

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
                    text = stringResource(id = R.string.sign_out_dialog_text),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    closeDialog()
                    onSignOut()
                }) {
                    Text(text = stringResource(id = R.string.confirm_text))
                }
            },
            dismissButton = {
                TextButton(onClick = closeDialog) {
                    Text(text = stringResource(id = R.string.dismiss_text))
                }
            }
        )
    }
}