package com.grusie.presentation.uiState

sealed class SplashUiState {
    object Empty : SplashUiState()
    object Loading : SplashUiState()
    object SuccessSignIn : SplashUiState()
    data class Alert(val alert: Int) : SplashUiState()
    data class Error(val error: Exception) : SplashUiState()
}