package com.grusie.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.grusie.presentation.R

@Composable
fun Progress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyView() {
    Column(modifier = Modifier
        .fillMaxSize()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.empty_box_icon), contentDescription = "empty_list")
        Text(text = stringResource(id = R.string.str_empty_list))
    }
}

@Composable
fun SingleAlertDialog(
    confirm: Boolean,
    title: String,
    content: String,
    confirmCallBack: () -> Unit = {},
    cancelCallback: () -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = title) },
            text = { Text(text = content) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        confirmCallBack()
                        // 확인 동작
                    }) {
                    Text(stringResource(id = R.string.str_confirm))
                }
            },
            dismissButton = {
                if (confirm) {
                    Button(
                        onClick = {
                            showDialog.value = false
                            cancelCallback()
                            // 취소 동작
                        }) {
                        Text(stringResource(id = R.string.str_cancel))
                    }
                }
            }
        )
    }
}