package com.grusie.presentation.screen.auth

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.grusie.presentation.R
import com.grusie.presentation.components.BackBtnTopBar
import com.grusie.presentation.components.Progress
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.ui.theme.Blue500
import com.grusie.presentation.uiState.SignUpUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

/**
 * 회원가입 스크린
 **/
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BackBtnTopBar(
                title = stringResource(id = R.string.str_signup),
                goToBack = { navController.popBackStack() })
        }) { paddingValues ->
        val idText = viewModel.idText.collectAsState()
        val pwText = viewModel.pwText.collectAsState()
        val verifyChecked = viewModel.verifyChecked.collectAsState().value
        val signUpUiState = viewModel.signUpUiState.collectAsState().value
        val context = LocalContext.current

        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(true) {
            lifecycleOwner.lifecycleScope.launch {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.isVerified()
                }
            }
        }

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
            EmailSignUp(
                idText = idText.value,
                pwText = pwText.value,
                changeIdText = { viewModel.changeIdText(it) },
                changePwText = { viewModel.changePwText(it) },
                authBtnOnClick = { viewModel.sendEmail() },
                signUpBtnOnClick = { viewModel.signUpEmail() },
                verifyMsgFlag = verifyChecked != null,
                verifyChecked = verifyChecked ?: false,
                emailRetry = { viewModel.changeVerifyChecked(null) }
            )

            when (signUpUiState) {
                is SignUpUiState.Loading -> {
                    Progress()
                }

                is SignUpUiState.SuccessSendEmail -> {

                }

                is SignUpUiState.SuccessSignUp -> {
                    navController.popBackStack()
                }

                is SignUpUiState.Error -> {
                    SingleAlertDialog(
                        confirm = false,
                        title = stringResource(id = R.string.str_title_error),
                        content = TextUtils.getErrorMsg(
                            context,
                            signUpUiState.error
                        )
                    )
                }

                is SignUpUiState.Alert -> {
                    SingleAlertDialog(
                        title = stringResource(id = R.string.str_title_error),
                        content = TextUtils.getAlertMsg(context, signUpUiState.alert)
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun EmailSignUp(
    idText: String,
    pwText: String,
    changeIdText: (String) -> Unit = {},
    changePwText: (String) -> Unit = {},
    authBtnOnClick: () -> Unit = {},
    signUpBtnOnClick: () -> Unit = {},
    verifyMsgFlag: Boolean = false,
    verifyChecked: Boolean = false,
    emailRetry: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        IdTextField(
            idText = idText,
            changeIdText = changeIdText,
            verifyFlag = true,
            enabled = !verifyMsgFlag,
            verifyOnClick = { authBtnOnClick() }
        )

        if (verifyMsgFlag) {
            Text(
                text = if (verifyChecked) stringResource(id = R.string.str_verified_checked) else stringResource(
                    id = R.string.str_send_email
                ), fontSize = 12.sp,
                color = if (verifyChecked) Blue500 else Color.Gray
            )
        }

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_default)))

        PasswordTextField(pwText = pwText, changePwText = changePwText)

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default)))

        Button(
            onClick = { signUpBtnOnClick() },
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            interactionSource = interactionSource
        ) {
            Text(text = stringResource(id = R.string.str_signup), color = Color.White)
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.str_email_retry),
                modifier = Modifier
                    .clickable {
                        emailRetry()
                    }
                    .align(Alignment.Center),
                color = Color.Gray,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(backgroundColor = 0x00ffffff, showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun SignUpScreenPreview() {
    Scaffold(
        topBar = {
            BackBtnTopBar(
                title = stringResource(id = R.string.str_signup),
                goToBack = { })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    horizontal = dimensionResource(
                        id = R.dimen.margin_large
                    )
                )
                .fillMaxSize()
        ) {
            EmailSignUp(
                idText = "이메일",
                pwText = "비밀번호",
            )
        }
    }
}