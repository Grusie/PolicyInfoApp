package com.grusie.presentation.screen.manageauth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.presentation.R
import com.grusie.presentation.components.BackBtnTopBar
import com.grusie.presentation.components.Progress
import com.grusie.presentation.eventState.ManageAuthEventState
import com.grusie.presentation.ui.theme.Blue500
import com.grusie.presentation.uiState.ManageAuthUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.ManageAuthViewModel
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ManageAuthScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ManageAuthViewModel = hiltViewModel()
) {
    val uiState = viewModel.manageAuthUiState.collectAsState().value
    val nicknameText = viewModel.nicknameText.collectAsState().value
    var editNicknameFlag by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var alertCode: Int? by remember { mutableStateOf(null) }
    var errorCode: Exception? by remember { mutableStateOf(null) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    val focusManage = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
        viewModel.manageAuthEventState.collect { eventState ->
            Log.d("confirm manageAuthEventState : ", "$eventState")
            when (eventState) {
                is ManageAuthEventState.Alert -> {
                    alertCode = eventState.alert
                }

                is ManageAuthEventState.Error -> {
                    errorCode = eventState.error
                }

                is ManageAuthEventState.SuccessChangeNickname -> {
                    editNicknameFlag = false
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BackBtnTopBar(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.margin_large)),
                title = stringResource(id = R.string.str_manage_auth),
                goToBack = { navController.popBackStack() })
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(
                            id = R.dimen.margin_large
                        ), vertical = dimensionResource(id = R.dimen.margin_default)
                    )
            ) {

                OutlinedTextField(
                    value = nicknameText,
                    onValueChange = { viewModel.changeNicknameText(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.str_change_nickname)) },
                    trailingIcon = {
                        if (!editNicknameFlag) {
                            IconButton(onClick = { editNicknameFlag = true }) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = "change_nickname_icon",
                                    tint = Color.Black
                                )
                            }
                        } else {
                            Text(
                                text = stringResource(id = R.string.str_save),
                                color = Blue500,
                                modifier = Modifier.clickable {
                                    focusManage.clearFocus()
                                    viewModel.saveNickname()
                                })
                        }
                    },
                    enabled = editNicknameFlag
                )

                Text(text = stringResource(id = R.string.str_go_signOut), modifier = Modifier
                    .clickable { viewModel.signOut() }
                )
            }

            when (uiState) {
                is ManageAuthUiState.Loading -> {
                    Progress()
                }

                is ManageAuthUiState.SuccessSignOut -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.str_success_signOut),
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        navController.popBackStack()
                    }, Toast.LENGTH_SHORT.toLong())
                }

                else -> {
                }
            }

            if (errorCode != null) {
                coroutine.launch {
                    val result = snackBarHostState.showSnackbar(
                        message = TextUtils.getErrorMsg(
                            context,
                            error = errorCode!!
                        ),
                        actionLabel = "닫기"
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> {
                            errorCode = null
                        }

                        SnackbarResult.ActionPerformed -> {
                            errorCode = null
                        }
                    }
                }
            }
            if (alertCode != null) {
                coroutine.launch {
                    val result = snackBarHostState.showSnackbar(
                        message = TextUtils.getAlertMsg(
                            context,
                            alertCode = alertCode!!
                        )
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> {
                            alertCode = null
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Preview(heightDp = 200, showBackground = true)
@Composable
fun ManageAuthScreenPreview() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier,
        topBar = {
            BackBtnTopBar(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.margin_large)),
                title = stringResource(id = R.string.str_manage_auth),
                goToBack = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(
                            id = R.dimen.margin_large
                        ), vertical = dimensionResource(id = R.dimen.margin_default)
                    )
            ) {

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "닉네임") },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "change_nickname_icon",
                                tint = Color.Black
                            )
                        }
                        Text(text = "저장")
                    },
                    enabled = false,

                    )
                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_default)))
                Text(
                    text = stringResource(id = R.string.str_go_signOut), modifier = Modifier
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, heightDp = 200, showBackground = true)
@Composable
fun ManageAuthScreenDarkPreview() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier,
        topBar = {
            BackBtnTopBar(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.margin_large)),
                title = stringResource(id = R.string.str_manage_auth),
                goToBack = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(
                            id = R.dimen.margin_large
                        ), vertical = dimensionResource(id = R.dimen.margin_default)
                    )
            ) {

                OutlinedTextField(
                    value = stringResource(id = R.string.str_nickname),
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "닉네임") },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "change_nickname_icon",
                                tint = Color.Black
                            )
                        }
                    },
                    enabled = false,

                    )
                Text(
                    text = stringResource(id = R.string.str_go_signOut), modifier = Modifier
                )
            }
        }
    }
}