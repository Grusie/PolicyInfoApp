package com.grusie.presentation.screen.auth

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.presentation.R
import com.grusie.presentation.components.BackBtnTopBar
import com.grusie.presentation.components.Progress
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.eventState.SignInEventState
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.uiState.SignInUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.SignInViewModel

/**
 * 로그인 스크린 (로그인 or 회원가입 이동)
 **/
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SignInScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val idText = viewModel.idText.collectAsState()
    val pwText = viewModel.pwText.collectAsState()
    val signInUiState = viewModel.signInUiState.collectAsState().value
    val context = LocalContext.current
    var alertCode: Int? by remember { mutableStateOf(null) }
    var errorCode: Exception? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.signInEventState.collect { signInEventStateValue ->
            when (signInEventStateValue) {
                is SignInEventState.Alert -> {
                    alertCode = signInEventStateValue.alert
                }

                is SignInEventState.Error -> {
                    errorCode = signInEventStateValue.error
                }

                else -> {

                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BackBtnTopBar(
                title = stringResource(id = R.string.str_signIn),
                goToBack = { navController.popBackStack() })
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    horizontal = dimensionResource(
                        id = R.dimen.margin_large
                    )
                )
                .fillMaxSize()
        ) {
            Column() {
                EmailSignIn(
                    idText = idText.value,
                    pwText = pwText.value,
                    changeIdText = { viewModel.changeIdText(it) },
                    changePwText = { viewModel.changePwText(it) },
                    signInBtnOnClick = { viewModel.emailSignIn() },
                )

                Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_large)))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { navController.navigate(Screen.Signup.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.margin_large))
                ) {
                    Text(text = stringResource(id = R.string.str_go_signup))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.margin_large))
                ) {

                    //TODO : 추후 아이디 비밀번호 찾기 추가 예정
                }
            }
            Log.d("confirm signInUiState : ", "$signInUiState")
            when (signInUiState) {
                is SignInUiState.Loading -> {
                    Log.d("confirm loading : ", "$signInUiState")
                    Progress()
                }

                is SignInUiState.SuccessSignIn -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.str_success_signIn),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                }

                else -> {}
            }

            if (errorCode != null) {
                SingleAlertDialog(
                    confirm = false,
                    title = stringResource(id = R.string.str_title_error),
                    content = TextUtils.getErrorMsg(context, error = errorCode!!),
                    onDismissRequest = {
                        errorCode = null
                    }
                )
            }
            if (alertCode != null) {
                SingleAlertDialog(
                    title = stringResource(id = R.string.str_title_error),
                    content = TextUtils.getAlertMsg(context, alertCode!!),
                    onDismissRequest = {
                        alertCode = null
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailSignIn(
    idText: String,
    pwText: String,
    changeIdText: (String) -> Unit = {},
    changePwText: (String) -> Unit = {},
    signInBtnOnClick: () -> Unit = {}
) {
    IdTextField(idText = idText, changeIdText = changeIdText)

    Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_default)))

    PasswordTextField(pwText = pwText, changePwText = changePwText)

    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default)))

    Button(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
        onClick = { signInBtnOnClick() }
    ) {
        Text(text = stringResource(id = R.string.str_go_signIn), color = Color.White)
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(backgroundColor = 0x00ffffff, showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}