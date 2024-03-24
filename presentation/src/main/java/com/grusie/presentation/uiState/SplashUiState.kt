package com.grusie.presentation.uiState

import com.grusie.domain.model.LocalAuth

sealed class SplashUiState {
    object Empty : SplashUiState()
    object Loading : SplashUiState()
    data class SuccessSignIn(val localAuth: LocalAuth?) : SplashUiState()
    data class Alert(val alert: Int) : SplashUiState()
    data class Error(val error: Exception) : SplashUiState()
}