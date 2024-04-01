package com.grusie.presentation.uiState

import com.grusie.domain.model.UserInfo

sealed class SplashUiState {
    object Init : SplashUiState()
    object Loading : SplashUiState()
    data class SuccessSignIn(val userInfo: UserInfo?) : SplashUiState()
}