package com.example.randomanimegenerator.feature_profile.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.randomanimegenerator.R

@Composable
fun ChangeUserNameDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    changeUserName: (String) -> Unit
) {
    var newUserName by remember {
        mutableStateOf("")
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(
                    text = stringResource(id = R.string.input_new_user_name_text),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                TextField(
                    value = newUserName,
                    onValueChange = {
                        newUserName = it
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    closeDialog()
                    changeUserName(newUserName)
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