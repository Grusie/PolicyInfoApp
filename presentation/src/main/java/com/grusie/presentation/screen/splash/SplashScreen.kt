package com.grusie.presentation.screen.splash

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.grusie.presentation.R
import com.grusie.presentation.components.Progress
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.uiState.SplashUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.SplashViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val splashUiState = viewModel.splashUiState.collectAsState().value
    val context = LocalContext.current

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = R.raw.splash_anim)
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f
        )
    }

    Scaffold(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Log.d("confirm splashUiState : ", "$splashUiState")
            when (splashUiState) {
                is SplashUiState.Loading -> {
                    Progress()
                }

                is SplashUiState.Error -> {
                    Toast.makeText(
                        context,
                        TextUtils.getErrorMsg(context, error = splashUiState.error),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(Screen.Home.route)
                }

                is SplashUiState.SuccessSignIn -> {
                    if (lottieAnimatable.isAtEnd) {
                        if(splashUiState.localAuth != null) {
                            Toast.makeText(
                                context,
                                stringResource(id = R.string.str_success_signIn),
                                Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                navController.navigate(Screen.Home.route)
                            }, Toast.LENGTH_SHORT.toLong())
                        } else {
                            navController.navigate(Screen.Home.route)
                        }
                    }
                }

                else -> {
                    if (lottieAnimatable.isAtEnd) navController.navigate(Screen.Home.route)
                }
            }
        }
    }
}