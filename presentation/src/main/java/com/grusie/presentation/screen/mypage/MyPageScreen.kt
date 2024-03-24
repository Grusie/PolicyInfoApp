package com.grusie.presentation.screen.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.viewmodel.MyPageViewModel

/**
 * 마이페이지 스크린
 * - 로그인 및 인증
 * - 기타 공지사항, 앱 버전 등 표시
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(it)) {
            val userInfo = viewModel.userInfo.collectAsState()

            LaunchedEffect(Unit){
                viewModel.getUserInfo()
            }

            MyProfile(
                modifier = Modifier.fillMaxWidth(),
                userInfo = userInfo.value,
                goToManageAuthScreen = { navController.navigate(route = Screen.ManageAuth.route)},
                goToAuthScreen = { navController.navigate(route = Screen.SignIn.route) }
            )
        }
    }
}

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
