package com.grusie.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import com.grusie.presentation.R
import com.grusie.presentation.ui.theme.Gray900

@Composable
fun Progress(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyView(modifier: Modifier = Modifier, query: String? = null) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_box_icon),
            contentDescription = "empty_list"
        )
        Text(
            text = if (query != null) stringResource(
                id = R.string.str_empty_search_list,
                query
            ) else stringResource(id = R.string.str_empty_list)
        )
    }
}

@Composable
fun SingleAlertDialog(
    confirm: Boolean = false,
    title: String,
    content: String,
    confirmCallBack: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                onDismissRequest()
            },
            title = { Text(text = title) },
            text = { Text(text = content) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        confirmCallBack()
                        onDismissRequest()
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
                            onDismissRequest()
                            // 취소 동작
                        }) {
                        Text(stringResource(id = R.string.str_cancel))
                    }
                }
            }
        )
    }
}

@Composable
fun LoadErrorScreen(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_default)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.error_msg),
                color = Gray900,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Button(
                onClick = onRetryClick,
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
fun LoadStateFooter(
    modifier: Modifier = Modifier,
    loadState: LoadState,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_default)),
    ) {
        if (loadState is LoadState.Error) {
            LoadErrorScreen(onRetryClick = onRetryClick)
        }
    }
}

@Composable
fun ScrollTopBtn(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.margin_default))
            .background(Color.LightGray, shape = CircleShape), onClick = { onClick() }) {
        Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "scroll_top_icon")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackBtnTopBar(modifier: Modifier = Modifier, title: String, goToBack: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {

            Text(text = title, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(
                onClick = { goToBack() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "signIn_back_icon"
                )
            }
        }
    )
}

