package com.grusie.presentation.screen.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.presentation.R
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.viewmodel.LoginViewModel

/**
 * 로그인 스크린 (로그인 or 회원가입 이동)
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val idText = viewModel.idText.collectAsState()
    val pwText = viewModel.pwText.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            AuthTopBar(title = stringResource(id = R.string.str_login), goToBack = { navController.popBackStack() })
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
            EmailLogin(
                idText = idText.value,
                pwText = pwText.value,
                changeIdText = { viewModel.changeIdText(it) },
                changePwText = { viewModel.changePwText(it) },
                loginBtnOnClick = { viewModel.emailLogin() },
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailLogin(
    idText: String,
    pwText: String,
    changeIdText: (String) -> Unit = {},
    changePwText: (String) -> Unit = {},
    loginBtnOnClick: () -> Unit ={}
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
        onClick = { loginBtnOnClick() }
    ) {
        Text(text = stringResource(id = R.string.str_go_login), color = Color.White)
    }
}

@Preview(backgroundColor = 0x00ffffff, showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}