package com.grusie.presentation.screen.mypage

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.domain.model.UserInfo
import com.grusie.presentation.R
import com.grusie.presentation.components.Progress
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.eventState.MyPageEventState
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.uiState.MyPageUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.MyPageViewModel

/**
 * 마이페이지 스크린
 * - 로그인 및 인증
 * - 기타 공지사항, 앱 버전 등 표시
 **/
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier.fillMaxSize()) {
        val myPageUiState = viewModel.myPageUiState.collectAsState().value
        val context = LocalContext.current
        var alertCode: Int? = null
        var errorCode: Exception? = null
        var userInfo: UserInfo? = null

        LaunchedEffect(Unit) {
            viewModel.getUserInfo()
            viewModel.myPageEventState.collect { myPageEventState ->
                when (myPageEventState) {
                    is MyPageEventState.Alert -> {
                        alertCode = myPageEventState.alert
                    }

                    is MyPageEventState.Error -> {
                        errorCode = myPageEventState.error
                    }

                    else -> {

                    }
                }
            }
        }

        Box(modifier = Modifier.padding(it)) {
            Column() {

                when (myPageUiState) {
                    is MyPageUiState.Loading -> {
                        Progress()
                    }

                    is MyPageUiState.SuccessLogIn -> {
                        userInfo = myPageUiState.data
                    }

                    else -> {

                    }
                }

                MyProfile(
                    modifier = Modifier.fillMaxWidth(),
                    userInfo = userInfo,
                    goToManageAuthScreen = { navController.navigate(route = Screen.ManageAuth.route) },
                    goToAuthScreen = { navController.navigate(route = Screen.SignIn.route) }
                )
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

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
@Preview(
    name = "MyPageScreenPreview",
    heightDp = 1000,
    backgroundColor = 0x00fffff,
    showBackground = true
)
fun MyPageScreenPreview() {
    MyPageScreen(navController = rememberNavController())
}
